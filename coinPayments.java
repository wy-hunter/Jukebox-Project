public class coinPayments implements Payments {
    private final balanceBox balance = new balanceBox();
    private String type = "Coins";

    // Takes payment. Returns new balance.
    public int takePayment(int i) {
        return balance.addFunds(i);
    }

    // Returns funds.
    public String returnFunds() {
        String s = "Returned " + balance.getFunds() + " in " + type;
        balance.deductFunds(balance.getFunds());
        return s;
    }
}
