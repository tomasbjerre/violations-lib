package se.bjurr.violations.lib.model.codeclimate;

import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.emptyToNull;
import static se.bjurr.violations.lib.util.Utils.firstNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CodeClimate {
  private final String description;
  private final String fingerprint;
  private final CodeClimateLocation location;
  private final List<CodeClimateLocation> other_locations;
  private final CodeClimateSeverity severity;
  private final String type;
  private final List<String> categories;

  /** Rule within the tool. */
  private final String check_name;

  /** Name of tool. */
  private final String engine_name;

  public CodeClimate(
      final String description,
      final String fingerprint,
      final CodeClimateLocation location,
      final CodeClimateSeverity severity,
      final String check_name,
      final String engine_name,
      final List<CodeClimateCategory> categories,
      final List<CodeClimateLocation> other_locations) {
    this.description = checkNotNull(emptyToNull(description), "description");
    this.fingerprint = checkNotNull(emptyToNull(fingerprint), "fingerprint");
    this.location = checkNotNull(location, "location");
    this.other_locations = firstNonNull(other_locations, new ArrayList<>());
    this.severity = severity;
    this.type = "issue";
    this.check_name = check_name;
    this.engine_name = engine_name;
    this.categories = categories.stream().map((it) -> it.getName()).collect(Collectors.toList());
  }

  public String getCheck_name() {
    return this.check_name;
  }

  public String getEngine_name() {
    return this.engine_name;
  }

  public List<CodeClimateCategory> getCategories() {
    return Arrays.asList(CodeClimateCategory.values()).stream()
        .filter((it) -> this.categories.contains(it.getName()))
        .collect(Collectors.toList());
  }

  public String getType() {
    return this.type;
  }

  public CodeClimateSeverity getSeverity() {
    return this.severity;
  }

  public String getDescription() {
    return this.description;
  }

  public String getFingerprint() {
    return this.fingerprint;
  }

  public CodeClimateLocation getLocation() {
    return this.location;
  }

  public List<CodeClimateLocation> getOther_locations() {
    return this.other_locations;
  }

  @Override
  public String toString() {
    return "CodeClimate [description="
        + this.description
        + ", fingerprint="
        + this.fingerprint
        + ", location="
        + this.location
        + ", severity="
        + this.severity
        + ", type="
        + this.type
        + ", categories="
        + this.categories
        + ", check_name="
        + this.check_name
        + ", engine_name="
        + this.engine_name
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.categories == null ? 0 : this.categories.hashCode());
    result = prime * result + (this.check_name == null ? 0 : this.check_name.hashCode());
    result = prime * result + (this.engine_name == null ? 0 : this.engine_name.hashCode());
    result = prime * result + (this.description == null ? 0 : this.description.hashCode());
    result = prime * result + (this.fingerprint == null ? 0 : this.fingerprint.hashCode());
    result = prime * result + (this.location == null ? 0 : this.location.hashCode());
    result = prime * result + (this.severity == null ? 0 : this.severity.hashCode());
    result = prime * result + (this.type == null ? 0 : this.type.hashCode());
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
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final CodeClimate other = (CodeClimate) obj;
    if (this.categories == null) {
      if (other.categories != null) {
        return false;
      }
    } else if (!this.categories.equals(other.categories)) {
      return false;
    }
    if (this.check_name == null) {
      if (other.check_name != null) {
        return false;
      }
    } else if (!this.check_name.equals(other.check_name)) {
      return false;
    }
    if (this.engine_name == null) {
      if (other.engine_name != null) {
        return false;
      }
    } else if (!this.engine_name.equals(other.engine_name)) {
      return false;
    }
    if (this.description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!this.description.equals(other.description)) {
      return false;
    }
    if (this.fingerprint == null) {
      if (other.fingerprint != null) {
        return false;
      }
    } else if (!this.fingerprint.equals(other.fingerprint)) {
      return false;
    }
    if (this.location == null) {
      if (other.location != null) {
        return false;
      }
    } else if (!this.location.equals(other.location)) {
      return false;
    }
    if (this.severity != other.severity) {
      return false;
    }
    if (this.type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!this.type.equals(other.type)) {
      return false;
    }
    return true;
  }
}
