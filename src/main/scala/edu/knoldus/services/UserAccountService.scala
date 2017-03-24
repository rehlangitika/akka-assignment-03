package edu.knoldus.services

import akka.actor.{ActorRef, ActorSystem, Props}
import edu.knoldus.actors.UserAccountGenerator
import edu.knoldus.models._


class UserAccountService {

  def createUserAccount(actorSystem: ActorSystem, dbRepoActorRef: ActorRef): Unit = {
    val userActor = actorSystem.actorOf(Props(classOf[UserAccountGenerator], dbRepoActorRef))
    for (i <- 1 to 10) {
      val user = UserAccount(i, "user" + i, "add" + i, "myname" + i, 0.0)
      val billerList = List(Biller(Phone, "biller1", i, "12/2/2017", 200, 2, 1, 100),
        Biller(Car, "biller2", i, "12/2/2017", 200, 2, 1, 100),
        Biller(Electricity, "biller3", i, "12/2/2017", 200, 2, 1, 100),
        Biller(Food, "biller4", i, "12/2/2017", 200, 2, 1, 100),
        Biller(Internet, "biller5", i, "12/2/2017", 200, 2, 1, 100))
      println("UserAccountService: Forwarding request to UserGeneratorActor")
      userActor ! (user, billerList)
    }
  }

}


