package se.bjurr.violations.lib;

import java.util.logging.Level;

/**
 * When using this library with Jenkins it is convenient to have the logging the build log and not
 * in the global log. This interface allows a Jenkins plugin to do such logging. Same with command
 * line tools, they can implement this to print to system out.
 */
public interface ViolationsLogger {
  void log(Level level, final String string);

  void log(Level level, final String string, Throwable t);
}
