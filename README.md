http-verbs-play-25
==========

[![Build Status](https://travis-ci.org/hmrc/http-verbs-play-25 .svg)](https://travis-ci.org/hmrc/http-verbs-play-25 ) [ ![Download](https://api.bintray.com/packages/hmrc/releases/http-verbs-play-25 /images/download.svg) ](https://bintray.com/hmrc/releases/http-verbs-play-25 /_latestVersion)

## This library is deprecated
### All classes from this library have been copied to http-verbs starting from version 8.5.0. Some time ago http-verbs was split into three libraries: http-core (meant to be play-independent), http-verbs (play independent) and http-verbs-play-25 (with a dependency on play). Unfortunately http-core still contained play-json which is not binary compatibile between play 2.5 and 2.6. This means that libraries compiled against http-core could fail at runtime when used with a microservice using play 2.6. A decision was made to collapse all the classess from http-core, http-verbs and http-verbs-play-25 and only keep one single library: http-verbs. It will be available for both Play 2.5 and Play 2.6.

http-verbs-play-25 is a Scala library providing concrete implementation of [hrmc/http-core](https://github.com/hmrc/http-core) for making asynchronous HTTP calls. The underlying implementation uses [Play 2.5 WS](https://www.playframework.com/documentation/2.5.x/ScalaWS).

It encapsulates some common concerns for calling other HTTP services on the HMRC Tax Platform, including:

* ~~Auditing~~
* Logging
* Propagation of common headers
* Response handling, converting failure status codes into a consistent set of exceptions - allows failures to be automatically propagated to the caller
* Request & Response de-serialization

**Auditing is no longer part of http-verbs, please see docs for [play-auditing](http://github.com/hmrc/play-auditing) for further info.**

## Adding to your build

In your SBT build add:

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

// is needed in your project to get the implementation for http transport
libraryDependencies += "uk.gov.hmrc" %% "http-verbs-play-25" % "x.x.x"

// is needed in your project to get the implementation of the business logic 
libraryDependencies += "uk.gov.hmrc" %% "http-verbs" % "x.x.x" 
```

## Usage

All examples are available here:[hmrc/http-verbs-example](https://github.com/hmrc/http-verbs-example)  



## License ##
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
