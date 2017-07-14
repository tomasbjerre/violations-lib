package se.bjurr.violations.lib.model;

import static se.bjurr.violations.lib.util.Optional.fromNullable;
import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.emptyToNull;
import static se.bjurr.violations.lib.util.Utils.firstNonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import se.bjurr.violations.lib.reports.Parser;
import se.bjurr.violations.lib.util.Optional;

public class Violation implements Serializable {
  public static class ViolationBuilder {

    private Integer column;
    private Integer endLine;
    private String file;
    private String message;
    private Parser parser;
    private String reporter;
    private String rule;
    private SEVERITY severity;
    private String source;
    private Map<String, String> specifics = new HashMap<>();
    private Integer startLine;

    private ViolationBuilder() {}

    public Violation build() {
      return new Violation(this);
    }

    public ViolationBuilder setColumn(Integer column) {
      this.column = column;
      return this;
    }

    public ViolationBuilder setEndLine(Integer endLine) {
      this.endLine = endLine;
      return this;
    }

    public ViolationBuilder setFile(String file) {
      this.file = file;
      return this;
    }

    public ViolationBuilder setMessage(String message) {
      this.message = message;
      return this;
    }

    public ViolationBuilder setParser(Parser parser) {
      this.parser = parser;
      return this;
    }

    public ViolationBuilder setReporter(String reporter) {
      this.reporter = reporter;
      return this;
    }

    public ViolationBuilder setRule(String rule) {
      this.rule = rule;
      return this;
    }

    public ViolationBuilder setSeverity(SEVERITY severity) {
      this.severity = severity;
      return this;
    }

    public ViolationBuilder setSource(String source) {
      this.source = source;
      return this;
    }

    public ViolationBuilder setSpecific(String specificsKey, Integer specificsValue) {
      specifics.put(specificsKey, Integer.toString(specificsValue));
      return this;
    }

    public ViolationBuilder setSpecific(String specificsKey, String specificsValue) {
      specifics.put(specificsKey, specificsValue);
      return this;
    }

    public ViolationBuilder setSpecifics(Map<String, String> specifics) {
      this.specifics = specifics;
      return this;
    }

    public ViolationBuilder setStartLine(Integer startLine) {
      this.startLine = startLine;
      return this;
    }
  }

  private static final long serialVersionUID = -6052921679385466168L;

  public static ViolationBuilder violationBuilder() {
    return new ViolationBuilder();
  }

  private Integer column;
  private final Integer endLine;
  private final String file;
  private final String message;
  /** The algorithm, the format, used. */
  private final Parser parser;
  /**
   * Intended as the tool used to find the violation. Like Detekt, when it is being used to find
   * violations and report them in the {@link Parser#CHECKSTYLE} format.
   */
  private String reporter;

  private final String rule;
  private final SEVERITY severity;
  private final String source;
  private final Map<String, String> specifics;
  private final Integer startLine;

  public Violation() {
    startLine = null;
    endLine = null;
    severity = null;
    message = null;
    file = null;
    source = null;
    rule = null;
    reporter = null;
    specifics = null;
    parser = null;
  }

  public Violation(ViolationBuilder vb) {
    parser = checkNotNull(vb.parser, "reporter");
    reporter = firstNonNull(vb.reporter, parser.name());
    startLine = checkNotNull(vb.startLine, "startline");
    endLine = firstNonNull(vb.endLine, vb.startLine);
    column = vb.column;
    severity = checkNotNull(vb.severity, "severity");
    message = checkNotNull(emptyToNull(vb.message), "message");
    file = checkNotNull(emptyToNull(vb.file), "file").replaceAll("\\\\", "/");
    source = emptyToNull(vb.source);
    rule = emptyToNull(vb.rule);
    specifics = vb.specifics;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Violation other = (Violation) obj;
    if (column == null) {
      if (other.column != null) {
        return false;
      }
    } else if (!column.equals(other.column)) {
      return false;
    }
    if (endLine == null) {
      if (other.endLine != null) {
        return false;
      }
    } else if (!endLine.equals(other.endLine)) {
      return false;
    }
    if (file == null) {
      if (other.file != null) {
        return false;
      }
    } else if (!file.equals(other.file)) {
      return false;
    }
    if (message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!message.equals(other.message)) {
      return false;
    }
    if (parser != other.parser) {
      return false;
    }
    if (reporter == null) {
      if (other.reporter != null) {
        return false;
      }
    } else if (!reporter.equals(other.reporter)) {
      return false;
    }
    if (rule == null) {
      if (other.rule != null) {
        return false;
      }
    } else if (!rule.equals(other.rule)) {
      return false;
    }
    if (severity != other.severity) {
      return false;
    }
    if (source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!source.equals(other.source)) {
      return false;
    }
    if (specifics == null) {
      if (other.specifics != null) {
        return false;
      }
    } else if (!specifics.equals(other.specifics)) {
      return false;
    }
    if (startLine == null) {
      if (other.startLine != null) {
        return false;
      }
    } else if (!startLine.equals(other.startLine)) {
      return false;
    }
    return true;
  }

  public Optional<Integer> getColumn() {
    return fromNullable(column);
  }

  public Integer getEndLine() {
    return endLine;
  }

  public String getFile() {
    return file;
  }

  public String getMessage() {
    return message;
  }

  public Parser getParser() {
    return parser;
  }

  public void setReporter(String reporter) {
    this.reporter = checkNotNull(reporter, "reporter");
  }

  public String getReporter() {
    return reporter;
  }

  /** Rule that was matched. All tools don't use rules, so this may be null. */
  public Optional<String> getRule() {
    return fromNullable(rule);
  }

  public SEVERITY getSeverity() {
    return severity;
  }

  /** The thing that contains the violations. Like a Java/C# class. */
  public Optional<String> getSource() {
    return fromNullable(source);
  }

  /**
   * Some parsers may find values that are specific to that kind of analysis. Those can be made
   * available through this map.
   */
  public Map<String, String> getSpecifics() {
    return specifics;
  }

  public Integer getStartLine() {
    return startLine;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (column == null ? 0 : column.hashCode());
    result = prime * result + (endLine == null ? 0 : endLine.hashCode());
    result = prime * result + (file == null ? 0 : file.hashCode());
    result = prime * result + (message == null ? 0 : message.hashCode());
    result = prime * result + (parser == null ? 0 : parser.hashCode());
    result = prime * result + (reporter == null ? 0 : reporter.hashCode());
    result = prime * result + (rule == null ? 0 : rule.hashCode());
    result = prime * result + (severity == null ? 0 : severity.hashCode());
    result = prime * result + (source == null ? 0 : source.hashCode());
    result = prime * result + (specifics == null ? 0 : specifics.hashCode());
    result = prime * result + (startLine == null ? 0 : startLine.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "Violation [column="
        + column
        + ", endLine="
        + endLine
        + ", file="
        + file
        + ", message="
        + message
        + ", reporter="
        + parser
        + ", rule="
        + rule
        + ", severity="
        + severity
        + ", source="
        + source
        + ", specifics="
        + specifics
        + ", startLine="
        + startLine
        + "]";
  }
}
