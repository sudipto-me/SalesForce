package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class Location {

    public int id;
    public String name;
    //public String location_name;

    public Location(){

    }



    public Location(int id,String name){
       this.id = id;
        this.name  = name;
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
