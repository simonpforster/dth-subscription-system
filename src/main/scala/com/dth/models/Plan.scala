package com.dth.models

import Plan.{planCost, planMonthlyCost}
import SubLength.{Annual, BiAnnual, Monthly, SubLength}

case class Plan(subscription: Subscription, content: Set[Content]) {
  override def toString: String = {

    content.map(c => s"$c\n").fold(subscription.pack.map(p => s"$p")
      .fold("CURRENT PLAN:\n")((a, b) => a + b) + "\n")((a, b) => a + b) +
      s"Current plan monthly cost: ${planMonthlyCost(this)}\n" +
      s"Current plan cost: ${planCost(this)._1} every ${subscription.period.id} month(s)"
  }
}

object Plan {
  implicit def any2iterable[A](a: A) : Iterable[A] = Some(a)

  def planMonthlyCost(plan: Plan): Int = plan.subscription.cost + plan.content.map(_.cost).sum

  def planCost(plan: Plan): (Int, SubLength) = plan.subscription.period match {
        case Monthly => (planMonthlyCost(plan), Monthly)
        case BiAnnual => (planMonthlyCost(plan) * 6, BiAnnual)
        case Annual => (planMonthlyCost(plan) * 12, Annual)
      }

  def getContent(plan: Plan): Set[Content] = plan.subscription.pack ++ plan.content

  def getChannels(plan: Plan) = getContent(plan).flatMap(_ match {
        case channel: Channel => channel
        case pack: Package => pack.channelList
      })

  def refine(plan: Plan): Plan = {
    val newChannels: Set[Channel] = plan.content.collect({ case channel: Channel => channel }).filter(channel =>
      !plan.subscription.pack.map(_.channelList.contains(channel)).flatten.contains(true) &&
        !plan.content.collect({ case a: Package => a.channelList.contains(channel) }).contains(true))
    val newContent: Set[Content] = newChannels ++ plan.content.collect { case p: Package => p }
      .filter(x => !plan.subscription.pack.contains(x))
    Plan(plan.subscription, newContent)
  }

//  override def toString(plan: Plan): String = {
//
//  }

}