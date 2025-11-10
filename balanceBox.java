public class balanceBox {
    private int funds = 0;

    // Adds funds to balance box.
    public int addFunds(int i) {
        funds += i;
        return funds;
    }

    // Returns the amount of funds in balance box.
    public int getFunds() {
        return funds;
    }

    // Deducts funds from the balance box. Returns false if not enough funds.
    public boolean deductFunds(int cost) {
        if (funds < cost) {
            System.out.println("Not enough funds.");
            return false;
        } else {
            //System.out.println("Song purchased.");
            funds -= cost;
            return true;
        }
    }
}
