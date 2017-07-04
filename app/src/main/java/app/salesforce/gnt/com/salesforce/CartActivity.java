package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.os.Bundle;
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
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    CartAdapter mcartAdapter;
    Button btn_proceedCart;
    ArrayList<Cart>cartArrayList = new ArrayList<>();
    Context context;

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

        context = this;

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_simple_cart);
        btn_proceedCart = (Button)findViewById(R.id.btn_proceed_cart);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        btn_proceedCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Order Placed Successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cartArrayList.clear();

    }

    public void proceedToCart(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String cart_url = "http://inbackoffice.com/app/inforce/cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, cart_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            if(res == 0){
                                return;
                            }

                            String msg = jsonObject.getString("message");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        mcartAdapter = new CartAdapter(CartActivity.this,cartArrayList);
                        mRecyclerView.setAdapter(mcartAdapter);
                        mcartAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put(KEY_NAME,name);
                params.put(KEY_QUANTITY,quantity);
                Log.d("Params", params.toString());
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }
}
