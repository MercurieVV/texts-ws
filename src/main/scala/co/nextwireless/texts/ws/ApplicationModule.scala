package co.nextwireless.texts.ws

import co.nextwireless.common.getquill.DbConfig
import co.nextwireless.common.ws.http4s.RequestProcessorExecuter

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 1/10/2018
  * Time: 7:55 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
abstract class ApplicationModule {
  import com.softwaremill.macwire._

  //  val profile = "prod"
  //  val props = List(s"application-$profile.yaml", "application.yaml", s"security-$profile.yaml")
  //  private val propertiesLoader                                = new PropertiesLoader()
  //  propertiesLoader.loadProperties(props)

  //  lazy val dbConfig: DbConfig                                 = dbConf
  def getDbConfig(): DbConfig
  def getAppProperties(): AppProperties
  lazy val dbConfig = getDbConfig()
  lazy val deliveryRepository: TextsRepository             = wire[TextsRepository]
  lazy val requestProcessorExecuter: RequestProcessorExecuter = wire[RequestProcessorExecuter]
}
