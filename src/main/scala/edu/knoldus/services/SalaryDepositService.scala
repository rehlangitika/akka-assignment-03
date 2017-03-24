package edu.knoldus.services

import akka.actor.{ActorRef, ActorSystem, Props}
import edu.knoldus.actors.SalaryDepositActor
import edu.knoldus.models.Salary


class SalaryDepositService(salaryList: List[Salary]) {

  def setSalary(actorSystem: ActorSystem, dbRepoActorRef: ActorRef) = {
    val salaryRef = actorSystem.actorOf(Props(classOf[SalaryDepositActor], dbRepoActorRef))
    for (salary <- salaryList) {
      println("SalaryDepositService: Forwarding each user's salary to SalaryDepositActor")
      salaryRef ! salary
    }
  }

}
