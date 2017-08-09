package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OutletActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OutletAdapter myOutletAdapter;
    public static ArrayList<Outlet> mOutletList = new ArrayList<>();
    Context context;
    public static final String KEY_USER_ID = "emp_id";
    public static final String KEY_LOCATION_ID = "location_id";
    Outlet outlet;
    Bundle extra;
    int employee_id;
    String location_name;
    int location_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.rv_outlet_name);

    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mlinearLayoutManager);
        mlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mOutletList.clear();
        sendRequestForOutlet();

        extra = getIntent().getExtras();
        if (extra != null) {
            location_id = extra.getInt("location_id");
            location_name = extra.getString("location_name");


            Log.d("Location id", String.valueOf(location_id));
            Log.d("Location Name", location_name);
        }


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, OrderActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mOutletList.clear();
        //sendRequestForOutlet();


    }

    public void sendRequestForOutlet() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String outlet_url = "http://inbackoffice.com/app/inforce/outlet.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, outlet_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Result",""+ response);
                        // Toast.makeText(context, "Response" + response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            if (res == 0) {
                                return;
                            }


                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("outlet_name");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jobj = jsonArray.getJSONObject(i);

                                outlet = new Outlet();


                                if (!jobj.isNull("outlet_id")) {

                                    outlet.id = jobj.getInt("outlet_id");
                                }

                                if (!jobj.isNull("outlet_name")) {
                                    outlet.outletname = jobj.getString("outlet_name");
                                }
                                mOutletList.add(i, outlet);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        myOutletAdapter = new OutletAdapter(OutletActivity.this, mOutletList, location_name);
                        recyclerView.setAdapter(myOutletAdapter);
                        myOutletAdapter.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USER_ID, "1");
                params.put(KEY_LOCATION_ID, "1");
                Log.d("Params", params.toString());
                return params;
            }
        };


        requestQueue.add(stringRequest);

    }
}
