public class BadTransactionException extends Exception {
  private int amount;
  public BadTransactionException(int badAmount) {
    super("Invalid transaction amount: " + badAmount);
    amount = badAmount;
  }
}