# violations-lib changelog

Changelog of violations-lib.

## 1.149.2 (2022-06-13)





### Bug Fixes

-  NPE when file empty in cppcheck ([5b3a0](https://github.com/tomasbjerre/violations-lib/commit/5b3a01320de6eee) Tomas Bjerre)  



## 1.149.1 (2022-05-22)







### Other changes

**favor markdown text**


[ceff3](https://github.com/tomasbjerre/violations-lib/commit/ceff306659d114a) Jeremy Long *2022-05-21 09:58:25*

**favor markdown when extracting the message from a finding**


[3a57e](https://github.com/tomasbjerre/violations-lib/commit/3a57e0ebd00eefd) Jeremy Long *2022-05-21 09:55:27*


## 1.149.0 (2022-05-04)



### Features

-  Add GreenHills log parser. ([3c6a1](https://github.com/tomasbjerre/violations-lib/commit/3c6a14cfd8ef969) Alexander Falkenstern)  




### Other changes

**add support for help text from Sarif rules**


[b3870](https://github.com/tomasbjerre/violations-lib/commit/b38700f7f1741a2) Jeremy Long *2022-05-04 13:33:33*

**use driver tool name as the reporter when available**


[c274a](https://github.com/tomasbjerre/violations-lib/commit/c274a675685077e) Jeremy Long *2022-05-04 11:33:32*


## 1.148.3 (2022-03-19)





### Bug Fixes

-  parsing severity of results in SARIF ([bfafe](https://github.com/tomasbjerre/violations-lib/commit/bfafee1692869e5) Tomas Bjerre)  [#144](https://github.com/tomasbjerre/violations-lib/issues/144)  



## 1.148.2 (2022-03-17)





### Bug Fixes

-  correcting groupId ([b3c8e](https://github.com/tomasbjerre/violations-lib/commit/b3c8edf25872cc2) Tomas Bjerre)  



## 1.148.1 (2022-03-16)





### Bug Fixes

-  parse safir locations ([0e9a1](https://github.com/tomasbjerre/violations-lib/commit/0e9a19b4b8c6d8a) Tomas Bjerre)  [#143](https://github.com/tomasbjerre/violations-lib/issues/143)  



## 1.148.0 (2021-12-11)



### Features

-  Dart MACHINE parser ([46dfd](https://github.com/tomasbjerre/violations-lib/commit/46dfd1ca444813d) Tomas Bjerre)  [#142](https://github.com/tomasbjerre/violations-lib/issues/142)  





## 1.147.3 (2021-12-09)







### Other changes

**add paths to valgrind Violation.file**


[ebca9](https://github.com/tomasbjerre/violations-lib/commit/ebca9e142112cd0) Tony Ciavarella *2021-12-09 00:31:20*


## 1.147.2 (2021-12-07)





### Bug Fixes

-  renaming SARIFPARSER to SARIF ([00f61](https://github.com/tomasbjerre/violations-lib/commit/00f6127b4f498b0) Tomas Bjerre)  



## 1.147.1 (2021-12-07)





### Bug Fixes

-  correcting sarif link ([b3684](https://github.com/tomasbjerre/violations-lib/commit/b3684ab1171b5d8) Tomas Bjerre)  



## 1.147.0 (2021-12-07)



### Features

-  sarif parser ([09a41](https://github.com/tomasbjerre/violations-lib/commit/09a41f43f75279e) Tomas Bjerre)  





## 1.146.3 (2021-12-06)





### Bug Fixes

-  make build script run on Windows ([84883](https://github.com/tomasbjerre/violations-lib/commit/84883773f3a1d97) Tomas Bjerre)  



## 1.146.2 (2021-12-06)







### Other changes

**fix valgrind suppression parsing**


[18f02](https://github.com/tomasbjerre/violations-lib/commit/18f0261f153a32a) Tony Ciavarella *2021-12-06 04:31:16*


## 1.146.1 (2021-12-05)







### Other changes

**add valgrind xml output parser**


[be845](https://github.com/tomasbjerre/violations-lib/commit/be845ce4851486a) Tony Ciavarella *2021-12-05 04:42:11*

**ci**


[0fd82](https://github.com/tomasbjerre/violations-lib/commit/0fd820adea2f6f8) Tomas Bjerre *2021-12-04 09:53:17*


## 1.146.0 (2021-11-28)



### Features

-  parse column from CPPCheck ([9458f](https://github.com/tomasbjerre/violations-lib/commit/9458f8f394c30f2) Tomas Bjerre)  [#136](https://github.com/tomasbjerre/violations-lib/issues/136)  





## 1.145.5 (2021-11-21)





### Bug Fixes

-  sort results in Jacoco ([b454f](https://github.com/tomasbjerre/violations-lib/commit/b454f8708ef4756) Tomas Bjerre)  



## 1.145.4 (2021-09-11)





### Bug Fixes

-  mapping engine_name and check_name in CodeClimate ([14497](https://github.com/tomasbjerre/violations-lib/commit/144971bb855a7a3) Tomas Bjerre)  [#132](https://github.com/tomasbjerre/violations-lib/issues/132)  [#133](https://github.com/tomasbjerre/violations-lib/issues/133)  



## 1.145.2 (2021-08-09)





### Bug Fixes

-  support CFN with JUnit format ([56175](https://github.com/tomasbjerre/violations-lib/commit/56175a442d188ee) Tomas Bjerre)  [#131](https://github.com/tomasbjerre/violations-lib/issues/131)  



## 1.145.1 (2021-08-05)







### Other changes

**Generated code does not have line information so we are skipping violations in those cases**


[89019](https://github.com/tomasbjerre/violations-lib/commit/890190b734f5220) Aurelian Teglas *2021-08-05 09:37:30*


## 1.145.0 (2021-07-12)



### Features

-  allow parser instance to be given ([3d606](https://github.com/tomasbjerre/violations-lib/commit/3d60602c95c54b5) Tomas Bjerre)  





## 1.144.6 (2021-07-12)







### Other changes

**Fixed failing tests from the framework**


[de042](https://github.com/tomasbjerre/violations-lib/commit/de042fa5423b9e6) Aurelian Teglas *2021-07-10 20:04:13*

**Migrated to XMLStreamReader**


[caeb0](https://github.com/tomasbjerre/violations-lib/commit/caeb0fa52558e4b) Aurelian Teglas *2021-07-10 19:45:55*

**Added unit tests for hte jacoco xml parser**


[29b36](https://github.com/tomasbjerre/violations-lib/commit/29b36ea182feb38) Aurelian Teglas *2021-07-10 18:47:25*

**Added starting point for Jacoco coverage parser**


[a818c](https://github.com/tomasbjerre/violations-lib/commit/a818cb6bc732add) Aurelian Teglas *2021-07-06 17:53:46*


## 1.144.5 (2021-06-16)







### Other changes

**add parser for msbuild log files**


[6ca81](https://github.com/tomasbjerre/violations-lib/commit/6ca811486b28540) Michael Kaiser *2021-06-15 14:06:06*


## 1.144.4 (2021-04-08)





### Bug Fixes

-  parsing junit parser with missing file ([b9bbc](https://github.com/tomasbjerre/violations-lib/commit/b9bbce64b93509a) Tomas Bjerre)  [#124](https://github.com/tomasbjerre/violations-lib/issues/124)  


### Other changes

**removing violations repoType**


[fd927](https://github.com/tomasbjerre/violations-lib/commit/fd92797b9c969f6) Tomas Bjerre *2021-04-05 07:48:37*

**correcting repoTyp**


[7e413](https://github.com/tomasbjerre/violations-lib/commit/7e413f219fdb726) Tomas Bjerre *2021-04-02 18:27:48*


## 1.144.3 (2021-04-02)







### Other changes

**Using new buildscript**


[f0e52](https://github.com/tomasbjerre/violations-lib/commit/f0e5223fbbda0c6) Tomas Bjerre *2021-04-02 17:50:33*


## 1.143 (2021-03-29)







### Other changes

**interpreting Fatal in AndroidLint as Error #121**


[47647](https://github.com/tomasbjerre/violations-lib/commit/47647c2886bfde9) Tomas Bjerre *2021-03-29 14:43:54*


## 1.142 (2021-01-29)







### Other changes

**testing archunit #120**


[0f9bc](https://github.com/tomasbjerre/violations-lib/commit/0f9bc7565e82956) Tomas Bjerre *2021-01-29 19:20:44*


## 1.141 (2021-01-01)







### Other changes

**Parse CPPCheck reports without file #118**


[c2c58](https://github.com/tomasbjerre/violations-lib/commit/c2c58662d6adee4) Tomas Bjerre *2021-01-01 13:12:42*


## 1.140 (2020-12-14)







### Other changes

**fix unit test**


[b71b5](https://github.com/tomasbjerre/violations-lib/commit/b71b5e4bdc362f8) Nov1kov *2020-12-13 09:18:45*

**merge master to fix_patch_parser**


[02e44](https://github.com/tomasbjerre/violations-lib/commit/02e44df7acc7076) Nov1kov *2020-12-13 09:09:19*

**Approval testing of PatchParser**


[68826](https://github.com/tomasbjerre/violations-lib/commit/688266f408a3d41) Tomas Bjerre *2020-12-12 11:31:21*

**prevent comment line out of diff**


[4517a](https://github.com/tomasbjerre/violations-lib/commit/4517af59127c16a) Nov1kov *2020-12-11 11:12:34*


## 1.139 (2020-12-06)







### Other changes

**Making isIgnored public jenkinsci/violation-comments-to-stash-plugin#72**


[37b6d](https://github.com/tomasbjerre/violations-lib/commit/37b6d19e9884302) Tomas Bjerre *2020-12-06 08:33:13*


## 1.138 (2020-12-06)







### Other changes

**ignorePaths jenkinsci/violation-comments-to-stash-plugin#72**


[1508d](https://github.com/tomasbjerre/violations-lib/commit/1508d313f91b7d2) Tomas Bjerre *2020-12-06 07:41:13*


## 1.137 (2020-11-27)







### Other changes

**fix formatting after merge**


[3583b](https://github.com/tomasbjerre/violations-lib/commit/3583b539d6064fe) Tomas Bjerre *2020-11-27 13:06:22*

**fix parse gradle kotlin windows-style path**


[80408](https://github.com/tomasbjerre/violations-lib/commit/80408d1630ce84f) Nov1kov *2020-11-27 12:49:42*

**fix parse gradle kotlin column line**


[5ac9c](https://github.com/tomasbjerre/violations-lib/commit/5ac9c50de3d6bbd) Nov1kov *2020-11-26 18:38:36*


## 1.136 (2020-11-15)







### Other changes

**Parsing error tag in JUnit reports #115**


[8853c](https://github.com/tomasbjerre/violations-lib/commit/8853ccd4f3ea181) Tomas Bjerre *2020-11-15 16:22:50*


## 1.135 (2020-10-17)







### Other changes

**JUnit parser throws StringIndexOutOfBoundsException #113**


[893e6](https://github.com/tomasbjerre/violations-lib/commit/893e64079ff11a0) Tomas Bjerre *2020-10-17 19:45:12*


## 1.134 (2020-10-04)







### Other changes

**check_name and engine_name in CodeClimate #112**


[c84cd](https://github.com/tomasbjerre/violations-lib/commit/c84cd5922cfa3cc) Tomas Bjerre *2020-10-04 10:19:52*


## 1.133 (2020-10-04)







### Other changes

**check_name and engine_name in CodeClimate #112**


[18f86](https://github.com/tomasbjerre/violations-lib/commit/18f8681ad3293ec) Tomas Bjerre *2020-10-04 07:19:35*


## 1.132 (2020-09-27)







### Other changes

**Find Security Bugs messages**


[f67a6](https://github.com/tomasbjerre/violations-lib/commit/f67a694356575c9) Tomas Bjerre *2020-09-27 16:13:07*


## 1.131 (2020-09-27)







### Other changes

**Rewriting ResharperParser parser #101**


[dcf86](https://github.com/tomasbjerre/violations-lib/commit/dcf86f74b53956d) Tomas Bjerre *2020-09-27 15:38:33*

**Rewriting PiTestParser parser #101**


[91d02](https://github.com/tomasbjerre/violations-lib/commit/91d02fa7e169df1) Tomas Bjerre *2020-09-27 15:04:27*

**Rewriting LintParser parser #101**


[c8289](https://github.com/tomasbjerre/violations-lib/commit/c8289ce3bc6142f) Tomas Bjerre *2020-09-27 14:45:48*

**Rewriting JSLintParser parser #101**


[0533e](https://github.com/tomasbjerre/violations-lib/commit/0533eca96a12c48) Tomas Bjerre *2020-09-27 14:24:33*

**Rewriting CSSLintParser parser #101**


[0516f](https://github.com/tomasbjerre/violations-lib/commit/0516f86b09078c7) Tomas Bjerre *2020-09-27 14:19:19*

**Rewriting PMDParser parser #101**


[5f968](https://github.com/tomasbjerre/violations-lib/commit/5f968ce7f75969e) Tomas Bjerre *2020-09-27 14:07:01*

**Rewriting CheckstyleParser parser #101**


[0b0b2](https://github.com/tomasbjerre/violations-lib/commit/0b0b28466969a11) Tomas Bjerre *2020-09-27 13:53:30*

**Rewriting AndroidLintParser parser #101**


[d2e8c](https://github.com/tomasbjerre/violations-lib/commit/d2e8ca2e819c989) Tomas Bjerre *2020-09-27 13:44:48*

**Rewriting FindbugsParser parser #101**


[c41fa](https://github.com/tomasbjerre/violations-lib/commit/c41fa9da64086e5) Tomas Bjerre *2020-09-27 09:32:45*

**Rewriting CPPCheckParser parser #101**


[17b0d](https://github.com/tomasbjerre/violations-lib/commit/17b0dfe72755568) Tomas Bjerre *2020-09-26 19:11:55*


## 1.130 (2020-09-25)







### Other changes

**Rewriting JUnit parser #108 #101**


[2eac2](https://github.com/tomasbjerre/violations-lib/commit/2eac2357a6a9606) Tomas Bjerre *2020-09-25 18:35:54*


## 1.129 (2020-09-23)







### Other changes

**Allow Checkstyle reports with no line #107**


[708c7](https://github.com/tomasbjerre/violations-lib/commit/708c769aeca3dff) Tomas Bjerre *2020-09-23 15:47:40*


## 1.128 (2020-09-14)







### Other changes

**change parser name to generic**


[99446](https://github.com/tomasbjerre/violations-lib/commit/99446c2bb39cbe5) Anirudh Bagri *2020-09-14 06:15:22*

**map file parser to generic reporter**


[dfe2a](https://github.com/tomasbjerre/violations-lib/commit/dfe2ac1d46365f4) Anirudh Bagri *2020-09-10 15:21:30*

**add file parser**


[0c984](https://github.com/tomasbjerre/violations-lib/commit/0c9846f76ea9a14) Anirudh Bagri *2020-09-10 15:12:07*


## 1.127 (2020-09-06)







### Other changes

**Moving PatchParser from violation-comments-lib #95**


[5c52a](https://github.com/tomasbjerre/violations-lib/commit/5c52a3933792d14) Tomas Bjerre *2020-09-06 05:24:56*


## 1.126 (2020-09-06)







### Other changes

**Moving PatchParser from violation-comments-lib #95**


[8430d](https://github.com/tomasbjerre/violations-lib/commit/8430d1796086717) Tomas Bjerre *2020-09-06 05:16:01*


## 1.125 (2020-09-05)







### Other changes

**adjusting message of junit parser**


[96e73](https://github.com/tomasbjerre/violations-lib/commit/96e73a365e55117) Tomas Bjerre *2020-09-05 06:35:02*


## 1.124 (2020-09-04)







### Other changes

**Support JUNI reports without message #100**


[925c9](https://github.com/tomasbjerre/violations-lib/commit/925c96b9f43b734) Tomas Bjerre *2020-09-04 15:41:14*


## 1.123 (2020-08-27)







### Other changes

**Avoid removing violations that are not duplicates #98**


[0b81e](https://github.com/tomasbjerre/violations-lib/commit/0b81ef8c7fcdd95) Tomas Bjerre *2020-08-27 17:43:08*


## 1.122 (2020-08-27)







### Other changes

**JUnit missing message and type #97**


[2d06b](https://github.com/tomasbjerre/violations-lib/commit/2d06bb63c1266b9) Tomas Bjerre *2020-08-27 16:20:21*


## 1.121 (2020-07-26)







### Other changes

**ignore codeclimate json**


[c04ae](https://github.com/tomasbjerre/violations-lib/commit/c04ae1f28ea2dda) Tomas Bjerre *2020-07-26 06:30:38*

**Add test for MyPy error messages containing colons**


[01cb5](https://github.com/tomasbjerre/violations-lib/commit/01cb5aa61ef7557) David Kolossa *2020-07-25 09:18:59*

**Non-greedy match in MyPy Parser**

* This avoids cutting of error messages containing colons &quot;:&quot; 
* e.g.: 
* error: Need type annotation for &#x27;a&#x27; (hint: &quot;a: List[&lt;type&gt;] &#x3D; ...&quot;) 

[3d40d](https://github.com/tomasbjerre/violations-lib/commit/3d40d5ec9564d35) David Kolossa *2020-07-25 08:37:08*


## 1.120 (2020-07-05)








## 1.119 (2020-07-05)








## 1.118 (2020-06-26)







### Other changes

**Allow missing message in JUnit report #91**


[bc740](https://github.com/tomasbjerre/violations-lib/commit/bc74058e8c2963c) Tomas Bjerre *2020-06-26 16:58:57*


## 1.117 (2020-06-12)







### Other changes

**Add Violation.endColumn property**

* Signed-off-by: Semyon Levin &lt;levin.semen@gmail.com&gt; 

[e5139](https://github.com/tomasbjerre/violations-lib/commit/e513933d7505021) Semyon Levin *2020-06-10 20:31:38*


## 1.116 (2020-05-20)







### Other changes

**Fixing SECURITY-1854**


[b100c](https://github.com/tomasbjerre/violations-lib/commit/b100cdb54e18cae) Tomas Bjerre *2020-05-20 14:42:04*


## 1.115 (2020-05-16)







### Other changes

**Reformatting after merge**


[c1bbc](https://github.com/tomasbjerre/violations-lib/commit/c1bbc0ce5d874c0) Tomas Bjerre *2020-05-16 16:58:22*

**Make CPPCheck opening regex more specific**


[aef04](https://github.com/tomasbjerre/violations-lib/commit/aef04ecae9e9874) Timothy Brackett *2020-05-16 15:21:28*

**Add better test for #86**


[27c0f](https://github.com/tomasbjerre/violations-lib/commit/27c0fe5f01ab3a6) Timothy Brackett *2020-05-16 15:20:20*

**Testing empty cppcheck #86**


[553fd](https://github.com/tomasbjerre/violations-lib/commit/553fd892d041c29) Tomas Bjerre *2020-05-15 16:20:49*


## 1.114 (2020-05-12)








## 1.113 (2020-03-29)







### Other changes

**Not including lines in codeclimate fingerprint tomasbjerre/violations-maven-plugin#1**


[1fff2](https://github.com/tomasbjerre/violations-lib/commit/1fff248fab4afe3) Tomas Bjerre *2020-03-29 14:08:24*


## 1.112 (2020-03-03)







### Other changes

**Doc**


[24a91](https://github.com/tomasbjerre/violations-lib/commit/24a917bc11d0a73) Tomas Bjerre *2020-03-03 18:22:05*


## 1.111 (2020-03-03)







### Other changes

**XUnit tomasbjerre/violations-lib#84**


[04762](https://github.com/tomasbjerre/violations-lib/commit/04762515e66b859) Tomas Bjerre *2020-03-03 16:42:09*

**CPPCheckParser results version 2 #82**


[2b96a](https://github.com/tomasbjerre/violations-lib/commit/2b96ad75cee880a) Tomas Bjerre *2020-02-06 18:27:04*


## 1.110 (2020-02-03)







### Other changes

**CPPCheckParser with auto closed <error/> tags #82**


[64781](https://github.com/tomasbjerre/violations-lib/commit/64781db68be28d7) Tomas Bjerre *2020-02-03 15:57:04*


## 1.109 (2020-01-19)







### Other changes

**Filtering out duplicated violations**


[487cf](https://github.com/tomasbjerre/violations-lib/commit/487cfbbad3f0692) Tomas Bjerre *2020-01-19 15:50:54*

**Correcting Spotbugs warnings**


[97ed6](https://github.com/tomasbjerre/violations-lib/commit/97ed66ad9a70c31) Tomas Bjerre *2020-01-03 06:41:18*


## 1.108 (2020-01-03)







### Other changes

**Add support for newer sonar report format**


[432e5](https://github.com/tomasbjerre/violations-lib/commit/432e5ab3f1efceb) Zakey Faieq *2020-01-02 22:18:04*


## 1.107 (2019-10-22)







### Other changes

**Add parser for protolint**


[80e3c](https://github.com/tomasbjerre/violations-lib/commit/80e3c8dd7fb95ea) David *2019-10-21 20:25:12*


## 1.106 (2019-10-10)







### Other changes

**Parsing PyDocStyle generated with `-s` #71**


[2de77](https://github.com/tomasbjerre/violations-lib/commit/2de776d9f8c8bbc) Tomas Bjerre *2019-10-10 15:22:54*

**1.105-SNAPSHOT**


[6cc2e](https://github.com/tomasbjerre/violations-lib/commit/6cc2ebd6bcc2845) Tomas Bjerre *2019-10-09 19:27:48*

**Accepting empty message in ResharperParser #78**


[fd089](https://github.com/tomasbjerre/violations-lib/commit/fd0896fd6dd242f) Tomas Bjerre *2019-10-09 19:18:36*

**doc**


[ff1af](https://github.com/tomasbjerre/violations-lib/commit/ff1afd7f34348d6) Tomas Bjerre *2019-10-09 16:57:30*

**Spotbugs**


[2cec5](https://github.com/tomasbjerre/violations-lib/commit/2cec50ee48b3f2c) Tomas Bjerre *2019-10-07 18:30:04*


## 1.103 (2019-10-05)







### Other changes

**More logging**


[266e2](https://github.com/tomasbjerre/violations-lib/commit/266e22ad8778fa5) Tomas Bjerre *2019-10-05 06:21:10*

**Testing reportsfinder traversal**


[019ea](https://github.com/tomasbjerre/violations-lib/commit/019eade65d41af1) Tomas Bjerre *2019-10-05 05:24:28*


## 1.102 (2019-10-04)







### Other changes

**Always front slashes when searching FS**

* Also adding logging the help determining why some files is not getting picked up. 

[7e237](https://github.com/tomasbjerre/violations-lib/commit/7e237cc5f23517a) Tomas Bjerre *2019-10-04 14:44:12*

**Create FUNDING.yml**


[42901](https://github.com/tomasbjerre/violations-lib/commit/429014771ec2c64) Tomas Bjerre *2019-09-28 06:58:05*

**Documenting HadoLint #77**


[d516f](https://github.com/tomasbjerre/violations-lib/commit/d516f1a69b84b72) Tomas Bjerre *2019-09-09 14:24:15*

**Validating attributes in CodeClimate Parser #76**


[e0703](https://github.com/tomasbjerre/violations-lib/commit/e07038ba58e9c62) Tomas Bjerre *2019-09-07 12:17:03*


## 1.100 (2019-09-07)







### Other changes

**Validating attributes in CodeClimate Parser #76**


[51074](https://github.com/tomasbjerre/violations-lib/commit/51074e66c7abce0) Tomas Bjerre *2019-09-07 12:09:14*


## 1.99 (2019-09-07)







### Other changes

**CodeClimate #76**


[60420](https://github.com/tomasbjerre/violations-lib/commit/60420531325577f) Tomas Bjerre *2019-09-07 11:45:10*


## 1.98 (2019-09-04)







### Other changes

**Documenting Sonar**


[f6f7f](https://github.com/tomasbjerre/violations-lib/commit/f6f7f7d209fa74d) Tomas Bjerre *2019-09-04 19:52:52*


## 1.97 (2019-09-04)







### Other changes

**Handling Sonar reports without severity #75**


[b7fff](https://github.com/tomasbjerre/violations-lib/commit/b7fff16b27cb2a4) Tomas Bjerre *2019-09-04 19:23:23*


## 1.96 (2019-08-03)







### Other changes

**Renaming JSHINT parser to JSLINT #74**

* Because the format parsed by the parser is actually JSLint. This breaks compatibility! 

[d34e2](https://github.com/tomasbjerre/violations-lib/commit/d34e2a36d3523a0) Tomas Bjerre *2019-08-03 10:36:59*


## 1.95 (2019-08-02)







### Other changes

**Bandit #73**


[0af66](https://github.com/tomasbjerre/violations-lib/commit/0af669fb166b952) Tomas Bjerre *2019-08-02 06:06:51*


## 1.94 (2019-06-15)







### Other changes

**Testing with Mavens Surefire Report #70**


[cd486](https://github.com/tomasbjerre/violations-lib/commit/cd486448a7c4b65) Tomas Bjerre *2019-06-15 05:40:39*

**OpenJDK 11 in Travis**


[d5e57](https://github.com/tomasbjerre/violations-lib/commit/d5e57cf07a068bb) Tomas Bjerre *2019-06-11 17:10:38*

**doc**


[7ec4f](https://github.com/tomasbjerre/violations-lib/commit/7ec4f26bf7b94a4) Tomas Bjerre *2019-06-09 18:09:59*

**Added JUnit parser**


[13100](https://github.com/tomasbjerre/violations-lib/commit/131000779822531) Antti Keistinen *2019-06-04 02:46:29*


## 1.93 (2019-06-07)







### Other changes

**Allowing noise in Flake8 parser #69**


[1ab66](https://github.com/tomasbjerre/violations-lib/commit/1ab666f7d16afdd) Tomas Bjerre *2019-06-07 05:38:45*

**doc**


[8d9a2](https://github.com/tomasbjerre/violations-lib/commit/8d9a2d5c16852f2) Tomas Bjerre *2019-06-01 13:41:05*

**doc**


[b9720](https://github.com/tomasbjerre/violations-lib/commit/b9720430d4db962) Tomas Bjerre *2019-06-01 13:16:48*

**doc**


[09670](https://github.com/tomasbjerre/violations-lib/commit/09670621f7e77fe) Tomas Bjerre *2019-06-01 10:30:52*

**doc**


[9697b](https://github.com/tomasbjerre/violations-lib/commit/9697bfaefe75836) Tomas Bjerre *2019-05-30 20:28:12*


## 1.92 (2019-04-24)







### Other changes

**Bumping version**


[2e345](https://github.com/tomasbjerre/violations-lib/commit/2e3458328cff557) Tomas Bjerre *2019-04-24 15:24:42*

**Bumping version**


[6a1fd](https://github.com/tomasbjerre/violations-lib/commit/6a1fd6fefb88a4c) Tomas Bjerre *2019-04-24 15:07:14*

**Handling line "None" in cpplint #68**


[d9164](https://github.com/tomasbjerre/violations-lib/commit/d9164ce9f45d564) Tomas Bjerre *2019-04-24 14:55:49*

**Testing #68**


[ae136](https://github.com/tomasbjerre/violations-lib/commit/ae13633f9e953aa) Tomas Bjerre *2019-04-23 17:52:56*


## 1.89 (2019-04-03)







### Other changes

**Note on Sonar**


[7656a](https://github.com/tomasbjerre/violations-lib/commit/7656a723dea9410) Tomas Bjerre *2019-04-03 14:17:03*

**Bumping version**


[22776](https://github.com/tomasbjerre/violations-lib/commit/227760877d0df93) Tomas Bjerre *2019-03-29 15:03:56*

**Bumping version**


[2aa01](https://github.com/tomasbjerre/violations-lib/commit/2aa01b76eaf839d) Tomas Bjerre *2019-03-29 14:56:12*

**Correcting Sonar severity mapping #67**


[1eeaf](https://github.com/tomasbjerre/violations-lib/commit/1eeafaff359c01b) Tomas Bjerre *2019-03-29 14:40:51*


## 1.86 (2019-03-23)







### Other changes

**Sonar Report #66**


[09af9](https://github.com/tomasbjerre/violations-lib/commit/09af9bc235197db) Tomas Bjerre *2019-03-23 14:28:21*


## 1.85 (2019-03-13)







### Other changes

**Correcting test case after merge of #65**


[c2f53](https://github.com/tomasbjerre/violations-lib/commit/c2f538660c3f1a5) Tomas Bjerre *2019-03-13 18:57:38*

**Add line attribute to one of the issues reported**

* Add line attribute to one of the issues reported to test optional line number plotting in klocwork report 

[7899d](https://github.com/tomasbjerre/violations-lib/commit/7899d43347d69e3) deepuj *2019-03-13 18:43:05*

**Support line number optionally in klocwork reports**


[b74f8](https://github.com/tomasbjerre/violations-lib/commit/b74f84862211f55) deepuj *2019-03-13 18:38:16*


## 1.84 (2019-03-07)







### Other changes

**golangci-lint**


[fb3dd](https://github.com/tomasbjerre/violations-lib/commit/fb3dd6173a2dc80) Tomas Bjerre *2019-03-07 17:08:38*


## 1.83 (2019-03-07)







### Other changes

**Add support for golangci-lint flavoured Checkstyle**

* aka Checkstyle 5.0, apparently. 
* Signed-off-by: Nic Cope &lt;negz@rk0n.org&gt; 

[1bf28](https://github.com/tomasbjerre/violations-lib/commit/1bf2826f6b26e4a) Nic Cope *2019-03-07 10:25:12*

**Add a test demonstrating that golangci-lint Checkstyle does not parse**

* Signed-off-by: Nic Cope &lt;negz@rk0n.org&gt; 

[82712](https://github.com/tomasbjerre/violations-lib/commit/82712daea397daf) Nic Cope *2019-03-07 10:22:37*


## 1.82 (2019-02-25)







### Other changes

**Adding group attribute #54**


[df1c8](https://github.com/tomasbjerre/violations-lib/commit/df1c897e8d473a3) Tomas Bjerre *2019-02-24 15:28:55*

**Example streaming**


[f9fb9](https://github.com/tomasbjerre/violations-lib/commit/f9fb95039e88d97) Tomas Bjerre *2019-02-20 17:19:25*


## 1.81 (2019-02-10)







### Other changes

**Correcting Flake8 parser #58**


[14d61](https://github.com/tomasbjerre/violations-lib/commit/14d611e4bbf390b) Tomas Bjerre *2019-02-10 15:35:41*


## 1.80 (2019-02-02)







### Other changes

**Sorting reporters in README**


[345c4](https://github.com/tomasbjerre/violations-lib/commit/345c4aa528b1bbf) Tomas Bjerre *2019-02-02 09:35:10*

**Documentation changed to reflect the support for Scalastyle**


[edd14](https://github.com/tomasbjerre/violations-lib/commit/edd14d12d04c619) Andre Carmo *2019-02-01 23:50:25*

**Added ScalaStyle reporter with CheckStyle parser**


[bcf70](https://github.com/tomasbjerre/violations-lib/commit/bcf7092fa4f321d) Andre Carmo *2019-02-01 23:30:12*

**Doc**


[2c6a3](https://github.com/tomasbjerre/violations-lib/commit/2c6a31e642acd8e) Tomas Bjerre *2019-01-30 17:00:29*


## 1.79 (2019-01-29)







### Other changes

**MSCPP and IAR #55 #56**


[91a9b](https://github.com/tomasbjerre/violations-lib/commit/91a9b89bc4514f4) Tomas Bjerre *2019-01-29 16:59:16*

**Including info in CppCheck**


[69e26](https://github.com/tomasbjerre/violations-lib/commit/69e26e53cdcbf77) Tomas Bjerre *2019-01-23 07:22:24*


## 1.78 (2019-01-14)







### Other changes

**Avoiding faulty slash with CodeNarc #53**


[fa97a](https://github.com/tomasbjerre/violations-lib/commit/fa97ac81772c01b) Tomas Bjerre *2019-01-14 17:05:33*


## 1.77 (2019-01-09)







### Other changes

**Setting version to fix faulty release**


[54775](https://github.com/tomasbjerre/violations-lib/commit/547751028bcc781) Tomas Bjerre *2019-01-09 16:42:44*

**Setting version to fix faulty release**


[5754b](https://github.com/tomasbjerre/violations-lib/commit/5754b1124779614) Tomas Bjerre *2019-01-09 16:27:05*

**Respecting source directory in CodeNarc #53**


[2fbb2](https://github.com/tomasbjerre/violations-lib/commit/2fbb203859a0636) Tomas Bjerre *2019-01-08 20:14:11*


## 1.74 (2019-01-02)







### Other changes

**Correctly parsing AnsibleLint #52**


[6c95c](https://github.com/tomasbjerre/violations-lib/commit/6c95cf891f06873) Tomas Bjerre *2019-01-02 21:06:41*

**Podam update**


[274b7](https://github.com/tomasbjerre/violations-lib/commit/274b7897621d919) Tomas Bjerre *2018-12-22 11:38:33*


## 1.73 (2018-11-15)







### Other changes

**Documenting ERB #51**


[50bcf](https://github.com/tomasbjerre/violations-lib/commit/50bcfaaf1afdbdd) Tomas Bjerre *2018-11-15 17:23:40*


## 1.72 (2018-11-14)







### Other changes

**Documenting Puppet-Lint #50**


[ec237](https://github.com/tomasbjerre/violations-lib/commit/ec23727e779c721) Tomas Bjerre *2018-11-14 19:10:04*


## 1.71 (2018-10-08)







### Other changes

**Removing custom Optional**


[8e000](https://github.com/tomasbjerre/violations-lib/commit/8e0005f4897a74d) Tomas Bjerre *2018-10-08 14:10:31*


## 1.70 (2018-10-08)







### Other changes

**Making readme update work on Windows**


[7fbdd](https://github.com/tomasbjerre/violations-lib/commit/7fbdda0d6a1090a) Tomas Bjerre *2018-10-08 06:22:35*


## 1.69 (2018-10-06)







### Other changes

**Including entire rule in Flake8 #46**


[67c84](https://github.com/tomasbjerre/violations-lib/commit/67c8480c165d673) Tomas Bjerre *2018-10-06 06:18:22*

**Updating changelog**


[df538](https://github.com/tomasbjerre/violations-lib/commit/df5387dbf1d4db3) Tomas Bjerre *2018-09-25 07:29:46*


## 1.67 (2018-09-23)







### Other changes

**Automating reporters in readme**


[01d4a](https://github.com/tomasbjerre/violations-lib/commit/01d4a55ff297bd5) Tomas Bjerre *2018-09-23 11:54:36*


## 1.66 (2018-09-23)







### Other changes

**Automating reporters in readme**


[ef097](https://github.com/tomasbjerre/violations-lib/commit/ef097c18f85e488) Tomas Bjerre *2018-09-23 11:38:55*

**Doc**


[93a27](https://github.com/tomasbjerre/violations-lib/commit/93a2736d0116198) Tomas Bjerre *2018-09-22 17:08:39*

**Documenting parsers as a table**


[ba0e4](https://github.com/tomasbjerre/violations-lib/commit/ba0e4e7044e2300) Tomas Bjerre *2018-09-22 08:37:03*

**Updating README.md**


[cc731](https://github.com/tomasbjerre/violations-lib/commit/cc731ae81a2150e) Tomas Bjerre *2018-09-22 08:01:27*


## 1.65 (2018-09-20)







### Other changes

**Correcting Kotlin parsers #45**


[fb2ce](https://github.com/tomasbjerre/violations-lib/commit/fb2ce3dc8149194) Tomas Bjerre *2018-09-20 23:01:21*


## 1.64 (2018-09-20)







### Other changes

**Kotlin Maven and Gradle parsers #44**


[59daa](https://github.com/tomasbjerre/violations-lib/commit/59daadfab031933) Tomas Bjerre *2018-09-20 13:45:35*


## 1.63 (2018-09-17)







### Other changes

**fixed yamllint parser**


[8f804](https://github.com/tomasbjerre/violations-lib/commit/8f804cf422e884d) Aleksei_Philippov *2018-09-17 12:59:38*


## 1.62 (2018-09-15)







### Other changes

**Correcting YAMLLint #42**


[fd4e6](https://github.com/tomasbjerre/violations-lib/commit/fd4e6df1b950bd4) Tomas Bjerre *2018-09-15 07:29:13*

**Gradle Wrapper 4.10.1**


[baf59](https://github.com/tomasbjerre/violations-lib/commit/baf59e87c9821da) Tomas Bjerre *2018-09-15 05:56:42*

**updated README**


[f6621](https://github.com/tomasbjerre/violations-lib/commit/f66213f734f7b6f) Alexey Filippov *2018-09-14 22:05:06*

**added rules for parser**


[93333](https://github.com/tomasbjerre/violations-lib/commit/9333395b1ec1dda) Alexey Filippov *2018-09-14 21:59:27*

**Added YAMLlint parser**


[ae03c](https://github.com/tomasbjerre/violations-lib/commit/ae03c28f28b005c) Aleksei_Philippov *2018-09-14 15:18:11*


## 1.61 (2018-09-12)







### Other changes

**Removing Fliptables dependency**


[32f55](https://github.com/tomasbjerre/violations-lib/commit/32f55e98cc590cb) Tomas Bjerre *2018-09-12 20:19:43*


## 1.60 (2018-09-12)







### Other changes

**Fix copy-n-paste in reporting CPD violations**


[bcc9a](https://github.com/tomasbjerre/violations-lib/commit/bcc9aaf0f8bf978) terje *2018-09-12 18:05:59*

**Testing Spotbugs**


[45778](https://github.com/tomasbjerre/violations-lib/commit/457780347d3fb94) Tomas Bjerre *2018-09-12 16:32:10*


## 1.59 (2018-08-18)







### Other changes

**Adding category to model #39**


[9fa7f](https://github.com/tomasbjerre/violations-lib/commit/9fa7ff29c678148) Tomas Bjerre *2018-08-18 14:13:38*


## 1.58 (2018-07-27)







### Other changes

**Cleaning Violation class, reducing memory**


[efb50](https://github.com/tomasbjerre/violations-lib/commit/efb508a5d73ad23) Tomas Bjerre *2018-07-27 07:14:16*


## 1.57 (2018-07-04)







### Other changes

**GCC, ARM GCC and Doxygen #38**


[06264](https://github.com/tomasbjerre/violations-lib/commit/062647397a858f0) Tomas Bjerre *2018-07-04 17:41:33*


## 1.56 (2018-07-04)







### Other changes

**Excaped message and filename**


[04313](https://github.com/tomasbjerre/violations-lib/commit/04313947c88e23a) Tomas Bjerre *2018-07-04 10:05:38*

**Issue template**


[990f4](https://github.com/tomasbjerre/violations-lib/commit/990f4d6da1000b6) Tomas Bjerre *2018-07-03 14:12:54*

**Updating docs on PyLint**


[40168](https://github.com/tomasbjerre/violations-lib/commit/4016805bf281f1f) Tomas Bjerre *2018-05-03 17:08:10*


## 1.55 (2018-05-01)







### Other changes

**Avoiding Optional in model**


[a2c03](https://github.com/tomasbjerre/violations-lib/commit/a2c03571ee99246) Tomas Bjerre *2018-05-01 04:58:35*

**TSLint**


[9df76](https://github.com/tomasbjerre/violations-lib/commit/9df7684f4fba7e8) Tomas Bjerre *2018-04-20 10:06:18*

**NullAway #33**


[72244](https://github.com/tomasbjerre/violations-lib/commit/72244241bbd2ead) Tomas Bjerre *2018-04-14 05:08:26*

**Testing cpplint variant #35**


[8455f](https://github.com/tomasbjerre/violations-lib/commit/8455fc596c6a547) Tomas Bjerre *2018-04-13 11:45:16*

**Adding another PMD test**


[c36c7](https://github.com/tomasbjerre/violations-lib/commit/c36c7bd5a99a442) Tomas Bjerre *2018-03-07 18:05:26*

**Fixed typo (assertion on wrong issue checked).**


[5381b](https://github.com/tomasbjerre/violations-lib/commit/5381b5d9381a6d1) Ulli Hafner *2018-03-02 22:56:12*

**Bumping version to fix release**


[1cbb6](https://github.com/tomasbjerre/violations-lib/commit/1cbb6a7a096604b) Tomas Bjerre *2018-02-13 18:25:15*

**Autoformatting and removed debug code**


[5e3d4](https://github.com/tomasbjerre/violations-lib/commit/5e3d49f4165798e) Øyvind Rørtveit *2018-02-13 15:10:07*

**Fixed file parsing under Windows, fixed PC-lint parser, added detection of MISRA errors for PC-lint**


[45d30](https://github.com/tomasbjerre/violations-lib/commit/45d308d631a6113) Øyvind Rørtveit *2018-02-13 14:51:34*

**Added PC-lint parser**


[be94e](https://github.com/tomasbjerre/violations-lib/commit/be94e0831808f2b) Øyvind Rørtveit *2018-02-12 16:36:04*


## 1.52 (2018-01-14)







### Other changes

**Google error-prone #10**


[f2720](https://github.com/tomasbjerre/violations-lib/commit/f2720de0ec7d36c) Tomas Bjerre *2018-01-14 11:20:10*


## 1.51 (2018-01-13)







### Other changes

**Parameterize max width of reporter table #30**


[87c5a](https://github.com/tomasbjerre/violations-lib/commit/87c5ab7cb5f4ae7) Tomas Bjerre *2018-01-13 19:06:03*


## 1.50 (2017-12-31)







### Other changes

**Gathering string utils**


[6951f](https://github.com/tomasbjerre/violations-lib/commit/6951f1a5039f968) Tomas Bjerre *2017-12-31 05:51:14*

**Un-escape XML when reading attributes.**

* Fixes #28 

[9b10f](https://github.com/tomasbjerre/violations-lib/commit/9b10f800c7b7941) Sam Judd *2017-12-31 03:25:14*


## 1.49 (2017-12-30)







### Other changes

**Relocating to correct Java identifier**


[8e645](https://github.com/tomasbjerre/violations-lib/commit/8e64583ce0fec03) Tomas Bjerre *2017-12-30 18:32:34*

**Doc**


[ba8ca](https://github.com/tomasbjerre/violations-lib/commit/ba8ca533c8ada71) Tomas Bjerre *2017-12-30 10:27:45*


## 1.48 (2017-12-25)







### Other changes

**Limiting width of report messages**


[0f4c1](https://github.com/tomasbjerre/violations-lib/commit/0f4c1919ccf1ae3) Tomas Bjerre *2017-12-25 16:02:22*


## 1.47 (2017-12-25)







### Other changes

**Using UTF-8, instead of default**


[b3c21](https://github.com/tomasbjerre/violations-lib/commit/b3c21719bdce84c) Tomas Bjerre *2017-12-24 21:49:53*

**Re-throwing any IOException**


[6c5f8](https://github.com/tomasbjerre/violations-lib/commit/6c5f8f5b9edf1dc) Tomas Bjerre *2017-12-24 20:31:13*


## 1.46 (2017-12-24)







### Other changes

**Implementing reporter output**


[e390a](https://github.com/tomasbjerre/violations-lib/commit/e390aaedcb7ec68) Tomas Bjerre *2017-12-24 12:00:57*


## 1.44 (2017-12-22)







### Other changes

**Packaging fat jar as main jar**

* So that no special classifier is needed to get the relocated gson. 

[764fe](https://github.com/tomasbjerre/violations-lib/commit/764fe8fd94835a4) Tomas Bjerre *2017-12-22 18:28:23*


## 1.43 (2017-12-22)







### Other changes

**Bumping version to fix faulty release**


[01745](https://github.com/tomasbjerre/violations-lib/commit/01745f931b7d426) Tomas Bjerre *2017-12-22 17:22:00*

**Adding project.ext.useShadowJar = true**

* Updated release.gradle to only optionally create shadow jar. 

[09f13](https://github.com/tomasbjerre/violations-lib/commit/09f13a83635d7ac) Tomas Bjerre *2017-12-22 15:38:14*

**Doc**


[cffd7](https://github.com/tomasbjerre/violations-lib/commit/cffd7099ed9ad2d) Tomas Bjerre *2017-12-22 12:31:48*


## 1.40 (2017-12-22)







### Other changes

**Fixing release script to include shadow jar**


[dd435](https://github.com/tomasbjerre/violations-lib/commit/dd4353ebc98226a) Tomas Bjerre *2017-12-22 10:51:49*


## 1.39 (2017-12-22)







### Other changes

**Replacing ScriptEngine with Gson**

* To avoid the security issue that arise if custom Javascript can be added to the DocFX file being parsed. 
* Using a shaddow jar (named all) to relocate Gson and avoid classpath issues. 

[524c3](https://github.com/tomasbjerre/violations-lib/commit/524c39c2ff47d40) Tomas Bjerre *2017-12-22 10:35:12*


## 1.38 (2017-12-21)







### Other changes

**DocFX parser JENKINS-48670**


[1ca81](https://github.com/tomasbjerre/violations-lib/commit/1ca81e02c394734) Tomas Bjerre *2017-12-21 12:32:33*

**Ignoring case when checking for equality JENKINS-48669**


[4d706](https://github.com/tomasbjerre/violations-lib/commit/4d7069f628813b6) Tomas Bjerre *2017-12-21 10:12:56*

**Doc**


[8c1de](https://github.com/tomasbjerre/violations-lib/commit/8c1de7f777b3c0e) Tomas Bjerre *2017-12-06 18:23:43*


## 1.36 (2017-12-03)







### Other changes

**Cleaning up build scripts**


[06831](https://github.com/tomasbjerre/violations-lib/commit/068312ba927037d) Tomas Bjerre *2017-12-03 07:09:14*


## 1.35 (2017-12-02)







### Other changes

**Adjusting to OSS repo in Bintray**


[f8c0a](https://github.com/tomasbjerre/violations-lib/commit/f8c0ad2397d2894) Tomas Bjerre *2017-12-02 07:29:54*

**Doc**


[8e0c4](https://github.com/tomasbjerre/violations-lib/commit/8e0c4d426273dd8) Tomas Bjerre *2017-12-02 05:56:31*


## 1.34 (2017-12-01)







### Other changes

**Bintray release scripts**


[a0c35](https://github.com/tomasbjerre/violations-lib/commit/a0c35cce38d7689) Tomas Bjerre *2017-12-01 18:42:50*

**Accepting PMD files without ruleset-tag #27**


[a8e1e](https://github.com/tomasbjerre/violations-lib/commit/a8e1e2ded94e9be) Tomas Bjerre *2017-11-22 19:56:15*

**Adding SwiftLint to Readme**


[70928](https://github.com/tomasbjerre/violations-lib/commit/709283d45a6537c) Tomas Bjerre *2017-11-18 13:37:31*

**Doc**


[f8457](https://github.com/tomasbjerre/violations-lib/commit/f845737670c5e10) Tomas Bjerre *2017-10-28 20:19:52*


## 1.33 (2017-10-13)







### Other changes

**Add Resharper WikiUrl to output message**


[7c102](https://github.com/tomasbjerre/violations-lib/commit/7c1024d3326478d) nickfish *2017-10-12 00:51:10*


## 1.32 (2017-10-09)







### Other changes

**Cleanup after merge #24**


[9eed4](https://github.com/tomasbjerre/violations-lib/commit/9eed4592d38dff7) Tomas Bjerre *2017-10-09 16:27:38*

**Added handling for empty IssueType Description attributes for Resharper**


[dbb61](https://github.com/tomasbjerre/violations-lib/commit/dbb61563dc31487) nickfish *2017-10-09 03:32:29*

**Travis with JDK8**


[4298d](https://github.com/tomasbjerre/violations-lib/commit/4298d4b63e9ba3f) Tomas Bjerre *2017-09-01 19:23:05*


## 1.31 (2017-09-01)







### Other changes

**Allowing absent source in Checkstyle parser #23**


[dd522](https://github.com/tomasbjerre/violations-lib/commit/dd522411c894105) Tomas Bjerre *2017-09-01 10:54:30*


## 1.30 (2017-08-11)







### Other changes

**Checking for null in API-calls**

* For better error messages. 

[21476](https://github.com/tomasbjerre/violations-lib/commit/214765989fbc706) Tomas Bjerre *2017-08-11 09:42:30*

**Testing with reporter**


[b7343](https://github.com/tomasbjerre/violations-lib/commit/b73430f34a55ff6) Tomas Bjerre *2017-07-14 19:31:04*


## 1.29 (2017-07-14)







### Other changes

**Adding withReporter in reporter API**


[5a013](https://github.com/tomasbjerre/violations-lib/commit/5a013dc35c75504) Tomas Bjerre *2017-07-14 19:25:23*


## 1.28 (2017-07-14)







### Other changes

**Renaming Reporter to Parser #22**

* Also adding a reporter String in Violation to record the tool being used to produce the Violation. 

[8401c](https://github.com/tomasbjerre/violations-lib/commit/8401c647572fb5d) Tomas Bjerre *2017-07-14 18:50:23*

**Doc**


[e6c85](https://github.com/tomasbjerre/violations-lib/commit/e6c85825c084b59) Tomas Bjerre *2017-07-12 18:03:16*

**Updating doc on Infer #20**


[22a2e](https://github.com/tomasbjerre/violations-lib/commit/22a2eb9add9884c) Tomas Bjerre *2017-06-23 12:44:57*

**Updating doc about Detekt #19**


[bf547](https://github.com/tomasbjerre/violations-lib/commit/bf5475d3e59fb3d) Tomas Bjerre *2017-06-13 18:14:41*


## 1.27 (2017-04-11)







### Other changes

**URL in Klocwork**


[43fc1](https://github.com/tomasbjerre/violations-lib/commit/43fc14f46fcfb98) Tomas Bjerre *2017-04-11 18:11:22*

**doc**


[4c367](https://github.com/tomasbjerre/violations-lib/commit/4c367bce8658529) Tomas Bjerre *2017-04-10 20:12:21*


## 1.26 (2017-04-10)







### Other changes

**Testing that parsers are mentioned in README.md**


[0f27a](https://github.com/tomasbjerre/violations-lib/commit/0f27abfb54542f3) Tomas Bjerre *2017-04-10 18:10:28*

**Support sbt-scalac**


[bc752](https://github.com/tomasbjerre/violations-lib/commit/bc75205ca390a62) Trung Nguyen *2017-04-10 17:35:19*

**doc**


[9a44d](https://github.com/tomasbjerre/violations-lib/commit/9a44d279d312230) Tomas Bjerre *2017-03-30 19:22:00*


## 1.25 (2017-03-30)







### Other changes

**Klocwork parser**


[4a875](https://github.com/tomasbjerre/violations-lib/commit/4a875240f568a02) Tomas Bjerre *2017-03-30 17:14:56*

**doc**


[009b8](https://github.com/tomasbjerre/violations-lib/commit/009b846e120e0ff) Tomas Bjerre *2017-03-17 21:39:52*


## 1.24 (2017-03-17)







### Other changes

**Adding filtering util**


[30788](https://github.com/tomasbjerre/violations-lib/commit/30788adc503bdd4) Tomas Bjerre *2017-03-17 14:09:58*

**Google Java Format**


[71746](https://github.com/tomasbjerre/violations-lib/commit/71746a1614d51b3) Tomas Bjerre *2017-02-25 12:25:45*

**doc**


[6dd87](https://github.com/tomasbjerre/violations-lib/commit/6dd871dfa4713e9) Tomas Bjerre *2017-02-19 14:15:07*


## 1.23 (2017-02-18)







### Other changes

**Support RubyCop #15**


[75ad3](https://github.com/tomasbjerre/violations-lib/commit/75ad3b3d415384a) Tomas Bjerre *2017-02-18 21:36:36*

**Support CLang #16**


[68ac9](https://github.com/tomasbjerre/violations-lib/commit/68ac9c8cf268d17) Tomas Bjerre *2017-02-18 21:28:31*

**Support GoLint #17**


[fdbc0](https://github.com/tomasbjerre/violations-lib/commit/fdbc050e06fd90a) Tomas Bjerre *2017-02-18 21:01:02*

**PHPMD and PHPCS #14**


[f7212](https://github.com/tomasbjerre/violations-lib/commit/f7212555f7a986b) Tomas Bjerre *2017-02-18 20:01:36*


## 1.22 (2017-02-16)







### Other changes

**Finding findbugsmessages and correcting codenarc**

* Was finding findbugs messages xml incorrectly in classpath. 
* Was not handling codenarc reports with empty line numbers. 

[60d19](https://github.com/tomasbjerre/violations-lib/commit/60d19fedbefdf85) Tomas Bjerre *2017-02-16 20:51:05*

**doc**


[84e50](https://github.com/tomasbjerre/violations-lib/commit/84e505343e497a2) Tomas Bjerre *2017-02-07 05:42:45*


## 1.21 (2017-02-06)







### Other changes

**Adding MyPy and PyDocStyle parsers #12 #13**


[acfe3](https://github.com/tomasbjerre/violations-lib/commit/acfe3312656237f) Tomas Bjerre *2017-02-06 17:07:33*

**Set theme jekyll-theme-slate**


[6063d](https://github.com/tomasbjerre/violations-lib/commit/6063d15ecfbb8a7) Tomas Bjerre *2017-01-12 03:06:06*

**doc**


[94315](https://github.com/tomasbjerre/violations-lib/commit/943154cb27cfd1e) Tomas Bjerre *2016-12-21 16:46:56*


## 1.20 (2016-12-21)







### Other changes

**Renaming parse method in ViolationsParser**

* To make its usage clearer. 

[f4dac](https://github.com/tomasbjerre/violations-lib/commit/f4dacbd541b6625) Tomas Bjerre *2016-12-21 16:34:24*

**doc**


[e1791](https://github.com/tomasbjerre/violations-lib/commit/e17915d91a4ef8a) Tomas Bjerre *2016-12-18 07:50:45*


## 1.19 (2016-12-16)







### Other changes

**Correcting utility method for finding resource**


[5dd52](https://github.com/tomasbjerre/violations-lib/commit/5dd52bd39c5265a) Tomas Bjerre *2016-12-16 07:19:49*


## 1.18 (2016-12-15)







### Other changes

**Removing SLF4J dependency**


[8961d](https://github.com/tomasbjerre/violations-lib/commit/8961db105e44f36) Tomas Bjerre *2016-12-15 17:32:00*


## 1.17 (2016-12-14)







### Other changes

**Removing Guava dependency**


[0b8af](https://github.com/tomasbjerre/violations-lib/commit/0b8af64b20efcca) Tomas Bjerre *2016-12-14 17:48:47*

**doc**


[5d94e](https://github.com/tomasbjerre/violations-lib/commit/5d94e7f3626a273) Tomas Bjerre *2016-11-06 18:12:13*


## 1.16 (2016-11-05)







### Other changes

**Simian, ZPTLint, JCReport**


[60fa8](https://github.com/tomasbjerre/violations-lib/commit/60fa8a9b6091ddd) Tomas Bjerre *2016-11-05 22:39:23*

**Gendarme**


[3ac58](https://github.com/tomasbjerre/violations-lib/commit/3ac58ccc82b8360) Tomas Bjerre *2016-11-05 21:28:33*

**CPD**


[7a4e6](https://github.com/tomasbjerre/violations-lib/commit/7a4e651c53ab3b2) Tomas Bjerre *2016-11-05 19:16:50*

**CodeNarc**


[23fa5](https://github.com/tomasbjerre/violations-lib/commit/23fa562feee2bb2) Tomas Bjerre *2016-11-05 18:58:32*


## 1.15 (2016-11-03)







### Other changes

**Exposing parser in reporter**


[2e216](https://github.com/tomasbjerre/violations-lib/commit/2e216ee38cb2fdb) Tomas Bjerre *2016-11-03 18:03:08*


## 1.14 (2016-11-03)







### Other changes

**Letting the reporters parse strings, not files**


[37570](https://github.com/tomasbjerre/violations-lib/commit/375706d8e579bfc) Tomas Bjerre *2016-11-03 17:54:20*


## 1.13 (2016-10-26)







### Other changes

**Handling css-lint reports where there are not line or evidence #11**

* Also setting severity level for PyLint. 

[35d5d](https://github.com/tomasbjerre/violations-lib/commit/35d5d33b447b37a) Tomas Bjerre *2016-10-26 15:39:37*


## 1.12 (2016-10-25)







### Other changes

**Changing rule format in PyLint to CODE(codeName)**


[24618](https://github.com/tomasbjerre/violations-lib/commit/24618707a88497c) Tomas Bjerre *2016-10-25 17:44:33*


## 1.11 (2016-10-24)







### Other changes

**PyLint parser**


[ec90a](https://github.com/tomasbjerre/violations-lib/commit/ec90aa741fdeb67) Tomas Bjerre *2016-10-24 17:16:46*


## 1.10 (2016-10-03)







### Other changes

**Supporting StyleCop**

* And project level issue in FxCop. 

[d68d3](https://github.com/tomasbjerre/violations-lib/commit/d68d368e8ffc609) Tomas Bjerre *2016-10-03 17:00:57*

**Formatting code**


[09347](https://github.com/tomasbjerre/violations-lib/commit/09347946765a096) Tomas Bjerre *2016-10-02 12:34:51*


## 1.9 (2016-10-01)







### Other changes

**ESLint #4**


[e9f33](https://github.com/tomasbjerre/violations-lib/commit/e9f338ef44d3a45) Tomas Bjerre *2016-10-01 11:25:19*

**Preliminary support for StyleCop**


[81e30](https://github.com/tomasbjerre/violations-lib/commit/81e30321fa1619b) Tomas Bjerre *2016-10-01 10:24:14*

**Support for FxCop**


[ac5a5](https://github.com/tomasbjerre/violations-lib/commit/ac5a58c002f8c97) Tomas Bjerre *2016-10-01 09:55:19*

**Refactoring, adding ViolationsParser interface**

* Also preparing for FxCop. 

[adef3](https://github.com/tomasbjerre/violations-lib/commit/adef3f6ed9c6cfc) Tomas Bjerre *2016-10-01 08:38:19*

**doc**


[4f542](https://github.com/tomasbjerre/violations-lib/commit/4f54260b7b47e96) Tomas Bjerre *2016-07-28 20:22:22*


## 1.8 (2016-04-27)







### Other changes

**Updating README.md**

* And formatting code after merge of PR. 

[34cc6](https://github.com/tomasbjerre/violations-lib/commit/34cc693dfcc7bad) Tomas Bjerre *2016-04-27 05:34:51*

**Add Android Lint parser**


[05a11](https://github.com/tomasbjerre/violations-lib/commit/05a11ed65560142) panpanini *2016-04-27 04:38:50*

**Doc**


[8a3d8](https://github.com/tomasbjerre/violations-lib/commit/8a3d860da48c56d) Tomas Bjerre *2016-04-23 17:08:41*


## 1.7 (2016-04-23)







### Other changes

**Making Violation class serializable**


[408ea](https://github.com/tomasbjerre/violations-lib/commit/408ea213a51516c) Tomas Bjerre *2016-04-23 17:06:14*

**Updating docs**


[30e7a](https://github.com/tomasbjerre/violations-lib/commit/30e7ac53b0b7efd) Tomas Bjerre *2016-04-07 16:39:05*


## 1.6 (2016-04-07)







### Other changes

**Correcting exception message thrown if attribute not found #7**

* XMLStreamReader does not have its own implementation of toString. 

[53719](https://github.com/tomasbjerre/violations-lib/commit/53719338add95a3) Tomas Bjerre *2016-04-07 16:24:14*

**FindBugsParser is using string matching to try to parse XML, which is**

* failing because SourceLine elements may, or may not self-terminate. 
* Convert to use a StAX parser, which is likely more performant since it 
* does not need to construct regexes and lists to store the results in, but 
* instead will do a forwards-only stream read. 
* Signed-off-by: Nigel Magnay &lt;nigel.magnay@gmail.com&gt; 

[c6679](https://github.com/tomasbjerre/violations-lib/commit/c6679a504380bcd) Nigel Magnay *2016-04-07 15:41:56*

**Adding pitest parser #5**


[eeb2a](https://github.com/tomasbjerre/violations-lib/commit/eeb2a624a99a7ff) Tomas Bjerre *2016-03-26 19:22:06*

**Adding Jenkins plugin link to README.md**


[eb90c](https://github.com/tomasbjerre/violations-lib/commit/eb90c644065c9b6) Tomas Bjerre *2016-03-06 20:20:42*


## 1.5 (2016-03-06)







### Other changes

**Support PerlCritic and XMLLint #3**


[b7c4e](https://github.com/tomasbjerre/violations-lib/commit/b7c4ea0c66a4c69) Tomas Bjerre *2016-03-06 13:45:16*

**Support CPPLint #3**


[319ea](https://github.com/tomasbjerre/violations-lib/commit/319eaabe55b383b) Tomas Bjerre *2016-03-06 13:39:27*

**Flake8 (PyLint) and Lint parser #3**


[30ba7](https://github.com/tomasbjerre/violations-lib/commit/30ba7fd5ed017d3) Tomas Bjerre *2016-03-06 13:39:25*

**Resharper support #3**


[5bfc5](https://github.com/tomasbjerre/violations-lib/commit/5bfc5d3448ab978) Tomas Bjerre *2016-03-06 13:39:23*

**CPPCheck support #3**


[e1a7a](https://github.com/tomasbjerre/violations-lib/commit/e1a7ad7be14653b) Tomas Bjerre *2016-03-06 13:39:20*


## 1.4 (2016-03-05)







### Other changes

**Adding possibility to set findbugs messages**


[3a8c7](https://github.com/tomasbjerre/violations-lib/commit/3a8c7089d924848) Tomas Bjerre *2016-03-05 09:34:51*


## 1.3 (2016-03-04)







### Other changes

**Only most specific FindBugs line**

* Also SLF4J and some debug logging. 

[b50c5](https://github.com/tomasbjerre/violations-lib/commit/b50c549861119cf) Tomas Bjerre *2016-03-04 17:16:52*


## 1.2 (2016-03-04)







### Other changes

**Adding reporter to Violation**


[dddda](https://github.com/tomasbjerre/violations-lib/commit/ddddaefba1f1aee) Tomas Bjerre *2016-03-04 05:34:23*

**Adding links to projects using this lib**


[b6b4b](https://github.com/tomasbjerre/violations-lib/commit/b6b4b74339b5008) Tomas Bjerre *2016-03-03 21:05:58*

**Moving example reports to its own repo**


[34456](https://github.com/tomasbjerre/violations-lib/commit/344567418ac1167) Tomas Bjerre *2016-03-03 18:53:24*


## 1.1 (2016-02-28)







### Other changes

**Adding accumulated builder**

* With sorting by file or severity. 
* With filtering of severity. 

[9b87b](https://github.com/tomasbjerre/violations-lib/commit/9b87b1d1184d184) Tomas Bjerre *2016-02-28 19:40:26*


## 1.0 (2016-02-21)







### Other changes

**Update README.md**


[2dca0](https://github.com/tomasbjerre/violations-lib/commit/2dca003de3f0764) Tomas Bjerre *2016-02-21 13:09:41*

**JSHint and PMD parsers**


[48773](https://github.com/tomasbjerre/violations-lib/commit/48773067b5e86d1) Tomas Bjerre *2016-02-21 12:18:13*

**Findbugs parser**


[39548](https://github.com/tomasbjerre/violations-lib/commit/395483a7117cdb7) Tomas Bjerre *2016-02-21 11:42:53*

**Adding specifics and column to Violation**


[d6e09](https://github.com/tomasbjerre/violations-lib/commit/d6e0920176a6d9f) Tomas Bjerre *2016-02-21 08:33:00*

**Parsers and sample reports**


[b8d5a](https://github.com/tomasbjerre/violations-lib/commit/b8d5a66e8ec407c) Tomas Bjerre *2016-02-20 21:40:52*

**First commit**


[f141f](https://github.com/tomasbjerre/violations-lib/commit/f141f3565174869) Tomas Bjerre *2016-02-18 18:17:50*


