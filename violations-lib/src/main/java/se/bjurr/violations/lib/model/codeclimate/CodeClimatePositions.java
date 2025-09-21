package se.bjurr.violations.lib.model.codeclimate;

public class CodeClimatePositions {
  private final CodeClimatePosition begin;
  private final CodeClimatePosition end;

  public CodeClimatePositions(final CodeClimatePosition begin, final CodeClimatePosition end) {
    this.begin = begin;
    this.end = end;
  }

  public CodeClimatePosition getBegin() {
    return begin;
  }

  public CodeClimatePosition getEnd() {
    return end;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (begin == null ? 0 : begin.hashCode());
    result = prime * result + (end == null ? 0 : end.hashCode());
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
    final CodeClimatePositions other = (CodeClimatePositions) obj;
    if (begin == null) {
      if (other.begin != null) {
        return false;
      }
    } else if (!begin.equals(other.begin)) {
      return false;
    }
    if (end == null) {
      if (other.end != null) {
        return false;
      }
    } else if (!end.equals(other.end)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "CodeClimatePositions [begin=" + begin + ", end=" + end + "]";
  }
}
