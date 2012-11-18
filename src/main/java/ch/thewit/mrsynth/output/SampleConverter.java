package ch.thewit.mrsynth.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
      List<String> sampleValues = Lists.newArrayList(Iterables.skip(TAB_SPLITTER.split(line), 1));
      int sampleValueShortL = (int) (Double.parseDouble(sampleValues.get(0)) * Integer.MAX_VALUE * 0.1);
      int sampleValueShortR = (int) (Double.parseDouble(sampleValues.get(1)) * Integer.MAX_VALUE * 0.1);
      os.write(ByteBuffer.allocate(4).putInt(sampleValueShortL).array());
      os.write(ByteBuffer.allocate(4).putInt(sampleValueShortR).array());
    }
    os.close();
    reader.close();
  }
}
