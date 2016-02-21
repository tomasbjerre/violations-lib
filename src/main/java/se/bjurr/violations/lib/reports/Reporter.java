package se.bjurr.violations.lib.reports;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.parsers.CSSLintParser;
import se.bjurr.violations.lib.parsers.CheckStyleParser;
import se.bjurr.violations.lib.parsers.FindbugsParser;
import se.bjurr.violations.lib.parsers.ViolationsParser;

public enum Reporter {
 CHECKSTYLE(new CheckStyleParser()), //
 CSSLINT(new CSSLintParser()), //
 FINDBUGS(new FindbugsParser());

 private static Logger LOG = Logger.getLogger(Reporter.class.getSimpleName());
 private ViolationsParser violationsParser;

 private Reporter(ViolationsParser violationsParser) {
  this.violationsParser = violationsParser;
 }

 public List<Violation> findViolations(List<File> includedFiles) {
  List<Violation> violations = newArrayList();
  for (File file : includedFiles) {
   try {
    violations.addAll(violationsParser.parseFile(file));
   } catch (Exception e) {
    LOG.log(SEVERE, "Error when parsing " + file.getAbsolutePath() + " as " + this.name(), e);
   }
  }
  return violations;
 }
}
