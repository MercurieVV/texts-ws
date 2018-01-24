package co.nextwireless.texts.ws.server.swagger.zapi.impl

import co.nextwireless.texts.ws.TextsRepository
import co.nextwireless.texts.ws.db.schema.Tables
import co.nextwireless.texts.ws.server.swagger.model.Texts
import co.nextwireless.texts.ws.server.swagger.zapi.Request_getTexts

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 12/8/2017
  * Time: 8:23 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class Process_getTexts(textsRepository: TextsRepository) extends Function1 [Request_getTexts, Texts]{
  override def apply(v1: Request_getTexts): Texts = {
    val langToMaybeString: Map[String, Option[String]] = textsRepository.getTexts(v1.textId)
      .groupBy(_._2)
      .map { case (lang, list) => (lang.lang, list.headOption.map(_._1.text)) }
      .filterNot(_._1.isEmpty)

    Texts(
      langToMaybeString.getOrElse("EN", None)
      , langToMaybeString.getOrElse("FR", None)
      , langToMaybeString.getOrElse("DE", None)
      , langToMaybeString.getOrElse("IT", None)
      , langToMaybeString.getOrElse("ES", None)
    )
  }
}
