package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 8/30/2017.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>{

    Context context;
    public List<PaymentFormat> mbillFormats;

    public PaymentAdapter(Context context, ArrayList<PaymentFormat> mbillFormats) {
        this.context = context;
        this.mbillFormats = mbillFormats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentview, parent, false);
        PaymentAdapter.ViewHolder viewHolder = new PaymentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PaymentFormat billFormat = mbillFormats.get(position);
        holder.tv_date.setText(billFormat.getDate());
        holder.tv_amount.setText(String.valueOf(billFormat.getAmount()) + " Taka");

    }


    @Override
    public int getItemCount() {
        return mbillFormats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_payment_date);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_payment_amount);

        }
    }
}
