package ch.thewit.mrsynth;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.aggregator.Sum;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import ch.thewit.mrsynth.output.SampleConverter;
import ch.thewit.mrsynth.synth.SynthFunction;
import fm.last.commons.hadoop.io.HadoopDirReader;

public class MrSynth {

  private static final Fields notationFields = new Fields("note", "octave", "startPos", "startFade", "endPos",
      "endFade", "waveform", "detune", "volume");
  private static final Fields sampleFields = new Fields("index", "sample");

  public static void main(String[] args) throws IOException {
    String input = "/home/davidaw/mrsynth/input.txt";
    String intermediate = "/home/davidaw/mrsynth/output.txt";

    mapReduceSynth(input, intermediate);
    Reader hadoopReader = new HadoopDirReader.Builder(new Path(intermediate)).deleteOnClose().build();

    new SampleConverter(hadoopReader).convert(new File("/home/davidaw/mrsynth/output.raw"));
  }

  private static void mapReduceSynth(String notationFile, String temporaryFile) {
    FlowDef flowDef = new FlowDef();

    Pipe notation = new Pipe("notation");
    flowDef.addSource(notation, new Hfs(new TextDelimited(notationFields, "\t"), notationFile));

    Pipe synth = new Each(notation, Fields.ALL, new SynthFunction(), Fields.SWAP);
    // Pipe summing = new SumBy(synth, new Fields("index"), new Fields("sample"), new Fields("sampleSum"), long.class);
    Pipe summing = new GroupBy(synth, new Fields("index"));
    summing = new Every(summing, new Fields("sample"), new Sum(new Fields("sample"), double.class), Fields.ALL);

    flowDef.addTailSink(summing, new Hfs(new TextDelimited(sampleFields, "\t"), temporaryFile));

    HadoopFlowConnector flowConnector = new HadoopFlowConnector();
    Flow<?> flow = flowConnector.connect(flowDef);

    flow.complete();
  }
}
