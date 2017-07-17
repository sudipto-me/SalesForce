package app.salesforce.gnt.com.salesforce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText et_email, et_passWord;
    Button btn_signIn;
    CheckBox cb_chekcpassword;

    public static final String KEY_NAME = "mob";
    public static final String KEY_PASS = "pass";

    public String id;

    Context context;

    AlertDialog.Builder builder;

    private String number;
    private String passWord;

    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = this;

        builder = new AlertDialog.Builder(LoginActivity.this);

        et_email = (EditText) findViewById(R.id.et_enter_email);
        et_passWord = (EditText) findViewById(R.id.et_enter_password);
        cb_chekcpassword = (CheckBox) findViewById(R.id.cb_show_password);
        btn_signIn = (Button) findViewById(R.id.btn_sign_in);


        //checkbox listener for the showing password

        cb_chekcpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_passWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        db = new MyDB(this);
        Cursor cursor = db.getData();
        if (cursor.moveToFirst()) {
            Toast.makeText(LoginActivity.this, "GET DATA FROM DB" + cursor.getString(1), Toast.LENGTH_LONG).show();


            //startActivity(new Intent(LoginActivity.this, LocationActivity.class));

            Intent intent = new Intent(context,LocationActivity.class);
            intent.putExtra("emp_id",cursor.getString(1));
            startActivity(intent);

        }

        //button listener for sign in
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = et_email.getText().toString().trim();//phone number input
                passWord = et_passWord.getText().toString().trim();//password input

                sendRequestforLogIN();
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

            }
        });
    }

    public void sendRequestforLogIN() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String LogInUrl = "http://inbackoffice.com/app/inforce/login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LogInUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        Log.d("Response", response.toString());
                        try {
                            JSONObject jobj = new JSONObject(response);
                            int res = jobj.getInt("success");
                            if (res == 0) {

                                Toast.makeText(context,"Result",Toast.LENGTH_LONG).show();
                                return;
                            }
                            Log.d("Success=1",response.toString());
                            String msg = jobj.getString("message");
                            JSONArray jsonArray = jobj.getJSONArray("user");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String dob = jsonObject.getString("dob");
                            String des = jsonObject.getString("desig");
                            db.insertData(id);
                            Log.d("Data", db.toString());

                           // Toast.makeText(context, "result: id" + id + " name: " + name + " dob:" + dob + " designation:" + des, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context,LocationActivity.class);
                            intent.putExtra("emp_id",id);
                            startActivity(intent);


                        } catch (JSONException e) {
                            Log.e("jsonexception",e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_NAME, number);
                params.put(KEY_PASS, passWord);
                Log.d("Params", params.toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}



