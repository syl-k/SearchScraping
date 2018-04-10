name := "SearchScraping"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
    "org.scalafx" %% "scalafx" % "8.0.92-R10",
    "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2",
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.5.0"
)

resolvers += Resolver.sonatypeRepo("releases")
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.opencsv" % "opencsv" % "3.7"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.0.0"

libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.2"
)