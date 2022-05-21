package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.Objects;
import java.util.function.Supplier;

class CaseEq<I, V> implements Case<I, V> {

  private final I condition;
  private final Supplier<V> valueSupplier;

  CaseEq(I condition, Supplier<V> valueSupplier) {
    this.condition = condition;
    this.valueSupplier = valueSupplier;
  }

  @Override
  public boolean matches(I evaluationTarget) {
    return Objects.equals(condition, evaluationTarget);
  }

  @Override
  public V value() {
    return valueSupplier.get();
  }

  static <I> CaseEq.Builder<I> builder(I condition) {
    return new CaseEq.Builder<>(condition);
  }

  static class Builder<I> implements CaseBuilder<I> {

    private final I condition;

    Builder(I condition) {
      this.condition = condition;
    }

    @Override
    public <V> Case<I, V> yield(Supplier<V> yield) {
      return new CaseEq<>(condition, yield);
    }
  }
}
