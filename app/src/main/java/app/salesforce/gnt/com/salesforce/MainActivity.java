package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
    Defining the variables
    EditText
    Button
    TextView
     */
    EditText et_email,et_passWord;
    Button btn_signIn,btn_forgetPassword;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        et_email = (EditText)findViewById(R.id.et_enter_email);
        et_passWord = (EditText)findViewById(R.id.et_enter_password);
        btn_signIn = (Button)findViewById(R.id.btn_sign_in);
        btn_forgetPassword = (Button) findViewById(R.id.btn_forget_password);


        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_email.getText().toString().equals("admin")&& et_passWord.getText().toString().equals("admin")){

                    Toast.makeText(MainActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();


                }
                else if(et_email.getText().toString().isEmpty()&& et_passWord.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this,"Please enter valid information",Toast.LENGTH_SHORT).show();

                else if ((et_email.getText().toString().equals("admin")
                        &&et_passWord.getText().toString().isEmpty())&&
                (et_email.getText().toString().isEmpty()
                        &&et_passWord.getText().toString().isEmpty()))

                    Toast.makeText(MainActivity.this,"Information incorrect",Toast.LENGTH_SHORT).show();

                else {
                    // Toast.makeText(MainActivity.this,"Please enter valid information",Toast.LENGTH_SHORT).show();


                }

            }

        });

        btn_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"You need to wait for email",Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, LocationActivity.class);
                startActivity(i);
            }
        });
    }
}
