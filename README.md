# Violations Lib [![Build Status](https://travis-ci.org/tomasbjerre/violations-lib.svg?branch=master)](https://travis-ci.org/tomasbjerre/violations-lib) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.violations/violations-lib)

This is a library for parsing report files from static code analyzis.

It supports:
 * [_PMD_](https://pmd.github.io/)
 * [_Findbugs_](http://findbugs.sourceforge.net/)
 * [_Checkstyle_](http://checkstyle.sourceforge.net/)
 * [_CSSLint_](https://github.com/CSSLint/csslint)
 * [_JSHint_](http://jshint.com/)

Very easy to use with a nice builder patternn
```
  List<Violation> actual = violationsApi() //
    .withPattern(".*/findbugs/.*\\.xml$") //
    .inFolder(rootFolder) //
    .findAll(FINDBUGS) //
    .violations();
```

## Developer instructions

To build the code, have a look at `.travis.yml`.

To do a release you need to do `./gradlew release` and release the artifact from [staging](https://oss.sonatype.org/#stagingRepositories). More information [here](http://central.sonatype.org/pages/releasing-the-deployment.html).
