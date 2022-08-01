package com.dth.repositories

import com.dth.models.{Channel, Package, Subscription}
import com.dth.models.Language.{English, French}
import com.dth.models.SubLength.{Annual, BiAnnual, Monthly}
object ChannelRepo {

  val ch1 = Channel(1, "Basic 1", 0, English)
  val ch2 = Channel(2, "Basic 2", 0, English)
  val ch3 = Channel(3, "French 1", 3, French)
  val ch4 = Channel(4, "French 2", 5, French)
  val ch5 = Channel(5, "Sports 1", 6, English)
  val ch6 = Channel(6, "Sports 2", 5, English)

  def channels = Set(ch1, ch2, ch3, ch4, ch5, ch6)

  val defaultPackage = Package(1, "Default Channels", 0, channels.filter(_.cost == 0))
  val frenchPackage = Package(2, "French Channels", 6, channels.filter(_.language == French))
  val sportsPackage = Package(3, "Sports Channels", 9, channels.filter(_.name.contains("Sports")))

  def packages = Set(defaultPackage, frenchPackage, sportsPackage)

  val defaultSubscription = Subscription(1, "Default Subscription", 0, Set(defaultPackage), Annual, 12)
  val frenchSubscription = Subscription(2, "French Subscription", 5, Set(defaultPackage, frenchPackage), BiAnnual, 6)
  val sportsSubscription = Subscription(3, "Sports Subscription", 8, Set(defaultPackage, sportsPackage), Monthly, 1)

  def subscriptions = Set(defaultSubscription, frenchSubscription, sportsSubscription)


}
