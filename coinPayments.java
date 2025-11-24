public class coinPayments implements Payments {
    /**
     * This class represents the coin payment.
     * It takes and returns payments in coins.
     * 
     * @author William Yang, Mohammed Uddin
     */
    private final balanceBox balance = new balanceBox();
    private String type = "Coins";

    /**
     * Takes payment. Returns new balance.
     * 
     * @param i
     * @return int
     */
    public int takePayment(int i) {
        return balance.addFunds(i);
    }

    /**
     * Returns funds.
     * 
     * @return String
     */
    public String returnFunds() {
        String s = "Returned " + balance.getFunds() + " in " + type;
        balance.deductFunds(balance.getFunds());
        return s;
    }
}
