package com.dth.utils

import scala.io.StdIn.readLine

object UserIO {

  def output(text: String): Unit = println(text)

  def readString(text: String): String = {
    print(text)
    readLine().toLowerCase
  }

  def readInt(text: String): Option[Int] = {
    print(text)
    try {
      Some(readLine().toInt)
    } catch {
      case _: NumberFormatException => None
      case e => None
    }
  }

}
