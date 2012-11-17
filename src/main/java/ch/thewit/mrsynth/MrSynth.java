package ch.thewit.mrsynth;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import ch.thewit.mrsynth.synth.SynthFunction;

public class MrSynth {

  private static final Fields notationFields = new Fields("note", "octave", "startPos", "startFade", "endPos",
      "endFade", "waveform", "detune");
  private static final Fields sampleFields = new Fields("index", "sample");

  public static void main(String[] args) {
    FlowDef flowDef = new FlowDef();

    Pipe notation = new Pipe("notation");
    flowDef.addSource(notation, new Hfs(new TextDelimited(notationFields, "\t"), "/home/davidaw/mrsynth/input.txt"));

    Pipe synth = new Each(notation, Fields.ALL, new SynthFunction(), Fields.SWAP);

    flowDef.addTailSink(synth, new Hfs(new TextDelimited(sampleFields, "\t"), "/home/davidaw/mrsynth/output.txt"));

    HadoopFlowConnector flowConnector = new HadoopFlowConnector();
    Flow<?> flow = flowConnector.connect(flowDef);

    flow.complete();
  }
}
