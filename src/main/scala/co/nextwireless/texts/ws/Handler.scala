package co.nextwireless.texts.ws

import co.nextwireless.common.ws.awslambda.http4s.Http4sAwsProxyHandler
import co.nextwireless.common.ws.awslambda.http4s.Http4sAwsProxyHandler.{Input, Output}
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 12/5/2017
  * Time: 2:30 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class Handler extends RequestHandler [Input, Output]{
  private val handler = new Http4sAwsProxyHandler
  override def handleRequest(input: Input, context: Context): Output = {
    handler.performRequest(input, )
  }
}
