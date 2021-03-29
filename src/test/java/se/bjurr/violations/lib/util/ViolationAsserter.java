package se.bjurr.violations.lib.util;

import java.util.ArrayList;
import java.util.Set;
import org.assertj.core.api.Assertions;
import se.bjurr.violations.lib.model.Violation;

public class ViolationAsserter {

  private final Set<Violation> actual;

  public ViolationAsserter(final Set<Violation> actual) {
    this.actual = actual;
  }

  public static ViolationAsserter assertThat(final Set<Violation> actual) {
    return new ViolationAsserter(actual);
  }

  public ViolationAsserter contains(final Violation expected, final int postition) {
    final Violation actualViolation = new ArrayList<>(this.actual).get(postition);
    Assertions.assertThat(actualViolation.getFile()) //
        .isEqualTo(expected.getFile());
    Assertions.assertThat(actualViolation.getSeverity()) //
        .isEqualTo(expected.getSeverity());
    Assertions.assertThat(actualViolation.getStartLine()) //
        .isEqualTo(expected.getStartLine());
    Assertions.assertThat(actualViolation.getMessage()) //
        .contains(expected.getMessage());
    Assertions.assertThat(actualViolation.getCategory()) //
        .isEqualTo(expected.getCategory());
    Assertions.assertThat(actualViolation.getColumn()) //
        .isEqualTo(expected.getColumn());
    Assertions.assertThat(actualViolation.getEndColumn()) //
        .isEqualTo(expected.getEndColumn());
    Assertions.assertThat(actualViolation.getEndLine()) //
        .isEqualTo(expected.getEndLine());
    Assertions.assertThat(actualViolation.getGroup()) //
        .isEqualTo(expected.getGroup());
    Assertions.assertThat(actualViolation.getReporter()) //
        .isEqualTo(expected.getReporter());
    Assertions.assertThat(actualViolation.getRule()) //
        .isEqualTo(expected.getRule());
    Assertions.assertThat(actualViolation.getSource()) //
        .isEqualTo(expected.getSource());
    Assertions.assertThat(actualViolation.getSpecifics()) //
        .isEqualTo(expected.getSpecifics());
    return this;
  }
}
