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
import se.bjurr.violations.lib.parsers.AndroidLintParser;
import se.bjurr.violations.lib.parsers.CLangParser;
import se.bjurr.violations.lib.parsers.CPDParser;
import se.bjurr.violations.lib.parsers.CPPCheckParser;
import se.bjurr.violations.lib.parsers.CSSLintParser;
import se.bjurr.violations.lib.parsers.CheckStyleParser;
import se.bjurr.violations.lib.parsers.CodeClimateParser;
import se.bjurr.violations.lib.parsers.CodeNarcParser;
import se.bjurr.violations.lib.parsers.CppLintParser;
import se.bjurr.violations.lib.parsers.DocFXParser;
import se.bjurr.violations.lib.parsers.FindbugsParser;
import se.bjurr.violations.lib.parsers.Flake8Parser;
import se.bjurr.violations.lib.parsers.FxCopParser;
import se.bjurr.violations.lib.parsers.GendarmeParser;
import se.bjurr.violations.lib.parsers.GenericParser;
import se.bjurr.violations.lib.parsers.GoLintParser;
import se.bjurr.violations.lib.parsers.GoogleErrorProneParser;
import se.bjurr.violations.lib.parsers.IARParser;
import se.bjurr.violations.lib.parsers.JCReportParser;
import se.bjurr.violations.lib.parsers.JSLintParser;
import se.bjurr.violations.lib.parsers.JUnitParser;
import se.bjurr.violations.lib.parsers.KlocworkParser;
import se.bjurr.violations.lib.parsers.KotlinGradleParser;
import se.bjurr.violations.lib.parsers.KotlinMavenParser;
import se.bjurr.violations.lib.parsers.LintParser;
import se.bjurr.violations.lib.parsers.MSCPPParser;
import se.bjurr.violations.lib.parsers.MyPyParser;
import se.bjurr.violations.lib.parsers.PCLintParser;
import se.bjurr.violations.lib.parsers.PMDParser;
import se.bjurr.violations.lib.parsers.PerlCriticParser;
import se.bjurr.violations.lib.parsers.PiTestParser;
import se.bjurr.violations.lib.parsers.ProtoLintParser;
import se.bjurr.violations.lib.parsers.PyDocStyleParser;
import se.bjurr.violations.lib.parsers.PyLintParser;
import se.bjurr.violations.lib.parsers.ResharperParser;
import se.bjurr.violations.lib.parsers.SbtScalacParser;
import se.bjurr.violations.lib.parsers.SimianParser;
import se.bjurr.violations.lib.parsers.SonarParser;
import se.bjurr.violations.lib.parsers.StyleCopParser;
import se.bjurr.violations.lib.parsers.ViolationsParser;
import se.bjurr.violations.lib.parsers.XMLLintParser;
import se.bjurr.violations.lib.parsers.XUnitParser;
import se.bjurr.violations.lib.parsers.YAMLlintParser;
import se.bjurr.violations.lib.parsers.ZPTLintParser;
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
  JCREPORT(new JCReportParser()), //
  JSLINT(new JSLintParser()), //
  JUNIT(new JUnitParser()), //
  LINT(new LintParser()), //
  KLOCWORK(new KlocworkParser()), //
  KOTLINMAVEN(new KotlinMavenParser()), //
  KOTLINGRADLE(new KotlinGradleParser()), //
  MSCPP(new MSCPPParser()), //
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
