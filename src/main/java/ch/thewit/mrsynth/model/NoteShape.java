package ch.thewit.mrsynth.model;

public class NoteShape {
  private final long startSample;
  private final long startFade;
  private final long endSample;
  private final long endFade;

  public NoteShape(long startSample, long startFade, long endSample, long endFade) {
    super();
    this.startSample = startSample;
    this.startFade = startFade;
    this.endSample = endSample;
    this.endFade = endFade;
  }

  public long getStartSample() {
    return startSample;
  }

  public long getStartFade() {
    return startFade;
  }

  public long getEndSample() {
    return endSample;
  }

  public long getEndFade() {
    return endFade;
  }

  public double getVolume(long sampleIndex) {
    if (sampleIndex < startSample || sampleIndex >= endSample) {
      return 0;
    }
    if (sampleIndex < startSample + startFade) {
      return (sampleIndex - startSample) / (double) startFade;
    }
    if (sampleIndex > endSample - endFade) {
      return (-(sampleIndex - endSample)) / (double) endFade;
    }
    return 1;
  }

}
