package co.nextwireless.texts.ws

import co.nextwireless.common.getquill.DbConfig
import co.nextwireless.texts.ws.db.schema.Tables.{TLang, TText}
//import fs2._

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 12/11/2017
  * Time: 8:19 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
class TextsRepository(dbConfig: DbConfig) {
  import dbConfig._
  import dbConfig.context._

  def searchForKeys(search: Option[String]): List[String] = search match {
    case None => Nil
    case Some(keySearchStr) => {
      val q = quote {
        query[TText]
          .filter((text: TText) => text.textkey like lift(keySearchStr + "%"))
          .map((text: TText) => text.textkey)
      }
      run(q)
    }
  }

  def getTexts(textId: String): List[(TText, TLang)] ={
//    Stream.eval(Task{
      val q = quote {
        query[TText]
          .filter(text => text.textkey == lift(textId))
          .join(query[TLang]).on((t, l) => t.lang == l.idTLang)
      }
      val value = run(q)
      value
//  })
  }

/*
  def runStream(quote: dbConfig.context.Quoted[dbConfig.context.EntityQuery[TText]]): Stream[Task, List[TText]] = {
    Stream.eval(Task{run(quote)})
  }
*/

}
