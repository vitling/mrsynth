package ch.thewit.mrsynth.model;

public class NoteDefinition {
  private final Note note;
  private final int octave;
  private final Envelope envelope;
  private final Waveform waveform;
  private final double detune;

  public NoteDefinition(Note note, int octave, Envelope envelope, Waveform waveform) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
    detune = 0;
  }

  public NoteDefinition(Note note, int octave, Envelope envelope, Waveform waveform, double detune) {
    super();
    this.note = note;
    this.octave = octave;
    this.envelope = envelope;
    this.waveform = waveform;
    this.detune = detune;
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

}
