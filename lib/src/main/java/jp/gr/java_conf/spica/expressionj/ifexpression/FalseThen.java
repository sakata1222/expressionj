package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

class FalseThen<V> implements Then<V> {

  @Override
  public V elseExp(Supplier<V> elseValueSupplier) {
    return Validations.requireNonNullValueSupplier(elseValueSupplier).get();
  }

  @Override
  @SuppressWarnings("unchecked")
  public ElseIfStatement<V> elseIf(BooleanSupplier condition) {
    if (Validations.requireNonNullCondition(condition).getAsBoolean()) {
      return (ElseIfStatement<V>) InstanceCaches.ELSE_IF_TURE;
    } else {
      return (ElseIfStatement<V>) InstanceCaches.ELSE_IF_FALSE;
    }
  }

  @Override
  public Optional<V> end() {
    return Optional.empty();
  }
}
