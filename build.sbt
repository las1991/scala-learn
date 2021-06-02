name := "scala-learn"

version := "0.1"

scalaVersion := "2.12.11"

resolvers += "aliyun" at "https://maven.aliyun.com/repository/public"

val logback = Seq(
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)

lazy val akkaVersion     = "2.6.14"
lazy val akkaHttpVersion = "10.2.4"
lazy val akkaGrpcVersion = "1.1.1"

val testStack = Seq(
  "org.scalatest"     %% "scalatest"                % "3.1.0",
  "org.scalamock"     %% "scalamock"                % "5.1.0",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit"      % akkaVersion
).map(_ % Test)

val akka      = Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"      % akkaVersion,
  "com.typesafe.akka" %% "akka-discovery"   % akkaVersion,
  "com.typesafe.akka" %% "akka-pki"         % akkaVersion
)

val akkaHttp = Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http2-support"   % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
)

val configVersion = "1.4.0"
val config        = Seq(
  "com.typesafe" % "config" % configVersion
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

val aspectj = Seq(
  "org.aspectj" % "aspectjweaver" % "1.7.2",
  "org.aspectj" % "aspectjrt"     % "1.7.2"
)

libraryDependencies in ThisBuild ++=
  logback ++
    testStack

lazy val scala_base = Project(id = "scala-base", base = file("scala-base"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn()
  .settings(
    libraryDependencies ++= akka
  )

lazy val akka_base = Project(id = "akka-base", base = file("akka-base"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn()
  .settings(
    libraryDependencies ++=
      akka ++
        akkaHttp
  )

lazy val akka_http = Project(id = "akka-http", base = file("akka-http"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn()
  .settings(
    libraryDependencies ++=
      akka ++
        akkaHttp
  )

lazy val embedded_redis = Project(id = "embedded-redis", base = file("embedded-redis"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++=
      Seq("it.ozimov" % "embedded-redis" % "0.7.1") ++
        redisClient
  )

lazy val aspectj_compile_annotations_tracer       =
  Project(id = "aspectj-compile-annotations-tracer", base = file("aspectj-compile-annotations-tracer"))
    .enablePlugins(JavaAppPackaging, SbtAspectj)
    .dependsOn()
    .settings(
      aspectjVerbose in Aspectj := true,
      // input compiled scala classes
      aspectjInputs in Aspectj += (aspectjCompiledClasses in Aspectj).value,
      // ignore warnings
      aspectjLintProperties in Aspectj += "invalidAbsoluteTypeName = ignore",
      aspectjLintProperties in Aspectj += "adviceDidNotMatch = ignore",
      // replace regular products with compiled aspects
      products in Compile := (products in Aspectj).value,
      libraryDependencies ++= aspectj
    )
lazy val aspectj_compile_annotations_instrumented =
  Project(id = "aspectj-compile-annotations-instrumented", base = file("aspectj-compile-annotations-instrumented"))
    .enablePlugins(JavaAppPackaging, SbtAspectj)
    .dependsOn(aspectj_compile_annotations_tracer)
    .settings(
      aspectjVerbose in Aspectj := true,
      // add the compiled aspects from tracer
      aspectjBinaries in Aspectj ++= (products in Compile in aspectj_compile_annotations_tracer).value,
      // weave this project's classes
      aspectjInputs in Aspectj += (aspectjCompiledClasses in Aspectj).value,
      products in Compile := (products in Aspectj).value,
      products in Runtime := (products in Compile).value,
      libraryDependencies ++= aspectj
    )

// for sbt scripted test:
TaskKey[Unit]("check_aspectj_compile_annotations_instrumented") := {
  import scala.sys.process.Process

  val cp   = (fullClasspath in Compile in aspectj_compile_annotations_instrumented).value
  val mc   = (mainClass in Compile in aspectj_compile_annotations_instrumented).value
  val opts = (javaOptions in run in Compile in aspectj_compile_annotations_instrumented).value

  val LF       = System.lineSeparator()
  val expected = "Printing sample:" + LF + "hello" + LF
  val output   = Process("java", opts ++ Seq("-classpath", cp.files.absString, mc getOrElse "")).!!
  if (output != expected) {
    println("Unexpected output:")
    println(output)
    println("Expected:")
    println(expected)
    sys.error("Unexpected output")
  } else {
    print(output)
  }
}

lazy val aspectj_weave_annotations =
  Project(id = "aspectj-weave-annotations", base = file("aspectj-weave-annotations"))
    .enablePlugins(JavaAppPackaging, SbtAspectj)
    .settings(
      aspectjShowWeaveInfo in Aspectj := true,
      aspectjVerbose in Aspectj := true,
      //      aspectjDirectory in Aspectj := crossTarget.value,
      // add compiled classes as an input to aspectj
      aspectjInputs in Aspectj += (aspectjCompiledClasses in Aspectj).value,
      // use the results of aspectj weaving
      products in Compile := (products in Aspectj).value,
      products in Runtime := (products in Compile).value,
      libraryDependencies ++= aspectj
    )

TaskKey[Unit]("check_aspectj_weave_annotations") := {
  import scala.sys.process.Process

  val cp   = (fullClasspath in Compile in aspectj_weave_annotations).value
  val mc   = (mainClass in Compile in aspectj_weave_annotations).value
  val opts = (javaOptions in run in Compile in aspectj_weave_annotations).value

  val LF       = System.lineSeparator()
  val expected = "Method=execution(Sum.checkSum(..)), Input=2,3, Result=5" + LF
  val output   = Process("java", opts ++ Seq("-classpath", cp.files.absString, mc getOrElse "")).!!
  if (output != expected) {
    println("Unexpected output:")
    println(output)
    println("Expected:")
    println(expected)
    sys.error("Unexpected output")
  } else {
    println(opts)
    println(Seq("-classpath", cp.files.absString, mc getOrElse ""))
    print("output: " + output)
  }
}

lazy val aspectj_weave_load_time =
  Project(id = "aspectj-weave-load-time", base = file("aspectj-weave-load-time"))
    .enablePlugins(JavaAppPackaging, SbtAspectj)
    .settings(
      aspectjShowWeaveInfo in Aspectj := true,
      aspectjVerbose in Aspectj := true,
      // only compile the aspects (no weaving)
      aspectjCompileOnly in Aspectj := true,
      // add the compiled aspects as products
      products in Compile ++= (products in Aspectj).value,
      // fork the run so that javaagent option can be added
      fork in run := true,
      // add the aspectj weaver javaagent option
      javaOptions in run ++= (aspectjWeaverOptions in Aspectj).value,
      libraryDependencies ++= aspectj
    )

TaskKey[Unit]("check_aspectj_weave_load_time") := {
  import scala.sys.process.Process

  val cp   = (fullClasspath in Compile in aspectj_weave_load_time).value
  val mc   = (mainClass in Compile in aspectj_weave_load_time).value
  val opts = (javaOptions in run in Compile in aspectj_weave_load_time).value

  val LF       = System.lineSeparator()
  val expected = "Method=execution(Sum.checkSum(..)), Input=2,3, Result=5" + LF

  println(opts)
  println(opts ++ Seq("-classpath", cp.files.absString, mc getOrElse ""))

  val output = Process("java", opts ++ Seq("-classpath", cp.files.absString, mc getOrElse "")).!!
  if (output != expected) {
    println("Unexpected output:")
    println(output)
    println("Expected:")
    println(expected)
    sys.error("Unexpected output")
  } else {

    print("output: " + output)
  }
}

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
      akka ++
        akkaHttp
  )

lazy val akka_grpc_client = Project(id = "akka-grpc-client", base = file("akka-grpc-client"))
  .enablePlugins(JavaAppPackaging, AkkaGrpcPlugin)
  .dependsOn(akka_grpc_stub, nacos_sdk_scala)
  .settings(
    libraryDependencies ++=
      akka ++
        akkaHttp
  )

lazy val akka_grpc_stub = Project(id = "akka-grpc-stub", base = file("akka-grpc-stub"))
  .enablePlugins(JavaAppPackaging, AkkaGrpcPlugin)
  .dependsOn()
  .settings(
  )
