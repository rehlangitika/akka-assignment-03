package edu.knoldus.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import edu.knoldus.models.{Biller, Salary}
import akka.util.Timeout
import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer


class SalaryDepositActor(dBRepoActor: ActorRef) extends Actor with ActorLogging {

  implicit val timeout = Timeout(10.seconds)
  override def receive = {

    case salary: Salary =>
      log.info("SalaryDepositActor: Forwarding to DBRepoActor!")
      dBRepoActor ! salary

    case billerList: ListBuffer[Biller] =>
      val actroRef: Option[ActorRef] = Some(dBRepoActor)
      println("SalaryActorRef: "+dBRepoActor)
      log.info("SalaryDepositActor: Forwarding to different child actors for billing")
      billerList.foreach {
        biller => dBRepoActor ! biller
      }
  }

}
