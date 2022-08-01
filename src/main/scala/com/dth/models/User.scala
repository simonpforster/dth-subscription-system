package com.dth.models

case class User(plan: Option[Plan] = None) {
  override def toString: String = plan match {
    case Some(plan) => plan.toString
    case None => "No plan defined yet."
  }
}
