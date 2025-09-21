package se.bjurr.violations.lib;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FilteringViolationsLogger implements ViolationsLogger {
  private static final Logger LOGGER = Logger.getLogger(ViolationsApi.class.getSimpleName());
  private final ViolationsLogger violationsLogger;

  public static ViolationsLogger filterLevel(ViolationsLogger violationsLogger) {
    return new FilteringViolationsLogger(violationsLogger);
  }

  private FilteringViolationsLogger(ViolationsLogger violationsLogger) {
    this.violationsLogger = Objects.requireNonNull(violationsLogger);
  }

  @Override
  public void log(Level level, String string) {
    if (LOGGER.isLoggable(level)) {
      this.violationsLogger.log(level, string);
    }
  }

  @Override
  public void log(Level level, String string, Throwable t) {
    if (LOGGER.isLoggable(level)) {
      this.violationsLogger.log(level, string, t);
    }
  }
}
