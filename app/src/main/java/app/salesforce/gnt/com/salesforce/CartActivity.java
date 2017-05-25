package app.salesforce.gnt.com.salesforce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    TextView tv;
    ProductDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);



    }
}
