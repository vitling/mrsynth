package ch.thewit.mrsynth.synth;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cascading.tuple.Tuple;
import ch.thewit.mrsynth.model.NoteDefinition;

public class SynthFunctionTest {

  @Test
  public void testTupleToNoteDefinition() {
    Tuple tuple = new Tuple("A", "0", "0", "10000", "100000", "10000", "PARTIAL_SAW", "0.0");
    SynthFunction f = new SynthFunction();

    NoteDefinition nd = f.tupleToNoteDefinition(tuple);

    assertEquals(440, nd.getFrequency(), 0.001);
  }

}
