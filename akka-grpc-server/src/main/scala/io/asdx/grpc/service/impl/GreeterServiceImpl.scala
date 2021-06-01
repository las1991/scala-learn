package io.asdx.grpc.service.impl

import akka.actor.typed.ActorSystem
import com.google.protobuf.any.Any
import io.asdx.grpc.stub.msg.{BaseResponse, User}
import io.asdx.grpc.stub.service.{GreeterService, HelloRequest}

import scala.concurrent.Future

/**
 * @auther: liansheng
 * @Date: 2021/5/16 20:43
 * @Description:
 */
class GreeterServiceImpl(system: ActorSystem[_]) extends GreeterService {
  override def sayHello(request: HelloRequest): Future[BaseResponse] = {
    val user = User(request.id, s"Hello, ${request.id}")
    Future.successful(BaseResponse(code = 0, msg = "success", data = Option(Any.pack[User](user))))
  }
}
