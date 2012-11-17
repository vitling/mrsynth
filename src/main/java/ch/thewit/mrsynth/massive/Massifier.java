package ch.thewit.mrsynth.massive;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;

import com.google.common.base.Joiner;

public class Massifier extends BaseOperation<Object> implements Function<Object> {

  private final int maxFactor;

  public Massifier(int maxFactor) {
    super(9, Fields.ARGS);
    this.maxFactor = maxFactor;
  }

  @Override
  public void operate(FlowProcess arg0, FunctionCall<Object> func) {
    TupleEntry input = func.getArguments();
    double detune = Double.parseDouble(input.getString("detune"));
    double volume = Double.parseDouble(input.getString("volume"));
    for (int i = 0; i < maxFactor; i++) {
      TupleEntry output = new TupleEntry(input);
      output.setString("detune", Double.toString(detune + Math.random() * 0.04 - 0.02));
      output.setString("volume", Double.toString(volume / maxFactor));
      output.setString("pan", Double.toString(Math.random()));
      System.out.println(Joiner.on(",").join(output.getTuple()));
      func.getOutputCollector().add(output);
    }
  }

}
