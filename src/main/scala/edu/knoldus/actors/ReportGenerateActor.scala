package edu.knoldus.actors

import akka.actor.{Actor, ActorRef, ActorSystem}
import edu.knoldus.models.ReportGenerator

import scala.concurrent.duration._

class ReportGenerateActor(dBRepoActor: ActorRef, actorSystem: ActorSystem) extends Actor {


  override def receive = {

    case ReportGenerator =>
      import actorSystem.dispatcher
      val cancellable =
        actorSystem.scheduler.schedule(
          10 second,
          10 second,
          dBRepoActor,
          ReportGenerator)
  }

}
