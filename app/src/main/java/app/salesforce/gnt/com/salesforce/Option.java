package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Option extends AppCompatActivity {
    public Button btn_order_collection, btn_bill_collection;
    Context context;
    Bundle extra;
    int outlet_id, location_id;
    String outlet_name;
    String location_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        setTitle("Option");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        btn_order_collection = (Button) findViewById(R.id.btn_order_collection);
        btn_bill_collection = (Button) findViewById(R.id.btn_bill_collection);
        extra = getIntent().getExtras();
        if (extra != null) {
            outlet_id = extra.getInt("id");
            outlet_name = extra.getString("name");
            location_id = extra.getInt("location_id");
            location_name = extra.getString("location_name");
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_order_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(context, OrderActivity.class);
                orderIntent.putExtra("id", outlet_id);
                startActivity(orderIntent);
            }
        });

        btn_bill_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent billIntent = new Intent(context, BillCollection.class);
                Log.d("Newid", String.valueOf(outlet_id));
                Log.d("Another", String.valueOf(location_id));
                Toast.makeText(context, "Outlet name " + outlet_name + "Location_name" + location_name, Toast.LENGTH_LONG).show();
                billIntent.putExtra("id", outlet_id);
                billIntent.putExtra("name", outlet_name);
                billIntent.putExtra("location_id", location_id);
                billIntent.putExtra("location_name", location_name);
                startActivity(billIntent);
            }
        });

    }

}
