package ch.thewit.mrsynth.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class SampleConverter {

  private final BufferedReader reader;
  private static final Splitter TAB_SPLITTER = Splitter.on("\t");

  public SampleConverter(Reader reader) {
    this.reader = new BufferedReader(reader);
  }

  public void convert(File outputFile) throws IOException {
    FileOutputStream os = new FileOutputStream(outputFile);
    String line;
    while ((line = reader.readLine()) != null) {
      String sampleValue = Iterables.getLast(TAB_SPLITTER.split(line));
      int sampleValueInt = (int) (Double.parseDouble(sampleValue) * Integer.MAX_VALUE * 0.1);
      os.write(ByteBuffer.allocate(4).putInt(sampleValueInt).array());
    }
    os.close();
    reader.close();
  }
}
