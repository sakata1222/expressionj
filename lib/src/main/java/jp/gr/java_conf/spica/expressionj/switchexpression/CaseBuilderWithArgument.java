package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.function.Function;

/**
 * Builder of the Case.
 *
 * @param <I> type of the evaluation target
 */
public interface CaseBuilderWithArgument<I, C extends I> {

  /**
   * build a case with specified value function.
   *
   * @param yield a value supplier
   * @param <V>   a type of the return value
   * @return case
   */
  <V> Case<I, V> yield(Function<C, V> yield);
}
