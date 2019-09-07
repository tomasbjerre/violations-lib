package se.bjurr.violations.lib.model.codeclimate;

public class CodeClimateLines {
  private final Integer begin;

  public CodeClimateLines(final Integer begin) {
    this.begin = begin;
  }

  public Integer getBegin() {
    return begin;
  }

  @Override
  public String toString() {
    return "CodeClimateLines [begin=" + begin + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (begin == null ? 0 : begin.hashCode());
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
    final CodeClimateLines other = (CodeClimateLines) obj;
    if (begin == null) {
      if (other.begin != null) {
        return false;
      }
    } else if (!begin.equals(other.begin)) {
      return false;
    }
    return true;
  }
}
