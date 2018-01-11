package co.nextwireless.texts.ws.server.swagger.zapi.impl

import co.nextwireless.texts.ws.server.swagger.zapi.TextApi
import co.nextwireless.texts.ws.{ApplicationModule, TextsRepository}

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 12/8/2017
  * Time: 8:22 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
trait TextApiProcessorsModule extends ApplicationModule {

  import com.softwaremill.macwire._

  lazy val process_getText: Process_getText     = wire[Process_getText]

  lazy val process_getTexts: Process_getTexts   = wire[Process_getTexts]
  lazy val api: TextApi                         = new TextApi(requestProcessorExecuter, this)

}
