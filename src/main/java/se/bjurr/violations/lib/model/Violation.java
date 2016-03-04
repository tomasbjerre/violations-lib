package se.bjurr.violations.lib.model;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Map.Entry;

import se.bjurr.violations.lib.reports.Reporter;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public class Violation {
 public static ViolationBuilder violationBuilder() {
  return new ViolationBuilder();
 }

 public static class ViolationBuilder {

  private Integer startLine;
  private Integer endLine;
  private Integer column;
  private SEVERITY severity;
  private String message;
  private String file;
  private String source;
  private String rule;
  private Reporter reporter;
  private Map<String, String> specifics = newHashMap();

  private ViolationBuilder() {
  }

  public ViolationBuilder setColumn(Integer column) {
   this.column = column;
   return this;
  }

  public ViolationBuilder setSpecifics(Map<String, String> specifics) {
   this.specifics = specifics;
   return this;
  }

  public ViolationBuilder setReporter(Reporter reporter) {
   this.reporter = reporter;
   return this;
  }

  public ViolationBuilder setSpecific(String specificsKey, String specificsValue) {
   this.specifics.put(specificsKey, specificsValue);
   return this;
  }

  public ViolationBuilder setSpecific(String specificsKey, Integer specificsValue) {
   this.specifics.put(specificsKey, Integer.toString(specificsValue));
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

  public ViolationBuilder setStartLine(Integer startLine) {
   this.startLine = startLine;
   return this;
  }

  public Violation build() {
   return new Violation(this);
  }

 }

 private final Integer startLine;
 private final Integer endLine;
 private final SEVERITY severity;
 private final String message;
 private final String file;
 private final String source;
 private final String rule;
 private Integer column;
 private Map<String, String> specifics;
 private Reporter reporter;

 public Violation() {
  this.startLine = null;
  this.endLine = null;
  this.severity = null;
  this.message = null;
  this.file = null;
  this.source = null;
  this.rule = null;
 }

 public Violation(ViolationBuilder vb) {
  this.reporter = checkNotNull(vb.reporter, "reporter");
  this.startLine = checkNotNull(vb.startLine, "startline");
  this.endLine = firstNonNull(vb.endLine, vb.startLine);
  this.column = vb.column;
  this.severity = checkNotNull(vb.severity, "severity");
  this.message = checkNotNull(emptyToNull(vb.message), "message");
  this.file = checkNotNull(emptyToNull(vb.file), "file").replaceAll("\\\\", "/");
  this.source = emptyToNull(vb.source);
  this.rule = emptyToNull(vb.rule);
  this.specifics = vb.specifics;
 }

 public Reporter getReporter() {
  return reporter;
 }

 public Optional<Integer> getColumn() {
  return fromNullable(column);
 }

 public Integer getEndLine() {
  return endLine;
 }

 public Integer getStartLine() {
  return startLine;
 }

 public SEVERITY getSeverity() {
  return severity;
 }

 public String getMessage() {
  return message;
 }

 public String getFile() {
  return file;
 }

 /**
  * Rule that was matched. All tools don't use rules, so this may be null.
  */
 public Optional<String> getRule() {
  return fromNullable(rule);
 }

 /**
  * The thing that contains the violations. Like a Java/C# class.
  */
 public Optional<String> getSource() {
  return fromNullable(source);
 }

 /**
  * Some parsers may find values that are specific to that kind of analysis.
  * Those can be made available through this map.
  */
 public Map<String, String> getSpecifics() {
  return specifics;
 }

 @Override
 public String toString() {
  return "file=" + file + //
    ", message=" + message + //
    ", source=" + source + //
    ", rule=" + rule + //
    ", severity=" + severity + //
    ", startLine=" + startLine + //
    ", endLine=" + endLine + //
    ", column=" + column + //
    ", specifics=[" + on(',').join(transform(specifics.entrySet(), new Function<Map.Entry<String, String>, String>() {
     @Override
     public String apply(Entry<String, String> input) {
      return input.getKey() + "=" + input.getValue();
     }
    })) + "]"; //
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result + ((column == null) ? 0 : column.hashCode());
  result = prime * result + ((endLine == null) ? 0 : endLine.hashCode());
  result = prime * result + ((file == null) ? 0 : file.hashCode());
  result = prime * result + ((message == null) ? 0 : message.hashCode());
  result = prime * result + ((rule == null) ? 0 : rule.hashCode());
  result = prime * result + ((severity == null) ? 0 : severity.hashCode());
  result = prime * result + ((source == null) ? 0 : source.hashCode());
  result = prime * result + ((specifics == null) ? 0 : specifics.hashCode());
  result = prime * result + ((startLine == null) ? 0 : startLine.hashCode());
  return result;
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

}
