package ch.thewit.mrsynth.synth;

import cascading.tuple.Tuple;
import ch.thewit.mrsynth.model.Envelope;
import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.Note;
import ch.thewit.mrsynth.model.NoteDefinition;
import ch.thewit.mrsynth.model.Waveform;

public class Conversions {
  public static NoteDefinition tupleToNoteDefinition(Tuple tuple) {
    Note note = Note.parse(tuple.getString(0));

    int octave = Integer.valueOf(tuple.getString(1));
    int startPos = Integer.valueOf(tuple.getString(2));
    int startFade = Integer.valueOf(tuple.getString(3));
    int endPos = Integer.valueOf(tuple.getString(4));
    int endFade = Integer.valueOf(tuple.getString(5));
    Envelope envelope = new Envelope(startPos, startFade, endPos, endFade);

    Waveform waveform = Waveform.valueOf(tuple.getString(6));

    double detune = Double.valueOf(tuple.getString(7));
    double volume = Double.valueOf(tuple.getString(8));
    return new NoteDefinition(note, octave, envelope, waveform, detune, volume);
  }

  public static Tuple indexedSampleToTuple(IndexedSample sample) {
    return new Tuple(sample.getIndex(), sample.getSample());
  }
}
