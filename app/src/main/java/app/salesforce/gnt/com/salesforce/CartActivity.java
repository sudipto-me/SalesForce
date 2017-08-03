package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    CartAdapter mcartAdapter;
    Button btn_proceedCart;
    TextView tv_Total_price;
    ArrayList<Cart> cartArrayList = new ArrayList<>();
    ArrayList<Product> products, updatedProducts;
    Context context;
    MyDB db;
    public Cursor cursor;
    public static final String KEY_OUTLET_ID = "outlet_id";
    public static final String KEY_AGENT_ID = "employee_id";
    public static final String KEY_id = "id";
    public static final String KEY_quantity = "q";
    public static final String KEY_total = "total";
    int position;
    Product P;
    int agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        products = new OrderActivity().products;
        updatedProducts = new ArrayList<>();
        context = this;
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_simple_cart);
        btn_proceedCart = (Button) findViewById(R.id.btn_proceed_cart);
        tv_Total_price = (TextView) findViewById(R.id.tv_total_price);

        //getting outlet id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("Outletid");

            Log.d("newOutlet", String.valueOf(position));
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        updatedProducts.clear();
        db = new MyDB(this);
        cursor = db.getData();
        if (cursor.moveToFirst()) {

            agent = cursor.getInt(0);
        }

        addToCart();

        TotalPrice();

        btn_proceedCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < updatedProducts.size(); i++) {
                        int id = updatedProducts.get(i).getId();
                        int quantity = updatedProducts.get(i).getQuantity();
                        int price = updatedProducts.get(i).getPrice() * quantity;
                        Log.d("Price Is ", String.valueOf(price));
                        db.insertITEMS(position, id, quantity, price);

                    }

                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }

                proceedToCart();

                btn_proceedCart.setVisibility(View.GONE);
                tv_Total_price.setVisibility(View.GONE);


            }
        });


    }


    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void addToCart() {
        for (int i = 0; i < products.size(); i++) {

            P = products.get(i);
            if (P.quantity > 0) {
                updatedProducts.add(P);

                int id = P.id;
                int quantity = P.quantity;
                int price = P.price * P.quantity;


                Log.d("Data id:", String.valueOf(id));
                Log.d("Data quantity:", String.valueOf(quantity));
                Log.d("Data Price", String.valueOf(price));


            }
            mcartAdapter = new CartAdapter(CartActivity.this, updatedProducts);
            mRecyclerView.setAdapter(mcartAdapter);
            mcartAdapter.notifyDataSetChanged();


        }


    }

    public void TotalPrice() {
        int TotalPrice = 0;
        for (int i = 0; i < updatedProducts.size(); i++) {
            int quantity = updatedProducts.get(i).getQuantity();
            int price = quantity * updatedProducts.get(i).getPrice();

            TotalPrice = TotalPrice + price;
        }

        Log.d("New", "Price" + TotalPrice);
        tv_Total_price.setText(TotalPrice+"Taka");
    }

    public void proceedToCart() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String cart_url = "http://inbackoffice.com/app/inforce/place_order.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, cart_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btn_proceedCart.setVisibility(View.VISIBLE);
                        tv_Total_price.setVisibility(View.VISIBLE);
                        Log.d("Cart", "" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            Log.d("Cart", "" + response);
                            if (res == 0) {
                                String msg = jsonObject.getString("message");
                                Toast.makeText(CartActivity.this, "MSG: " + msg, Toast.LENGTH_LONG).show();
                            }

                            // String msg = jsonObject.getString("message");
                            //Toast.makeText(context, msg.toString(), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error", error.toString());


                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_AGENT_ID, String.valueOf(agent));
                params.put(KEY_OUTLET_ID, String.valueOf(position));
                params.put(KEY_total, String.valueOf(updatedProducts.size()));
                for (int i = 0; i < updatedProducts.size(); i++) {

                    params.put(KEY_id + "_" + i, String.valueOf(updatedProducts.get(i).getId()));
                    params.put(KEY_quantity + "_" + i, String.valueOf(updatedProducts.get(i).getQuantity()));
                    
                }
                Log.d("Params", params.toString());
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }


}
