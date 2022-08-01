package com.dth.controllers

import com.dth.models.{Content, Plan, Subscription, User}
import com.dth.models.Plan.refine

class UserController {

  implicit def any2iterable[A](a: A) : Iterable[A] = Some(a)

  def createPlan(user: User, subscription: Subscription): Option[User] = {
    user.plan match {
      case None => Some(User(Some(Plan(subscription, Set()))))
      case Some(_) => None
    }
  }

  def removePlan: User = new User

  def addContent(user: User, content: Content): Option[User] = user.plan match {
    case Some(plan) => Some(User(Some(refine(Plan(plan.subscription, plan.content ++ content)))))
    case None => None
  }
  def removeContent(user: User, content: Content): Option[User] = user.plan match {
    case Some(plan) => Some(User(Some(refine(Plan(plan.subscription, plan.content -- content)))))
    case None => None
  }
}
