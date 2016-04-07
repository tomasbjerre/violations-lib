# Git Changelog changelog

Changelog of Git Changelog.

## 1.6
### GitHub [#5](https://github.com/tomasbjerre/violations-lib/issues/5) 

**Adding pitest parser**


[eeb2a624a99a7ff](https://github.com/tomasbjerre/git-changelog-lib/commit/eeb2a624a99a7ff) Tomas Bjerre *2016-03-26 19:22:06*


### GitHub [#7](https://github.com/tomasbjerre/violations-lib/issues/7) 

**Correcting exception message thrown if attribute not found**

 * XMLStreamReader does not have its own implementation of toString. 

[53719338add95a3](https://github.com/tomasbjerre/git-changelog-lib/commit/53719338add95a3) Tomas Bjerre *2016-04-07 16:24:14*


### No issue

**FindBugsParser is using string matching to try to parse XML, which is**

 * failing because SourceLine elements may, or may not self-terminate. 
 * Convert to use a StAX parser, which is likely more performant since it 
 * does not need to construct regexes and lists to store the results in, but 
 * instead will do a forwards-only stream read. 
 * Signed-off-by: Nigel Magnay &lt;nigel.magnay@gmail.com&gt; 

[c6679a504380bcd](https://github.com/tomasbjerre/git-changelog-lib/commit/c6679a504380bcd) Nigel Magnay *2016-04-07 15:41:56*

**Adding Jenkins plugin link to README.md**


[eb90c644065c9b6](https://github.com/tomasbjerre/git-changelog-lib/commit/eb90c644065c9b6) Tomas Bjerre *2016-03-06 20:20:42*


## 1.5
### GitHub [#3](https://github.com/tomasbjerre/violations-lib/issues/3) 

**Support PerlCritic and XMLLint**


[b7c4ea0c66a4c69](https://github.com/tomasbjerre/git-changelog-lib/commit/b7c4ea0c66a4c69) Tomas Bjerre *2016-03-06 13:45:16*

**Support CPPLint**


[319eaabe55b383b](https://github.com/tomasbjerre/git-changelog-lib/commit/319eaabe55b383b) Tomas Bjerre *2016-03-06 13:39:27*

**Flake8 (PyLint) and Lint parser**


[30ba7fd5ed017d3](https://github.com/tomasbjerre/git-changelog-lib/commit/30ba7fd5ed017d3) Tomas Bjerre *2016-03-06 13:39:25*

**Resharper support**


[5bfc5d3448ab978](https://github.com/tomasbjerre/git-changelog-lib/commit/5bfc5d3448ab978) Tomas Bjerre *2016-03-06 13:39:23*

**CPPCheck support**


[e1a7ad7be14653b](https://github.com/tomasbjerre/git-changelog-lib/commit/e1a7ad7be14653b) Tomas Bjerre *2016-03-06 13:39:20*


## 1.4
### No issue

**Adding possibility to set findbugs messages**


[3a8c7089d924848](https://github.com/tomasbjerre/git-changelog-lib/commit/3a8c7089d924848) Tomas Bjerre *2016-03-05 09:34:51*


## 1.3
### No issue

**Only most specific FindBugs line**

 * Also SLF4J and some debug logging. 

[b50c549861119cf](https://github.com/tomasbjerre/git-changelog-lib/commit/b50c549861119cf) Tomas Bjerre *2016-03-04 17:16:52*


## 1.2
### No issue

**Adding reporter to Violation**


[ddddaefba1f1aee](https://github.com/tomasbjerre/git-changelog-lib/commit/ddddaefba1f1aee) Tomas Bjerre *2016-03-04 05:34:23*

**Adding links to projects using this lib**


[b6b4b74339b5008](https://github.com/tomasbjerre/git-changelog-lib/commit/b6b4b74339b5008) Tomas Bjerre *2016-03-03 21:05:58*

**Moving example reports to its own repo**


[344567418ac1167](https://github.com/tomasbjerre/git-changelog-lib/commit/344567418ac1167) Tomas Bjerre *2016-03-03 18:53:24*


## 1.1
### No issue

**Adding accumulated builder**

 * With sorting by file or severity. 
 * With filtering of severity. 

[9b87b1d1184d184](https://github.com/tomasbjerre/git-changelog-lib/commit/9b87b1d1184d184) Tomas Bjerre *2016-02-28 19:40:26*


## 1.0
### No issue

**Update README.md**


[2dca003de3f0764](https://github.com/tomasbjerre/git-changelog-lib/commit/2dca003de3f0764) Tomas Bjerre *2016-02-21 13:09:41*

**JSHint and PMD parsers**


[48773067b5e86d1](https://github.com/tomasbjerre/git-changelog-lib/commit/48773067b5e86d1) Tomas Bjerre *2016-02-21 12:18:13*

**Findbugs parser**


[395483a7117cdb7](https://github.com/tomasbjerre/git-changelog-lib/commit/395483a7117cdb7) Tomas Bjerre *2016-02-21 11:42:53*

**Adding specifics and column to Violation**


[d6e0920176a6d9f](https://github.com/tomasbjerre/git-changelog-lib/commit/d6e0920176a6d9f) Tomas Bjerre *2016-02-21 08:33:00*

**Parsers and sample reports**


[b8d5a66e8ec407c](https://github.com/tomasbjerre/git-changelog-lib/commit/b8d5a66e8ec407c) Tomas Bjerre *2016-02-20 21:40:52*

**First commit**


[f141f3565174869](https://github.com/tomasbjerre/git-changelog-lib/commit/f141f3565174869) Tomas Bjerre *2016-02-18 18:17:50*


