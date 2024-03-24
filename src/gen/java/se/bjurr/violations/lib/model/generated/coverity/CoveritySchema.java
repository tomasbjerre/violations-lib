
package se.bjurr.violations.lib.model.generated.coverity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoveritySchema {

    private String type;
    private Integer formatVersion;
    private Integer suppressedIssueCount;
    private List<Issue> issues = new ArrayList<Issue>();
    private Object desktopAnalysisSettings;
    private Object error;
    private List<Object> warnings = new ArrayList<Object>();
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CoveritySchema withType(String type) {
        this.type = type;
        return this;
    }

    public Integer getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(Integer formatVersion) {
        this.formatVersion = formatVersion;
    }

    public CoveritySchema withFormatVersion(Integer formatVersion) {
        this.formatVersion = formatVersion;
        return this;
    }

    public Integer getSuppressedIssueCount() {
        return suppressedIssueCount;
    }

    public void setSuppressedIssueCount(Integer suppressedIssueCount) {
        this.suppressedIssueCount = suppressedIssueCount;
    }

    public CoveritySchema withSuppressedIssueCount(Integer suppressedIssueCount) {
        this.suppressedIssueCount = suppressedIssueCount;
        return this;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public CoveritySchema withIssues(List<Issue> issues) {
        this.issues = issues;
        return this;
    }

    public Object getDesktopAnalysisSettings() {
        return desktopAnalysisSettings;
    }

    public void setDesktopAnalysisSettings(Object desktopAnalysisSettings) {
        this.desktopAnalysisSettings = desktopAnalysisSettings;
    }

    public CoveritySchema withDesktopAnalysisSettings(Object desktopAnalysisSettings) {
        this.desktopAnalysisSettings = desktopAnalysisSettings;
        return this;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public CoveritySchema withError(Object error) {
        this.error = error;
        return this;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public CoveritySchema withWarnings(List<Object> warnings) {
        this.warnings = warnings;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public CoveritySchema withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CoveritySchema.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("formatVersion");
        sb.append('=');
        sb.append(((this.formatVersion == null)?"<null>":this.formatVersion));
        sb.append(',');
        sb.append("suppressedIssueCount");
        sb.append('=');
        sb.append(((this.suppressedIssueCount == null)?"<null>":this.suppressedIssueCount));
        sb.append(',');
        sb.append("issues");
        sb.append('=');
        sb.append(((this.issues == null)?"<null>":this.issues));
        sb.append(',');
        sb.append("desktopAnalysisSettings");
        sb.append('=');
        sb.append(((this.desktopAnalysisSettings == null)?"<null>":this.desktopAnalysisSettings));
        sb.append(',');
        sb.append("error");
        sb.append('=');
        sb.append(((this.error == null)?"<null>":this.error));
        sb.append(',');
        sb.append("warnings");
        sb.append('=');
        sb.append(((this.warnings == null)?"<null>":this.warnings));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.warnings == null)? 0 :this.warnings.hashCode()));
        result = ((result* 31)+((this.suppressedIssueCount == null)? 0 :this.suppressedIssueCount.hashCode()));
        result = ((result* 31)+((this.desktopAnalysisSettings == null)? 0 :this.desktopAnalysisSettings.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.error == null)? 0 :this.error.hashCode()));
        result = ((result* 31)+((this.formatVersion == null)? 0 :this.formatVersion.hashCode()));
        result = ((result* 31)+((this.issues == null)? 0 :this.issues.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CoveritySchema) == false) {
            return false;
        }
        CoveritySchema rhs = ((CoveritySchema) other);
        return (((((((((this.warnings == rhs.warnings)||((this.warnings!= null)&&this.warnings.equals(rhs.warnings)))&&((this.suppressedIssueCount == rhs.suppressedIssueCount)||((this.suppressedIssueCount!= null)&&this.suppressedIssueCount.equals(rhs.suppressedIssueCount))))&&((this.desktopAnalysisSettings == rhs.desktopAnalysisSettings)||((this.desktopAnalysisSettings!= null)&&this.desktopAnalysisSettings.equals(rhs.desktopAnalysisSettings))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.error == rhs.error)||((this.error!= null)&&this.error.equals(rhs.error))))&&((this.formatVersion == rhs.formatVersion)||((this.formatVersion!= null)&&this.formatVersion.equals(rhs.formatVersion))))&&((this.issues == rhs.issues)||((this.issues!= null)&&this.issues.equals(rhs.issues))));
    }

}
