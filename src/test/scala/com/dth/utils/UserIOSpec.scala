package com.dth.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.ByteArrayInputStream

class UserIOSpec extends AnyFlatSpec with Matchers {

  behavior of "The UserIO object"

  it should "read 'yes'" in {
    val testValue: String = "yes"
    Console.withIn(new ByteArrayInputStream(testValue.getBytes)) {
      val input: String = UserIO.readString("test")
      input shouldEqual testValue
    }
  }

  it should "read '10'" in {
    val testValue: String = "10"
    Console.withIn(new ByteArrayInputStream(testValue.getBytes)) {
      val input: String = UserIO.readString("test")
      input shouldEqual testValue
    }
  }

  it should "read 10" in {
    val testValue: Int = 10
    Console.withIn(new ByteArrayInputStream(testValue.toString.getBytes)) {
      val input: Option[Int] = UserIO.readInt("test")
      input shouldEqual Some(testValue)
    }
  }

  it should "not read" in {
    val testValue: String = "yes"
    Console.withIn(new ByteArrayInputStream(testValue.getBytes)) {
      val input: Option[Int] = UserIO.readInt("test")
      input shouldEqual None
    }
  }
}
