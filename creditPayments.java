public class creditPayments implements Payments {
    private final balanceBox balance = new balanceBox();
    private String type = "Credit";

    public int takePayment(int i) {
        return balance.addFunds(i);
    }

    public String returnFunds() {
        String s = "Returned " + balance.getFunds() + " in " + type;
        balance.deductFunds(balance.getFunds());
        return s;
    }

    /* Testing the program
    public static void main(String[] args) {
        creditPayments c = new creditPayments();
        System.out.println(c.returnFunds());
    }
    */
}
