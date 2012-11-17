package ch.thewit.mrsynth.model;

public class IndexedSample {
  private final long index;
  private final double lsample;
  private final double rsample;

  public IndexedSample(long index, double lsample, double rsample) {
    this.index = index;
    this.lsample = lsample;
    this.rsample = rsample;
  }

  public long getIndex() {
    return index;
  }

  public double getLSample() {
    return lsample;
  }

  public double getRSample() {
    return rsample;
  }

}
