package edu.knoldus.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.dispatch.{BoundedMessageQueueSemantics, RequiresMessageQueue}
import edu.knoldus.models.{Biller, UserAccount}


class UserAccountGenerator(dBRepoActor: ActorRef) extends Actor with ActorLogging
  with RequiresMessageQueue[BoundedMessageQueueSemantics] {

  override def receive = {

    case (userAcnt: UserAccount, billerList: List[Biller]) =>
      log.info("UserAccountGenerator: Forwarding user account info and their billers to DBRepoActor")
      dBRepoActor forward ((userAcnt, billerList))
  }

}
