package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PC-05 on 6/1/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    public List<Product> mCartList;

    public CartAdapter(Context context, ArrayList<Product> mCartList) {
        this.context = context;
        this.mCartList = mCartList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartview, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product cart = mCartList.get(position);

        holder.tv_productName.setText(cart.getName());
        holder.tv_quantity.setText(String.valueOf(cart.getQuantity()));
        holder.tv_price.setText(String.valueOf(cart.getPrice()));

    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  tv_productName, tv_ProductQuantity, tv_quantity, tv_product_price, tv_price, tv_taka;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_ProductQuantity = (TextView) itemView.findViewById(R.id.tv_Product_Quantity);
            tv_quantity = (TextView) itemView.findViewById(R.id.tv_Quanitity);
            tv_price = (TextView) itemView.findViewById(R.id.tv_Price);

        }
    }
}
