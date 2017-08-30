package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class PaymentHistory extends AppCompatActivity {


    public RecyclerView rv_payment;
    public PaymentAdapter billCollectionAdapter;
    public ArrayList<PaymentFormat> billFormats = new ArrayList<>();
    public static final String KEY_OUTLET_ID = "outlet_id";
    Context context;
    Bundle extras;
    int outlet_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        rv_payment = (RecyclerView) findViewById(R.id.rv_payment_history);
        extras = getIntent().getExtras();
        if (extras != null) {
            outlet_id = extras.getInt("outlet_id");
            Log.d("Payment_id",String.valueOf(outlet_id));

        } else {
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        rv_payment.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rv_payment.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        paymentHistory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        billFormats.clear();
        rv_payment.clearOnChildAttachStateChangeListeners();
    }

    public void paymentHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String get_payment = "http://inbackoffice.com/app/inforce/payment_history.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_payment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            Log.d("Payment", "History" + response);
                            if (res == 0) {
                                String msg = jsonObject.getString("message");
                                Toast.makeText(PaymentHistory.this, "MSG: " + msg, Toast.LENGTH_LONG).show();
                            }
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("transition");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jobj = jsonArray.getJSONObject(i);
                                PaymentFormat billFormat = new PaymentFormat();

                                if (!jobj.isNull("date")) {
                                    billFormat.date = jobj.getString("date");
                                }
                                if (!jobj.isNull("amount")) {
                                    billFormat.amount = jobj.getInt("amount");
                                }
                                billFormats.add(i, billFormat);

                            }
                            billCollectionAdapter = new PaymentAdapter(PaymentHistory.this, billFormats);
                            rv_payment.setAdapter(billCollectionAdapter);
                            billCollectionAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
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





}
