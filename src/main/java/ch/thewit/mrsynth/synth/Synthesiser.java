package ch.thewit.mrsynth.synth;

import java.util.ArrayList;
import java.util.List;

import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.NoteDefinition;

public class Synthesiser {

  private final int sampleRate;

  public Synthesiser(int sampleRate) {
    this.sampleRate = sampleRate;
  }

  public List<IndexedSample> synthesise(NoteDefinition note) {
    List<IndexedSample> result = new ArrayList<IndexedSample>((int) note.getLength());
    double frequency = note.getNote().getPitch(note.getOctave());
    for (long i = note.getEnvelope().getStartSample(); i < note.getEnvelope().getEndSample(); i++) {
      double secondsPosition = (i - note.getEnvelope().getStartSample()) / (double) sampleRate;
      double wave = note.getWaveform().getValue(secondsPosition * frequency);
      double scaledWave = wave * note.getEnvelope().getVolume(i);
      result.add(new IndexedSample(i, scaledWave));
    }
    return result;
  }
}
