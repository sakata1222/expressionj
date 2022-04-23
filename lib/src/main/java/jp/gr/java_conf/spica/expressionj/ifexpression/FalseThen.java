package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

class FalseThen<V> implements Then<V> {

  private static final ElseIfTrue<?> ELSE_IF_TURE = new ElseIfTrue<>();
  private static final ElseIfFalse<?> ELSE_IF_FALSE = new ElseIfFalse<>();

  @Override
  public V elseEx(Supplier<V> elseValueSupplier) {
    return Objects.requireNonNull(elseValueSupplier,
            "value supplier should not be null. If you want to use null as a return value of if expression, specify () -> null")
        .get();
  }

  @Override
  @SuppressWarnings("unchecked")
  public ElseIfStatement<V> elseIf(BooleanSupplier condition) {
    if (condition.getAsBoolean()) {
      return (ElseIfStatement<V>) ELSE_IF_TURE;
    } else {
      return (ElseIfStatement<V>) ELSE_IF_FALSE;
    }
  }
}
