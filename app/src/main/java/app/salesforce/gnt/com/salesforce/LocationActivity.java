package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LocationAdapter myAdapter;
    DBHelper db;
    EditText et_location;
    Button btn_syncData;

    ConnectionDetector cd;

    String location;
    ArrayList<Location> locations = new ArrayList<>();
    Context context;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_syncData = (Button)findViewById(R.id.btn_sync_data);
        cd = new ConnectionDetector(this);

        btn_syncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.rv_location_name);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new LocationAdapter(LocationActivity.this,locations);
        recyclerView.setAdapter(myAdapter);

    }


public void sendRequest(){
    RequestQueue queue = Volley.newRequestQueue(context);
    String location_url = "http://inbackoffice.com/app/inforce/location.php";

    StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST,location_url,
            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("Result",response.toString());
                    try {
                        JSONObject jobj = new JSONObject(response);
                        int res = jobj.getInt("success");

                        if(res == 0){
                            return;
                        }


                        String msg = jobj.getString("message");
                        JSONArray jsonArray = jobj.getJSONArray("location");
                        for (int i = 0; i<jsonArray.length();i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Location location = new Location();
                            if (!jsonObject.isNull("id")){
                                location.id = jsonObject.getInt("id");
                            }
                            if (!jsonObject.isNull("name")) {
                                location.name = jsonObject.getString("name");
                            }
                            locations.add(i, location);


                        }
                        myAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(LocationActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
        }
    });

    queue.add(jsonArrayRequest);


}





}



