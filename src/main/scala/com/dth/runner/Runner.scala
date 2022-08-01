package com.dth.runner

import com.dth.controllers.UserController
import com.dth.models.Plan.getContent
import com.dth.models.{Channel, Package, User}
import com.dth.repositories.ChannelRepo
import com.dth.utils.UserIO

object Runner {

  val userController = new UserController

  def main(args: Array[String]): Unit = {
    var running = true
    var user = new User
    println("Welcome to the DTH service")
    while (running) {
      user.plan match {
        case None =>
          UserIO.readString("Would you like to create a plan? y/n") match {
            case "yes" | "y" => user = planner(user)
            case "no" | "n" => running = false
            case _ =>
          }
        case Some(_) =>
          UserIO.readString("Would you like to view, edit or delete your current plan? (v)iew/(e)dit/(d)elete") match {
            case "view" | "v" => println(user)
            case "edit" | "e" => user = edit(user)
            case "delete" | "d" => user = userController.removePlan
            case _ =>
          }
      }
    }
  }

  def planner(u: User): User = {
    var planning = true
    var us = u
    while (planning) {
      println("Which subscription type would you like to choose?")
      println("1. Default subscription | Cost 0 | Monthly")
      println("2. French subscription  | Cost 5 | Annual")
      println("3. Sports subscription  | Cost 8 | BiAnnual")
      UserIO.readInt("Enter the number of your desired subscription type (0 to cancel):") match {
        case Some(0) =>
          planning = false
        case Some(id) =>
          val choice = ChannelRepo.subscriptions.filter(_.id == id)
          if (choice.size == 1) {
            planning = false
            val sub = choice.iterator.next
            userController.createPlan(us, sub) match {
              case Some(user) => us = edit(user)
              case None => planning = false
            }
          } else {
            println("Invalid choice, try again.")
          }
        case None =>
      }
    }
    us
  }

  def edit(u: User): User = {
    var editing = true
    var us = u
    while (editing) {
      println("Would you like to add any extras to your plan?")
      println("1. View list of channels and packages")
      println("2. View your current plan")
      println("3. Add a package")
      println("4. Remove a package")
      println("5. Add a channel")
      println("6. Remove a channel")
      UserIO.readInt("Enter the number of your desired action (0 to cancel):") match {
        case Some(action) => action match {
          case 0 => editing = false
          case 1 => ChannelRepo.packages.foreach(println)
          case 2 => println(us)
          case 3 => us = addPackage(us)
          case 4 => us = removePackage(us)
          case 5 => us = addChannel(us)
          case 6 => us = removeChannel(us)
          case _ => println("Invalid answer, try again.")
        }
        case None => println("Invalid answer, try again.")
      }
    }
    us
  }

  def addPackage(u: User): User = {
    UserIO.readInt("Which package would you like to add? (enter id or 0 to cancel)") match {
      case Some(0) =>
        return u
      case Some(id) =>
        val choice = ChannelRepo.packages.filter(_.id == id)
        if (choice.size == 1) {
          val pack = choice.iterator.next
          val newu = userController.addContent(u, pack)
          println(newu)
          newu match {
            case Some(newContentUser) =>
              println("New content added to plan.")
              return newContentUser
            case None =>
          }
        }
      case None =>
    }
    println("Invalid choice, try again.")
    u
  }

  def removePackage(u: User): User = {
    UserIO.readInt("Which package would you like to remove? (enter id or 0 to cancel)") match {
      case Some(0) =>
        return u
      case Some(id) =>
        u.plan match {
          case Some(plan) =>
            val choice = getContent(plan).collect({ case p: Package => p }).filter(_.id == id)
            if (choice.size == 1) {
              userController.removeContent(u, choice.iterator.next) match {
                case Some(newContentUser) =>
                  println("Content removed from plan.")
                  return newContentUser
                case None =>
              }
            }
          case None =>
        }
      case None =>
    }
    println("Invalid choice, try again.")
    u
  }

  def addChannel(u: User): User = {
    UserIO.readInt("Which channel would you like to add? (enter id or 0 to cancel)") match {
      case Some(0) =>
        return u
      case Some(id) =>
        val choice = ChannelRepo.channels.filter(_.id == id)
        if (choice.size == 1) {
          val pack = choice.iterator.next
          userController.addContent(u, pack) match {
            case Some(newContentUser) =>
              println("New content added to plan.")
              return newContentUser
            case None =>
          }
        }
      case None =>
    }
    println("Invalid choice, try again.")
    u
  }

  def removeChannel(u: User): User = {
    UserIO.readInt("Which package would you like to remove? (enter id or 0 to cancel)") match {
      case Some(0) =>
        return u
      case Some(id) =>
        u.plan match {
          case Some(plan) =>
            val choice = getContent(plan).collect({ case p: Channel => p }).filter(_.id == id)
            if (choice.size == 1) {
              userController.removeContent(u, choice.iterator.next) match {
                case Some(newContentUser) =>
                  println("Content removed from plan.")
                  return newContentUser
                case None =>
              }
            }
          case None =>
        }
      case None =>
    }
    println("Invalid choice, try again.")
    u
  }
}
