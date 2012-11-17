package ch.thewit.mrsynth.model;

public class NoteDefinition {
  private final Note note;
  private final int octave;
  private final Envelope envelope;
  private final Waveform waveform;
  private final double detune;
  private final double volume;
  private final double pan;

  public NoteDefinition(Note note, int octave, Envelope envelope, Waveform waveform) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
    detune = 0;
    volume = 1;
    pan = 0.5;
  }

  public NoteDefinition(Note note, int octave, Envelope envelope, Waveform waveform, double detune, double volume,
      double pan) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
    this.detune = detune;
    this.volume = volume;
    this.pan = pan;
  }

  public double getFrequency() {
    return note.getPitch(octave) * (detune + 1);
  }

  public Envelope getEnvelope() {
    return envelope;
  }

  public Waveform getWaveform() {
    return waveform;
  }

  public long getLength() {
    return envelope.getEndSample() - envelope.getStartSample();
  }

  public double getVolume() {
    return volume;
  }

  public double getPan() {
    return pan;
  }
}
