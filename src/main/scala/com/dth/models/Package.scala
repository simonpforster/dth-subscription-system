package com.dth.models

case class Package(id: Int, name: String, cost: Int, channelList: Set[Channel]) extends Content {
  override def toString: String = {
    channelList.map(c => s"  $c\n").fold(s"$id. $name\n")((a, b) => a + b)
  }
}

