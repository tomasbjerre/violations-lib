package se.bjurr.violations.lib.reports;

import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import se.bjurr.violations.lib.model.Violation;
import se.bjurr.violations.lib.parsers.*;
import se.bjurr.violations.lib.util.Utils;

public enum Parser {
  ANDROIDLINT(new AndroidLintParser()), //
  CHECKSTYLE(new CheckStyleParser()), //
  CODENARC(new CodeNarcParser()), //
  CLANG(new CLangParser()), //
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
  KLOCWORK(new KlocworkParser()), //
  MYPY(new MyPyParser()), //
  GOLINT(new GoLintParser()), //
  GOOGLEERRORPRONE(new GoogleErrorProneParser()), //
  PERLCRITIC(new PerlCriticParser()), //
  PITEST(new PiTestParser()), //
  PMD(new PMDParser()), //
  PYDOCSTYLE(new PyDocStyleParser()), //
  PYLINT(new PyLintParser()), //
  RESHARPER(new ResharperParser()), //
  SBTSCALAC(new SbtScalacParser()), //
  SIMIAN(new SimianParser()), //
  STYLECOP(new StyleCopParser()), //
  XMLLINT(new XMLLintParser()), //
  YAMLLINT(new YAMLlintParser()), //
  ZPTLINT(new ZPTLintParser()), //
  DOCFX(new DocFXParser()), //
  PCLINT(new PCLintParser());

  private static Logger LOG = Logger.getLogger(Parser.class.getSimpleName());
  private transient ViolationsParser violationsParser;

  private Parser(final ViolationsParser violationsParser) {
    this.violationsParser = violationsParser;
  }

  public List<Violation> findViolations(final List<File> includedFiles) {
    final List<Violation> violations = new ArrayList<>();
    for (final File file : includedFiles) {
      try {
        final String string = Utils.toString(new FileInputStream(file));
        violations.addAll(violationsParser.parseReportOutput(string));
      } catch (final Throwable e) {
        LOG.log(SEVERE, "Error when parsing " + file.getAbsolutePath() + " as " + this.name(), e);
      }
    }
    return violations;
  }

  public ViolationsParser getViolationsParser() {
    return violationsParser;
  }
}
