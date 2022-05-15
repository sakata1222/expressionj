package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;

class PerformanceTest {

  private static final int LOOP_COUNT = 10000000;

  /*
   * - java.vm.vendor:Eclipse Adoptium
   * - java.vm.version:17.0.3+7
   * - os.name:Windows 10
   * - os.version:10.0
   *
   * Benchmark of FizzBuzz.
   * Loop count is 10000000.
   * Elapsed Time in ms:
   *
   * |Count|If statement with method|If Expression|
   * |---|---|---|
   * |1|201|472|
   * |2|154|419|
   * |3|146|386|
   * |4|146|396|
   * |5|139|428|
   */

  @BeforeAll
  static void beforeAll() {
    Stream.of(
            "java.vm.vendor",
            "java.vm.version",
            "os.name",
            "os.version")
        .map(key -> "- " + key + ":" + System.getProperty(key))
        .forEach(System.out::println);
    System.out.println();

    System.out.println("Benchmark of FizzBuzz.");
    System.out.println("Loop count is " + LOOP_COUNT + ".");
    System.out.println("Elapsed Time in ms:");
    System.out.println();
    System.out.println(markdownRow("Count", "If statement with method", "If Expression"));
    System.out.println(markdownRow("---", "---", "---"));
  }

  @RepeatedTest(6)
  @Tag("performance")
  void ifExpressionTest(RepetitionInfo repetitionInfo) {

    BenchmarkResult ifStatementResult = runBenchmarkOfIfStatement();
    BenchmarkResult ifExpressionResult = runBenchmarkOfIfExpression();

    System.out.println(markdownRow(
        repetitionInfo.getCurrentRepetition(),
        ifStatementResult.elapsedTime,
        ifExpressionResult.elapsedTime));

    if (repetitionInfo.getCurrentRepetition() == 1) {
      // the 1st take is just a warming up.
      return;
    }

    assertAll(
        () -> assertThat(ifStatementResult.lengthCount)
            .isEqualTo(ifExpressionResult.lengthCount)
            .isGreaterThan(LOOP_COUNT * 3),
        () -> assertThat(ifStatementResult.elapsedTime).isLessThanOrEqualTo(1000),
        () -> assertThat(ifExpressionResult.elapsedTime)
            .isLessThanOrEqualTo(1000)
            .isLessThanOrEqualTo(ifStatementResult.elapsedTime * 4));
  }

  private BenchmarkResult runBenchmarkOfIfStatement() {
    StopWatch stopWatch = StopWatch.createStarted();
    LengthCounter counter = new LengthCounter();
    IntStream.rangeClosed(1, LOOP_COUNT)
        .mapToObj(this::fizzBuzz)
        .forEach(counter);
    stopWatch.stop();
    return new BenchmarkResult(
        stopWatch,
        counter);
  }

  private String fizzBuzz(int i) {
    if (i % 15 == 0) {
      return "FizzBuzz";
    } else if (i % 3 == 0) {
      return "Fizz";
    } else if (i % 5 == 0) {
      return "Buzz";
    } else {
      return String.valueOf(i);
    }
  }

  private BenchmarkResult runBenchmarkOfIfExpression() {
    StopWatch stopWatch = StopWatch.createStarted();
    LengthCounter counter = new LengthCounter();
    IntStream.rangeClosed(1, LOOP_COUNT)
        .mapToObj(i -> ifExp(i % 15 == 0).then(() -> "FizzBuzz")
            .elseIf(() -> i % 3 == 0).then(() -> "Fizz")
            .elseIf(() -> i % 5 == 0).then(() -> "Buzz")
            .elseExp(() -> String.valueOf(i)))
        .forEach(counter);
    stopWatch.stop();
    return new BenchmarkResult(
        stopWatch,
        counter);
  }

  private static String markdownRow(Object... values) {
    return Arrays.stream(values).map(String::valueOf).collect(Collectors.joining("|", "|", "|"));
  }

  static class LengthCounter implements Consumer<String> {

    private long count = 0;

    @Override
    public void accept(String s) {
      count += s.length();
    }
  }

  static class BenchmarkResult {

    private final long elapsedTime;
    private final long lengthCount;

    BenchmarkResult(StopWatch stopWatch, LengthCounter counter) {
      this.elapsedTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
      this.lengthCount = counter.count;
    }
  }
}
