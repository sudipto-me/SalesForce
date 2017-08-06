package app.salesforce.gnt.com.salesforce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    public ImageView iv_shop;
    public TextView tv_outlet_id,tv_outlet_name,tv_outlet_area,tv_total_due,tv_total_pay;
    public EditText et_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        iv_shop = (ImageView)findViewById(R.id.iv_shop_icon);
        tv_outlet_id = (TextView)findViewById(R.id.tv_Outlet_ID);
        tv_outlet_name = (TextView)findViewById(R.id.tv_Outlet_Name);
        tv_outlet_area = (TextView)findViewById(R.id.tv_Outlet_Area);
        tv_total_due = (TextView)findViewById(R.id.tv_Total_Due);
        tv_total_pay = (TextView)findViewById(R.id.tv_Total_Pay);

        et_payment = (EditText)findViewById(R.id.et_Pay_Money);
    }
}
