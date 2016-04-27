package se.bjurr.violations.lib.reports;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.parsers.*;

public enum Reporter {
 CHECKSTYLE(new CheckStyleParser()), //
 CSSLINT(new CSSLintParser()), //
 LINT(new LintParser()), //
 FINDBUGS(new FindbugsParser()), //
 JSHINT(new JSHintParser()), //
 PMD(new PMDParser()), //
 CPPCHECK(new CPPCheckParser()), //
 RESHARPER(new ResharperParser()), //
 FLAKE8(new Flake8Parser()), //
 CPPLINT(new CppLintParser()), //
 XMLLINT(new XMLLintParser()), //
 PERLCRITIC(new PerlCriticParser()), //
 PITEST(new PiTestParser()), //
 ANDROIDLINT(new AndroidLintParser());

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
