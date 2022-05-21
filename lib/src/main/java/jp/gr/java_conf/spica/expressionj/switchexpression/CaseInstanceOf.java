package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.function.Function;

class CaseInstanceOf<I, C extends I, V> implements Case<I, V> {

  private final Class<C> clazz;
  private final Function<C, V> yield;
  private I evaluationTarget;

  CaseInstanceOf(Class<C> clazz, Function<C, V> yield) {
    this.clazz = clazz;
    this.yield = yield;
  }

  @Override
  public boolean matches(I evaluationTarget) {
    this.evaluationTarget = evaluationTarget;
    return evaluationTarget.getClass().isAssignableFrom(clazz);
  }

  @Override
  public V value() {
    C cast = clazz.cast(evaluationTarget);
    return yield.apply(cast);
  }

  static <I, C extends I> CaseInstanceOf.Builder<I, C> builder(Class<C> clazz) {
    return new Builder<>(clazz);
  }

  static class Builder<I, C extends I> implements CaseBuilderWithArgument<I, C> {

    private final Class<C> clazz;

    Builder(Class<C> clazz) {
      this.clazz = clazz;
    }

    @Override
    public <V> Case<I, V> yield(Function<C, V> yield) {
      return new CaseInstanceOf<>(clazz, yield);
    }
  }
}
