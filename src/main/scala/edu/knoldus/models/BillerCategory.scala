package edu.knoldus.models


sealed trait BillerCategory {}

case object Phone extends BillerCategory

case object Electricity extends BillerCategory

case object Internet extends BillerCategory

case object Food extends BillerCategory

case object Car extends BillerCategory


