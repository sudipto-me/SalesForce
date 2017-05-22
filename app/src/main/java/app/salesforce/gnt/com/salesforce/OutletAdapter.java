package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by PC-05 on 5/21/2017.
 */

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.ViewHolder>  {

    Context context;
    public List<Outlet> moutletList;
    private ItemClickListener itemClickListener;

    public OutletAdapter(Context context,List<Outlet> moutletList)
    {
        this.context = context;
        this.moutletList = moutletList;
        Log.d("Adapetr Size",""+this.moutletList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outletview,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Outlet outlet = moutletList.get(position);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Toast.makeText(context,"You clicked:"+outlet.getId(),Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,OrderActivity.class));
            }
        });



      // holder.outlet_id.setText(outlet.getId());
        holder.outlet_Name.setText(outlet.getOutletname());

    }

    @Override
    public int getItemCount() {
        Log.d("Adapetr Size",""+moutletList.size());
        return moutletList.size();


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public TextView outlet_Name;
        public TextView outlet_id;
        public ItemClickListener itemClickListener;



        public ViewHolder(View itemView) {
            super(itemView);

            //outlet_id = (TextView)itemView.findViewById(R.id.tv_id);

            outlet_Name = (TextView)itemView.findViewById(R.id.tv_outlet_name);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.OnClick(view, (int) getItemId());
        }
    }

}
