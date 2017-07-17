package app.salesforce.gnt.com.salesforce;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    EditText et_location;
    ImageButton btn_syncData;
    ConnectionDetector cd;
    String s;
    ProgressBar progressBar;
    private Toolbar toolbar;
    //String location;
    ArrayList<Location> locations = new ArrayList<>();
    Context context;
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setTitle("Location");

        context = this;
        // toolbar = (Toolbar)findViewById(R.id.tool_bar);
        //toolbar.hideOverflowMenu();
        getSupportActionBar().setDisplayOptions(DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        View view = getSupportActionBar().getCustomView();


        TextView actionbar_title = (TextView) view.findViewById(R.id.textView);

        btn_syncData = (ImageButton) view.findViewById(R.id.btn_sync_data);
        cd = new ConnectionDetector(this);


        recyclerView = (RecyclerView) findViewById(R.id.rv_location_name);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            int employee = extras.getInt("emp_id");

            Log.d("Employee id", String.valueOf(employee));
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

                Intent myIntent = new Intent(context, OutletActivity.class);
                //myIntent.putExtra("UserID",s);
                //myIntent.putExtra("LocaionID",location.getId());
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
                Toast.makeText(LocationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsonArrayRequest);


    }


}









