package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class BillCollection extends AppCompatActivity {

    public TextView tv_total_bill, tv_total_pay, tv_total_due, tv_bill, tv_pay, tv_due;
    public TextView tv_date, tv_amount;
    public Button btn_payment;
    public RecyclerView rv_billcollection;
    public BillCollectionAdapter billCollectionAdapter;
    public ArrayList<BillFormat> billFormats = new ArrayList<>();
    public static final String KEY_OUTLET_ID = "outlet_id";
    Context context;
    Bundle extras;
    int outlet_id;
    String outlet_name;
    String location_name;
    int total = 0;
    int pay = 0;
    int due = 0;
    MyDB db;
    Cursor cursor;
    int agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_collection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        tv_date = (TextView) findViewById(R.id.tv_Date);
        tv_amount = (TextView) findViewById(R.id.tv_Amount);

        tv_total_bill = (TextView) findViewById(R.id.tv_Total_Bill);
        tv_total_due = (TextView) findViewById(R.id.tv_Total_Due);
        tv_total_pay = (TextView) findViewById(R.id.tv_Total_Pay);
        btn_payment = (Button) findViewById(R.id.btn_Payment);
        tv_bill = (TextView) findViewById(R.id.tv_bill);
        //tv_pay = (TextView)findViewById(R.id.tv_pay);
        //tv_due = (TextView)findViewById(R.id.tv_Due);


        rv_billcollection = (RecyclerView) findViewById(R.id.rv_bill_collection);


    }

    @Override
    protected void onStart() {
        super.onStart();

        rv_billcollection.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rv_billcollection.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        billcollection();


        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent paymentIntent = new Intent(context, PaymentActivity.class);

                extras = getIntent().getExtras();
                if (extras != null) {
                    outlet_id = extras.getInt("id");
                    outlet_name = extras.getString("name");
                    location_name = extras.getString("location_name");
                }


                Toast.makeText(context, "Id is:" + outlet_id + "Name is:" + outlet_name, Toast.LENGTH_LONG).show();

                paymentIntent.putExtra("id", outlet_id);
                paymentIntent.putExtra("name", outlet_name);
                paymentIntent.putExtra("location_name", location_name);
                paymentIntent.putExtra("due", total);

                Log.d("Total"+"Due",String.valueOf(total));


                startActivity(paymentIntent);
            }
        });


    }

    public void billcollection() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String get_invoice = "http://inbackoffice.com/app/inforce/get_invoice.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_invoice,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            Log.d("Invoice", "" + response);
                            if (res == 0) {
                                String msg = jsonObject.getString("message");
                                Toast.makeText(BillCollection.this, "MSG: " + msg, Toast.LENGTH_LONG).show();
                            }

                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("invoice");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = jsonArray.getJSONObject(i);

                                BillFormat billFormat = new BillFormat();

                                if (!jobj.isNull("id")) {
                                    billFormat.id = jobj.getInt("id");

                                }

                                if (!jobj.isNull("date")) {
                                    billFormat.date = jobj.getString("date");
                                }
                                if (!jobj.isNull("bill")) {
                                    billFormat.amount = jobj.getInt("bill");

                                }


                                billFormats.add(i, billFormat);
                                TotalBill();

                            }

                            billCollectionAdapter = new BillCollectionAdapter(BillCollection.this, billFormats);
                            rv_billcollection.setAdapter(billCollectionAdapter);
                            billCollectionAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                params.put(KEY_OUTLET_ID, String.valueOf(outlet_id));
                  Log.d("Params", toString());

                return params;

            }
        };

        requestQueue.add(stringRequest);

    }

    public void TotalBill() {


        for (int i = 0; i < billFormats.size(); i++) {
            int money = billFormats.get(i).getAmount();
            total = total + money;
            pay = 0;

        }
        tv_bill.setText(String.valueOf(total));


    }
}
