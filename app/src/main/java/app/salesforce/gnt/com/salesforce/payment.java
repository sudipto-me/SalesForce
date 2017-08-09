package app.salesforce.gnt.com.salesforce;

/**
 * Created by Android on 8/9/2017.
 */

public class payment {
    int id;
    String amount;

    public payment(int id, String amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
