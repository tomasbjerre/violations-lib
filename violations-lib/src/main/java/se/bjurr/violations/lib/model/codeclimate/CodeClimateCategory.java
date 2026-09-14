package se.bjurr.violations.lib.model.codeclimate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeClimateCategory {
  BUGRISK("Bug Risk"),
  CLARITY("Clarity"),
  COMPAT("Compatibility"),
  COMPLEXITY("Complexity"),
  DUPL("Duplication"),
  PERFORM("Performance"),
  SECURITY("Security"),
  STYLE("Style");

  private final String name;

  CodeClimateCategory(final String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
