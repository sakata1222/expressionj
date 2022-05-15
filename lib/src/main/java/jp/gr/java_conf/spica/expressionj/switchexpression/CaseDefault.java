package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.function.Supplier;

class CaseDefault<I, V> implements Case<I, V> {

  private final Supplier<V> yield;

  CaseDefault(Supplier<V> yield) {
    this.yield = yield;
  }

  @Override
  public boolean matches(I evaluationTarget) {
    return true;
  }

  @Override
  public V value() {
    return yield.get();
  }

  static <I> CaseDefault.Builder<I> builder() {
    return new CaseDefault.Builder<>();
  }

  static class Builder<I> implements CaseBuilder<I> {

    @Override
    public <V> Case<I, V> yield(Supplier<V> yield) {
      return new CaseDefault<>(yield);
    }
  }

}
