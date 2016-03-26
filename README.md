# Violations Lib [![Build Status](https://travis-ci.org/tomasbjerre/violations-lib.svg?branch=master)](https://travis-ci.org/tomasbjerre/violations-lib) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib)

This is a library for parsing report files from static code analysis.

It supports:
 * [_Checkstyle_](http://checkstyle.sourceforge.net/)
 * [_CPPLint_](https://github.com/theandrewdavis/cpplint)
 * [_CPPCheck_](http://cppcheck.sourceforge.net/)
 * [_CSSLint_](https://github.com/CSSLint/csslint)
 * [_Findbugs_](http://findbugs.sourceforge.net/)
 * [_Flake8_](http://flake8.readthedocs.org/en/latest/) ([_PyLint_](https://www.pylint.org/), [_Pep8_](https://github.com/PyCQA/pycodestyle), [_Mccabe_](https://pypi.python.org/pypi/mccabe), [_PyFlakes_](https://pypi.python.org/pypi/pyflakes))
 * [_JSHint_](http://jshint.com/)
 * _Lint_ A common XML format, used by different linters.
 * [_PerlCritic_](https://github.com/Perl-Critic)
 * [_PiTest_](http://pitest.org/)
 * [_PMD_](https://pmd.github.io/)
 * [_ReSharper_](https://www.jetbrains.com/resharper/)
 * [_XMLLint_](http://xmlsoft.org/xmllint.html)

Very easy to use with a nice builder pattern
```
  List<Violation> violations = violationsReporterApi() //
    .withPattern(".*/findbugs/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(FINDBUGS) //
    .violations();
```

Or

```
  List<Violation> violations = violationsAccumulatedReporterApi() //
    .withViolations( //
      violationsReporterApi() //
      .withPattern(".*/findbugs/.*\\.xml$") //
      .inFolder(rootFolder) //
      .findAll(FINDBUGS) //
      .violations() //
    ) //
    .withViolations( //
      violationsReporterApi() //
      .withPattern(".*/pmd/.*\\.xml$") //
      .inFolder(rootFolder) //
      .findAll(PMD) //
      .violations() //
    ) //
    .withAtLeastSeverity(ERROR)//
    .orderedBy(FILE)//
    .violations();
```

It is used by:
 * [Violation Comments to GitHub Gradle Plugin](https://github.com/tomasbjerre/violation-comments-to-github-gradle-plugin).
 * [Violation Comments to GitHub Maven Plugin](https://github.com/tomasbjerre/violation-comments-to-github-maven-plugin).
 * [Violation Comments to GitHub Jenkins Plugin](https://github.com/tomasbjerre/violation-comments-to-github-jenkins-plugin).
 * [Violation Comments to GitHub Lib](https://github.com/tomasbjerre/violation-comments-to-github-lib).
 * [Violation Comments Lib](https://github.com/tomasbjerre/violation-comments-lib).

## Developer instructions

To build the code, have a look at `.travis.yml`.

To do a release you need to do `./gradlew release` and release the artifact from [staging](https://oss.sonatype.org/#stagingRepositories). More information [here](http://central.sonatype.org/pages/releasing-the-deployment.html).
