package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-05 on 5/22/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>

{

    Context context;
    List<Product> mProductList;

    int sumValue = 0;

    public OrderAdapter(Context context, List<Product> mProductList) {
        this.context = context;
        this.mProductList = mProductList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productview, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Product product = mProductList.get(position);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {

            }
        });
        holder.tv_productid.setText(String.valueOf(product.getId()));
        holder.tv_productname.setText(product.getName());
        holder.tv_productquantity.setText(String.valueOf(product.getQuantity()));
        holder.tv_productquantity.invalidate();

        holder.btn_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.quantity++;
                holder.tv_productquantity.setText(String.valueOf(product.getQuantity()));

            }
        });

        holder.btn_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.quantity--;
                if(product.quantity<=0)
                    product.quantity = 0;
                holder.tv_productquantity.setText(String.valueOf(product.getQuantity()));


            }
        });


    }


    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_productname, tv_productid;
        public TextView tv_productquantity,tv_productprice;
        public Button btn_increment;
        public Button btn_decrement;

        public ItemClickListener itemClickListener;


        int quantity = 0;

        public ViewHolder(View itemView) {
            super(itemView);




            tv_productid = (TextView)itemView.findViewById(R.id.tv_product_id);
            tv_productname = (TextView) itemView.findViewById(R.id.tv_shop_name);

            btn_increment = (Button) itemView.findViewById(R.id.btn_add_to_cart);

            btn_decrement = (Button) itemView.findViewById(R.id.btn_delete_from_cart);

            tv_productquantity = (TextView) itemView.findViewById(R.id.tv_quantity);

            //tv_productprice = (TextView)itemView.findViewById(R.id.tv_ProductPrice);


            itemView.setOnClickListener(this);



        }



        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {




        }

        public int getQuantity() {
            return quantity;
        }


    }


}


