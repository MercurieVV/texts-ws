package co.nextwireless.texts.ws.server.swagger.zapi.impl

import co.nextwireless.texts.ws.TextsRepository
import co.nextwireless.texts.ws.server.swagger.zapi.Request_searchKeys

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 3/15/2018
  * Time: 11:12 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class Process_searchKeys(textsRepository: TextsRepository) extends ((Request_searchKeys) => List[String]) {
  override def apply(v1: Request_searchKeys): List[String] = {
    textsRepository.searchForKeys(v1.search)
  }
}
