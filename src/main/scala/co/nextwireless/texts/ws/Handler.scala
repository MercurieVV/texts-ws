package co.nextwireless.texts.ws

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import co.nextwireless.common.getquill.DbConfig
import co.nextwireless.common.ws.awslambda.http4s.Http4sAwsProxyHandler
import co.nextwireless.common.ws.awslambda.http4s.Http4sAwsProxyHandler.{Input, Output}
import co.nextwireless.common.{LogHelper, PropertiesLoader}
import co.nextwireless.texts.ws.server.swagger.zapi.TextApi
import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import fs2.{Stream, Task}
import org.http4s.{HttpService, QueryParamDecoder}
import io.circe.generic.auto._
import io.circe.{yaml, _}
import org.http4s.QueryParamDecoder.fromUnsafeCast

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 12/5/2017
  * Time: 2:30 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class Handler extends RequestHandler[Input, Output] {

  override def handleRequest(input: Input, context: Context): Output = {
    val handler = new Http4sAwsProxyHandler()
    val profile = "prod"
    val props = List(s"application-$profile.yaml", "application.yaml", s"security-$profile.yaml")

    val httpService: Stream[Task, (HttpService)] = new PropertiesLoader()
      .loadProperties[AppProperties](props)
      .map((properties: AppProperties) => {
        val dbConfig = LogHelper.log("DbConfig", () => new DbConfig(properties.db))
        new DIContext(properties, dbConfig)
      })
      .map((context: DIContext) => context.api)
      .map((api: TextApi) => api.httpService)


    handler.performRequest(input, httpService)
  }
}

