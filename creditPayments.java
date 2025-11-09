public class creditPayments implements Payments {
    private int balance = 0;
    private String type = "Credit";

    public int takePayment(int i) {
        balance += i;
        return balance;
    }
    public String returnFunds() {
        String s = "Returned " + balance + " in " + type;
        return s;
    }

    /* Testing the program
    public static void main(String[] args) {
        creditPayments c = new creditPayments();
        System.out.println(c.returnFunds());
    }
    */
}
