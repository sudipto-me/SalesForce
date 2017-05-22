package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter <LocationAdapter.ViewHolder> {

    Context context ;
    public List<Location> mlocationList;
    private ItemClickListener clickListener;




    public LocationAdapter(Context context, List<Location> mlocationList) {
        this.context = context;
        this.mlocationList = mlocationList;
    }

    public void swap(ArrayList<Location>locationList){

        mlocationList.clear();
        mlocationList.addAll(locationList);
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationview, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        final Location location = mlocationList.get(position);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Toast.makeText(context, "You clicked :" + location.getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,OutletActivity.class));



            }
        });


        //holder.location_id.setText(location.getId());
        holder.location_name.setText(location.getName());

    }

    @Override
    public int getItemCount() {
        if (mlocationList != null) {
            return mlocationList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView location_name;
        public TextView location_id;
        public ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            //location_id = (TextView)itemView.findViewById(R.id.tv_location_id);
            location_name = (TextView) itemView.findViewById(R.id.tv_location_name);

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

