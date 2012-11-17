package ch.thewit.mrsynth.synth;

import java.util.List;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import ch.thewit.mrsynth.model.Envelope;
import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.Note;
import ch.thewit.mrsynth.model.NoteDefinition;
import ch.thewit.mrsynth.model.Waveform;

public class SynthFunction extends BaseOperation<Object> implements Function<Object> {

  private static final long serialVersionUID = -2472192768571802906L;
  private final Synthesiser synthesiser;

  public SynthFunction() {
    super(8, new Fields("index", "sample"));
    synthesiser = new Synthesiser(44100);
  }

  @Override
  public void operate(FlowProcess arg0, FunctionCall<Object> functionCall) {
    NoteDefinition noteDef = tupleToNoteDefinition(functionCall.getArguments().getTuple());
    List<IndexedSample> samples = synthesiser.synthesise(noteDef);

    for (IndexedSample sample : samples) {
      functionCall.getOutputCollector().add(indexedSampleToTuple(sample));
    }
  }

  NoteDefinition tupleToNoteDefinition(Tuple tuple) {
    Note note = Note.parse(tuple.getString(0));

    int octave = Integer.valueOf(tuple.getString(1));
    int startPos = Integer.valueOf(tuple.getString(2));
    int startFade = Integer.valueOf(tuple.getString(3));
    int endPos = Integer.valueOf(tuple.getString(4));
    int endFade = Integer.valueOf(tuple.getString(5));
    Envelope envelope = new Envelope(startPos, startFade, endPos, endFade);

    Waveform waveform = Waveform.valueOf(tuple.getString(6));

    double detune = Double.valueOf(tuple.getString(7));
    return new NoteDefinition(note, octave, envelope, waveform, detune);
  }

  Tuple indexedSampleToTuple(IndexedSample sample) {
    return new Tuple(sample.getIndex(), sample.getSample());
  }
}
