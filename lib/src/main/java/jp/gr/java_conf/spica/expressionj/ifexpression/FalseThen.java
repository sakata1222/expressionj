package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

class FalseThen<V> implements Then<V> {

  private static final ElseIfTrue<?> ELSE_IF_TURE = new ElseIfTrue<>();
  private static final ElseIfFalse<?> ELSE_IF_FALSE = new ElseIfFalse<>();

  @Override
  public V elseExp(Supplier<V> elseValueSupplier) {
    return Validations.requireNonNullValueSupplier(elseValueSupplier).get();
  }

  @Override
  @SuppressWarnings("unchecked")
  public ElseIfStatement<V> elseIf(BooleanSupplier condition) {
    if (Validations.requireNonNullCondition(condition).getAsBoolean()) {
      return (ElseIfStatement<V>) ELSE_IF_TURE;
    } else {
      return (ElseIfStatement<V>) ELSE_IF_FALSE;
    }
  }

  @Override
  public Optional<V> end() {
    return Optional.empty();
  }
}
