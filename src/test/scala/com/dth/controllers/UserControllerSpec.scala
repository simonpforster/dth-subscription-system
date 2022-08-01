package com.dth.controllers

import com.dth.models.Language.{English, French}
import com.dth.models.SubLength.{Annual, BiAnnual, Monthly}
import com.dth.models.{Channel, Package, Plan, Subscription, User}
import dth.models._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class UserControllerSpec extends AnyFlatSpec with Matchers {

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

  val userController = new UserController

  behavior of "The UserController"

  it should "create a plan for a user" in {
    val user = new User
    userController.createPlan(user, defaultSubscription) shouldEqual Some(User(Some(Plan(defaultSubscription, Set()))))
  }

  it should "not create a plan for a user" in {
    val user = User(Some(Plan(frenchSubscription, Set(defaultPackage, ch5))))
    userController.createPlan(user, defaultSubscription) shouldEqual None
  }

  it should "remove the plan for a user" in {
    val user = User(Some(Plan(defaultSubscription, Set(ch6, ch3))))
    userController.removePlan shouldEqual User(None)
  }

  it should "add a package to a user's plan" in {
    val user = User(Some(Plan(defaultSubscription, Set(ch3))))
    userController.addContent(user, frenchPackage) shouldEqual Some(User(Some(Plan(defaultSubscription, Set(frenchPackage)))))
  }

  it should "remove a package from a user's plan" in {
    val user = User(Some(Plan(defaultSubscription, Set(frenchPackage))))
    userController.removeContent(user, frenchPackage) shouldEqual Some(User(Some(Plan(defaultSubscription, Set()))))
  }

  it should "add a channel to a user's plan" in {
    val user = User(Some(Plan(defaultSubscription, Set(frenchPackage))))
    userController.addContent(user, ch5) shouldEqual Some(User(Some(Plan(defaultSubscription, Set(frenchPackage, ch5)))))
  }

  it should "remove a channel from a user's plan" in {
    val user = User(Some(Plan(defaultSubscription, Set(ch4))))
    userController.removeContent(user, ch4) shouldEqual Some(User(Some(Plan(defaultSubscription, Set()))))
  }

  // add cases where you try to add or remove content that is either already in the plan or not in the plan respectively

  // add additional cases where you add a content that has duplicates in the plan
}
