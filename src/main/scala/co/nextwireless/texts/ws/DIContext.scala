package co.nextwireless.texts.ws

import co.nextwireless.common.getquill.DbConfig
import co.nextwireless.texts.ws.server.swagger.zapi.TextApi
import co.nextwireless.texts.ws.server.swagger.zapi.impl.TextApiProcessorsModule

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 1/10/2018
  * Time: 7:17 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class DIContext(ap: AppProperties, dbc: DbConfig) extends TextApiProcessorsModule {
  import com.softwaremill.macwire._

  override def getDbConfig(): DbConfig = dbc

  override def getAppProperties(): AppProperties = ap
}

