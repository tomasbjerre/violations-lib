package se.bjurr.violations.lib.reports;

import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.parsers.AndroidLintParser;
import se.bjurr.violations.lib.parsers.CPDParser;
import se.bjurr.violations.lib.parsers.CPPCheckParser;
import se.bjurr.violations.lib.parsers.CSSLintParser;
import se.bjurr.violations.lib.parsers.CheckStyleParser;
import se.bjurr.violations.lib.parsers.CodeNarcParser;
import se.bjurr.violations.lib.parsers.CppLintParser;
import se.bjurr.violations.lib.parsers.FindbugsParser;
import se.bjurr.violations.lib.parsers.Flake8Parser;
import se.bjurr.violations.lib.parsers.FxCopParser;
import se.bjurr.violations.lib.parsers.GendarmeParser;
import se.bjurr.violations.lib.parsers.JCReportParser;
import se.bjurr.violations.lib.parsers.JSHintParser;
import se.bjurr.violations.lib.parsers.LintParser;
import se.bjurr.violations.lib.parsers.PMDParser;
import se.bjurr.violations.lib.parsers.PerlCriticParser;
import se.bjurr.violations.lib.parsers.PiTestParser;
import se.bjurr.violations.lib.parsers.PyLintParser;
import se.bjurr.violations.lib.parsers.ResharperParser;
import se.bjurr.violations.lib.parsers.SimianParser;
import se.bjurr.violations.lib.parsers.StyleCopParser;
import se.bjurr.violations.lib.parsers.ViolationsParser;
import se.bjurr.violations.lib.parsers.XMLLintParser;
import se.bjurr.violations.lib.parsers.ZPTLintParser;
import se.bjurr.violations.lib.util.Utils;

public enum Reporter {
 ANDROIDLINT(new AndroidLintParser()), //
 CHECKSTYLE(new CheckStyleParser()), //
 CODENARC(new CodeNarcParser()), //
 CPD(new CPDParser()), //
 CPPCHECK(new CPPCheckParser()), //
 CPPLINT(new CppLintParser()), //
 CSSLINT(new CSSLintParser()), //
 FINDBUGS(new FindbugsParser()), //
 FLAKE8(new Flake8Parser()), //
 FXCOP(new FxCopParser()), //
 GENDARME(new GendarmeParser()), //
 JCREPORT(new JCReportParser()), //
 JSHINT(new JSHintParser()), //
 LINT(new LintParser()), //
 PERLCRITIC(new PerlCriticParser()), //
 PITEST(new PiTestParser()), //
 PMD(new PMDParser()), //
 PYLINT(new PyLintParser()), //
 RESHARPER(new ResharperParser()), //
 SIMIAN(new SimianParser()), //
 STYLECOP(new StyleCopParser()), //
 XMLLINT(new XMLLintParser()), //
 ZPTLINT(new ZPTLintParser());

 private static Logger LOG = Logger.getLogger(Reporter.class.getSimpleName());
 private ViolationsParser violationsParser;

 private Reporter(ViolationsParser violationsParser) {
  this.violationsParser = violationsParser;
 }

 public List<Violation> findViolations(List<File> includedFiles) {
  List<Violation> violations = new ArrayList<>();
  for (File file : includedFiles) {
   try {
    String string = Utils.toString(new FileInputStream(file));
    violations.addAll(violationsParser.parseFile(string));
   } catch (Exception e) {
    LOG.log(SEVERE, "Error when parsing " + file.getAbsolutePath() + " as " + this.name(), e);
   }
  }
  return violations;
 }

 public ViolationsParser getViolationsParser() {
  return violationsParser;
 }
}
