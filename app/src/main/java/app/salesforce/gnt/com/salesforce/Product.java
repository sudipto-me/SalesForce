package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 5/22/2017.
 */

public class Product {

    public int id;
    public String name;

    public Product(){

    }

    public Product(int id, String name){
        this.id = id;
        this.name = name;
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
}
