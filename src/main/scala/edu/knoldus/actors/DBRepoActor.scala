package edu.knoldus.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import edu.knoldus.models._

class DBRepoActor extends Actor with ActorLogging {

  var senderActor: Option[ActorRef] = None

  override def receive = {

    case (user: UserAccount, billerList: List[Biller]) =>
      log.info("DBRepoActor: Adding Users and their Billers to the DB")
      DBMemory.userAccountList += user
      DBMemory.billerList ++= billerList

    case Salary(acc, salary) =>
      senderActor = Some(sender)
      log.info("DBRepoActor: Adding salary to the user account")
      val user = DBMemory.userAccountList.filter(user => user.accountNumber == acc)
      if(user.nonEmpty){
        DBMemory.userAccountList --= user
        DBMemory.userAccountList ++= user
          .map(user => user.copy(initialAmount = user.initialAmount + salary))
        println("The updated salary is: " + DBMemory.userAccountList)
        val salaryDepositRef = context.actorOf(Props(classOf[SalaryDepositActor], self))
        salaryDepositRef ! DBMemory.billerList.filter(biller => biller.accNum == acc)
      }
      else {
        log.error("User Not Found!")
      }

    case biller: Biller =>
      log.info("DBRepoActor: Deducting billing amount from user's account")
      val user = DBMemory.userAccountList.filter(user => user.accountNumber == biller.accNum)
      if(user.nonEmpty) {
        DBMemory.userAccountList --= user
        DBMemory.userAccountList ++= user.map(user => user.copy(initialAmount = user.initialAmount - biller.amount))
        println("The Updated salary after deduction is: " + DBMemory.userAccountList)
        val biller1 = DBMemory.billerList.filter(_ == biller)
        DBMemory.billerList --= biller1
        DBMemory.billerList ++= biller1.map(b => b.copy(paidAmount = biller.amount + b.paidAmount))
        println("The Updated Biller Amount is: " + DBMemory.billerList)
      }
      else {
       log.error("User not found for the given biller")
      }

    case ReportGenerator =>
      log.info("DBRepoActor: Sending the reports to the ReportGeneratorActor")
      DBMemory.userAccountList.foreach(user => {
        log.info(s"User Info: ${user.userName}, ${user.accountNumber}")
        val newBillerList = DBMemory.billerList.filter(biller => biller.accNum == user.accountNumber)
        log.info(s"Biller Associated: $newBillerList")
      })
  }

}
