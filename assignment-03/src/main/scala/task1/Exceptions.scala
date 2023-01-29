package task1

object Exceptions {
  trait ParseError extends Throwable {
    val msg: String
  }

  case object IntError extends ParseError {
    override val msg: String = "failed to parse Integer from string!"
  }
}
