package app.salesforce.gnt.com.salesforce;

/**
 * Created by Android on 8/3/2017.
 */

public class BillFormat {
    public String date;
    public int amount;

    public BillFormat(){

    }
    public BillFormat(String date,int amount){
        this.date = date;
        this.amount = amount;
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
