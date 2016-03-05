# Violations Lib [![Build Status](https://travis-ci.org/tomasbjerre/violations-lib.svg?branch=master)](https://travis-ci.org/tomasbjerre/violations-lib) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib)

This is a library for parsing report files from static code analysis.

It supports:
 * [_PMD_](https://pmd.github.io/)
 * [_Findbugs_](http://findbugs.sourceforge.net/)
 * [_Checkstyle_](http://checkstyle.sourceforge.net/)
 * [_CPPCheck_](http://cppcheck.sourceforge.net/)
 * [_CSSLint_](https://github.com/CSSLint/csslint)
 * [_JSHint_](http://jshint.com/)
 * [_Resharper_](https://www.jetbrains.com/resharper/)

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
 * [Violation Comments to GitHub Lib](https://github.com/tomasbjerre/violation-comments-to-github-lib).
 * [Violation Comments Lib](https://github.com/tomasbjerre/violation-comments-lib).

## Developer instructions

To build the code, have a look at `.travis.yml`.

To do a release you need to do `./gradlew release` and release the artifact from [staging](https://oss.sonatype.org/#stagingRepositories). More information [here](http://central.sonatype.org/pages/releasing-the-deployment.html).
