trait CorePrinter {
  def doPrint(str: String): Unit
}

trait BusinessPrinter extends CorePrinter {
  def PRINT(str: String): Unit = {
    doPrint("pretty " + str)
  }
}

trait ConsolePrinter extends CorePrinter {
  override def doPrint(str: String): Unit = println(str)
}

implicit class PrinterWrapper(businessPrinter: BusinessPrinter) {
  def PRINT(str: String, in: Int): Unit = {
    businessPrinter.PRINT(s"$in: $str")
  }
}

val servicePrinter = new BusinessPrinter with ConsolePrinter {}
servicePrinter.PRINT("hello", 1)