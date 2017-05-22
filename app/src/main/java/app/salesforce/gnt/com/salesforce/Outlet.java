package app.salesforce.gnt.com.salesforce;

/**
 * Created by PC-05 on 5/21/2017.
 */

public class Outlet {

    public int id;


    public String outletname;
    //public DateFormat dateFormat;

    public Outlet(){

    }

    public Outlet(int id,String outletname){

        this.id = id;

        this.outletname = outletname;

    }

    public int  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletname() {
        return outletname;
    }

    public void setOutletname(String outletname) {
        this.outletname = outletname;
    }



}
