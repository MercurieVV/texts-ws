package co.nextwireless.mwsunthrottling.ws

import org.scalacheck.Test.Parameters
import org.scalacheck.util.ConsoleReporter
import org.scalacheck.{Gen, Prop, Test => SchkTest}
import org.scalacheck.Prop.forAll

/**
  * Created with IntelliJ IDEA.
  * User: Victor Mercurievv
  * Date: 7/20/2017
  * Time: 2:21 PM
  * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
  */
object TestUtils {
  implicit def doCheck(p: Prop): Boolean = SchkTest.check(Parameters.default.withTestCallback(ConsoleReporter(3)), p).passed

  def isOk(f: () => Unit): Boolean = {
    try {
      f.apply()
      true
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        false
    }
  }

  def genericPBTest[P](props: Gen[P], test: (P) => Unit): Unit = {
    assert(forAll(props) { data =>
      try {
        test.apply(data)
        true
      } catch {
        case e: Throwable =>
          e.printStackTrace()
          false
      }
    })
  }

  /*
    def assertThereAndBack[I](func: (I) => I, args: I): Unit = {
      val outp = func(args)
      Assertions.assert(outp == args)
    }

    def assertThereAndBack[I, O](func: (I) => O, inp: I, assertTransform: (O) => I): Unit = {
      val outp = func(inp)
      Assertions.assert(outp == inp)
    }

    def assertIdempotent[I, O](func: (I) => Array[O], args: I): Unit = {
      val outp1 = func(args)
      val outp2 = func(args)

      Assertions.assert(outp1 sameElements outp2)
    }

    def assertNotIdempotent[I, O](func: (I) => Array[O], args: I): Unit = {
      val outp1 = func(args)
      val outp2 = func(args)

      val idempotent = outp1 sameElements outp2
      if(idempotent)
        println("\noutp1 = " + outp1 + "\noutp2 = " + outp2)
      Assertions.assert(!idempotent)
    }


    def assert(prop: Prop): Unit = {
      val propLoginPass = prop
      Assertions.assert(doCheck(propLoginPass))
    }
  */
}
