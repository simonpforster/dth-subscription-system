package com.dth.models

trait Purchasable {
  val cost: Int
}

trait Content extends Purchasable