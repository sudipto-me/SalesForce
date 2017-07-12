package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC-05 on 6/1/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    public List<Cart>mCartList;

    public CartAdapter(Context context,List<Cart>mCartList){
        this.context = context;
        this.mCartList = mCartList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartview,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cart cart = mCartList.get(position);
        holder.tv_productName.setText(cart.getName());
        holder.tv_quantity.setText(cart.getPrice());

    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_productLogo;
        public TextView tv_productName,tv_ProductQuantity,tv_quantity;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_productLogo = (ImageView)itemView.findViewById(R.id.iv_product_logo);
            tv_productName = (TextView)itemView.findViewById(R.id.tv_product_name);
            tv_ProductQuantity = (TextView)itemView.findViewById(R.id.tv_Product_Quantity);
            tv_quantity = (TextView)itemView.findViewById(R.id.tv_Quanitity);
        }
    }
}
