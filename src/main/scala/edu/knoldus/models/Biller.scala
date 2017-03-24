package edu.knoldus.models


case class Biller(category: BillerCategory, billerName: String, accNum: Long,
                  transactionDate: String, amount: Double, totalIterations: Int,
                  executedIterations: Int, paidAmount: Double)
