package co.nextwireless.texts.ws

import java.util

import co.nextwireless.common.ws.awslambda.ApiGatewayProxyRequest
import co.nextwireless.common.ws.awslambda.http4s.Http4sAwsProxyHandler.Output
import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger}
import org.scalatest.FunSuite

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 1/12/2018
  * Time: 7:47 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class HandlerTest extends FunSuite {

  test("testHandleRequest") {
    val output: Output = new Handler().handleRequest(
      new ApiGatewayProxyRequest(
        ""
        , "/texts/admin.product.menu_missing_ean"
        , "GET"
        , new util.HashMap[String, String]()
        , new util.HashMap[String, String]()
        , new util.HashMap[String, String]()
        , new util.HashMap[String, String]()
        , context
        , ""
        , false
      )
      , context
    )
    println(output.getBody)

  }

  val context = new Context {
    override def getFunctionName: String = ???

    override def getRemainingTimeInMillis: Int = ???

    override def getLogger: LambdaLogger = ???

    override def getFunctionVersion: String = ???

    override def getMemoryLimitInMB: Int = ???

    override def getClientContext: ClientContext = ???

    override def getLogStreamName: String = ???

    override def getInvokedFunctionArn: String = ???

    override def getIdentity: CognitoIdentity = ???

    override def getLogGroupName: String = ???

    override def getAwsRequestId: String = ???
  }

}
