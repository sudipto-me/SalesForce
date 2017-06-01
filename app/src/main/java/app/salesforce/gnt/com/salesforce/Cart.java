package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 6/1/2017.
 */

public class Cart {
    public int id;
    public String name;
    public int price;

    public Cart(){

    }

    public Cart(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
