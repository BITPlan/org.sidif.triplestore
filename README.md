### org.sidif.triplestore
[Simple Data Interchange Format (SiDIF) and Triplestore see http://www.sidif.org](http://wiki.bitplan.com/index.php/SiDIF-TripleStore)

[![Travis (.org)](https://img.shields.io/travis/BITPlan/org.sidif.triplestore.svg)](https://travis-ci.org/BITPlan/org.sidif.triplestore)
[![Maven Central](https://img.shields.io/maven-central/v/com.bitplan/org.sidif.triplestore.svg)](https://search.maven.org/artifact/com.bitplan/org.sidif.triplestore/0.0.9/jar)

[![GitHub issues](https://img.shields.io/github/issues/BITPlan/org.sidif.triplestore.svg)](https://github.com/BITPlan/org.sidif.triplestore/issues)
[![GitHub issues](https://img.shields.io/github/issues-closed/BITPlan/org.sidif.triplestore.svg)](https://github.com/BITPlan/org.sidif.triplestore/issues/?q=is%3Aissue+is%3Aclosed)
[![GitHub](https://img.shields.io/github/license/BITPlan/org.sidif.triplestore.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![BITPlan](http://wiki.bitplan.com/images/wiki/thumb/3/38/BITPlanLogoFontLessTransparent.png/198px-BITPlanLogoFontLessTransparent.png)](http://www.bitplan.com)

### Documentation
* [Wiki](http://wiki.bitplan.com/index.php/SiDIF-TripleStore)
* [org.sidif.triplestore Project pages](https://BITPlan.github.io/org.sidif.triplestore)
* [Javadoc](https://BITPlan.github.io/org.sidif.triplestore/apidocs/index.html)
* [Test-Report](https://BITPlan.github.io/org.sidif.triplestore/surefire-report.html)
### Maven dependency

Maven dependency
```xml
<!-- Simple Data Interchange Format (SiDIF) and Triplestore see http://www.sidif.org http://wiki.bitplan.com/index.php/SiDIF-TripleStore -->
<dependency>
  <groupId>com.bitplan</groupId>
  <artifactId>org.sidif.triplestore</artifactId>
  <version>0.0.9</version>
</dependency>
```

[Current release at repo1.maven.org](https://repo1.maven.org/maven2/com/bitplan/org.sidif.triplestore/0.0.9/)

### How to build
```
git clone https://github.com/BITPlan/org.sidif.triplestore
cd org.sidif.triplestore
mvn install
```
## Version history
* 0.0.1 - 2015-03-12: first release
* 0.0.2 - 2015-03-13: adds command line interface
* 0.0.3 - 2015-05-18: fixes issue #1
* 0.0.4 - 2015-11-18: adds travis build, refactors to extract some interfaces
* 0.0.5 - 2016-11-15: fixes #2
* 0.0.6 - 2017-05-24: fixes #3
* 0.0.7 - 2017-10-15: refactors to use ANTLR parser
* 0.0.8 - 2018-01-14: improves test handling 
* 0.0.9 - 2018-01-14: fixes ANTLR TreeViewer dependency
