package jp.gr.java_conf.spica.expressionj.ifexpression;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

class TrueThen<V> implements Then<V> {

  private final Supplier<V> valueSupplier;

  TrueThen(Supplier<V> valueSupplier) {
    this.valueSupplier = valueSupplier;
  }

  @Override
  public V elseEx(Supplier<V> elseValueSupplier) {
    return valueSupplier.get();
  }

  @Override
  public ElseIfStatement<V> elseIf(BooleanSupplier condition) {
    return new TrueElseIf<>(valueSupplier);
  }
}
