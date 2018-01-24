package co.nextwireless.texts.ws.server.swagger.zapi.impl

import java.time.OffsetDateTime

import co.nextwireless.texts.ws.TextsRepository
import co.nextwireless.texts.ws.db.schema.Tables
import co.nextwireless.texts.ws.server.swagger.zapi.Request_getTexts
import org.scalatest.FunSuite

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 1/12/2018
  * Time: 7:21 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class Process_getTextsTest extends FunSuite {

  test("testApply") {
    val texts = new Process_getTexts(new TextsRepository(null) {
      override def getTexts(textId: String): List[(Tables.TText, Tables.TLang)] = List(
        (Tables.TText(0, "ttt", 1, OffsetDateTime.now(), "t.1.t"), Tables.TLang(1, Some("frfrfr"), "FR", Some(OffsetDateTime.now())))
      )
    }).apply(Request_getTexts("t.1.t"))

    assert(texts.en.isEmpty)
  }

}
