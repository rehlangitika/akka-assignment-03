package edu.knoldus.models


case class UserAccount(accountNumber: Long, actHolderName: String, address: String, userName: String,
                       initialAmount: Double)

case object ReportGenerator