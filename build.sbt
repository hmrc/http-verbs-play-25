val appName = "http-verbs-play-25"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(
    majorVersion                     := 1,
    makePublicallyAvailableOnBintray := true,
    scalaVersion                     := "2.11.7",
    crossScalaVersions               := Seq("2.11.7")
  )
