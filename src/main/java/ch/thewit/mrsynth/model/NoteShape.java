package ch.thewit.mrsynth.model;

public class NoteShape {
  private final int startSample;
  private final int startFade;
  private final int endSample;
  private final int endFade;

  public NoteShape(int startSample, int startFade, int endSample, int endFade) {
    super();
    this.startSample = startSample;
    this.startFade = startFade;
    this.endSample = endSample;
    this.endFade = endFade;
  }

  public int getStartSample() {
    return startSample;
  }

  public int getStartFade() {
    return startFade;
  }

  public int getEndSample() {
    return endSample;
  }

  public int getEndFade() {
    return endFade;
  }

  public double getVolume(int sampleIndex) {
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
