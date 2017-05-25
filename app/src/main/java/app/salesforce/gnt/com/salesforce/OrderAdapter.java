package app.salesforce.gnt.com.salesforce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC-05 on 5/22/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>

{

    Context context;
    List<Product> mProductList;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Product product = mProductList.get(position);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
               // Toast.makeText(context,"You selected:",Toast.LENGTH_SHORT).show();


            }
        });

        holder.tv_productname.setText(product.getName());


    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView tv_productname;
        public TextView tv_productquantity;
        public Button btn_increment;
        public Button btn_decrement;

        public ItemClickListener itemClickListener;

        int quantity = 0;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_productname = (TextView) itemView.findViewById(R.id.tv_shop_name);

            btn_increment = (Button) itemView.findViewById(R.id.btn_add_to_cart);

            btn_decrement = (Button) itemView.findViewById(R.id.btn_delete_from_cart);

            itemView.setOnClickListener(this);

            btn_increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity++;

                    displayQuantity(quantity);

                }
            });

            btn_decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity--;
                    if (quantity <= 0) {
                        quantity = 0;
                    }
                    displayQuantity(quantity);
                }
            });

        }

        public void displayQuantity(int number) {

            tv_productquantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            tv_productquantity.setText("" + number);

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
