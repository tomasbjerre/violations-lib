package se.bjurr.violations.lib.model.codeclimate;

public class CodeClimatePosition {
  private final Integer line;
  private final Integer column;

  public CodeClimatePosition(final Integer line, final Integer column) {
    this.line = line;
    this.column = column;
  }

  public Integer getColumn() {
    return column;
  }

  public Integer getLine() {
    return line;
  }

  @Override
  public String toString() {
    return "CodeClimatePosition [line=" + line + ", column=" + column + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (column == null ? 0 : column.hashCode());
    result = prime * result + (line == null ? 0 : line.hashCode());
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
    final CodeClimatePosition other = (CodeClimatePosition) obj;
    if (column == null) {
      if (other.column != null) {
        return false;
      }
    } else if (!column.equals(other.column)) {
      return false;
    }
    if (line == null) {
      if (other.line != null) {
        return false;
      }
    } else if (!line.equals(other.line)) {
      return false;
    }
    return true;
  }
}
