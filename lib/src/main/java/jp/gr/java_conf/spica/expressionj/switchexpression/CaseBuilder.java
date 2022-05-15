package jp.gr.java_conf.spica.expressionj.switchexpression;

import java.util.function.Supplier;

/**
 * Builder of the Case.
 *
 * @param <I> type of the evaluation target
 */
public interface CaseBuilder<I> {

  /**
   * build a case with specified value supplier.
   *
   * @param yield a value supplier
   * @param <V>   a type of the return value
   * @return case
   */
  <V> Case<I, V> yield(Supplier<V> yield);
}
