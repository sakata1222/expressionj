package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.Supplier;

class TrueElseIf<V> implements ElseIfStatement<V> {

  private final Supplier<V> valueSupplier;

  TrueElseIf(Supplier<V> valueSupplier) {
    Validations.requireNonNullValueSupplier(valueSupplier);
    this.valueSupplier = valueSupplier;
  }

  @Override
  public Then<V> then(Supplier<V> valueSupplier) {
    return new TrueThen<>(this.valueSupplier);
  }
}
