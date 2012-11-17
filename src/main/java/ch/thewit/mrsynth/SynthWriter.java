package ch.thewit.mrsynth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.Note;
import ch.thewit.mrsynth.model.NoteDefinition;
import ch.thewit.mrsynth.model.Envelope;
import ch.thewit.mrsynth.model.Waveform;
import ch.thewit.mrsynth.synth.Synthesiser;

public class SynthWriter {

  public static void main(String[] args) throws IOException {
    FileOutputStream os = new FileOutputStream(new File("/tmp/synth_output"));
    NoteDefinition noteDefinition = new NoteDefinition(Note.A, 0, new Envelope(0, 4000, 100000, 10000),
        Waveform.PARTIAL_SAW);
    Synthesiser synth = new Synthesiser(44100);
    for (IndexedSample sample : synth.synthesise(noteDefinition)) {
      int sampleValue = (int) (sample.getSample() * Integer.MAX_VALUE * 0.1);
      byte[] sampleBytes = ByteBuffer.allocate(4).putInt(sampleValue).array();
      os.write(sampleBytes);
    }
    os.close();
  }
}
