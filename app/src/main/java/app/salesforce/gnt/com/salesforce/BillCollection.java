package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BillCollection extends AppCompatActivity {

    public TextView tv_total_bill,tv_total_pay,tv_total_due;
    public Button btn_payment;
    public RecyclerView rv_billcollection;
    public BillCollectionAdapter billCollectionAdapter;
    public ArrayList<BillFormat>billFormats;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_collection);

        context = this;

        tv_total_bill = (TextView)findViewById(R.id.tv_Total_Bill);
        tv_total_due = (TextView)findViewById(R.id.tv_Total_Due);
        tv_total_pay = (TextView)findViewById(R.id.tv_Total_Pay);
        btn_payment = (Button)findViewById(R.id.btn_Payment);

        rv_billcollection = (RecyclerView)findViewById(R.id.rv_bill_collection);
        rv_billcollection.setHasFixedSize(true);
        billCollectionAdapter = new BillCollectionAdapter(BillCollection.this,billFormats);
        rv_billcollection.setAdapter(billCollectionAdapter);
        billCollectionAdapter.notifyDataSetChanged();

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Go to the Payment method",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
