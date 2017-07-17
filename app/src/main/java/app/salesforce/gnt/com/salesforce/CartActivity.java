package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    ArrayList<Cart> cartArrayList = new ArrayList<>();
    ArrayList<Product> products, updatedProducts;
//    private static RecyclerView.State mBundleRecyclerViewState;
//    private final String KEY_RECYCLER_STATE = "recycler_state";


    Context context;

    LinearLayoutManager linearLayoutManager;

    public static final String KEY_NAME = "name";
    public static final String KEY_QUANTITY = "quantity";

    private String name;
    private String quantity;

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

        //getting outlet id
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("Outletid");


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

        addToCart();

        btn_proceedCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Order Placed Successfully", Toast.LENGTH_SHORT).show();

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


            Product P = products.get(i);
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
}
/*



    public void proceedToCart() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String cart_url = "http://inbackoffice.com/app/inforce/cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, cart_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            if (res == 0) {
                                return;
                            }

                            String msg = jsonObject.getString("message");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //  mcartAdapter = new CartAdapter(CartActivity.this, cartArrayList);
                        mRecyclerView.setAdapter(mcartAdapter);
                        mcartAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NAME, name);
                params.put(KEY_QUANTITY, quantity);
                Log.d("Params", params.toString());
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }
    */