package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 5/15/2017.
 */

public class Employee {

    public int id;

    public String value;


    public Employee() {
    }

    public Employee(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
