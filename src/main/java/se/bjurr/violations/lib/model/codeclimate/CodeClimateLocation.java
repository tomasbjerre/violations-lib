package se.bjurr.violations.lib.model.codeclimate;

import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.emptyToNull;

public class CodeClimateLocation {
  private final String path;
  private final CodeClimateLines lines;
  private final CodeClimatePositions positions;

  public CodeClimateLocation(
      final String path, final CodeClimateLines lines, final CodeClimatePositions positions) {
    this.path = checkNotNull(emptyToNull(path), "path");
    this.lines = checkNotNull(lines, "lines");
    this.positions = positions;
  }

  public CodeClimatePositions getPositions() {
    return positions;
  }

  public CodeClimateLines getLines() {
    return lines;
  }

  public String getPath() {
    return path;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (lines == null ? 0 : lines.hashCode());
    result = prime * result + (path == null ? 0 : path.hashCode());
    result = prime * result + (positions == null ? 0 : positions.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CodeClimateLocation other = (CodeClimateLocation) obj;
    if (lines == null) {
      if (other.lines != null) {
        return false;
      }
    } else if (!lines.equals(other.lines)) {
      return false;
    }
    if (path == null) {
      if (other.path != null) {
        return false;
      }
    } else if (!path.equals(other.path)) {
      return false;
    }
    if (positions == null) {
      if (other.positions != null) {
        return false;
      }
    } else if (!positions.equals(other.positions)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "CodeClimateLocation [path="
        + path
        + ", lines="
        + lines
        + ", positions="
        + positions
        + "]";
  }
}
