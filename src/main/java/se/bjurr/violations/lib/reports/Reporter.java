package se.bjurr.violations.lib.reports;

public enum Reporter {
  ARMGCC("ARM-GCC", Parser.CLANG, "https://developer.arm.com/open-source/gnu-toolchain/gnu-rm", ""),
  ANDROIDLINT(
      "AndroidLint", Parser.ANDROIDLINT, "http://developer.android.com/tools/help/lint.html", ""),
  ANSIBLELINT(
      "AnsibleLint", Parser.FLAKE8, "https://github.com/willthames/ansible-lint", "With `-p`"),
  CLANG("CLang", Parser.CLANG, "https://clang-analyzer.llvm.org/", ""),
  CPD("CPD", Parser.CPD, "http://pmd.sourceforge.net/pmd-4.3.0/cpd.html", ""),
  CPPCHECK("CPPCheck", Parser.CPPCHECK, "http://cppcheck.sourceforge.net/", ""),
  CPPLINT("CPPLint", Parser.CPPLINT, "https://github.com/theandrewdavis/cpplint", ""),
  CSSLINT("CSSLint", Parser.CSSLINT, "https://github.com/CSSLint/csslint", ""),
  CHECKSTYLE("Checkstyle", Parser.CHECKSTYLE, "http://checkstyle.sourceforge.net/", ""),
  CODENARC("CodeNarc", Parser.CODENARC, "http://codenarc.sourceforge.net/", ""),
  DETEKT(
      "Detekt",
      Parser.CHECKSTYLE,
      "https://github.com/arturbosch/detekt",
      "With `--output-format xml`."),
  ERB(
      "ERB",
      Parser.CLANG,
      "https://www.puppetcookbook.com/posts/erb-template-validation.html",
      "With `erb -P -x -T '-' \"${it}\" | ruby -c 2>&1 >/dev/null | grep '^-' | sed -E 's/^-([a-zA-Z0-9:]+)/${filename}\\1 ERROR:/p' > erbfiles.out`."),
  DOCFX("DocFX", Parser.DOCFX, "http://dotnet.github.io/docfx/", ""),
  DOXYGEN("Doxygen", Parser.CLANG, "https://www.stack.nl/~dimitri/doxygen/", ""),
  ESLINT(
      "ESLint",
      Parser.CHECKSTYLE,
      "https://github.com/sindresorhus/grunt-eslint",
      "With `format: 'checkstyle'`."),
  FINDBUGS("Findbugs", Parser.FINDBUGS, "http://findbugs.sourceforge.net/", ""),
  FLAKE8("Flake8", Parser.FLAKE8, "http://flake8.readthedocs.org/en/latest/", ""),
  FXCOP("FxCop", Parser.FXCOP, "https://en.wikipedia.org/wiki/FxCop", ""),
  GCC("GCC", Parser.CLANG, "https://gcc.gnu.org/", ""),
  GENDARME(
      "Gendarme",
      Parser.GENDARME,
      "http://www.mono-project.com/docs/tools+libraries/tools/gendarme/",
      ""),
  GOLINT("GoLint", Parser.GOLINT, "https://github.com/golang/lint", ""),
  GOVET("GoVet", Parser.GOLINT, "https://golang.org/cmd/vet/", "Same format as GoLint."),
  GOOGLEERRORPRONE(
      "GoogleErrorProne", Parser.GOOGLEERRORPRONE, "https://github.com/google/error-prone", ""),
  INFER("Infer", Parser.PMD, "http://fbinfer.com/", "Facebook Infer. With `--pmd-xml`."),
  IAR(
      "IAR",
      Parser.IAR,
      "https://www.iar.com/iar-embedded-workbench/",
      "With `--no_wrap_diagnostics`"),
  JCREPORT("JCReport", Parser.JCREPORT, "https://github.com/jCoderZ/fawkez/wiki/JcReport", ""),
  JSHINT("JSHint", Parser.JSHINT, "http://jshint.com/", ""),
  KTLINT("KTLint", Parser.CHECKSTYLE, "https://github.com/shyiko/ktlint", ""),
  KLOCWORK(
      "Klocwork",
      Parser.KLOCWORK,
      "http://www.klocwork.com/products-services/klocwork/static-code-analysis",
      ""),
  KOTLINGRADLE(
      "KotlinGradle",
      Parser.KOTLINGRADLE,
      "https://github.com/JetBrains/kotlin",
      "Output from Kotlin Gradle Plugin."),
  KOTLINMAVEN(
      "KotlinMaven",
      Parser.KOTLINMAVEN,
      "https://github.com/JetBrains/kotlin",
      "Output from Kotlin Maven Plugin."),
  LINT("Lint", Parser.LINT, "", "A common XML format, used by different linters."),
  MCCABE("Mccabe", Parser.FLAKE8, "https://pypi.python.org/pypi/mccabe", ""),
  MYPY("MyPy", Parser.MYPY, "https://pypi.python.org/pypi/mypy-lang", ""),
  MSCPP("MSCpp", Parser.MSCPP, "https://visualstudio.microsoft.com/vs/features/cplusplus/", ""),
  NULLAWAY(
      "NullAway",
      Parser.GOOGLEERRORPRONE,
      "https://github.com/uber/NullAway",
      "Same format as Google Error Prone."),
  PCLINT(
      "PCLint",
      Parser.PCLINT,
      "http://www.gimpel.com/html/pcl.htm",
      "PC-Lint using the same output format as the Jenkins warnings plugin, [_details here_](https://wiki.jenkins.io/display/JENKINS/PcLint+options)"),
  PHPCS(
      "PHPCS",
      Parser.CHECKSTYLE,
      "https://github.com/squizlabs/PHP_CodeSniffer",
      "With `phpcs api.php --report=checkstyle`."),
  PHPPMD("PHPPMD", Parser.PMD, "https://phpmd.org/", "With `phpmd api.php xml ruleset.xml`."),
  PMD("PMD", Parser.PMD, "https://pmd.github.io/", ""),
  PEP8("Pep8", Parser.FLAKE8, "https://github.com/PyCQA/pycodestyle", ""),
  PERLCRITIC("PerlCritic", Parser.PERLCRITIC, "https://github.com/Perl-Critic", ""),
  PITEST("PiTest", Parser.PITEST, "http://pitest.org/", ""),
  PUPPETLINT(
      "Puppet-Lint",
      Parser.CLANG,
      "http://puppet-lint.com/",
      "With `-log-format %{fullpath}:%{line}:%{column}: %{kind}: %{message}`"),
  PYDOCSTYLE("PyDocStyle", Parser.PYDOCSTYLE, "https://pypi.python.org/pypi/pydocstyle", ""),
  PYFLAKES("PyFlakes", Parser.FLAKE8, "https://pypi.python.org/pypi/pyflakes", ""),
  PYLINT(
      "PyLint",
      Parser.PYLINT,
      "https://www.pylint.org/",
      "With `pylint --output-format=parseable`."),
  RESHARPER("ReSharper", Parser.RESHARPER, "https://www.jetbrains.com/resharper/", ""),
  RUBYCOP(
      "RubyCop",
      Parser.CLANG,
      "http://rubocop.readthedocs.io/en/latest/formatters/",
      "With `rubycop -f clang file.rb`"),
  SBTSCALAC("SbtScalac", Parser.SBTSCALAC, "http://www.scala-sbt.org/", ""),
  SIMIAN("Simian", Parser.SIMIAN, "http://www.harukizaemon.com/simian/", ""),
  SPOTBUGS("Spotbugs", Parser.FINDBUGS, "https://spotbugs.github.io/", ""),
  STYLECOP("StyleCop", Parser.STYLECOP, "https://stylecop.codeplex.com/", ""),
  SWIFTLINT(
      "SwiftLint",
      Parser.CHECKSTYLE,
      "https://github.com/realm/SwiftLint",
      "With `--reporter checkstyle`."),
  TSLINT(
      "TSLint",
      Parser.CHECKSTYLE,
      "https://palantir.github.io/tslint/usage/cli/",
      "With `-t checkstyle`"),
  XMLLINT("XMLLint", Parser.XMLLINT, "http://xmlsoft.org/xmllint.html", ""),
  YAMLLINT(
      "YAMLLint",
      Parser.YAMLLINT,
      "https://yamllint.readthedocs.io/en/stable/index.html",
      "With `-f parsable`"),
  ZPTLINT("ZPTLint", Parser.ZPTLINT, "https://pypi.python.org/pypi/zptlint", "");

  private final String url;
  private final String name;
  private final Parser parser;
  private final String note;

  Reporter(final String name, final Parser parser, String url, final String note) {
    this.name = name;
    this.parser = parser;
    this.url = url;
    this.note = note;
  }

  public Parser getParser() {
    return parser;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public String getNote() {
    return note;
  }
}
