package app.salesforce.gnt.com.salesforce;

/**
 * Created by Android on 8/3/2017.
 */

public class BillFormat {
    public int id;
    public String date;
    public int amount;

    public BillFormat(){

    }
    public BillFormat(int id,String date,int amount){
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
