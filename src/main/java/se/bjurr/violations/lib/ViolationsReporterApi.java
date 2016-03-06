package se.bjurr.violations.lib;

import static se.bjurr.violations.lib.reports.ReportsFinder.findAllReports;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.reports.Reporter;

public class ViolationsReporterApi {
 private static Logger LOG = LoggerFactory.getLogger(ViolationsReporterApi.class);
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
  if (LOG.isDebugEnabled()) {
   LOG.debug("Found " + includedFiles.size() + " reports:");
   for (File f : includedFiles) {
    LOG.debug(f.getAbsolutePath());
   }
  }
  List<Violation> foundViolations = reporter.findViolations(includedFiles);
  if (LOG.isDebugEnabled()) {
   LOG.debug("Found " + foundViolations.size() + " violations:");
   for (Violation v : foundViolations) {
    LOG.debug(v.getReporter() + " " + v.getSeverity() + " (" + v.getRule().or("?") + ") " + v.getFile() + " "
      + v.getStartLine() + " -> " + v.getEndLine());
   }
  }
  return foundViolations;
 }
}
