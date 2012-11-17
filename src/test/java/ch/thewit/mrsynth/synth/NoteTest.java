package ch.thewit.mrsynth.synth;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.thewit.mrsynth.model.Note;

public class NoteTest {

  @Test
  public void testGetPitch() {
    assertEquals(440.0, Note.A.getPitch(0), 0.1);
    assertEquals(220.0, Note.A.getPitch(-1), 0.1);
    assertEquals(880.0, Note.A.getPitch(1), 0.1);

  }

}
