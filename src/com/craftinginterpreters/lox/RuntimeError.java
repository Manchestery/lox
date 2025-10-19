package src.com.craftinginterpreters.lox;

class RuntimeError extends RuntimeException {
  RuntimeError(Token token, String message) {
    super(message);
    this.token = token;
  }
}