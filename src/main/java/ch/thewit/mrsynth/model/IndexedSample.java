package ch.thewit.mrsynth.model;

public class IndexedSample {
  private final long index;
  private final double sample;

  public IndexedSample(long index, double sample) {
    this.index = index;
    this.sample = sample;
  }

  public long getIndex() {
    return index;
  }

  public double getSample() {
    return sample;
  }

  @Override
  public String toString() {
    return "IndexedSample [index=" + index + ", sample=" + sample + "]";
  }

}
