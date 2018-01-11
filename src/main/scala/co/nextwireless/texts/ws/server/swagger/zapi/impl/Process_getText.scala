package co.nextwireless.texts.ws.server.swagger.zapi.impl

import co.nextwireless.texts.ws.TextsRepository
import co.nextwireless.texts.ws.db.schema.Tables
import co.nextwireless.texts.ws.server.swagger.zapi.Request_getText

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 12/8/2017
  * Time: 8:23 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class Process_getText(textsRepository: TextsRepository) extends Function1[ Request_getText, String] {
  override def apply(req: Request_getText): String = {
    textsRepository.getTexts(req.textId)
      .filter(textLang => textLang._2.lang == req.languageId)
      .map(tl => tl._1.text)
      .headOption
      .getOrElse("")
  }
}
