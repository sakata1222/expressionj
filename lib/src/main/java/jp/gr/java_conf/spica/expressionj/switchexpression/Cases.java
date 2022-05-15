package jp.gr.java_conf.spica.expressionj.switchexpression;

/**
 * Static factory of Cases.
 */
public class Cases {

  private Cases() {
  }

  /**
   * build an equal case with specified argument.
   *
   * @param value value will be checked
   * @param <I>   type of the evaluation target
   * @return a case that will be used when the specified value equals to the specified value
   */
  public static <I> CaseBuilder<I> caseEq(I value) {
    return CaseEq.builder(value);
  }

  /**
   * build an in case with specified argument.
   *
   * <p>This method is same case {@link #caseEq(Object)}
   *
   * @param i1  value
   * @param <I> type of the evaluation target
   * @return a case that will be used when the specified value equals to the specified value
   */
  public static <I> CaseBuilder<I> caseIn(I i1) {
    return CaseIn.builder(i1);
  }

  /**
   * build an in case with specified argument.
   *
   * @param i1  value1
   * @param i2  value2
   * @param <I> type of the evaluation target
   * @return a case that will be used when the specified value equals to any of the specified value
   */
  public static <I> CaseBuilder<I> caseIn(I i1, I i2) {
    return CaseIn.builder(i1, i2);
  }

  /**
   * build an in case with specified argument.
   *
   * @param i1  value1
   * @param i2  value2
   * @param i3  value3
   * @param <I> type of the evaluation target
   * @return a case that will be used when the specified value equals to any of the specified value
   */
  public static <I> CaseBuilder<I> caseIn(I i1, I i2, I i3) {
    return CaseIn.builder(i1, i2, i3);
  }

  /**
   * build an in case with specified argument.
   *
   * @param i1  value1
   * @param i2  value2
   * @param i3  value3
   * @param i4  value4
   * @param <I> type of the evaluation target
   * @return a case that will be used when the specified value equals to any of the specified value
   */
  public static <I> CaseBuilder<I> caseIn(I i1, I i2, I i3, I i4) {
    return CaseIn.builder(i1, i2, i3, i4);
  }

  /**
   * build an in case with specified argument.
   *
   * @param values value1
   * @param <I>    type of the evaluation target
   * @return a case that will be used when the specified value equals to any of the specified value
   */
  @SuppressWarnings("varargs")
  @SafeVarargs
  public static <I> CaseBuilder<I> caseIn(I... values) {
    return CaseIn.builder(values);
  }

  /**
   * build a default case.
   *
   * @param <I> type of the evaluation target
   * @return a case that will be used always
   */
  public static <I> CaseBuilder<I> caseDefault() {
    return CaseDefault.builder();
  }
}
