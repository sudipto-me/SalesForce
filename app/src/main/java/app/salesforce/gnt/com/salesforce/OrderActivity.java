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
    Button btn_newOrder, btn_currentOrder, btn_deleteOrder,btn_showCart;
    RecyclerView recyclerView;
    OrderAdapter myAdapter;
    ArrayList<Product> products = new ArrayList<>();

    public static final String KEY_ID = "product_id";
    public static final String KEY_NAME = "product_name";

    //ProductDB mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        btn_newOrder = (Button) findViewById(R.id.btn_new_order);
        btn_currentOrder = (Button) findViewById(R.id.btn_current_order);
        btn_deleteOrder = (Button) findViewById(R.id.btn_delete_order);
        recyclerView = (RecyclerView) findViewById(R.id.rv_product_list);
        btn_showCart = (Button)findViewById(R.id.btn_show_cart);

        //mydb = new ProductDB(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequestforProducts();
            }
        });
        btn_currentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "Show current Order", Toast.LENGTH_SHORT).show();
            }
        });
        btn_deleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "Delete Order", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new OrderAdapter(OrderActivity.this, products);
        recyclerView.setAdapter(myAdapter);

        myAdapter.notifyDataSetChanged();

        btn_showCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mydb.getProducts();
                //Toast.makeText(context,"You selected: "+mydb.getProducts(),Toast.LENGTH_SHORT).show();
                Intent showcart = new Intent(context,CartActivity.class);
                startActivity(showcart);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();




       // mydb.getProducts();
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
                            JSONArray jsonArray = jobj.getJSONArray("product");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Product product = new Product();

                                if (!jsonObject.isNull("product_id")) {
                                    product.id = jsonObject.getInt("product_id");

                                    //mydb.insertProduct(product.id);

                                }
                                if (!jsonObject.isNull("product_name")) {
                                    product.name = jsonObject.getString("product_name");
                                }
                                products.add(i, product);
                                myAdapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

