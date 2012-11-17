package ch.thewit.mrsynth.model;

public class NoteDefinition {
  private final Note note;
  private final int octave;
  private final NoteShape envelope;
  private final Waveform waveform;

  public NoteDefinition(Note note, int octave, NoteShape envelope, Waveform waveform) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
  }

  public Note getNote() {
    return note;
  }

  public int getOctave() {
    return octave;
  }

  public NoteShape getEnvelope() {
    return envelope;
  }

  public Waveform getWaveform() {
    return waveform;
  }

}
