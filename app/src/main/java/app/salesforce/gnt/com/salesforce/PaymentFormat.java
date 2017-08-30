package app.salesforce.gnt.com.salesforce;

/**
 * Created by Android on 8/30/2017.
 */

public class PaymentFormat {

    public String date;
    public int amount;

    public PaymentFormat(){

    }

    public PaymentFormat(String date, int id) {
        this.date = date;
        this.amount = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
