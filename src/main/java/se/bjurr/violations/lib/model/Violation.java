package se.bjurr.violations.lib.model;

import static se.bjurr.violations.lib.util.Utils.checkNotNull;
import static se.bjurr.violations.lib.util.Utils.emptyToNull;
import static se.bjurr.violations.lib.util.Utils.firstNonNull;
import static se.bjurr.violations.lib.util.Utils.nullToEmpty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import se.bjurr.violations.lib.parsers.CPPCheckParser;
import se.bjurr.violations.lib.reports.Parser;

public class Violation implements Serializable, Comparable<Violation> {
  public static class ViolationBuilder {

    private Integer column;
    private Integer endLine;
    private Integer endColumn;
    private String file;
    private String message;
    private Parser parser;
    private String reporter;
    private String rule;
    private String category;
    private SEVERITY severity;
    private String source;
    private Map<String, String> specifics = new HashMap<>();
    private Integer startLine;
    private String group;

    private ViolationBuilder() {}

    public Violation build() {
      return new Violation(this);
    }

    public ViolationBuilder setColumn(final Integer column) {
      this.column = column;
      return this;
    }

    public ViolationBuilder setEndLine(final Integer endLine) {
      this.endLine = endLine;
      return this;
    }

    public ViolationBuilder setEndColumn(final Integer endColumn) {
      this.endColumn = endColumn;
      return this;
    }

    public ViolationBuilder setFile(final String file) {
      this.file = file;
      return this;
    }

    public ViolationBuilder setMessage(final String message) {
      this.message = message;
      return this;
    }

    public ViolationBuilder setParser(final Parser parser) {
      this.parser = parser;
      return this;
    }

    public ViolationBuilder setReporter(final String reporter) {
      this.reporter = reporter;
      return this;
    }

    public ViolationBuilder setRule(final String rule) {
      this.rule = rule;
      return this;
    }

    public ViolationBuilder setCategory(final String category) {
      this.category = category;
      return this;
    }

    public ViolationBuilder setSeverity(final SEVERITY severity) {
      this.severity = severity;
      return this;
    }

    public ViolationBuilder setSource(final String source) {
      this.source = source;
      return this;
    }

    public ViolationBuilder setSpecific(final String specificsKey, final Integer specificsValue) {
      this.specifics.put(specificsKey, Integer.toString(specificsValue));
      return this;
    }

    public ViolationBuilder setSpecific(final String specificsKey, final String specificsValue) {
      this.specifics.put(specificsKey, specificsValue);
      return this;
    }

    public ViolationBuilder setSpecifics(final Map<String, String> specifics) {
      this.specifics = specifics;
      return this;
    }

    public ViolationBuilder setStartLine(final Integer startLine) {
      this.startLine = startLine;
      return this;
    }

    public ViolationBuilder setGroup(final String group) {
      this.group = group;
      return this;
    }
  }

  private static final long serialVersionUID = -6052921679385466168L;
  /** A {@link #file} used when there is no file specified in the parsed report. */
  public static final String NO_FILE = "-";

  public static ViolationBuilder violationBuilder() {
    return new ViolationBuilder();
  }

  private Integer column;
  private final Integer endLine;
  private final Integer endColumn;
  /** Also see {@link #NO_FILE} */
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
  private final String category;
  /**
   * Something that identifies a group that this violation belongs to. First introduced with {@link
   * CPPCheckParser} to record what error tag each violation belongs to.
   */
  private final String group;

  private final SEVERITY severity;
  private final String source;
  private final Map<String, String> specifics;
  private final Integer startLine;

  public Violation() {
    this.startLine = null;
    this.endLine = null;
    this.endColumn = null;
    this.severity = null;
    this.message = null;
    this.file = null;
    this.source = null;
    this.rule = null;
    this.category = null;
    this.reporter = null;
    this.specifics = null;
    this.parser = null;
    this.group = null;
  }

  public Violation(final ViolationBuilder vb) {
    this.parser = checkNotNull(vb.parser, "reporter");
    if (vb.reporter != null && !vb.reporter.trim().isEmpty()) {
      this.reporter = vb.reporter;
    } else {
      this.reporter = this.parser.name();
    }
    this.startLine = checkNotNull(vb.startLine, "startline");
    this.endLine = firstNonNull(vb.endLine, vb.startLine);
    this.column = vb.column;
    this.endColumn = vb.endColumn;
    this.severity = checkNotNull(vb.severity, "severity");
    this.message = checkNotNull(emptyToNull(vb.message), "message");
    this.file = checkNotNull(emptyToNull(vb.file), "file").replaceAll("\\\\", "/");
    this.source = nullToEmpty(vb.source);
    this.rule = nullToEmpty(vb.rule);
    this.category = nullToEmpty(vb.category);
    this.group = nullToEmpty(vb.group);
    this.specifics = vb.specifics;
  }

  public Violation(final Violation v) {
    this.parser = v.parser;
    this.reporter = v.reporter;
    this.startLine = v.startLine;
    this.endLine = v.endLine;
    this.endColumn = v.endColumn;
    this.column = v.column;
    this.severity = v.severity;
    this.message = v.message;
    this.file = v.file;
    this.source = v.source;
    this.rule = v.rule;
    this.category = v.category;
    this.group = v.group;
    this.specifics = v.specifics;
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
    final Violation other = (Violation) obj;
    if (this.category == null) {
      if (other.category != null) {
        return false;
      }
    } else if (!this.category.equals(other.category)) {
      return false;
    }
    if (this.column == null) {
      if (other.column != null) {
        return false;
      }
    } else if (!this.column.equals(other.column)) {
      return false;
    }
    if (this.endLine == null) {
      if (other.endLine != null) {
        return false;
      }
    } else if (!this.endLine.equals(other.endLine)) {
      return false;
    }
    if (this.endColumn == null) {
      if (other.endColumn != null) {
        return false;
      }
    } else if (!this.endColumn.equals(other.endColumn)) {
      return false;
    }
    if (this.file == null) {
      if (other.file != null) {
        return false;
      }
    } else if (!this.file.equals(other.file)) {
      return false;
    }
    if (this.group == null) {
      if (other.group != null) {
        return false;
      }
    } else if (!this.group.equals(other.group)) {
      return false;
    }
    if (this.message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!this.message.equals(other.message)) {
      return false;
    }
    if (this.parser != other.parser) {
      return false;
    }
    if (this.reporter == null) {
      if (other.reporter != null) {
        return false;
      }
    } else if (!this.reporter.equals(other.reporter)) {
      return false;
    }
    if (this.rule == null) {
      if (other.rule != null) {
        return false;
      }
    } else if (!this.rule.equals(other.rule)) {
      return false;
    }
    if (this.severity != other.severity) {
      return false;
    }
    if (this.source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!this.source.equals(other.source)) {
      return false;
    }
    if (this.specifics == null) {
      if (other.specifics != null) {
        return false;
      }
    } else if (!this.specifics.equals(other.specifics)) {
      return false;
    }
    if (this.startLine == null) {
      if (other.startLine != null) {
        return false;
      }
    } else if (!this.startLine.equals(other.startLine)) {
      return false;
    }
    return true;
  }

  public Integer getColumn() {
    return firstNonNull(this.column, -1);
  }

  public Integer getEndLine() {
    return this.endLine;
  }

  public Integer getEndColumn() {
    return this.endColumn;
  }

  public String getFile() {
    return this.file;
  }

  public String getMessage() {
    return this.message;
  }

  public Parser getParser() {
    return this.parser;
  }

  public void setReporter(final String reporter) {
    this.reporter = checkNotNull(reporter, "reporter");
  }

  public String getReporter() {
    return this.reporter;
  }

  public String getRule() {
    return this.rule;
  }

  public String getCategory() {
    return this.category;
  }

  public SEVERITY getSeverity() {
    return this.severity;
  }

  /** The thing that contains the violations. Like a Java/C# class. */
  public String getSource() {
    return this.source;
  }

  /**
   * Some parsers may find values that are specific to that kind of analysis. Those can be made
   * available through this map.
   */
  public Map<String, String> getSpecifics() {
    return this.specifics;
  }

  public Integer getStartLine() {
    return this.startLine;
  }

  public String getGroup() {
    return this.group;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.category == null ? 0 : this.category.hashCode());
    result = prime * result + (this.column == null ? 0 : this.column.hashCode());
    result = prime * result + (this.endLine == null ? 0 : this.endLine.hashCode());
    result = prime * result + (this.endColumn == null ? 0 : this.endColumn.hashCode());
    result = prime * result + (this.file == null ? 0 : this.file.hashCode());
    result = prime * result + (this.group == null ? 0 : this.group.hashCode());
    result = prime * result + (this.message == null ? 0 : this.message.hashCode());
    result = prime * result + (this.parser == null ? 0 : this.parser.hashCode());
    result = prime * result + (this.reporter == null ? 0 : this.reporter.hashCode());
    result = prime * result + (this.rule == null ? 0 : this.rule.hashCode());
    result = prime * result + (this.severity == null ? 0 : this.severity.hashCode());
    result = prime * result + (this.source == null ? 0 : this.source.hashCode());
    result = prime * result + (this.specifics == null ? 0 : this.specifics.hashCode());
    result = prime * result + (this.startLine == null ? 0 : this.startLine.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "Violation [column="
        + this.column
        + ", endLine="
        + this.endLine
        + ", endColumn="
        + this.endColumn
        + ", file="
        + this.file
        + ", message="
        + this.message
        + ", parser="
        + this.parser
        + ", reporter="
        + this.reporter
        + ", rule="
        + this.rule
        + ", category="
        + this.category
        + ", severity="
        + this.severity
        + ", source="
        + this.source
        + ", specifics="
        + this.specifics
        + ", startLine="
        + this.startLine
        + ", group="
        + this.group
        + "]";
  }

  @Override
  public int compareTo(final Violation o) {
    return this.comparingString(this).compareTo(this.comparingString(o));
  }

  private String comparingString(final Violation o) {
    return o.file
        + "_"
        + (Integer.MAX_VALUE - o.getStartLine())
        + "_"
        + o.getParser()
        + "_"
        + o.getMessage()
        + "_"
        + o.getReporter()
        + "_"
        + o.getEndLine()
        + "_"
        + o.getEndColumn()
        + "_"
        + o.getColumn()
        + "_"
        + o.getSeverity()
        + "_"
        + o.getSource()
        + "_"
        + o.getRule()
        + "_"
        + o.getCategory()
        + "_"
        + o.getGroup();
  }
}
