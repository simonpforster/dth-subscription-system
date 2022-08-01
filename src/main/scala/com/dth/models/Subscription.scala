package com.dth.models

import SubLength.SubLength

case class Subscription(id: Int, name: String, cost: Int, pack: Set[Package], period: SubLength, duration: Int) extends Purchasable

object SubLength extends Enumeration {
  type SubLength = Value
  val Monthly  = Value(1)
  val BiAnnual = Value(6)
  val Annual   = Value(12)
}