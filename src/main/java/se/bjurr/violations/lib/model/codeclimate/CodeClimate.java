package se.bjurr.violations.lib.model.codeclimate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CodeClimate {
  private final String description;
  private final String fingerprint;
  private final CodeClimateLocation location;
  private final CodeClimateSeverity severity;
  private final String type;
  private final List<String> categories;
  /** Name of tool. */
  private final String check_name;

  public CodeClimate(
      final String description,
      final String fingerprint,
      final CodeClimateLocation location,
      final CodeClimateSeverity severity,
      final String check_name,
      final List<CodeClimateCategory> categories) {
    this.description = description;
    this.fingerprint = fingerprint;
    this.location = location;
    this.severity = severity;
    this.type = "issue";
    this.check_name = check_name;
    this.categories = categories.stream().map((it) -> it.getName()).collect(Collectors.toList());
  }

  public String getCheck_name() {
    return check_name;
  }

  public List<CodeClimateCategory> getCategories() {
    return Arrays.asList(CodeClimateCategory.values())
        .stream()
        .filter((it) -> this.categories.contains(it.getName()))
        .collect(Collectors.toList());
  }

  public String getType() {
    return type;
  }

  public CodeClimateSeverity getSeverity() {
    return severity;
  }

  public String getDescription() {
    return description;
  }

  public String getFingerprint() {
    return fingerprint;
  }

  public CodeClimateLocation getLocation() {
    return location;
  }

  @Override
  public String toString() {
    return "CodeClimate [description="
        + description
        + ", fingerprint="
        + fingerprint
        + ", location="
        + location
        + ", severity="
        + severity
        + ", type="
        + type
        + ", categories="
        + categories
        + ", check_name="
        + check_name
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (categories == null ? 0 : categories.hashCode());
    result = prime * result + (check_name == null ? 0 : check_name.hashCode());
    result = prime * result + (description == null ? 0 : description.hashCode());
    result = prime * result + (fingerprint == null ? 0 : fingerprint.hashCode());
    result = prime * result + (location == null ? 0 : location.hashCode());
    result = prime * result + (severity == null ? 0 : severity.hashCode());
    result = prime * result + (type == null ? 0 : type.hashCode());
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
    final CodeClimate other = (CodeClimate) obj;
    if (categories == null) {
      if (other.categories != null) {
        return false;
      }
    } else if (!categories.equals(other.categories)) {
      return false;
    }
    if (check_name == null) {
      if (other.check_name != null) {
        return false;
      }
    } else if (!check_name.equals(other.check_name)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (fingerprint == null) {
      if (other.fingerprint != null) {
        return false;
      }
    } else if (!fingerprint.equals(other.fingerprint)) {
      return false;
    }
    if (location == null) {
      if (other.location != null) {
        return false;
      }
    } else if (!location.equals(other.location)) {
      return false;
    }
    if (severity != other.severity) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    return true;
  }
}
