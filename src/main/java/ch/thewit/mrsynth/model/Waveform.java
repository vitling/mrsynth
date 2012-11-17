package ch.thewit.mrsynth.model;

public enum Waveform {
  SINE {
    @Override
    public double getValueFixedRange(double position) {
      return Math.sin(position * Math.PI * 2);
    }
  },
  PARTIAL_SAW {
    @Override
    public double getValueFixedRange(double position) {
      double v = 0;
      for (int i = 1; i < 11; i++) {
        v += Math.sin(position * Math.PI * 2 * i) / i;
      }
      return v;
    }
  },
  SQUARE {
    @Override
    public double getValueFixedRange(double position) {
      return position < 0.5 ? -1 : 1;
    }
  };

  public abstract double getValueFixedRange(double position);

  public double getValue(double position) {
    return getValueFixedRange(position - Math.floor(position));
  }
}
