package ch.thewit.mrsynth.model;

public class NoteDefinition {
  private final Note note;
  private final int octave;
  private final Envelope envelope;
  private final Waveform waveform;
  private final double detune;
  private final double volume;

  public NoteDefinition(Note note, int octave, Envelope envelope, Waveform waveform) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
    detune = 0;
    volume = 1;
  }

  public NoteDefinition(Note note, int octave, Envelope envelope, Waveform waveform, double detune, double volume) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
    this.detune = detune;
    this.volume = volume;
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

}
