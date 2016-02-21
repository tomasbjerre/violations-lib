package se.bjurr.violations.lib;

import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;

import java.io.File;
import java.util.List;

import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Reporter;

public class ViolationsReporterApi {
 private String pattern;
 private Reporter reporter;
 private File startFile;

 private ViolationsReporterApi() {
 }

 public static ViolationsReporterApi violationsReporterApi() {
  return new ViolationsReporterApi();
 }

 public ViolationsReporterApi withPattern(String regularExpression) {
  this.pattern = regularExpression;
  return this;
 }

 public ViolationsReporterApi findAll(Reporter reporter) {
  this.reporter = reporter;
  return this;
 }

 public ViolationsReporterApi inFolder(String folder) {
  this.startFile = new File(folder);
  if (!startFile.exists()) {
   throw new RuntimeException(folder + " not found");
  }
  return this;
 }

 public List<Violation> violations() {
  List<File> includedFiles = findAllReports(startFile, pattern);
  return reporter.findViolations(includedFiles);
 }
}
