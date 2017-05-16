package app.salesforce.gnt.com.salesforce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText et_email, et_passWord;
    Button btn_signIn;
    CheckBox cb_chekcpassword;

    public static final String KEY_NAME = "mob";
    public static final String KEY_PASS = "pas";

    Context context;

    AlertDialog.Builder builder;

    private String number;
    private String passWord;

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


        //button listener for sign in
        btn_signIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        number = et_email.getText().toString().trim();//phone number input
                        passWord = et_passWord.getText().toString().trim();//password input

                        String register_url = "http://inbackoffice.com/app/inforce/login.php";//url for the database

                        //post method for checking the information from the database
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("Result", response.toString());
                                        try {
                                            JSONObject jobj = new JSONObject(response);
                                            int res = jobj.getInt("success");//chcek the success message
                                            if (res == 0) {
                                                return;
                                            }
                                            String msg = jobj.getString("message");
                                            JSONArray jsonArray = jobj.getJSONArray("user");
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String id = jsonObject.getString("id");
                                            String name = jsonObject.getString("name");
                                            String dob = jsonObject.getString("dob");
                                            String des = jsonObject.getString("des");

                                            //id added to database
                                            Toast.makeText(LoginActivity.this, "result: id" + id + " name: " + name + " dob:" + dob + " des:" + des, Toast.LENGTH_LONG).show();

                                            //id store in database

                                            DBHelper db = new DBHelper(context);




                                           // db.addEmployee(new Employee(id));


                                             db.addEmployee(new Employee(id));

                                            List<Employee>employees = db.getAllId();

                                            Employee employee = db.getEmployee(Integer.parseInt(id));

                                           Log.d("Data:",employee.toString());

                                            Log.d("Show:", String.valueOf(employees.toArray()));

                                            //String fjg = db.getEmployee(id);





                                           // Log.d("Value:",fjg.toString());



                                            //Log.d("Data Show:",toString());

                                           // Log.d("Database",response.toString());

                                            //Log.d("ID",db.getEmployee(id).toString());


                                            /*
                                            Cursor c = (Cursor) db.getEmployee(Integer.parseInt(id));
                                            if (c.moveToFirst()) {
                                                Log.d("id", "From DB" + c.getString(0));
                                            } else
                                                Log.e("DB", "Empty");


                                             */
                                            Intent i = new Intent(context,LocationActivity.class);
                                            startActivity(i);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put(KEY_NAME, number);
                                params.put(KEY_PASS, passWord);
                                return params;
                            }


                        };


                        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                        requestQueue.add(stringRequest);
                    }
                });



    }


}
