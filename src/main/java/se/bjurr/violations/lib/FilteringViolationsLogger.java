package se.bjurr.violations.lib;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FilteringViolationsLogger implements ViolationsLogger {
  private static final Logger LOGGER = Logger.getLogger(ViolationsApi.class.getSimpleName());
  private final ViolationsLogger violationsLogger;

  public static ViolationsLogger filterLevel(final ViolationsLogger violationsLogger) {
    return new FilteringViolationsLogger(violationsLogger);
  }

  private FilteringViolationsLogger(final ViolationsLogger violationsLogger) {
    this.violationsLogger = Objects.requireNonNull(violationsLogger);
  }

  @Override
  public void log(final Level level, final String string) {
    if (LOGGER.isLoggable(level)) {
      this.violationsLogger.log(level, string);
    }
  }

  @Override
  public void log(final Level level, final String string, final Throwable t) {
    if (LOGGER.isLoggable(level)) {
      this.violationsLogger.log(level, string, t);
    }
  }
}
