package ch.thewit.mrsynth;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.pipe.Checkpoint;
import cascading.pipe.Each;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.AggregateBy;
import cascading.pipe.assembly.SumBy;
import cascading.property.AppProps;
import cascading.scheme.Scheme;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import ch.thewit.mrsynth.massive.Massifier;
import ch.thewit.mrsynth.output.SampleConverter;
import ch.thewit.mrsynth.synth.SynthFunction;
import fm.last.commons.hadoop.io.HadoopDirReader;

public class MrSynth {

  private static final Fields notationFields = new Fields("note", "octave", "startPos", "startFade", "endPos",
      "endFade", "waveform", "detune", "volume", "pan");
  private static final Fields sampleFields = new Fields("index", "samplel", "sampler");

  public static void main(String[] args) throws IOException {
    String input = args[0];
    int maxFactor = Integer.parseInt(args[2]);
    String output = args[1];
    String intermediate = "/user/davidaw/mrsynth/intermediate";

    mapReduceSynth(input, intermediate, maxFactor);
    Reader hadoopReader = new HadoopDirReader.Builder(new Path(intermediate)).deleteOnClose().build();

    new SampleConverter(hadoopReader).convert(new File(output));
  }

  private static void mapReduceSynth(String notationFile, String temporaryFile, int maxFactor) {
    FlowDef flowDef = new FlowDef();

    Pipe notation = new Pipe("notation");
    flowDef.addSource(notation, new Hfs(new TextDelimited(notationFields, "\t"), notationFile));
    Pipe massiveNotation = new Each(notation, Fields.ALL, new Massifier(maxFactor), Fields.SWAP);
    massiveNotation = new Checkpoint(massiveNotation);
    Each synth = new Each(massiveNotation, Fields.ALL, new SynthFunction(), Fields.SWAP);
    synth.getStepConfigDef().setProperty("mapred.map.tasks", "1000");
    synth.getStepConfigDef().setProperty("mapred.min.split.size", "2048");
    SumBy sumLeft = new SumBy(new Fields("samplel"), new Fields("samplel_sum"), double.class);
    SumBy sumRight = new SumBy(new Fields("sampler"), new Fields("sampler_sum"), double.class);

    AggregateBy summing = new AggregateBy(synth, new Fields("index"), sumLeft, sumRight);
    Pipe checkpoint = new Checkpoint(summing);

    // Pipe summing = new GroupBy(synth, new Fields("index"));
    // summing = new Every(summing, new Fields("samplel"), new Sum(new Fields("samplel_sum"), double.class),
    // Fields.ALL);
    // summing = new Every(summing, new Fields("sampler"), new Sum(new Fields("sampler_sum"), double.class),
    // Fields.ALL);

    Pipe finalSort = new GroupBy(checkpoint, new Fields("index"));

    Scheme outputScheme = new TextDelimited(new Fields("index", "samplel_sum", "sampler_sum"), "\t");
    outputScheme.setNumSinkParts(1);
    flowDef.addTailSink(finalSort, new Hfs(outputScheme,
        temporaryFile));

    Properties properties = new Properties();
    AppProps.setApplicationJarClass(properties, MrSynth.class);
    properties.put("mapred.max.split.size", 1024);
    HadoopFlowConnector flowConnector = new HadoopFlowConnector(properties);
    Flow<?> flow = flowConnector.connect(flowDef);

    flow.complete();
  }
}
