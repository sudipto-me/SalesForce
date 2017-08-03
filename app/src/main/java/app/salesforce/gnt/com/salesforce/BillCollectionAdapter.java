package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Android on 8/3/2017.
 */

public class BillCollectionAdapter extends RecyclerView.Adapter<BillCollectionAdapter.ViewHolder> {

    Context context;
    public List<BillFormat> mbillFormats;

    public BillCollectionAdapter(Context context, List<BillFormat> mbillFormats) {
        this.context = context;
        this.mbillFormats = mbillFormats;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final BillFormat billFormat = mbillFormats.get(position);
        holder.tv_date.setText(billFormat.getDate());
        holder.tv_amount.setText(billFormat.getAmount());

    }


    @Override
    public int getItemCount() {
        return mbillFormats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
        }
    }
}
