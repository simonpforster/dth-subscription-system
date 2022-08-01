package com.dth.models

import Language.{English, French}
import Plan.{planMonthlyCost, refine}
import SubLength.{Annual, BiAnnual, Monthly}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PlanSpec extends AnyFlatSpec with Matchers {

  val ch1 = Channel(1, "Basic 1", 0, English)
  val ch2 = Channel(2, "Basic 2", 0, English)
  val ch3 = Channel(3, "French 1", 3, French)
  val ch4 = Channel(4, "French 2", 5, French)
  val ch5 = Channel(5, "Sports 1", 6, English)
  val ch6 = Channel(6, "Sports 2", 5, English)

  val channels = Set(ch1, ch2, ch3, ch4, ch5, ch6)

  val defaultPackage = Package(1, "Default Channels", 0, channels.filter(_.cost == 0))
  val frenchPackage = Package(2, "French Channels", 6, channels.filter(_.language == French))
  val sportsPackage = Package(3, "Sports Channels", 9, channels.filter(_.name.contains("Sports")))

  val defaultSubscription = Subscription(1, "Default Subscription", 0, Set(defaultPackage), Monthly, 3)
  val frenchSubscription = Subscription(2, "French Subscription", 5, Set(defaultPackage, frenchPackage), Annual, 12)
  val sportsSubscription = Subscription(3, "Sports Subscription", 8, Set(defaultPackage, sportsPackage), BiAnnual, 6)

  val packages = Seq(defaultPackage, frenchPackage, sportsPackage)
  val subscriptions = Seq(defaultSubscription, frenchSubscription, sportsSubscription)

  behavior of "Plan object"

  it should "remove duplicate channels and packages" in {
    val plan = Plan(defaultSubscription, Set(frenchPackage, ch1, ch6, ch6, ch3, frenchPackage))
    refine(plan) shouldEqual Plan(defaultSubscription, Set(frenchPackage, ch6))
  }

  it should "refine nothing" in {
    val plan = Plan(defaultSubscription, Set())
    refine(plan) shouldEqual plan
  }

  it should "price the user's monthly plan cost" in {
    val plan = Plan(defaultSubscription, Set(frenchPackage, ch6))
    planMonthlyCost(plan) shouldEqual 11
  }


}
