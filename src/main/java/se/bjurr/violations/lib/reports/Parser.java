package se.bjurr.violations.lib.reports;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import se.bjurr.violations.lib.ViolationsLogger;
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
  GENERIC(new GenericParser()),
  FINDBUGS(new FindbugsParser()), //
  FLAKE8(new Flake8Parser()), //
  FXCOP(new FxCopParser()), //
  GENDARME(new GendarmeParser()), //
  IAR(new IARParser()), //
  JACOCO(new JacocoParser()), //
  JCREPORT(new JCReportParser()), //
  JSLINT(new JSLintParser()), //
  JUNIT(new JUnitParser()), //
  LINT(new LintParser()), //
  KLOCWORK(new KlocworkParser()), //
  KOTLINMAVEN(new KotlinMavenParser()), //
  KOTLINGRADLE(new KotlinGradleParser()), //
  MSCPP(new MSCPPParser()), //
  MSBULDLOG(new MSBuildLogParser()), //
  MYPY(new MyPyParser()), //
  GOLINT(new GoLintParser()), //
  GOOGLEERRORPRONE(new GoogleErrorProneParser()), //
  PERLCRITIC(new PerlCriticParser()), //
  PITEST(new PiTestParser()), //
  PMD(new PMDParser()), //
  PROTOLINT(new ProtoLintParser()), //
  PYDOCSTYLE(new PyDocStyleParser()), //
  PYLINT(new PyLintParser()), //
  RESHARPER(new ResharperParser()), //
  SBTSCALAC(new SbtScalacParser()), //
  SIMIAN(new SimianParser()), //
  SONAR(new SonarParser()), //
  STYLECOP(new StyleCopParser()), //
  XMLLINT(new XMLLintParser()), //
  YAMLLINT(new YAMLlintParser()), //
  ZPTLINT(new ZPTLintParser()), //
  DOCFX(new DocFXParser()), //
  PCLINT(new PCLintParser()), //
  CODECLIMATE(new CodeClimateParser()), //
  XUNIT(new XUnitParser());

  private transient ViolationsParser violationsParser;

  private Parser(final ViolationsParser violationsParser) {
    this.violationsParser = violationsParser;
  }

  public Set<Violation> findViolations(
      final ViolationsLogger violationsLogger, final List<File> includedFiles) {
    final Set<Violation> violations = new TreeSet<>();
    for (final File file : includedFiles) {
      String content = null;
      try {
        content = Utils.toString(new FileInputStream(file));
        if (Logger.getLogger(Parser.class.getSimpleName()).isLoggable(FINE)) {
          violationsLogger.log(
              FINE, "Using " + this.violationsParser.getClass().getName() + " to parse " + content);
        }
        violations.addAll(this.violationsParser.parseReportOutput(content, violationsLogger));
      } catch (final Throwable e) {
        final String withContent = content == null ? "" : " content:\n\n" + content;
        violationsLogger.log(
            SEVERE,
            "Error when parsing " + file.getAbsolutePath() + " as " + this.name() + withContent,
            e);
      }
    }
    return violations;
  }

  public ViolationsParser getViolationsParser() {
    return this.violationsParser;
  }
}
