public class coinPayments implements Payments {
    private int balance = 0;
    private String type = "Coins";

    public int takePayment(int i) {
        balance += i;
        return balance;
    }
    public String returnFunds() {
        String s = "Returned " + balance + " in " + type;
        return s;
    }
}
