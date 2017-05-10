package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class Location {

    public int location_id;
    public String location_name;

    public Location(){

    }

    public Location(int location_id,String location_name){
        this.location_id = location_id;
        this.location_name = location_name;
    }

    public Location(String location_name){
        this.location_name = location_name;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }
}
