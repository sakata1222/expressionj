package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

class CaseIn<I, V> implements Case<I, V> {

  private final Set<I> condition;
  private final Supplier<V> yield;

  CaseIn(Set<I> condition, Supplier<V> yield) {
    this.condition = condition;
    this.yield = yield;
  }

  @Override
  public boolean matches(I evaluationTarget) {
    return condition.contains(evaluationTarget);
  }

  @Override
  public V value() {
    return yield.get();
  }

  @SuppressWarnings("varargs")
  @SafeVarargs
  static <I> Builder<I> builder(I... conditions) {
    return new Builder<>(new HashSet<>(Arrays.asList(conditions)));
  }

  static class Builder<I> implements CaseBuilder<I> {

    private final Set<I> condition;

    Builder(Set<I> condition) {
      this.condition = condition;
    }

    @Override
    public <V> Case<I, V> yield(Supplier<V> yield) {
      return new CaseIn<>(condition, yield);
    }
  }
}
