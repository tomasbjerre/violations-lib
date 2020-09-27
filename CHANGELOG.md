
 # Violations lib changelog

Changelog of Violations lib.

## Unreleased
### GitHub [#101](https://github.com/tomasbjerre/violations-lib/issues/101) Replace the regexp xml-parsing with STL xml parser  

**Rewriting CSSLintParser parser**


[0516f86b09078c7](https://github.com/tomasbjerre/violations-lib/commit/0516f86b09078c7) Tomas Bjerre *2020-09-27 14:19:19*

**Rewriting PMDParser parser**


[5f968ce7f75969e](https://github.com/tomasbjerre/violations-lib/commit/5f968ce7f75969e) Tomas Bjerre *2020-09-27 14:07:01*

**Rewriting CheckstyleParser parser**


[0b0b28466969a11](https://github.com/tomasbjerre/violations-lib/commit/0b0b28466969a11) Tomas Bjerre *2020-09-27 13:53:30*

**Rewriting AndroidLintParser parser**


[d2e8ca2e819c989](https://github.com/tomasbjerre/violations-lib/commit/d2e8ca2e819c989) Tomas Bjerre *2020-09-27 13:44:48*

**Rewriting FindbugsParser parser**


[c41fa9da64086e5](https://github.com/tomasbjerre/violations-lib/commit/c41fa9da64086e5) Tomas Bjerre *2020-09-27 09:32:45*

**Rewriting CPPCheckParser parser**


[17b0dfe72755568](https://github.com/tomasbjerre/violations-lib/commit/17b0dfe72755568) Tomas Bjerre *2020-09-26 19:11:55*


## 1.130
### GitHub [#101](https://github.com/tomasbjerre/violations-lib/issues/101) Replace the regexp xml-parsing with STL xml parser  

**Rewriting JUnit parser**


[2eac2357a6a9606](https://github.com/tomasbjerre/violations-lib/commit/2eac2357a6a9606) Tomas Bjerre *2020-09-25 18:35:54*


### GitHub [#108](https://github.com/tomasbjerre/violations-lib/issues/108) Parse jest-junit  

**Rewriting JUnit parser**


[2eac2357a6a9606](https://github.com/tomasbjerre/violations-lib/commit/2eac2357a6a9606) Tomas Bjerre *2020-09-25 18:35:54*


## 1.129
### GitHub [#107](https://github.com/tomasbjerre/violations-lib/issues/107) Checkstyle: don&#39;t fail if line attribute is not present  

**Allow Checkstyle reports with no line**


[708c769aeca3dff](https://github.com/tomasbjerre/violations-lib/commit/708c769aeca3dff) Tomas Bjerre *2020-09-23 15:47:40*


## 1.128
### No issue

**change parser name to generic**


[99446c2bb39cbe5](https://github.com/tomasbjerre/violations-lib/commit/99446c2bb39cbe5) Anirudh Bagri *2020-09-14 06:15:22*

**map file parser to generic reporter**


[dfe2ac1d46365f4](https://github.com/tomasbjerre/violations-lib/commit/dfe2ac1d46365f4) Anirudh Bagri *2020-09-10 15:21:30*

**add file parser**


[0c9846f76ea9a14](https://github.com/tomasbjerre/violations-lib/commit/0c9846f76ea9a14) Anirudh Bagri *2020-09-10 15:12:07*


## 1.127
### GitHub [#95](https://github.com/tomasbjerre/violations-lib/issues/95) Add support for &quot;suggested change&quot;    *enhancement*  

**Moving PatchParser from violation-comments-lib**


[5c52a3933792d14](https://github.com/tomasbjerre/violations-lib/commit/5c52a3933792d14) Tomas Bjerre *2020-09-06 05:24:56*


## 1.126
### GitHub [#95](https://github.com/tomasbjerre/violations-lib/issues/95) Add support for &quot;suggested change&quot;    *enhancement*  

**Moving PatchParser from violation-comments-lib**


[8430d1796086717](https://github.com/tomasbjerre/violations-lib/commit/8430d1796086717) Tomas Bjerre *2020-09-06 05:16:01*


## 1.125
### No issue

**adjusting message of junit parser**


[96e73a365e55117](https://github.com/tomasbjerre/violations-lib/commit/96e73a365e55117) Tomas Bjerre *2020-09-05 06:35:02*


## 1.124
### GitHub [#100](https://github.com/tomasbjerre/violations-lib/issues/100) JUnit failures without type or message will be skipped  

**Support JUNI reports without message**


[925c96b9f43b734](https://github.com/tomasbjerre/violations-lib/commit/925c96b9f43b734) Tomas Bjerre *2020-09-04 15:41:14*


## 1.123
### GitHub [#98](https://github.com/tomasbjerre/violations-lib/issues/98) compareTo method of Violation is broken and filters too many unique warnings  

**Avoid removing violations that are not duplicates**


[0b81ef8c7fcdd95](https://github.com/tomasbjerre/violations-lib/commit/0b81ef8c7fcdd95) Tomas Bjerre *2020-08-27 17:43:08*


## 1.122
### GitHub [#97](https://github.com/tomasbjerre/violations-lib/issues/97) NoSuchElementException when parsing JUnit report without type  

**JUnit missing message and type**


[2d06bb63c1266b9](https://github.com/tomasbjerre/violations-lib/commit/2d06bb63c1266b9) Tomas Bjerre *2020-08-27 16:20:21*


## 1.121
### No issue

**ignore codeclimate json**


[c04ae1f28ea2dda](https://github.com/tomasbjerre/violations-lib/commit/c04ae1f28ea2dda) Tomas Bjerre *2020-07-26 06:30:38*

**Add test for MyPy error messages containing colons**


[01cb5aa61ef7557](https://github.com/tomasbjerre/violations-lib/commit/01cb5aa61ef7557) David Kolossa *2020-07-25 09:18:59*

**Non-greedy match in MyPy Parser**

 * This avoids cutting of error messages containing colons &quot;:&quot; 
 * e.g.: 
 * error: Need type annotation for &#39;a&#39; (hint: &quot;a: List[&lt;type&gt;] = ...&quot;) 

[3d40d5ec9564d35](https://github.com/tomasbjerre/violations-lib/commit/3d40d5ec9564d35) David Kolossa *2020-07-25 08:37:08*


## 1.120
### No issue

**New: using Set for violations everywhere, to avoid transforming with List**


[d8c2e0c0851f8cc](https://github.com/tomasbjerre/violations-lib/commit/d8c2e0c0851f8cc) Tomas Bjerre *2020-07-05 10:32:22*


## 1.119
### No issue

**New: allow custom logger**


[84e42361237a9fa](https://github.com/tomasbjerre/violations-lib/commit/84e42361237a9fa) Tomas Bjerre *2020-07-05 09:13:11*


## 1.118
### GitHub [#91](https://github.com/tomasbjerre/violations-lib/issues/91) Can&#39;t parse Junit5 (Jupiter) report  

**Allow missing message in JUnit report**


[bc74058e8c2963c](https://github.com/tomasbjerre/violations-lib/commit/bc74058e8c2963c) Tomas Bjerre *2020-06-26 16:58:57*


## 1.117
### No issue

**Sonar: parse endColumn**

 * Signed-off-by: Semyon Levin &lt;levin.semen@gmail.com&gt; 

[2fc844d71ffe6d5](https://github.com/tomasbjerre/violations-lib/commit/2fc844d71ffe6d5) Semyon Levin *2020-06-10 20:32:03*

**PMD: parse endColumn**

 * Signed-off-by: Semyon Levin &lt;levin.semen@gmail.com&gt; 

[596e76a21cafd51](https://github.com/tomasbjerre/violations-lib/commit/596e76a21cafd51) Semyon Levin *2020-06-10 20:31:55*

**Add Violation.endColumn property**

 * Signed-off-by: Semyon Levin &lt;levin.semen@gmail.com&gt; 

[e513933d7505021](https://github.com/tomasbjerre/violations-lib/commit/e513933d7505021) Semyon Levin *2020-06-10 20:31:38*


## 1.116
### No issue

**Fixing SECURITY-1854**


[b100cdb54e18cae](https://github.com/tomasbjerre/violations-lib/commit/b100cdb54e18cae) Tomas Bjerre *2020-05-20 14:42:04*


## 1.115
### GitHub [#86](https://github.com/tomasbjerre/violations-lib/issues/86) CppCheck parser errors out on translation unit with no warnings followed by self-closing XML tag  

**Add better test for**


[27c0fe5f01ab3a6](https://github.com/tomasbjerre/violations-lib/commit/27c0fe5f01ab3a6) Timothy Brackett *2020-05-16 15:20:20*

**Testing empty cppcheck**


[553fd892d041c29](https://github.com/tomasbjerre/violations-lib/commit/553fd892d041c29) Tomas Bjerre *2020-05-15 16:20:49*


### No issue

**Reformatting after merge**


[c1bbc0ce5d874c0](https://github.com/tomasbjerre/violations-lib/commit/c1bbc0ce5d874c0) Tomas Bjerre *2020-05-16 16:58:22*

**Make CPPCheck opening regex more specific**


[aef04ecae9e9874](https://github.com/tomasbjerre/violations-lib/commit/aef04ecae9e9874) Timothy Brackett *2020-05-16 15:21:28*


## 1.114
### GitHub [#85](https://github.com/tomasbjerre/violations-lib/issues/85) JUnitParser does not detect failures in xml generated by Ant&#39;s Junit task  

**Fix: JUunit parser**


[39ecef0ea441b89](https://github.com/tomasbjerre/violations-lib/commit/39ecef0ea441b89) Tomas Bjerre *2020-05-12 14:59:52*


## 1.113
### GitHub [#1](https://github.com/tomasbjerre/violations-lib/pull/1) This is just a test...  

**Not including lines in codeclimate fingerprint tomasbjerre/violations-maven-plugin**


[1fff248fab4afe3](https://github.com/tomasbjerre/violations-lib/commit/1fff248fab4afe3) Tomas Bjerre *2020-03-29 14:08:24*


## 1.112
### No issue

**Doc**


[24a917bc11d0a73](https://github.com/tomasbjerre/violations-lib/commit/24a917bc11d0a73) Tomas Bjerre *2020-03-03 18:22:05*


## 1.111
### GitHub [#82](https://github.com/tomasbjerre/violations-lib/issues/82) CPPCheckParser does not parse auto closed &lt;error/&gt; tags  

**CPPCheckParser results version 2**


[2b96ad75cee880a](https://github.com/tomasbjerre/violations-lib/commit/2b96ad75cee880a) Tomas Bjerre *2020-02-06 18:27:04*


### GitHub [#84](https://github.com/tomasbjerre/violations-lib/issues/84) XUnit parser    *enhancement*  

**XUnit tomasbjerre/violations-lib**


[04762515e66b859](https://github.com/tomasbjerre/violations-lib/commit/04762515e66b859) Tomas Bjerre *2020-03-03 16:42:09*


## 1.110
### GitHub [#82](https://github.com/tomasbjerre/violations-lib/issues/82) CPPCheckParser does not parse auto closed &lt;error/&gt; tags  

**CPPCheckParser with auto closed <error/> tags**


[64781db68be28d7](https://github.com/tomasbjerre/violations-lib/commit/64781db68be28d7) Tomas Bjerre *2020-02-03 15:57:04*


## 1.109
### No issue

**Filtering out duplicated violations**


[487cfbbad3f0692](https://github.com/tomasbjerre/violations-lib/commit/487cfbbad3f0692) Tomas Bjerre *2020-01-19 15:50:54*

**Correcting Spotbugs warnings**


[97ed66ad9a70c31](https://github.com/tomasbjerre/violations-lib/commit/97ed66ad9a70c31) Tomas Bjerre *2020-01-03 06:41:18*


## 1.108
### No issue

**Add support for newer sonar report format**


[432e5ab3f1efceb](https://github.com/tomasbjerre/violations-lib/commit/432e5ab3f1efceb) Zakey Faieq *2020-01-02 22:18:04*


## 1.107
### No issue

**Add parser for protolint**


[80e3c8dd7fb95ea](https://github.com/tomasbjerre/violations-lib/commit/80e3c8dd7fb95ea) David *2019-10-21 20:25:12*


## 1.106
### GitHub [#71](https://github.com/tomasbjerre/violations-lib/issues/71) PyDocStyleParser fails with IndexOutOfBoundsException    *bug*  

**Parsing PyDocStyle generated with `-s`**


[2de776d9f8c8bbc](https://github.com/tomasbjerre/violations-lib/commit/2de776d9f8c8bbc) Tomas Bjerre *2019-10-10 15:22:54*


### GitHub [#78](https://github.com/tomasbjerre/violations-lib/issues/78) ResharperParser throws exception if message is empty    *bug*  

**Accepting empty message in ResharperParser**


[fd0896fd6dd242f](https://github.com/tomasbjerre/violations-lib/commit/fd0896fd6dd242f) Tomas Bjerre *2019-10-09 19:18:36*


### No issue

**1.105-SNAPSHOT**


[6cc2ebd6bcc2845](https://github.com/tomasbjerre/violations-lib/commit/6cc2ebd6bcc2845) Tomas Bjerre *2019-10-09 19:27:48*

**doc**


[ff1afd7f34348d6](https://github.com/tomasbjerre/violations-lib/commit/ff1afd7f34348d6) Tomas Bjerre *2019-10-09 16:57:30*

**Spotbugs**


[2cec50ee48b3f2c](https://github.com/tomasbjerre/violations-lib/commit/2cec50ee48b3f2c) Tomas Bjerre *2019-10-07 18:30:04*


## 1.103
### No issue

**More logging**


[266e22ad8778fa5](https://github.com/tomasbjerre/violations-lib/commit/266e22ad8778fa5) Tomas Bjerre *2019-10-05 06:21:10*

**Testing reportsfinder traversal**


[019eade65d41af1](https://github.com/tomasbjerre/violations-lib/commit/019eade65d41af1) Tomas Bjerre *2019-10-05 05:24:28*


## 1.102
### GitHub [#76](https://github.com/tomasbjerre/violations-lib/issues/76) Support CodeClimate as output format for GitLab integration    *enhancement*  

**Validating attributes in CodeClimate Parser**


[e07038ba58e9c62](https://github.com/tomasbjerre/violations-lib/commit/e07038ba58e9c62) Tomas Bjerre *2019-09-07 12:17:03*


### GitHub [#77](https://github.com/tomasbjerre/violations-lib/issues/77) Add hadolint support  

**Documenting HadoLint**


[d516f1a69b84b72](https://github.com/tomasbjerre/violations-lib/commit/d516f1a69b84b72) Tomas Bjerre *2019-09-09 14:24:15*


### No issue

**Always front slashes when searching FS**

 * Also adding logging the help determining why some files is not getting picked up. 

[7e237cc5f23517a](https://github.com/tomasbjerre/violations-lib/commit/7e237cc5f23517a) Tomas Bjerre *2019-10-04 14:44:12*

**Create FUNDING.yml**


[429014771ec2c64](https://github.com/tomasbjerre/violations-lib/commit/429014771ec2c64) Tomas Bjerre *2019-09-28 06:58:05*


## 1.100
### GitHub [#76](https://github.com/tomasbjerre/violations-lib/issues/76) Support CodeClimate as output format for GitLab integration    *enhancement*  

**Validating attributes in CodeClimate Parser**


[51074e66c7abce0](https://github.com/tomasbjerre/violations-lib/commit/51074e66c7abce0) Tomas Bjerre *2019-09-07 12:09:14*


## 1.99
### GitHub [#76](https://github.com/tomasbjerre/violations-lib/issues/76) Support CodeClimate as output format for GitLab integration    *enhancement*  

**CodeClimate**


[60420531325577f](https://github.com/tomasbjerre/violations-lib/commit/60420531325577f) Tomas Bjerre *2019-09-07 11:45:10*


## 1.98
### No issue

**Documenting Sonar**


[f6f7f7d209fa74d](https://github.com/tomasbjerre/violations-lib/commit/f6f7f7d209fa74d) Tomas Bjerre *2019-09-04 19:52:52*


## 1.97
### GitHub [#75](https://github.com/tomasbjerre/violations-lib/issues/75) Sonar parser doesn&#39;t work  

**Handling Sonar reports without severity**


[b7fff16b27cb2a4](https://github.com/tomasbjerre/violations-lib/commit/b7fff16b27cb2a4) Tomas Bjerre *2019-09-04 19:23:23*


## 1.96
### GitHub [#74](https://github.com/tomasbjerre/violations-lib/issues/74) JSHint parser uses JSLint format    *bug*  

**Renaming JSHINT parser to JSLINT**

 * Because the format parsed by the parser is actually JSLint. This breaks compatibility! 

[d34e2a36d3523a0](https://github.com/tomasbjerre/violations-lib/commit/d34e2a36d3523a0) Tomas Bjerre *2019-08-03 10:36:59*


## 1.95
### GitHub [#73](https://github.com/tomasbjerre/violations-lib/issues/73) New format: Bandit for Python    *enhancement*  

**Bandit**


[0af669fb166b952](https://github.com/tomasbjerre/violations-lib/commit/0af669fb166b952) Tomas Bjerre *2019-08-02 06:06:51*


## 1.94
### GitHub [#70](https://github.com/tomasbjerre/violations-lib/pull/70) Added JUnit parser    *enhancement*  

**Testing with Mavens Surefire Report**


[cd486448a7c4b65](https://github.com/tomasbjerre/violations-lib/commit/cd486448a7c4b65) Tomas Bjerre *2019-06-15 05:40:39*


### No issue

**OpenJDK 11 in Travis**


[d5e57cf07a068bb](https://github.com/tomasbjerre/violations-lib/commit/d5e57cf07a068bb) Tomas Bjerre *2019-06-11 17:10:38*

**doc**


[7ec4f26bf7b94a4](https://github.com/tomasbjerre/violations-lib/commit/7ec4f26bf7b94a4) Tomas Bjerre *2019-06-09 18:09:59*

**Added JUnit parser**


[131000779822531](https://github.com/tomasbjerre/violations-lib/commit/131000779822531) Antti Keistinen *2019-06-04 02:46:29*


## 1.93
### GitHub [#69](https://github.com/tomasbjerre/violations-lib/issues/69) NumberFormatException at Flake8 parser  

**Allowing noise in Flake8 parser**


[1ab666f7d16afdd](https://github.com/tomasbjerre/violations-lib/commit/1ab666f7d16afdd) Tomas Bjerre *2019-06-07 05:38:45*


### No issue

**doc**


[8d9a2d5c16852f2](https://github.com/tomasbjerre/violations-lib/commit/8d9a2d5c16852f2) Tomas Bjerre *2019-06-01 13:41:05*

**doc**


[b9720430d4db962](https://github.com/tomasbjerre/violations-lib/commit/b9720430d4db962) Tomas Bjerre *2019-06-01 13:16:48*

**doc**


[09670621f7e77fe](https://github.com/tomasbjerre/violations-lib/commit/09670621f7e77fe) Tomas Bjerre *2019-06-01 10:30:52*

**doc**


[9697bfaefe75836](https://github.com/tomasbjerre/violations-lib/commit/9697bfaefe75836) Tomas Bjerre *2019-05-30 20:28:12*


## 1.92
### GitHub [#68](https://github.com/tomasbjerre/violations-lib/issues/68) cpplint parser does not recognize any violations from cpplint report  

**Handling line "None" in cpplint**


[d9164ce9f45d564](https://github.com/tomasbjerre/violations-lib/commit/d9164ce9f45d564) Tomas Bjerre *2019-04-24 14:55:49*

**Testing**


[ae13633f9e953aa](https://github.com/tomasbjerre/violations-lib/commit/ae13633f9e953aa) Tomas Bjerre *2019-04-23 17:52:56*


### No issue

**Bumping version**


[2e3458328cff557](https://github.com/tomasbjerre/violations-lib/commit/2e3458328cff557) Tomas Bjerre *2019-04-24 15:24:42*

**Bumping version**


[6a1fd6fefb88a4c](https://github.com/tomasbjerre/violations-lib/commit/6a1fd6fefb88a4c) Tomas Bjerre *2019-04-24 15:07:14*


## 1.89
### GitHub [#67](https://github.com/tomasbjerre/violations-lib/issues/67) Sonar Parser : Wrong Severity level -&gt; when unknow then by default set to INFO  

**Correcting Sonar severity mapping**


[1eeafaff359c01b](https://github.com/tomasbjerre/violations-lib/commit/1eeafaff359c01b) Tomas Bjerre *2019-03-29 14:40:51*


### No issue

**Note on Sonar**


[7656a723dea9410](https://github.com/tomasbjerre/violations-lib/commit/7656a723dea9410) Tomas Bjerre *2019-04-03 14:17:03*

**Bumping version**


[227760877d0df93](https://github.com/tomasbjerre/violations-lib/commit/227760877d0df93) Tomas Bjerre *2019-03-29 15:03:56*

**Bumping version**


[2aa01b76eaf839d](https://github.com/tomasbjerre/violations-lib/commit/2aa01b76eaf839d) Tomas Bjerre *2019-03-29 14:56:12*


## 1.86
### GitHub [#66](https://github.com/tomasbjerre/violations-lib/issues/66) Add support for SonarQube preview report    *enhancement*  

**Sonar Report**


[09af9bc235197db](https://github.com/tomasbjerre/violations-lib/commit/09af9bc235197db) Tomas Bjerre *2019-03-23 14:28:21*


## 1.85
### GitHub [#65](https://github.com/tomasbjerre/violations-lib/pull/65) Add line attribute to one of the issues reported  

**Correcting test case after merge of**


[c2f538660c3f1a5](https://github.com/tomasbjerre/violations-lib/commit/c2f538660c3f1a5) Tomas Bjerre *2019-03-13 18:57:38*


### No issue

**Add line attribute to one of the issues reported**

 * Add line attribute to one of the issues reported to test optional line number plotting in klocwork report 

[7899d43347d69e3](https://github.com/tomasbjerre/violations-lib/commit/7899d43347d69e3) deepuj *2019-03-13 18:43:05*

**Support line number optionally in klocwork reports**


[b74f84862211f55](https://github.com/tomasbjerre/violations-lib/commit/b74f84862211f55) deepuj *2019-03-13 18:38:16*


## 1.84
### No issue

**golangci-lint**


[fb3dd6173a2dc80](https://github.com/tomasbjerre/violations-lib/commit/fb3dd6173a2dc80) Tomas Bjerre *2019-03-07 17:08:38*


## 1.83
### No issue

**Add support for golangci-lint flavoured Checkstyle**

 * aka Checkstyle 5.0, apparently. 
 * Signed-off-by: Nic Cope &lt;negz@rk0n.org&gt; 

[1bf2826f6b26e4a](https://github.com/tomasbjerre/violations-lib/commit/1bf2826f6b26e4a) Nic Cope *2019-03-07 10:25:12*

**Add a test demonstrating that golangci-lint Checkstyle does not parse**

 * Signed-off-by: Nic Cope &lt;negz@rk0n.org&gt; 

[82712daea397daf](https://github.com/tomasbjerre/violations-lib/commit/82712daea397daf) Nic Cope *2019-03-07 10:22:37*


## 1.82
### GitHub [#54](https://github.com/tomasbjerre/violations-lib/issues/54) locationChunks in CppCheck are reported as individual violations  

**Adding group attribute**


[df1c897e8d473a3](https://github.com/tomasbjerre/violations-lib/commit/df1c897e8d473a3) Tomas Bjerre *2019-02-24 15:28:55*


### No issue

**Example streaming**


[f9fb95039e88d97](https://github.com/tomasbjerre/violations-lib/commit/f9fb95039e88d97) Tomas Bjerre *2019-02-20 17:19:25*


## 1.81
### GitHub [#58](https://github.com/tomasbjerre/violations-lib/issues/58) [flake8] file names with prefix `./` are not detected  

**Correcting Flake8 parser**


[14d611e4bbf390b](https://github.com/tomasbjerre/violations-lib/commit/14d611e4bbf390b) Tomas Bjerre *2019-02-10 15:35:41*


## 1.80
### No issue

**Sorting reporters in README**


[345c4aa528b1bbf](https://github.com/tomasbjerre/violations-lib/commit/345c4aa528b1bbf) Tomas Bjerre *2019-02-02 09:35:10*

**Documentation changed to reflect the support for Scalastyle**


[edd14d12d04c619](https://github.com/tomasbjerre/violations-lib/commit/edd14d12d04c619) Andre Carmo *2019-02-01 23:50:25*

**Added ScalaStyle reporter with CheckStyle parser**


[bcf7092fa4f321d](https://github.com/tomasbjerre/violations-lib/commit/bcf7092fa4f321d) Andre Carmo *2019-02-01 23:30:12*

**Doc**


[2c6a31e642acd8e](https://github.com/tomasbjerre/violations-lib/commit/2c6a31e642acd8e) Tomas Bjerre *2019-01-30 17:00:29*


## 1.79
### GitHub [#55](https://github.com/tomasbjerre/violations-lib/issues/55) Support for IAR compiler warnings    *enhancement*  

**MSCPP and IAR**


[91a9b89bc4514f4](https://github.com/tomasbjerre/violations-lib/commit/91a9b89bc4514f4) Tomas Bjerre *2019-01-29 16:59:16*


### GitHub [#56](https://github.com/tomasbjerre/violations-lib/issues/56) Support for Microsoft Visual C++ compiler via msys2 makefile    *enhancement*  

**MSCPP and IAR**


[91a9b89bc4514f4](https://github.com/tomasbjerre/violations-lib/commit/91a9b89bc4514f4) Tomas Bjerre *2019-01-29 16:59:16*


### No issue

**Including info in CppCheck**


[69e26e53cdcbf77](https://github.com/tomasbjerre/violations-lib/commit/69e26e53cdcbf77) Tomas Bjerre *2019-01-23 07:22:24*


## 1.78
### GitHub [#53](https://github.com/tomasbjerre/violations-lib/issues/53) Shouldn&#39;t CodeNarc-Parsing respect SourceDirectory-Element    *bug*  

**Avoiding faulty slash with CodeNarc**


[fa97ac81772c01b](https://github.com/tomasbjerre/violations-lib/commit/fa97ac81772c01b) Tomas Bjerre *2019-01-14 17:05:33*


## 1.77
### GitHub [#53](https://github.com/tomasbjerre/violations-lib/issues/53) Shouldn&#39;t CodeNarc-Parsing respect SourceDirectory-Element    *bug*  

**Respecting source directory in CodeNarc**


[2fbb203859a0636](https://github.com/tomasbjerre/violations-lib/commit/2fbb203859a0636) Tomas Bjerre *2019-01-08 20:14:11*


### No issue

**Setting version to fix faulty release**


[547751028bcc781](https://github.com/tomasbjerre/violations-lib/commit/547751028bcc781) Tomas Bjerre *2019-01-09 16:42:44*

**Setting version to fix faulty release**


[5754b1124779614](https://github.com/tomasbjerre/violations-lib/commit/5754b1124779614) Tomas Bjerre *2019-01-09 16:27:05*


## 1.74
### GitHub [#52](https://github.com/tomasbjerre/violations-lib/issues/52) Ansible Lint issue    *bug*  

**Correctly parsing AnsibleLint**


[6c95cf891f06873](https://github.com/tomasbjerre/violations-lib/commit/6c95cf891f06873) Tomas Bjerre *2019-01-02 21:06:41*


### No issue

**Podam update**


[274b7897621d919](https://github.com/tomasbjerre/violations-lib/commit/274b7897621d919) Tomas Bjerre *2018-12-22 11:38:33*


## 1.73
### GitHub [#51](https://github.com/tomasbjerre/violations-lib/issues/51) Embedded Ruby Templates    *enhancement*  

**Documenting ERB**


[50bcfaaf1afdbdd](https://github.com/tomasbjerre/violations-lib/commit/50bcfaaf1afdbdd) Tomas Bjerre *2018-11-15 17:23:40*


## 1.72
### GitHub [#50](https://github.com/tomasbjerre/violations-lib/issues/50) puppet-lint  

**Documenting Puppet-Lint**


[ec23727e779c721](https://github.com/tomasbjerre/violations-lib/commit/ec23727e779c721) Tomas Bjerre *2018-11-14 19:10:04*


## 1.71
### No issue

**Removing custom Optional**


[8e0005f4897a74d](https://github.com/tomasbjerre/violations-lib/commit/8e0005f4897a74d) Tomas Bjerre *2018-10-08 14:10:31*


## 1.70
### No issue

**Making readme update work on Windows**


[7fbdda0d6a1090a](https://github.com/tomasbjerre/violations-lib/commit/7fbdda0d6a1090a) Tomas Bjerre *2018-10-08 06:22:35*


## 1.69
### GitHub [#46](https://github.com/tomasbjerre/violations-lib/issues/46) Incomplete rule IDs in Flake8 parser    *enhancement*  

**Including entire rule in Flake8**


[67c8480c165d673](https://github.com/tomasbjerre/violations-lib/commit/67c8480c165d673) Tomas Bjerre *2018-10-06 06:18:22*


### No issue

**Updating changelog**


[df5387dbf1d4db3](https://github.com/tomasbjerre/violations-lib/commit/df5387dbf1d4db3) Tomas Bjerre *2018-09-25 07:29:46*


## 1.67
### No issue

**Automating reporters in readme**


[01d4a55ff297bd5](https://github.com/tomasbjerre/violations-lib/commit/01d4a55ff297bd5) Tomas Bjerre *2018-09-23 11:54:36*


## 1.66
### No issue

**Automating reporters in readme**


[ef097c18f85e488](https://github.com/tomasbjerre/violations-lib/commit/ef097c18f85e488) Tomas Bjerre *2018-09-23 11:38:55*

**Doc**


[93a2736d0116198](https://github.com/tomasbjerre/violations-lib/commit/93a2736d0116198) Tomas Bjerre *2018-09-22 17:08:39*

**Documenting parsers as a table**


[ba0e4e7044e2300](https://github.com/tomasbjerre/violations-lib/commit/ba0e4e7044e2300) Tomas Bjerre *2018-09-22 08:37:03*

**Updating README.md**


[cc731ae81a2150e](https://github.com/tomasbjerre/violations-lib/commit/cc731ae81a2150e) Tomas Bjerre *2018-09-22 08:01:27*


## 1.65
### GitHub [#45](https://github.com/tomasbjerre/violations-lib/issues/45) False positive matches in KotlinGradleParser    *enhancement*  

**Correcting Kotlin parsers**


[fb2ce3dc8149194](https://github.com/tomasbjerre/violations-lib/commit/fb2ce3dc8149194) Tomas Bjerre *2018-09-20 23:01:21*


## 1.64
### GitHub [#44](https://github.com/tomasbjerre/violations-lib/issues/44) Support kotlinc warnings    *enhancement*  

**Kotlin Maven and Gradle parsers**


[59daadfab031933](https://github.com/tomasbjerre/violations-lib/commit/59daadfab031933) Tomas Bjerre *2018-09-20 13:45:35*


## 1.63
### No issue

**fixed yamllint parser**


[8f804cf422e884d](https://github.com/tomasbjerre/violations-lib/commit/8f804cf422e884d) Aleksei_Philippov *2018-09-17 12:59:38*


## 1.62
### GitHub [#42](https://github.com/tomasbjerre/violations-lib/pull/42) Added yamllint parser  

**Correcting YAMLLint**


[fd4e6df1b950bd4](https://github.com/tomasbjerre/violations-lib/commit/fd4e6df1b950bd4) Tomas Bjerre *2018-09-15 07:29:13*


### No issue

**Gradle Wrapper 4.10.1**


[baf59e87c9821da](https://github.com/tomasbjerre/violations-lib/commit/baf59e87c9821da) Tomas Bjerre *2018-09-15 05:56:42*

**updated README**


[f66213f734f7b6f](https://github.com/tomasbjerre/violations-lib/commit/f66213f734f7b6f) Alexey Filippov *2018-09-14 22:05:06*

**added rules for parser**


[9333395b1ec1dda](https://github.com/tomasbjerre/violations-lib/commit/9333395b1ec1dda) Alexey Filippov *2018-09-14 21:59:27*

**Added YAMLlint parser**


[ae03c28f28b005c](https://github.com/tomasbjerre/violations-lib/commit/ae03c28f28b005c) Aleksei_Philippov *2018-09-14 15:18:11*


## 1.61
### No issue

**Removing Fliptables dependency**


[32f55e98cc590cb](https://github.com/tomasbjerre/violations-lib/commit/32f55e98cc590cb) Tomas Bjerre *2018-09-12 20:19:43*


## 1.60
### No issue

**Fix copy-n-paste in reporting CPD violations**


[bcc9aaf0f8bf978](https://github.com/tomasbjerre/violations-lib/commit/bcc9aaf0f8bf978) terje *2018-09-12 18:05:59*

**Testing Spotbugs**


[457780347d3fb94](https://github.com/tomasbjerre/violations-lib/commit/457780347d3fb94) Tomas Bjerre *2018-09-12 16:32:10*


## 1.59
### GitHub [#39](https://github.com/tomasbjerre/violations-lib/issues/39) AndroidLintParser doesn&#39;t expose rule correctly    *enhancement*  

**Adding category to model**


[9fa7ff29c678148](https://github.com/tomasbjerre/violations-lib/commit/9fa7ff29c678148) Tomas Bjerre *2018-08-18 14:13:38*


## 1.58
### No issue

**Cleaning Violation class, reducing memory**


[efb508a5d73ad23](https://github.com/tomasbjerre/violations-lib/commit/efb508a5d73ad23) Tomas Bjerre *2018-07-27 07:14:16*


## 1.57
### GitHub [#38](https://github.com/tomasbjerre/violations-lib/issues/38) gcc, ARM-Gcc and Doxygen    *enhancement*  

**GCC, ARM GCC and Doxygen**


[062647397a858f0](https://github.com/tomasbjerre/violations-lib/commit/062647397a858f0) Tomas Bjerre *2018-07-04 17:41:33*


## 1.56
### No issue

**Excaped message and filename**


[04313947c88e23a](https://github.com/tomasbjerre/violations-lib/commit/04313947c88e23a) Tomas Bjerre *2018-07-04 10:05:38*

**Issue template**


[990f4d6da1000b6](https://github.com/tomasbjerre/violations-lib/commit/990f4d6da1000b6) Tomas Bjerre *2018-07-03 14:12:54*

**Updating docs on PyLint**


[4016805bf281f1f](https://github.com/tomasbjerre/violations-lib/commit/4016805bf281f1f) Tomas Bjerre *2018-05-03 17:08:10*


## 1.55
### GitHub [#33](https://github.com/tomasbjerre/violations-lib/issues/33) Add support for NullAway    *enhancement*  

**NullAway**


[72244241bbd2ead](https://github.com/tomasbjerre/violations-lib/commit/72244241bbd2ead) Tomas Bjerre *2018-04-14 05:08:26*


### GitHub [#35](https://github.com/tomasbjerre/violations-lib/issues/35) cpplint parser does not recognize any violations    *bug*  

**Testing cpplint variant**


[8455fc596c6a547](https://github.com/tomasbjerre/violations-lib/commit/8455fc596c6a547) Tomas Bjerre *2018-04-13 11:45:16*


### No issue

**Avoiding Optional in model**


[a2c03571ee99246](https://github.com/tomasbjerre/violations-lib/commit/a2c03571ee99246) Tomas Bjerre *2018-05-01 04:58:35*

**TSLint**


[9df7684f4fba7e8](https://github.com/tomasbjerre/violations-lib/commit/9df7684f4fba7e8) Tomas Bjerre *2018-04-20 10:06:18*

**Adding another PMD test**


[c36c7bd5a99a442](https://github.com/tomasbjerre/violations-lib/commit/c36c7bd5a99a442) Tomas Bjerre *2018-03-07 18:05:26*

**Fixed typo (assertion on wrong issue checked).**


[5381b5d9381a6d1](https://github.com/tomasbjerre/violations-lib/commit/5381b5d9381a6d1) Ulli Hafner *2018-03-02 22:56:12*

**Bumping version to fix release**


[1cbb6a7a096604b](https://github.com/tomasbjerre/violations-lib/commit/1cbb6a7a096604b) Tomas Bjerre *2018-02-13 18:25:15*

**Autoformatting and removed debug code**


[5e3d49f4165798e](https://github.com/tomasbjerre/violations-lib/commit/5e3d49f4165798e) Øyvind Rørtveit *2018-02-13 15:10:07*

**Fixed file parsing under Windows, fixed PC-lint parser, added detection of MISRA errors for PC-lint**


[45d308d631a6113](https://github.com/tomasbjerre/violations-lib/commit/45d308d631a6113) Øyvind Rørtveit *2018-02-13 14:51:34*

**Added PC-lint parser**


[be94e0831808f2b](https://github.com/tomasbjerre/violations-lib/commit/be94e0831808f2b) Øyvind Rørtveit *2018-02-12 16:36:04*


## 1.52
### GitHub [#10](https://github.com/tomasbjerre/violations-lib/issues/10) Google error-prone    *enhancement*  

**Google error-prone**


[f2720de0ec7d36c](https://github.com/tomasbjerre/violations-lib/commit/f2720de0ec7d36c) Tomas Bjerre *2018-01-14 11:20:10*


## 1.51
### GitHub [#30](https://github.com/tomasbjerre/violations-lib/pull/30) Parameterize the max line length for the report table.  

**Parameterize max width of reporter table**


[87c5ab7cb5f4ae7](https://github.com/tomasbjerre/violations-lib/commit/87c5ab7cb5f4ae7) Tomas Bjerre *2018-01-13 19:06:03*


## 1.50
### GitHub [#28](https://github.com/tomasbjerre/violations-lib/issues/28) Single quotes in xml are printed as &amp;apos;   

**Un-escape XML when reading attributes.**

 * Fixes 

[9b10f800c7b7941](https://github.com/tomasbjerre/violations-lib/commit/9b10f800c7b7941) Sam Judd *2017-12-31 03:25:14*


### No issue

**Gathering string utils**


[6951f1a5039f968](https://github.com/tomasbjerre/violations-lib/commit/6951f1a5039f968) Tomas Bjerre *2017-12-31 05:51:14*


## 1.49
### No issue

**Relocating to correct Java identifier**


[8e64583ce0fec03](https://github.com/tomasbjerre/violations-lib/commit/8e64583ce0fec03) Tomas Bjerre *2017-12-30 18:32:34*

**Doc**


[ba8ca533c8ada71](https://github.com/tomasbjerre/violations-lib/commit/ba8ca533c8ada71) Tomas Bjerre *2017-12-30 10:27:45*


## 1.48
### No issue

**Limiting width of report messages**


[0f4c1919ccf1ae3](https://github.com/tomasbjerre/violations-lib/commit/0f4c1919ccf1ae3) Tomas Bjerre *2017-12-25 16:02:22*


## 1.47
### No issue

**Using UTF-8, instead of default**


[b3c21719bdce84c](https://github.com/tomasbjerre/violations-lib/commit/b3c21719bdce84c) Tomas Bjerre *2017-12-24 21:49:53*

**Re-throwing any IOException**


[6c5f8f5b9edf1dc](https://github.com/tomasbjerre/violations-lib/commit/6c5f8f5b9edf1dc) Tomas Bjerre *2017-12-24 20:31:13*


## 1.46
### No issue

**Implementing reporter output**


[e390aaedcb7ec68](https://github.com/tomasbjerre/violations-lib/commit/e390aaedcb7ec68) Tomas Bjerre *2017-12-24 12:00:57*


## 1.44
### No issue

**Packaging fat jar as main jar**

 * So that no special classifier is needed to get the relocated gson. 

[764fe8fd94835a4](https://github.com/tomasbjerre/violations-lib/commit/764fe8fd94835a4) Tomas Bjerre *2017-12-22 18:28:23*


## 1.43
### No issue

**Bumping version to fix faulty release**


[01745f931b7d426](https://github.com/tomasbjerre/violations-lib/commit/01745f931b7d426) Tomas Bjerre *2017-12-22 17:22:00*

**Adding project.ext.useShadowJar = true**

 * Updated release.gradle to only optionally create shadow jar. 

[09f13a83635d7ac](https://github.com/tomasbjerre/violations-lib/commit/09f13a83635d7ac) Tomas Bjerre *2017-12-22 15:38:14*

**Doc**


[cffd7099ed9ad2d](https://github.com/tomasbjerre/violations-lib/commit/cffd7099ed9ad2d) Tomas Bjerre *2017-12-22 12:31:48*


## 1.40
### No issue

**Fixing release script to include shadow jar**


[dd4353ebc98226a](https://github.com/tomasbjerre/violations-lib/commit/dd4353ebc98226a) Tomas Bjerre *2017-12-22 10:51:49*


## 1.39
### No issue

**Replacing ScriptEngine with Gson**

 * To avoid the security issue that arise if custom Javascript can be added to the DocFX file being parsed. 
 * Using a shaddow jar (named all) to relocate Gson and avoid classpath issues. 

[524c39c2ff47d40](https://github.com/tomasbjerre/violations-lib/commit/524c39c2ff47d40) Tomas Bjerre *2017-12-22 10:35:12*


## 1.38
### Jira JENKINS-48669   

**Ignoring case when checking for equality**


[4d7069f628813b6](https://github.com/tomasbjerre/violations-lib/commit/4d7069f628813b6) Tomas Bjerre *2017-12-21 10:12:56*


### Jira JENKINS-48670   

**DocFX parser**


[1ca81e02c394734](https://github.com/tomasbjerre/violations-lib/commit/1ca81e02c394734) Tomas Bjerre *2017-12-21 12:32:33*


### No issue

**Doc**


[8c1de7f777b3c0e](https://github.com/tomasbjerre/violations-lib/commit/8c1de7f777b3c0e) Tomas Bjerre *2017-12-06 18:23:43*


## 1.36
### No issue

**Cleaning up build scripts**


[068312ba927037d](https://github.com/tomasbjerre/violations-lib/commit/068312ba927037d) Tomas Bjerre *2017-12-03 07:09:14*


## 1.35
### No issue

**Adjusting to OSS repo in Bintray**


[f8c0ad2397d2894](https://github.com/tomasbjerre/violations-lib/commit/f8c0ad2397d2894) Tomas Bjerre *2017-12-02 07:29:54*

**Doc**


[8e0c4d426273dd8](https://github.com/tomasbjerre/violations-lib/commit/8e0c4d426273dd8) Tomas Bjerre *2017-12-02 05:56:31*


## 1.34
### GitHub [#27](https://github.com/tomasbjerre/violations-lib/issues/27) PMD may miss ruleSet and externalInfoUrl    *bug*  

**Accepting PMD files without ruleset-tag**


[a8e1e2ded94e9be](https://github.com/tomasbjerre/violations-lib/commit/a8e1e2ded94e9be) Tomas Bjerre *2017-11-22 19:56:15*


### No issue

**Bintray release scripts**


[a0c35cce38d7689](https://github.com/tomasbjerre/violations-lib/commit/a0c35cce38d7689) Tomas Bjerre *2017-12-01 18:42:50*

**Adding SwiftLint to Readme**


[709283d45a6537c](https://github.com/tomasbjerre/violations-lib/commit/709283d45a6537c) Tomas Bjerre *2017-11-18 13:37:31*

**Doc**


[f845737670c5e10](https://github.com/tomasbjerre/violations-lib/commit/f845737670c5e10) Tomas Bjerre *2017-10-28 20:19:52*


## 1.33
### No issue

**Add Resharper WikiUrl to output message**


[7c1024d3326478d](https://github.com/tomasbjerre/violations-lib/commit/7c1024d3326478d) nickfish *2017-10-12 00:51:10*


## 1.32
### GitHub [#24](https://github.com/tomasbjerre/violations-lib/pull/24) Added handling for empty IssueType Description attributes for Resharper  

**Cleanup after merge**


[9eed4592d38dff7](https://github.com/tomasbjerre/violations-lib/commit/9eed4592d38dff7) Tomas Bjerre *2017-10-09 16:27:38*


### No issue

**Added handling for empty IssueType Description attributes for Resharper**


[dbb61563dc31487](https://github.com/tomasbjerre/violations-lib/commit/dbb61563dc31487) nickfish *2017-10-09 03:32:29*

**Travis with JDK8**


[4298d4b63e9ba3f](https://github.com/tomasbjerre/violations-lib/commit/4298d4b63e9ba3f) Tomas Bjerre *2017-09-01 19:23:05*


## 1.31
### GitHub [#23](https://github.com/tomasbjerre/violations-lib/issues/23) Checkstyle: don&#39;t fail if rule attribute is not present  

**Allowing absent source in Checkstyle parser**


[dd522411c894105](https://github.com/tomasbjerre/violations-lib/commit/dd522411c894105) Tomas Bjerre *2017-09-01 10:54:30*


## 1.30
### No issue

**Checking for null in API-calls**

 * For better error messages. 

[214765989fbc706](https://github.com/tomasbjerre/violations-lib/commit/214765989fbc706) Tomas Bjerre *2017-08-11 09:42:30*

**Testing with reporter**


[b73430f34a55ff6](https://github.com/tomasbjerre/violations-lib/commit/b73430f34a55ff6) Tomas Bjerre *2017-07-14 19:31:04*


## 1.29
### No issue

**Adding withReporter in reporter API**


[5a013dc35c75504](https://github.com/tomasbjerre/violations-lib/commit/5a013dc35c75504) Tomas Bjerre *2017-07-14 19:25:23*


## 1.28
### GitHub [#19](https://github.com/tomasbjerre/violations-lib/issues/19) Support detekt    *enhancement*  

**Updating doc about Detekt**


[bf5475d3e59fb3d](https://github.com/tomasbjerre/violations-lib/commit/bf5475d3e59fb3d) Tomas Bjerre *2017-06-13 18:14:41*


### GitHub [#20](https://github.com/tomasbjerre/violations-lib/issues/20) Support Facebook Infer    *enhancement*  

**Updating doc on Infer**


[22a2eb9add9884c](https://github.com/tomasbjerre/violations-lib/commit/22a2eb9add9884c) Tomas Bjerre *2017-06-23 12:44:57*


### GitHub [#22](https://github.com/tomasbjerre/violations-lib/pull/22) Renaming Reporter to Parser    *enhancement*  

**Renaming Reporter to Parser**

 * Also adding a reporter String in Violation to record the tool being used to produce the Violation. 

[8401c647572fb5d](https://github.com/tomasbjerre/violations-lib/commit/8401c647572fb5d) Tomas Bjerre *2017-07-14 18:50:23*


### No issue

**Doc**


[e6c85825c084b59](https://github.com/tomasbjerre/violations-lib/commit/e6c85825c084b59) Tomas Bjerre *2017-07-12 18:03:16*


## 1.27
### No issue

**URL in Klocwork**


[43fc14f46fcfb98](https://github.com/tomasbjerre/violations-lib/commit/43fc14f46fcfb98) Tomas Bjerre *2017-04-11 18:11:22*

**doc**


[4c367bce8658529](https://github.com/tomasbjerre/violations-lib/commit/4c367bce8658529) Tomas Bjerre *2017-04-10 20:12:21*


## 1.26
### No issue

**Testing that parsers are mentioned in README.md**


[0f27abfb54542f3](https://github.com/tomasbjerre/violations-lib/commit/0f27abfb54542f3) Tomas Bjerre *2017-04-10 18:10:28*

**Support sbt-scalac**


[bc75205ca390a62](https://github.com/tomasbjerre/violations-lib/commit/bc75205ca390a62) Trung Nguyen *2017-04-10 17:35:19*

**doc**


[9a44d279d312230](https://github.com/tomasbjerre/violations-lib/commit/9a44d279d312230) Tomas Bjerre *2017-03-30 19:22:00*


## 1.25
### No issue

**Klocwork parser**


[4a875240f568a02](https://github.com/tomasbjerre/violations-lib/commit/4a875240f568a02) Tomas Bjerre *2017-03-30 17:14:56*

**doc**


[009b846e120e0ff](https://github.com/tomasbjerre/violations-lib/commit/009b846e120e0ff) Tomas Bjerre *2017-03-17 21:39:52*


## 1.24
### No issue

**Adding filtering util**


[30788adc503bdd4](https://github.com/tomasbjerre/violations-lib/commit/30788adc503bdd4) Tomas Bjerre *2017-03-17 14:09:58*

**Google Java Format**


[71746a1614d51b3](https://github.com/tomasbjerre/violations-lib/commit/71746a1614d51b3) Tomas Bjerre *2017-02-25 12:25:45*

**doc**


[6dd871dfa4713e9](https://github.com/tomasbjerre/violations-lib/commit/6dd871dfa4713e9) Tomas Bjerre *2017-02-19 14:15:07*


## 1.23
### GitHub [#14](https://github.com/tomasbjerre/violations-lib/issues/14) Support php checkers    *enhancement*  

**PHPMD and PHPCS**


[f7212555f7a986b](https://github.com/tomasbjerre/violations-lib/commit/f7212555f7a986b) Tomas Bjerre *2017-02-18 20:01:36*


### GitHub [#15](https://github.com/tomasbjerre/violations-lib/issues/15) Support Rubycop    *enhancement*  

**Support RubyCop**


[75ad3b3d415384a](https://github.com/tomasbjerre/violations-lib/commit/75ad3b3d415384a) Tomas Bjerre *2017-02-18 21:36:36*


### GitHub [#16](https://github.com/tomasbjerre/violations-lib/issues/16) Support clang    *enhancement*  

**Support CLang**


[68ac9c8cf268d17](https://github.com/tomasbjerre/violations-lib/commit/68ac9c8cf268d17) Tomas Bjerre *2017-02-18 21:28:31*


### GitHub [#17](https://github.com/tomasbjerre/violations-lib/issues/17) Support golint    *enhancement*  

**Support GoLint**


[fdbc050e06fd90a](https://github.com/tomasbjerre/violations-lib/commit/fdbc050e06fd90a) Tomas Bjerre *2017-02-18 21:01:02*


## 1.22
### No issue

**Finding findbugsmessages and correcting codenarc**

 * Was finding findbugs messages xml incorrectly in classpath. 
 * Was not handling codenarc reports with empty line numbers. 

[60d19fedbefdf85](https://github.com/tomasbjerre/violations-lib/commit/60d19fedbefdf85) Tomas Bjerre *2017-02-16 20:51:05*

**doc**


[84e505343e497a2](https://github.com/tomasbjerre/violations-lib/commit/84e505343e497a2) Tomas Bjerre *2017-02-07 05:42:45*


## 1.21
### GitHub [#12](https://github.com/tomasbjerre/violations-lib/issues/12) Support pydocstyle (old pep257)    *enhancement*  

**Adding MyPy and PyDocStyle parsers**


[acfe3312656237f](https://github.com/tomasbjerre/violations-lib/commit/acfe3312656237f) Tomas Bjerre *2017-02-06 17:07:33*


### GitHub [#13](https://github.com/tomasbjerre/violations-lib/issues/13) Support mypy    *enhancement*  

**Adding MyPy and PyDocStyle parsers**


[acfe3312656237f](https://github.com/tomasbjerre/violations-lib/commit/acfe3312656237f) Tomas Bjerre *2017-02-06 17:07:33*


### No issue

**Set theme jekyll-theme-slate**


[6063d15ecfbb8a7](https://github.com/tomasbjerre/violations-lib/commit/6063d15ecfbb8a7) Tomas Bjerre *2017-01-12 03:06:06*

**doc**


[943154cb27cfd1e](https://github.com/tomasbjerre/violations-lib/commit/943154cb27cfd1e) Tomas Bjerre *2016-12-21 16:46:56*


## 1.20
### No issue

**Renaming parse method in ViolationsParser**

 * To make its usage clearer. 

[f4dacbd541b6625](https://github.com/tomasbjerre/violations-lib/commit/f4dacbd541b6625) Tomas Bjerre *2016-12-21 16:34:24*

**doc**


[e17915d91a4ef8a](https://github.com/tomasbjerre/violations-lib/commit/e17915d91a4ef8a) Tomas Bjerre *2016-12-18 07:50:45*


## 1.19
### No issue

**Correcting utility method for finding resource**


[5dd52bd39c5265a](https://github.com/tomasbjerre/violations-lib/commit/5dd52bd39c5265a) Tomas Bjerre *2016-12-16 07:19:49*


## 1.18
### No issue

**Removing SLF4J dependency**


[8961db105e44f36](https://github.com/tomasbjerre/violations-lib/commit/8961db105e44f36) Tomas Bjerre *2016-12-15 17:32:00*


## 1.17
### No issue

**Removing Guava dependency**


[0b8af64b20efcca](https://github.com/tomasbjerre/violations-lib/commit/0b8af64b20efcca) Tomas Bjerre *2016-12-14 17:48:47*

**doc**


[5d94e7f3626a273](https://github.com/tomasbjerre/violations-lib/commit/5d94e7f3626a273) Tomas Bjerre *2016-11-06 18:12:13*


## 1.16
### No issue

**Simian, ZPTLint, JCReport**


[60fa8a9b6091ddd](https://github.com/tomasbjerre/violations-lib/commit/60fa8a9b6091ddd) Tomas Bjerre *2016-11-05 22:39:23*

**Gendarme**


[3ac58ccc82b8360](https://github.com/tomasbjerre/violations-lib/commit/3ac58ccc82b8360) Tomas Bjerre *2016-11-05 21:28:33*

**CPD**


[7a4e651c53ab3b2](https://github.com/tomasbjerre/violations-lib/commit/7a4e651c53ab3b2) Tomas Bjerre *2016-11-05 19:16:50*

**CodeNarc**


[23fa562feee2bb2](https://github.com/tomasbjerre/violations-lib/commit/23fa562feee2bb2) Tomas Bjerre *2016-11-05 18:58:32*


## 1.15
### No issue

**Exposing parser in reporter**


[2e216ee38cb2fdb](https://github.com/tomasbjerre/violations-lib/commit/2e216ee38cb2fdb) Tomas Bjerre *2016-11-03 18:03:08*


## 1.14
### No issue

**Letting the reporters parse strings, not files**


[375706d8e579bfc](https://github.com/tomasbjerre/violations-lib/commit/375706d8e579bfc) Tomas Bjerre *2016-11-03 17:54:20*


## 1.13
### GitHub [#11](https://github.com/tomasbjerre/violations-lib/issues/11) CSSLint rollup output causes parsing error    *bug*  

**Handling css-lint reports where there are not line or evidence**

 * Also setting severity level for PyLint. 

[35d5d33b447b37a](https://github.com/tomasbjerre/violations-lib/commit/35d5d33b447b37a) Tomas Bjerre *2016-10-26 15:39:37*


## 1.12
### No issue

**Changing rule format in PyLint to CODE(codeName)**


[24618707a88497c](https://github.com/tomasbjerre/violations-lib/commit/24618707a88497c) Tomas Bjerre *2016-10-25 17:44:33*


## 1.11
### No issue

**PyLint parser**


[ec90aa741fdeb67](https://github.com/tomasbjerre/violations-lib/commit/ec90aa741fdeb67) Tomas Bjerre *2016-10-24 17:16:46*


## 1.10
### No issue

**Supporting StyleCop**

 * And project level issue in FxCop. 

[d68d368e8ffc609](https://github.com/tomasbjerre/violations-lib/commit/d68d368e8ffc609) Tomas Bjerre *2016-10-03 17:00:57*

**Formatting code**


[09347946765a096](https://github.com/tomasbjerre/violations-lib/commit/09347946765a096) Tomas Bjerre *2016-10-02 12:34:51*


## 1.9
### GitHub [#4](https://github.com/tomasbjerre/violations-lib/issues/4) eslint    *enhancement*  

**ESLint**


[e9f338ef44d3a45](https://github.com/tomasbjerre/violations-lib/commit/e9f338ef44d3a45) Tomas Bjerre *2016-10-01 11:25:19*


### No issue

**Preliminary support for StyleCop**


[81e30321fa1619b](https://github.com/tomasbjerre/violations-lib/commit/81e30321fa1619b) Tomas Bjerre *2016-10-01 10:24:14*

**Support for FxCop**


[ac5a58c002f8c97](https://github.com/tomasbjerre/violations-lib/commit/ac5a58c002f8c97) Tomas Bjerre *2016-10-01 09:55:19*

**Refactoring, adding ViolationsParser interface**

 * Also preparing for FxCop. 

[adef3f6ed9c6cfc](https://github.com/tomasbjerre/violations-lib/commit/adef3f6ed9c6cfc) Tomas Bjerre *2016-10-01 08:38:19*

**doc**


[4f54260b7b47e96](https://github.com/tomasbjerre/violations-lib/commit/4f54260b7b47e96) Tomas Bjerre *2016-07-28 20:22:22*


## 1.8
### No issue

**Updating README.md**

 * And formatting code after merge of PR. 

[34cc693dfcc7bad](https://github.com/tomasbjerre/violations-lib/commit/34cc693dfcc7bad) Tomas Bjerre *2016-04-27 05:34:51*

**Add Android Lint parser**


[05a11ed65560142](https://github.com/tomasbjerre/violations-lib/commit/05a11ed65560142) panpanini *2016-04-27 04:38:50*

**Doc**


[8a3d860da48c56d](https://github.com/tomasbjerre/violations-lib/commit/8a3d860da48c56d) Tomas Bjerre *2016-04-23 17:08:41*


## 1.7
### No issue

**Making Violation class serializable**


[408ea213a51516c](https://github.com/tomasbjerre/violations-lib/commit/408ea213a51516c) Tomas Bjerre *2016-04-23 17:06:14*

**Updating docs**


[30e7ac53b0b7efd](https://github.com/tomasbjerre/violations-lib/commit/30e7ac53b0b7efd) Tomas Bjerre *2016-04-07 16:39:05*


## 1.6
### GitHub [#5](https://github.com/tomasbjerre/violations-lib/issues/5) PiTest    *enhancement*  

**Adding pitest parser**


[eeb2a624a99a7ff](https://github.com/tomasbjerre/violations-lib/commit/eeb2a624a99a7ff) Tomas Bjerre *2016-03-26 19:22:06*


### GitHub [#7](https://github.com/tomasbjerre/violations-lib/issues/7) Findbugs parser misses bugs    *bug*  

**Correcting exception message thrown if attribute not found**

 * XMLStreamReader does not have its own implementation of toString. 

[53719338add95a3](https://github.com/tomasbjerre/violations-lib/commit/53719338add95a3) Tomas Bjerre *2016-04-07 16:24:14*


### No issue

**FindBugsParser is using string matching to try to parse XML, which is**

 * failing because SourceLine elements may, or may not self-terminate. 
 * Convert to use a StAX parser, which is likely more performant since it 
 * does not need to construct regexes and lists to store the results in, but 
 * instead will do a forwards-only stream read. 
 * Signed-off-by: Nigel Magnay &lt;nigel.magnay@gmail.com&gt; 

[c6679a504380bcd](https://github.com/tomasbjerre/violations-lib/commit/c6679a504380bcd) Nigel Magnay *2016-04-07 15:41:56*

**Adding Jenkins plugin link to README.md**


[eb90c644065c9b6](https://github.com/tomasbjerre/violations-lib/commit/eb90c644065c9b6) Tomas Bjerre *2016-03-06 20:20:42*


## 1.5
### GitHub [#3](https://github.com/tomasbjerre/violations-lib/issues/3) Support more formats    *enhancement*  

**Support PerlCritic and XMLLint**


[b7c4ea0c66a4c69](https://github.com/tomasbjerre/violations-lib/commit/b7c4ea0c66a4c69) Tomas Bjerre *2016-03-06 13:45:16*

**Support CPPLint**


[319eaabe55b383b](https://github.com/tomasbjerre/violations-lib/commit/319eaabe55b383b) Tomas Bjerre *2016-03-06 13:39:27*

**Flake8 (PyLint) and Lint parser**


[30ba7fd5ed017d3](https://github.com/tomasbjerre/violations-lib/commit/30ba7fd5ed017d3) Tomas Bjerre *2016-03-06 13:39:25*

**Resharper support**


[5bfc5d3448ab978](https://github.com/tomasbjerre/violations-lib/commit/5bfc5d3448ab978) Tomas Bjerre *2016-03-06 13:39:23*

**CPPCheck support**


[e1a7ad7be14653b](https://github.com/tomasbjerre/violations-lib/commit/e1a7ad7be14653b) Tomas Bjerre *2016-03-06 13:39:20*


## 1.4
### No issue

**Adding possibility to set findbugs messages**


[3a8c7089d924848](https://github.com/tomasbjerre/violations-lib/commit/3a8c7089d924848) Tomas Bjerre *2016-03-05 09:34:51*


## 1.3
### No issue

**Only most specific FindBugs line**

 * Also SLF4J and some debug logging. 

[b50c549861119cf](https://github.com/tomasbjerre/violations-lib/commit/b50c549861119cf) Tomas Bjerre *2016-03-04 17:16:52*


## 1.2
### No issue

**Adding reporter to Violation**


[ddddaefba1f1aee](https://github.com/tomasbjerre/violations-lib/commit/ddddaefba1f1aee) Tomas Bjerre *2016-03-04 05:34:23*

**Adding links to projects using this lib**


[b6b4b74339b5008](https://github.com/tomasbjerre/violations-lib/commit/b6b4b74339b5008) Tomas Bjerre *2016-03-03 21:05:58*

**Moving example reports to its own repo**


[344567418ac1167](https://github.com/tomasbjerre/violations-lib/commit/344567418ac1167) Tomas Bjerre *2016-03-03 18:53:24*


## 1.1
### No issue

**Adding accumulated builder**

 * With sorting by file or severity. 
 * With filtering of severity. 

[9b87b1d1184d184](https://github.com/tomasbjerre/violations-lib/commit/9b87b1d1184d184) Tomas Bjerre *2016-02-28 19:40:26*


## 1.0
### No issue

**Update README.md**


[2dca003de3f0764](https://github.com/tomasbjerre/violations-lib/commit/2dca003de3f0764) Tomas Bjerre *2016-02-21 13:09:41*

**JSHint and PMD parsers**


[48773067b5e86d1](https://github.com/tomasbjerre/violations-lib/commit/48773067b5e86d1) Tomas Bjerre *2016-02-21 12:18:13*

**Findbugs parser**


[395483a7117cdb7](https://github.com/tomasbjerre/violations-lib/commit/395483a7117cdb7) Tomas Bjerre *2016-02-21 11:42:53*

**Adding specifics and column to Violation**


[d6e0920176a6d9f](https://github.com/tomasbjerre/violations-lib/commit/d6e0920176a6d9f) Tomas Bjerre *2016-02-21 08:33:00*

**Parsers and sample reports**


[b8d5a66e8ec407c](https://github.com/tomasbjerre/violations-lib/commit/b8d5a66e8ec407c) Tomas Bjerre *2016-02-20 21:40:52*


 