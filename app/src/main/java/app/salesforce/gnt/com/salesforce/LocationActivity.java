package app.salesforce.gnt.com.salesforce;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;

public class LocationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LocationAdapter myAdapter;
    ImageButton btn_syncData;
    ConnectionDetector cd;
    ArrayList<Location> locations = new ArrayList<>();
    Context context;
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    Bundle extras;
    int employee_id;
    public int id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setTitle("Location");
        context = this;
        getSupportActionBar().setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        View view = getSupportActionBar().getCustomView();
        btn_syncData = (ImageButton) view.findViewById(R.id.btn_sync_data);
        cd = new ConnectionDetector(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_location_name);
        extras = getIntent().getExtras();
        if (extras != null) {
            employee_id = extras.getInt("Agent_ID");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        locations.clear();
        sendRequestforLocation();
        id = employee_id;
        Log.v("Fucked", String.valueOf(id));
        btn_syncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locations.clear();
                sendRequestforLocation();
                myAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Rec.Onclk", "Emp_id:" + employee_id);
                Toast.makeText(LocationActivity.this, "Emp_id:" + employee_id, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(context, OutletActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        locations.clear();
        super.onResume();
    }

    public void sendRequestforLocation() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String location_url = "http://inbackoffice.com/app/inforce/location.php";
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, location_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Result", response.toString());
                        try {
                            JSONObject jobj = new JSONObject(response);
                            int res = jobj.getInt("success");
                            if (res == 0) {
                                return;
                            }

                            String msg = jobj.getString("message");
                            JSONArray jsonArray = jobj.getJSONArray("location");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Location location = new Location();

                                if (!jsonObject.isNull("id")) {
                                    location.id = jsonObject.getInt("id");
                                }
                                if (!jsonObject.isNull("name")) {
                                    location.name = jsonObject.getString("name");
                                }
                                locations.add(i, location);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        myAdapter = new LocationAdapter(LocationActivity.this, locations);
                        recyclerView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LocationActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);

    }
}









