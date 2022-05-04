# Violations Lib
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib)

This is a Java library for parsing report files like static code analysis.

Example of supported reports are available [here](https://github.com/tomasbjerre/violations-lib/tree/master/src/test/resources).

A number of **parsers** have been implemented. Some **parsers** can parse output from several **reporters**.

| Reporter | Parser | Notes
| --- | --- | ---
| [_ARM-GCC_](https://developer.arm.com/open-source/gnu-toolchain/gnu-rm)               | `CLANG`              | 
| [_AndroidLint_](http://developer.android.com/tools/help/lint.html)                    | `ANDROIDLINT`        | 
| [_AnsibleLint_](https://github.com/willthames/ansible-lint)                           | `FLAKE8`             | With `-p`
| [_Bandit_](https://github.com/PyCQA/bandit)                                           | `CLANG`              | With `bandit -r examples/ -f custom -o bandit.out --msg-template "{abspath}:{line}: {severity}: {test_id}: {msg}"`
| [_CLang_](https://clang-analyzer.llvm.org/)                                           | `CLANG`              | 
| [_CPD_](http://pmd.sourceforge.net/pmd-4.3.0/cpd.html)                                | `CPD`                | 
| [_CPPCheck_](http://cppcheck.sourceforge.net/)                                        | `CPPCHECK`           | With `cppcheck test.cpp --output-file=cppcheck.xml --xml`
| [_CPPLint_](https://github.com/theandrewdavis/cpplint)                                | `CPPLINT`            | 
| [_CSSLint_](https://github.com/CSSLint/csslint)                                       | `CSSLINT`            | 
| [_Checkstyle_](http://checkstyle.sourceforge.net/)                                    | `CHECKSTYLE`         | 
| [_CloudFormation Linter_](https://github.com/aws-cloudformation/cfn-lint)             | `JUNIT`              | `cfn-lint . -f junit --output-file report-junit.xml`
| [_CodeClimate_](https://codeclimate.com/)                                             | `CODECLIMATE`        | 
| [_CodeNarc_](http://codenarc.sourceforge.net/)                                        | `CODENARC`           | 
| [_Dart_](https://dart.dev/)                                                           | `MACHINE`            | With `dart analyze --format=machine`
| [_Detekt_](https://github.com/arturbosch/detekt)                                      | `CHECKSTYLE`         | With `--output-format xml`.
| [_DocFX_](http://dotnet.github.io/docfx/)                                             | `DOCFX`              | 
| [_Doxygen_](https://www.stack.nl/~dimitri/doxygen/)                                   | `CLANG`              | 
| [_ERB_](https://www.puppetcookbook.com/posts/erb-template-validation.html)            | `CLANG`              | With `erb -P -x -T '-' "${it}" \| ruby -c 2>&1 >/dev/null \| grep '^-' \| sed -E 's/^-([a-zA-Z0-9:]+)/${filename}\1 ERROR:/p' > erbfiles.out`.
| [_ESLint_](https://github.com/sindresorhus/grunt-eslint)                              | `CHECKSTYLE`         | With `format: 'checkstyle'`.
| [_Findbugs_](http://findbugs.sourceforge.net/)                                        | `FINDBUGS`           | 
| [_Flake8_](http://flake8.readthedocs.org/en/latest/)                                  | `FLAKE8`             | 
| [_FxCop_](https://en.wikipedia.org/wiki/FxCop)                                        | `FXCOP`              | 
| [_GCC_](https://gcc.gnu.org/)                                                         | `CLANG`              | 
| [_GHS_](https://www.ghs.com/)                                                         | `GHS`                | 
| [_Gendarme_](http://www.mono-project.com/docs/tools+libraries/tools/gendarme/)        | `GENDARME`           | 
| [_Generic reporter_]()                                                                | `GENERIC`            | Will create one single violation with all the content as message.
| [_GoLint_](https://github.com/golang/lint)                                            | `GOLINT`             | 
| [_GoVet_](https://golang.org/cmd/vet/)                                                | `GOLINT`             | Same format as GoLint.
| [_GolangCI-Lint_](https://github.com/golangci/golangci-lint/)                         | `CHECKSTYLE`         | With `--out-format=checkstyle`.
| [_GoogleErrorProne_](https://github.com/google/error-prone)                           | `GOOGLEERRORPRONE`   | 
| [_HadoLint_](https://github.com/hadolint/hadolint/)                                   | `CHECKSTYLE`         | With `-f checkstyle`
| [_IAR_](https://www.iar.com/iar-embedded-workbench/)                                  | `IAR`                | With `--no_wrap_diagnostics`
| [_Infer_](http://fbinfer.com/)                                                        | `PMD`                | Facebook Infer. With `--pmd-xml`.
| [_JACOCO_](https://www.jacoco.org/)                                                   | `JACOCO`             | 
| [_JCReport_](https://github.com/jCoderZ/fawkez/wiki/JcReport)                         | `JCREPORT`           | 
| [_JSHint_](http://jshint.com/)                                                        | `JSLINT`             | With `--reporter=jslint` or the CHECKSTYLE parser with `--reporter=checkstyle`
| [_JUnit_](https://junit.org/junit4/)                                                  | `JUNIT`              | It only contains the failures.
| [_KTLint_](https://github.com/shyiko/ktlint)                                          | `CHECKSTYLE`         | 
| [_Klocwork_](http://www.klocwork.com/products-services/klocwork/static-code-analysis)  | `KLOCWORK`           | 
| [_KotlinGradle_](https://github.com/JetBrains/kotlin)                                 | `KOTLINGRADLE`       | Output from Kotlin Gradle Plugin.
| [_KotlinMaven_](https://github.com/JetBrains/kotlin)                                  | `KOTLINMAVEN`        | Output from Kotlin Maven Plugin.
| [_Lint_]()                                                                            | `LINT`               | A common XML format, used by different linters.
| [_MSBuildLog_](https://docs.microsoft.com/en-us/visualstudio/msbuild/obtaining-build-logs-with-msbuild?view=vs-2019)  | `MSBULDLOG`          | With `-fileLogger` use `.*msbuild\\.log$` as pattern or `-fl -flp:logfile=MyProjectOutput.log;verbosity=diagnostic` for a custom output filename
| [_MSCpp_](https://visualstudio.microsoft.com/vs/features/cplusplus/)                  | `MSCPP`              | 
| [_Mccabe_](https://pypi.python.org/pypi/mccabe)                                       | `FLAKE8`             | 
| [_MyPy_](https://pypi.python.org/pypi/mypy-lang)                                      | `MYPY`               | 
| [_NullAway_](https://github.com/uber/NullAway)                                        | `GOOGLEERRORPRONE`   | Same format as Google Error Prone.
| [_PCLint_](http://www.gimpel.com/html/pcl.htm)                                        | `PCLINT`             | PC-Lint using the same output format as the Jenkins warnings plugin, [_details here_](https://wiki.jenkins.io/display/JENKINS/PcLint+options)
| [_PHPCS_](https://github.com/squizlabs/PHP_CodeSniffer)                               | `CHECKSTYLE`         | With `phpcs api.php --report=checkstyle`.
| [_PHPPMD_](https://phpmd.org/)                                                        | `PMD`                | With `phpmd api.php xml ruleset.xml`.
| [_PMD_](https://pmd.github.io/)                                                       | `PMD`                | 
| [_Pep8_](https://github.com/PyCQA/pycodestyle)                                        | `FLAKE8`             | 
| [_PerlCritic_](https://github.com/Perl-Critic)                                        | `PERLCRITIC`         | 
| [_PiTest_](http://pitest.org/)                                                        | `PITEST`             | 
| [_ProtoLint_](https://github.com/yoheimuta/protolint)                                 | `PROTOLINT`          | 
| [_Puppet-Lint_](http://puppet-lint.com/)                                              | `CLANG`              | With `-log-format %{fullpath}:%{line}:%{column}: %{kind}: %{message}`
| [_PyDocStyle_](https://pypi.python.org/pypi/pydocstyle)                               | `PYDOCSTYLE`         | 
| [_PyFlakes_](https://pypi.python.org/pypi/pyflakes)                                   | `FLAKE8`             | 
| [_PyLint_](https://www.pylint.org/)                                                   | `PYLINT`             | With `pylint --output-format=parseable`.
| [_ReSharper_](https://www.jetbrains.com/resharper/)                                   | `RESHARPER`          | 
| [_RubyCop_](http://rubocop.readthedocs.io/en/latest/formatters/)                      | `CLANG`              | With `rubycop -f clang file.rb`
| [_SARIF_](https://github.com/oasis-tcs/sarif-spec)                                    | `SARIF`              | v2.x. Microsoft Visual C# can generate it with `ErrorLog="BuildErrors.sarif,version=2"`.
| [_SbtScalac_](http://www.scala-sbt.org/)                                              | `SBTSCALAC`          | 
| [_Scalastyle_](http://www.scalastyle.org/)                                            | `CHECKSTYLE`         | 
| [_Simian_](http://www.harukizaemon.com/simian/)                                       | `SIMIAN`             | 
| [_Sonar_](https://www.sonarqube.org/)                                                 | `SONAR`              | With `mvn sonar:sonar -Dsonar.analysis.mode=preview -Dsonar.report.export.path=sonar-report.json`. Removed in 7.7, see [SONAR-11670](https://jira.sonarsource.com/browse/SONAR-11670) but can be retrieved with: `curl --silent 'http://sonar-server/api/issues/search?componentKeys=unique-key&resolved=false' \| jq -f sonar-report-builder.jq > sonar-report.json`.
| [_Spotbugs_](https://spotbugs.github.io/)                                             | `FINDBUGS`           | 
| [_StyleCop_](https://stylecop.codeplex.com/)                                          | `STYLECOP`           | 
| [_SwiftLint_](https://github.com/realm/SwiftLint)                                     | `CHECKSTYLE`         | With `--reporter checkstyle`.
| [_TSLint_](https://palantir.github.io/tslint/usage/cli/)                              | `CHECKSTYLE`         | With `-t checkstyle`
| [_Valgrind_](https://valgrind.org/)                                                   | `VALGRIND`           | With `--xml=yes`.
| [_XMLLint_](http://xmlsoft.org/xmllint.html)                                          | `XMLLINT`            | 
| [_XUnit_](https://xunit.net/)                                                         | `XUNIT`              | It only contains the failures.
| [_YAMLLint_](https://yamllint.readthedocs.io/en/stable/index.html)                    | `YAMLLINT`           | With `-f parsable`
| [_ZPTLint_](https://pypi.python.org/pypi/zptlint)                                     | `ZPTLINT`            |

49 parsers and 75 reporters.

Missing a format? Open an issue [here](https://github.com/tomasbjerre/violations-lib/issues)!

## Usage

Very easy to use with a nice builder pattern

```java
  List<Violation> violations = violationsReporterApi() //
    .withPattern(".*/findbugs/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(FINDBUGS) //
    .violations();
```

It can also export the violations to the [_CodeClimate_](https://codeclimate.com/) and [_SARIF_](https://github.com/oasis-tcs/sarif-spec) formats with:

 * `.codeClimate()`
 * `.sarif()`

If you need to convert a report from one format to another, the [command line tool](https://github.com/tomasbjerre/violations-command-line/) is probably easiest to use.

The library is used in a bunch of other projects, these are some of them.

GitHub Action:

 * [Violation Comments Action](https://github.com/tomasbjerre/violation-comments-action)

Command line:
 * [Violations Command Line](https://github.com/tomasbjerre/violations-command-line) Can parse, log, fail, and/or export to `CodeClimate` and `Sarif` files.
 * [Violation Comments to GitLab Command Line](https://github.com/tomasbjerre/violation-comments-to-gitlab-command-line)
 * [Violation Comments to GitHub Command Line](https://github.com/tomasbjerre/violation-comments-to-github-command-line)
 * [Violation Comments to Bitbucket Server Command Line](https://github.com/tomasbjerre/violation-comments-to-bitbucket-server-command-line)
 * [Violation Comments to Bitbucket Cloud Command Line](https://github.com/tomasbjerre/violation-comments-to-bitbucket-cloud-command-line)

Gradle:
 * [Violation Comments to GitHub Gradle Plugin](https://github.com/tomasbjerre/violation-comments-to-github-gradle-plugin).
 * [Violation Comments to GitLab Gradle Plugin](https://github.com/tomasbjerre/violation-comments-to-gitlab-gradle-plugin).
 * [Violations Gradle Plugin](https://github.com/tomasbjerre/violations-gradle-plugin).

Maven:
 * [Violation Comments to GitHub Maven Plugin](https://github.com/tomasbjerre/violation-comments-to-github-maven-plugin).
 * [Violation Comments to GitLab Maven Plugin](https://github.com/tomasbjerre/violation-comments-to-gitlab-maven-plugin).
 * [Violations Maven Plugin](https://github.com/tomasbjerre/violations-maven-plugin).

Jenkins:
 * [Violation Comments to GitHub Jenkins Plugin](https://github.com/jenkinsci/violation-comments-to-github-plugin).
 * [Violation Comments to GitLab Jenkins Plugin](https://github.com/jenkinsci/violation-comments-to-gitlab-plugin).
 * [Violation Comments to Bitbucket Server Jenkins Plugin](https://github.com/jenkinsci/violation-comments-to-stash-plugin).

And these supporting libraries:
 * [Violation Comments to GitHub Lib](https://github.com/tomasbjerre/violation-comments-to-github-lib).
 * [Violation Comments to GitLab Lib](https://github.com/tomasbjerre/violation-comments-to-gitlab-lib).
 * [Violation Comments to Bitbucket Cloud Lib](https://github.com/tomasbjerre/violation-comments-to-bitbucket-cloud-lib).
 * [Violation Comments to Bitbucket Server Lib](https://github.com/tomasbjerre/violation-comments-to-bitbucket-server-lib).
 * [Violation Comments Lib](https://github.com/tomasbjerre/violation-comments-lib).
 * [Violations Git Lib](https://github.com/tomasbjerre/violations-git-lib).
