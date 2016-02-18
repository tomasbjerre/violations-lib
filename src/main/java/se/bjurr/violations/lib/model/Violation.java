package se.bjurr.violations.lib.model;

import static com.google.common.base.Optional.fromNullable;

import com.google.common.base.Optional;

public class Violation {
 private final Integer startLine;
 private final Integer endLine;
 private final SEVERITY severity;
 private final String message;
 private final String file;
 private final String source;
 private final String rule;

 public Violation() {
  this.startLine = null;
  this.endLine = null;
  this.severity = null;
  this.message = null;
  this.file = null;
  this.source = null;
  this.rule = null;
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

}
