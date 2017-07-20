package app.salesforce.gnt.com.salesforce;

import org.json.JSONObject;

/**
 * Created by Android on 7/19/2017.
 */

public class EstimateObject {
    String id,quantity,price;

    public EstimateObject(String id,String quantity,String price){

        this.id = id;
        this.quantity = quantity;
        this.price = price;

    }

    public JSONObject getJSONObject(){

        JSONObject jobj = new JSONObject();

        try{
            jobj.put("id",id);
            jobj.put("quantity",quantity);
            jobj.put("price",price);

        }catch (Exception e){
            e.printStackTrace();
        }

        return jobj;
    }
}
