public class balanceBox {
    /**
     * This class represents the Balance Box.
     * It stores the amount of funds, and the user can add funds or get the amount of available funds.
     * The balance box will also deduct funds when a song is purchased.
     * 
     * @author William Yang, Mohammed Uddin
     */
    private int funds = 0;

    /**
     * Adds funds to balance box.
     * 
     * @param i
     * @return int
     */
    public int addFunds(int i) {
        funds += i;
        return funds;
    }

    /**
     *  Returns the amount of funds in balance box.
     * 
     * @return int
     */
    public int getFunds() {
        return funds;
    }

    /**
     * Deducts funds from the balance box. Returns false if not enough funds.
     * 
     * @param cost
     * @return boolean
     */
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
