package jp.gr.java_conf.spica.expressionj;

import static jp.gr.java_conf.spica.expressionj.Day.FRIDAY;
import static jp.gr.java_conf.spica.expressionj.Day.MONDAY;
import static jp.gr.java_conf.spica.expressionj.Day.SATURDAY;
import static jp.gr.java_conf.spica.expressionj.Day.SUNDAY;
import static jp.gr.java_conf.spica.expressionj.Day.THURSDAY;
import static jp.gr.java_conf.spica.expressionj.Day.TUESDAY;
import static jp.gr.java_conf.spica.expressionj.Day.WEDNESDAY;
import static jp.gr.java_conf.spica.expressionj.Expressions.ifExp;
import static jp.gr.java_conf.spica.expressionj.Expressions.switchExp;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseEq;
import static jp.gr.java_conf.spica.expressionj.switchexpression.Cases.caseIn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import jp.gr.java_conf.spica.expressionj.switchexpression.Cases;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@Tag("performance")
class PerformanceTest {

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

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  class IfTest {

    private static final int FOR_LOOP_COUNT = 10000000;

    @BeforeAll
    void beforeAll() {
      Stream.of(
              "java.vm.vendor",
              "java.vm.version",
              "os.name",
              "os.version")
          .map(key -> "- " + key + ":" + System.getProperty(key))
          .forEach(System.out::println);
      System.out.println();

      System.out.println("Benchmark of FizzBuzz.");
      System.out.println();
      System.out.println("Loop count is " + FOR_LOOP_COUNT + ".");
      System.out.println();
      System.out.println("Elapsed Time in ms:");
      System.out.println();
      System.out.println(markdownRow("Count", "If statement with method", "If Expression"));
      System.out.println(markdownRow("---", "---", "---"));
    }

    @RepeatedTest(6)
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
              .isGreaterThan(FOR_LOOP_COUNT * 3),
          () -> assertThat(ifStatementResult.elapsedTime).isLessThanOrEqualTo(1000),
          () -> assertThat(ifExpressionResult.elapsedTime)
              .isLessThanOrEqualTo(1000)
              .isLessThanOrEqualTo(ifStatementResult.elapsedTime * 4));
    }

    private BenchmarkResult runBenchmarkOfIfStatement() {
      StopWatch stopWatch = StopWatch.createStarted();
      LengthCounter counter = new LengthCounter();
      IntStream.rangeClosed(1, FOR_LOOP_COUNT)
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
      IntStream.rangeClosed(1, FOR_LOOP_COUNT)
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
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  class SwitchTest {

    private final int SWITCH_LOOP_COUNT = 1000000 / Day.values().length;

    @BeforeAll
    void beforeAll() {
      Stream.of(
              "java.vm.vendor",
              "java.vm.version",
              "os.name",
              "os.version")
          .map(key -> "- " + key + ":" + System.getProperty(key))
          .forEach(System.out::println);
      System.out.println();

      System.out.println("Benchmark of length count of days.");
      System.out.println("Loop count is " + SWITCH_LOOP_COUNT + ".");
      System.out.println("Elapsed Time in ms:");
      System.out.println();
      System.out.println(markdownRow("Count", "Switch statement with method", "Switch Expression"));
      System.out.println(markdownRow("---", "---", "---"));
    }

    @RepeatedTest(6)
    void switchExpression(RepetitionInfo repetitionInfo) {
      BenchmarkResult switchStatementResult = runBenchmarkOfSwitchStatement();
      BenchmarkResult switchExpressionResult = runBenchmarkOfSwitchExpression();

      System.out.println(markdownRow(
          repetitionInfo.getCurrentRepetition(),
          switchStatementResult.elapsedTime,
          switchExpressionResult.elapsedTime));

      if (repetitionInfo.getCurrentRepetition() == 1) {
        // the 1st take is just a warming up.
        return;
      }

      assertAll(
          () -> assertThat(switchStatementResult.lengthCount)
              .isEqualTo(switchExpressionResult.lengthCount)
              .isGreaterThan(SWITCH_LOOP_COUNT * 3),
          () -> assertThat(switchStatementResult.elapsedTime).isLessThanOrEqualTo(1000),
          () -> assertThat(switchExpressionResult.elapsedTime)
              .isLessThanOrEqualTo(1000)
              .isLessThanOrEqualTo(switchStatementResult.elapsedTime * 30));
    }

    private BenchmarkResult runBenchmarkOfSwitchStatement() {
      StopWatch stopWatch = StopWatch.createStarted();
      AtomicLong counter = new AtomicLong();
      IntStream.rangeClosed(1, SWITCH_LOOP_COUNT)
          .boxed()
          .flatMap(i -> Arrays.stream(Day.values()))
          .map(d -> dayLength(d))
          .forEach(l -> counter.addAndGet(l));
      stopWatch.stop();
      return new BenchmarkResult(
          stopWatch,
          counter.get());
    }

    private long dayLength(Day day) {
      switch (day) {
        case MONDAY:
        case FRIDAY:
        case SUNDAY:
          return 6;
        case TUESDAY:
          return 7;
        case THURSDAY:
        case SATURDAY:
          return 8;
        case WEDNESDAY:
          return 9;
        default:
          return -1;
      }
    }

    private BenchmarkResult runBenchmarkOfSwitchExpression() {
      StopWatch stopWatch = StopWatch.createStarted();
      AtomicLong counter = new AtomicLong();
      IntStream.rangeClosed(1, SWITCH_LOOP_COUNT)
          .boxed()
          .flatMap(i -> Arrays.stream(Day.values()))
          .map(d -> switchExp(d).<Integer>cases(
              caseIn(MONDAY, FRIDAY, SUNDAY).yield(() -> 6),
              caseEq(TUESDAY).yield(() -> 7),
              caseIn(THURSDAY, SATURDAY).yield(() -> 8),
              caseIn(WEDNESDAY).yield(() -> 9),
              Cases.<Day>caseDefault().yield(() -> -1)))
          .forEach(l -> counter.addAndGet(l));
      stopWatch.stop();
      return new BenchmarkResult(
          stopWatch,
          counter.get());
    }
  }

  static class LengthCounter implements Consumer<String> {

    private long count = 0;

    @Override
    public void accept(String s) {
      count += s.length();
    }
  }

  private static String markdownRow(Object... values) {
    return Arrays.stream(values).map(String::valueOf).collect(Collectors.joining("|", "|", "|"));
  }

  static class BenchmarkResult {

    private final long elapsedTime;
    private final long lengthCount;

    BenchmarkResult(StopWatch stopWatch, LengthCounter counter) {
      this.elapsedTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
      this.lengthCount = counter.count;
    }

    BenchmarkResult(StopWatch stopWatch, long lengthCount) {
      this.elapsedTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
      this.lengthCount = lengthCount;
    }
  }

}
