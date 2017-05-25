package app.salesforce.gnt.com.salesforce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /*
    Defining the variables
    EditText
    Button
    TextView
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);


    }
}
