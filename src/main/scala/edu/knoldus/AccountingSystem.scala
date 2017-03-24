package edu.knoldus

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import edu.knoldus.actors.{DBRepoActor, ReportGenerateActor}
import edu.knoldus.models.{ReportGenerator, Salary}
import edu.knoldus.services.{SalaryDepositService, UserAccountService}

object AccountingSystem extends App {

  val conf: Config = ConfigFactory.load()
  val actorSystem = ActorSystem("AccountSystem", conf)
  val userAccService = new UserAccountService
  val dbRepoActor = actorSystem.actorOf(Props[DBRepoActor])
  println("Creating userservice")
  userAccService.createUserAccount(actorSystem, dbRepoActor)
  val salaryList = List(Salary(1, 5000)/*, Salary(2, 5000), Salary(3, 5000), Salary(4, 5000),
    Salary(5, 5000), Salary(6, 5000), Salary(7, 5000), Salary(8, 5000), Salary(9, 5000), Salary(10, 5000)*/)
  val salaryDepositService = new SalaryDepositService(salaryList)
  println("Setting salaries to users")
  salaryDepositService.setSalary(actorSystem, dbRepoActor)
  val reportGenerator = actorSystem.actorOf(Props(classOf[ReportGenerateActor],dbRepoActor, actorSystem))
  reportGenerator ! ReportGenerator

}
