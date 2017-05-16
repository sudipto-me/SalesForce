package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 5/15/2017.
 */

public class Employee {

    public String id;


    public Employee(){}



    public Employee(int i) {

        this.id = String.valueOf(i);

    }

    public Employee(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return  "Employee id:"+getId();
    }


}
