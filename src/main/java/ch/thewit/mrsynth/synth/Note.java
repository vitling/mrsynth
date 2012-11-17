package ch.thewit.mrsynth.synth;

public enum Note {
  C(0),
  CS(1),
  D(2),
  DS(3),
  E(4),
  F(5),
  FS(6),
  G(7),
  GS(8),
  A(9),
  AS(10),
  B(11);
  private final int position;

  public static double TWELFTH_ROOT_OF_TWO = Math.pow(2, 1.0 / 12.0);

  private Note(int position) {
    this.position = position;
  }

  public double getPitch(int octave) {
    return 440 * Math.pow(TWELFTH_ROOT_OF_TWO, (position - 9));
  }
}
