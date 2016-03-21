name := """Providence"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.hibernate" % "hibernate-c3p0" % "4.3.9.Final",
  "org.hibernate" % "hibernate-ehcache" % "4.3.9.Final",
  "org.postgresql" % "postgresql" % "9.4.1207",
  "org.springframework.security" % "spring-security-core" % "4.0.4.RELEASE"
)

routesGenerator := InjectedRoutesGenerator
