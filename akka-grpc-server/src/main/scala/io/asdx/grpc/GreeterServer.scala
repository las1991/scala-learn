package io.asdx.grpc

import java.security.{KeyStore, SecureRandom}
import java.security.cert.{Certificate, CertificateFactory}

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.{ConnectionContext, Http, HttpsConnectionContext}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.pki.pem.{DERPrivateKeyLoader, PEMDecoder}
import com.typesafe.config.ConfigFactory
import io.asdx.grpc.service.impl.GreeterServiceImpl
import io.asdx.grpc.stub.service.GreeterServiceHandler
import io.asdx.nacos4s.client.util.Nacos4s
import javax.net.ssl.{KeyManagerFactory, SSLContext}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.util.{Failure, Success}

/** @auther: liansheng
  * @Date: 2021/5/18 19:01
  * @Description:
  */
object GreeterServer {
  def main(args: Array[String]): Unit = {
    // important to enable HTTP/2 in ActorSystem's config
    val conf   = ConfigFactory
      .parseString("akka.http.server.preview.enable-http2 = on")
      .withFallback(ConfigFactory.defaultApplication())
    val system = ActorSystem[Nothing](Behaviors.empty, "GreeterServer", conf)
    new GreeterServer(system).run()
  }
}

class GreeterServer(system: ActorSystem[_]) {

  def run(): Future[Http.ServerBinding] = {
    implicit val sys                  = system
    implicit val ec: ExecutionContext = system.executionContext

    val service: HttpRequest => Future[HttpResponse] =
      GreeterServiceHandler(new GreeterServiceImpl(system))

    val bound: Future[Http.ServerBinding] = Http(system)
      .newServerAt(interface = "127.0.0.1", port = 8080)
      //      .enableHttps(serverHttpContext)
      .bind(service)
      .map(_.addToCoordinatedShutdown(hardTerminationDeadline = 10.seconds))

    bound.onComplete {
      case Success(binding) =>
        val address       = binding.localAddress
        println("gRPC server bound to {}:{}", address.getHostString, address.getPort)
        val configService = Nacos4s.configService(ConfigFactory.load().getConfig("nacos4s.client.config"))
        val namingService = Nacos4s.namingService(ConfigFactory.load().getConfig("nacos4s.client.naming"))
      case Failure(ex)      =>
        println("Failed to bind gRPC endpoint, terminating system", ex)
        system.terminate()
    }
    bound
  }

  private def serverHttpContext: HttpsConnectionContext = {
    val privateKey        =
      DERPrivateKeyLoader.load(PEMDecoder.decode(readPrivateKeyPem()))
    val fact              = CertificateFactory.getInstance("X.509")
    val cer               = fact.generateCertificate(
      classOf[GreeterServer].getResourceAsStream("/certs/server1.pem")
    )
    val ks                = KeyStore.getInstance("PKCS12")
    ks.load(null)
    ks.setKeyEntry(
      "private",
      privateKey,
      new Array[Char](0),
      Array[Certificate](cer)
    )
    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(ks, null)
    val context           = SSLContext.getInstance("TLS")
    context.init(keyManagerFactory.getKeyManagers, null, new SecureRandom)
    ConnectionContext.https(context)
  }

  private def readPrivateKeyPem(): String =
    Source.fromResource("certs/server1.key").mkString

}
