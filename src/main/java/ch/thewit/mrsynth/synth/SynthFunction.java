package ch.thewit.mrsynth.synth;

import java.util.List;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.NoteDefinition;

public class SynthFunction extends BaseOperation<Object> implements Function<Object> {

  private static final long serialVersionUID = -2472192768571802906L;
  private final Synthesiser synthesiser;

  public SynthFunction() {
    super(8, new Fields("index", "sample"));
    synthesiser = new Synthesiser(44100);
  }

  @Override
  public void operate(FlowProcess arg0, FunctionCall<Object> functionCall) {
    NoteDefinition noteDef = Conversions.tupleToNoteDefinition(functionCall.getArguments().getTuple());
    List<IndexedSample> samples = synthesiser.synthesise(noteDef);

    for (IndexedSample sample : samples) {
      functionCall.getOutputCollector().add(Conversions.indexedSampleToTuple(sample));
    }
  }

}
