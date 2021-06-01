name := "scala-learn"

version := "0.1"

scalaVersion := "2.12.11"

resolvers += "aliyun" at "https://maven.aliyun.com/repository/public"

lazy val akkaVersion     = "2.6.14"
lazy val akkaHttpVersion = "10.2.4"
lazy val akkaGrpcVersion = "1.1.1"

val testStack = Seq(
  "org.scalatest"     %% "scalatest"                % "3.1.0",
  "org.scalamock"     %% "scalamock"                % "5.1.0",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit"      % akkaVersion
).map(_ % Test)

val akk       = Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"      % akkaVersion,
  "com.typesafe.akka" %% "akka-discovery"   % akkaVersion,
  "com.typesafe.akka" %% "akka-pki"         % akkaVersion
)

val akkHttp = Seq(
  "com.typesafe.akka" %% "akka-http"          % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http2-support" % akkaHttpVersion
)

val versionConfig = "1.4.0"
val config        = Seq(
  "com.typesafe" % "config" % versionConfig
)

val versionNacos                 = "1.3.2"
val versionScalaCollectionCompat = "2.2.0"
val nacosClient                  = Seq(
  "org.scala-lang.modules" %% "scala-collection-compat" % versionScalaCollectionCompat,
  "com.alibaba.nacos"       % "nacos-client"            % versionNacos
)

val scalaCache = Seq(
  "com.github.cb372" %% "scalacache-guava" % "0.28.0",
  "com.github.cb372" %% "scalacache-redis" % "0.28.0"
)

val embeddedkafka = Seq(
  "io.github.embeddedkafka" %% "embedded-kafka" % "2.6.0"
)

val h2          = Seq(
  "com.h2database" % "h2" % "1.4.197"
)
val redisClient = Seq(
  "net.debasishg" %% "redisclient" % "3.30"
)

libraryDependencies in ThisBuild ++=
  testStack

lazy val embedded_redis = Project(id = "embedded-redis", base = file("embedded-redis"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++=
      Seq("it.ozimov" % "embedded-redis" % "0.7.1") ++
        redisClient
  )

lazy val scala_cache = Project(id = "scala-cache", base = file("scala-cache"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn(embedded_redis)
  .settings(
    libraryDependencies ++=
      scalaCache
  )

lazy val scala_mock = Project(id = "scala-mock", base = file("scala-mock"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn(embedded_redis)
  .settings(
    libraryDependencies ++=
      h2 ++
        embeddedkafka
  )

lazy val scala_test = Project(id = "scala-test", base = file("scala-test"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn(embedded_redis)
  .settings(
  )

lazy val nacos_sdk_scala = Project(id = "nacos-sdk-scala", base = file("nacos-sdk-scala"))
  .enablePlugins(JavaAppPackaging, AkkaGrpcPlugin)
  .dependsOn()
  .settings(
    libraryDependencies ++=
      config ++
        nacosClient
  )

lazy val akka_grpc_server = Project(id = "akka-grpc-server", base = file("akka-grpc-server"))
  .enablePlugins(JavaAppPackaging, AkkaGrpcPlugin)
  .dependsOn(akka_grpc_stub, nacos_sdk_scala)
  .settings(
    libraryDependencies ++=
      akk ++
        akkHttp
  )

lazy val akka_grpc_client = Project(id = "akka-grpc-client", base = file("akka-grpc-client"))
  .enablePlugins(JavaAppPackaging, AkkaGrpcPlugin)
  .dependsOn(akka_grpc_stub, nacos_sdk_scala)
  .settings(
    libraryDependencies ++=
      akk ++
        akkHttp
  )

lazy val akka_grpc_stub = Project(id = "akka-grpc-stub", base = file("akka-grpc-stub"))
  .enablePlugins(JavaAppPackaging, AkkaGrpcPlugin)
  .dependsOn()
  .settings(
  )
