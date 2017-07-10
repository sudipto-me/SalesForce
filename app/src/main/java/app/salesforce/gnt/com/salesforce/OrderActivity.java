package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    Context context;
    Button btn_showCart;
    RecyclerView recyclerView;
    OrderAdapter myAdapter;
    ArrayList<Product> products = new ArrayList<>();

    public static final String KEY_ID = "product_id";
    public static final String KEY_NAME = "product_name";
    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;


        recyclerView = (RecyclerView) findViewById(R.id.rv_product_list);
        btn_showCart = (Button) findViewById(R.id.btn_show_cart);


    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        products.clear();
        sendRequestforProducts();


        btn_showCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Log.d("Quantity", String.valueOf(products.size()));
                for (int i = 0; i < products.size(); i++) {
                    Product p = products.get(i);
                    Log.d("product ids", " = " + p.id);
                }

                Log.d("Message",recyclerView.findViewHolderForAdapterPosition(0).toString());
                //Log.d("Message2", String.valueOf(recyclerView.getChildAdapterPosition(v)));


                Intent showcart = new Intent(context, CartActivity.class);
                startActivity(showcart);


            }
        });
    }

    @Override
    protected void onResume() {
        products.clear();
        super.onResume();

    }

    public void sendRequestforProducts() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String product_url = "http://inbackoffice.com/app/inforce/product.php";

        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, product_url,
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
                            JSONArray jsonArray = jobj.getJSONArray("products");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                product = new Product();


                                if (!jsonObject.isNull("id")) {
                                    product.id = jsonObject.getInt("id");

                                }
                                if (!jsonObject.isNull("name")) {
                                    product.name = jsonObject.getString("name");
                                }

                                if (!jsonObject.isNull("quantity")) {
                                    product.quantity = jsonObject.getInt("quantity");
                                }

                                products.add(i, product);


                                Log.d("Message", String.valueOf(product.id));


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        myAdapter = new OrderAdapter(OrderActivity.this, products);
                        myAdapter.setOnRecyclerViewItemClickListener(new OrderAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClicked(String text) {
                                Log.d("Quantity", " = " + text);
                            }
                        });
                        recyclerView.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsonArrayRequest);


    }


}

