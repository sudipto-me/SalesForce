package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter <LocationAdapter.ViewHolder>{

    Context context;
    public List<Location>mlocationList;


    public LocationAdapter(Context context,List<Location>mlocationList){
        this.context = context;
        this.mlocationList = mlocationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationview,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Location location = mlocationList.get(position);
        holder.location_name.setText(location.getLocation_name());
    }

    @Override
    public int getItemCount() {
        return mlocationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {


        public TextView location_name;
        public ViewHolder(View itemView) {
            super(itemView);
            location_name = (TextView)itemView.findViewById(R.id.tv_location_name);
        }

    }
}

