package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class PaymentActivity extends AppCompatActivity {

    public ImageView iv_shop;
    public TextView tv_outlet_id, tv_outlet_name, tv_outlet_area, tv_total_due, tv_total_pay,tv_total_bill;
    public EditText et_payment;
    Button btn_make_payment;
    Bundle extras;
    int id;
    int total_due;
    String name;
    String location_name;
    public ArrayList<payment>paymentlist = new ArrayList<>();

    public static final String KEY_OUTLET_ID = "outlet_id";
    public static final String KEY_EMP_ID = "employee_id";
    public static final String KEY_AMOUNT = "amount";

    MyDB db;
    Cursor cursor;
    int agent;
    int paidmoney;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        iv_shop = (ImageView) findViewById(R.id.iv_shop_icon);
        tv_outlet_id = (TextView) findViewById(R.id.tv_Outlet_ID);
        tv_outlet_name = (TextView) findViewById(R.id.tv_Outlet_Name);
        tv_outlet_area = (TextView) findViewById(R.id.tv_Outlet_Area);
        tv_total_due = (TextView) findViewById(R.id.tv_Due);
        tv_total_pay = (TextView)findViewById(R.id.tv_Paid);
        tv_total_bill = (TextView)findViewById(R.id.tv_total_Bill);

        et_payment = (EditText) findViewById(R.id.et_Pay_Money);

        btn_make_payment = (Button)findViewById(R.id.btn_make_payment);

        context = this;

        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            name = extras.getString("name");

            location_name = extras.getString("location_name");

            total_due = extras.getInt("due");
        }

        db = new MyDB(this);
        cursor = db.getData();
        if (cursor.moveToFirst()) {

            agent = cursor.getInt(0);
        }



        tv_outlet_id.setText("Outlet Id: " + id);

        tv_outlet_name.setText("Outlet Name:" + name);

        tv_outlet_area.setText("Location Name:" + location_name);

        tv_total_bill.setText("Total Due: " + String.valueOf(total_due));

        tv_total_pay.setText("Total Pay:0");
        tv_total_due.setText("Total Due"+String.valueOf(total_due));

        Log.d("Total", "Payment" + String.valueOf(total_due));




    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et_payment.setVisibility(View.INVISIBLE);

                paidmoney = Integer.parseInt(et_payment.getText().toString().trim());
                int due = total_due -  paidmoney;
                sendpayment();
                tv_total_pay.setText("Total Paid Amount:" + paidmoney);
                tv_total_due.setText("Total Due:"+String.valueOf(due));

            }
        });
    }

    public void sendpayment(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String payment_url = "http://inbackoffice.com/app/inforce/input_transition.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, payment_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int res = jsonObject.getInt("success");
                            Log.d("Sent", "Money" + String.valueOf(res));
                            if (res == 0)
                                return;

                            String message = jsonObject.getString("message");
                           
//                            Editable payment = et_payment.getText();
//                             paidmoney = String.valueOf(payment);
//                            Log.d("Paid","Amount"+paidmoney);
//                            tv_total_pay.setText("Total Paid Amount:" + paidmoney);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params = new HashMap<String,String>();

                params.put(KEY_OUTLET_ID,String.valueOf(id));
                params.put(KEY_EMP_ID,String.valueOf(agent));
                params.put(KEY_AMOUNT,String.valueOf(paidmoney));


                return params;

            }
        };

        requestQueue.add(stringRequest);
    }
}
