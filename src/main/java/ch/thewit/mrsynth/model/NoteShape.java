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

}
