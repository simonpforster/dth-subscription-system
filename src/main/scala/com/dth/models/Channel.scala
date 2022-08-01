package com.dth.models

import Language.Language

case class Channel(id: Int, name: String, cost: Int, language: Language) extends Content {
  override def toString: String = s"$id. $name"
}


object Language extends Enumeration {
  type Language = Value
  val English = Value("English")
  val French  = Value("French")
}