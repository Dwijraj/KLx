package com.kiit.klx.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kiit.klx.Activities.Account;
import com.kiit.klx.Model.Items;
import com.kiit.klx.R;

import java.util.ArrayList;

/**
 * Created by 1405214 on 24-02-2017.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    private ArrayList<Items> ListData;
    private LayoutInflater inflater;
    private Context Main;


    public RecyclerViewAdapter(ArrayList<Items> listData, Context c) {
        this.ListData = listData;
        this.inflater=LayoutInflater.from(c);
        Main=c;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.list_item,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        Items Product=ListData.get(position);

        holder.Price.setText(Product.Price);
        holder.Description.setText(Product.Description);
        holder.ProductName.setText(Product.ProductName);

        Glide.with(Main)
                .load(Product.IMAGE1)
                .into(holder.IMG1);

        Glide.with(Main)
                .load(Product.IMAGE2)
                .into(holder.IMG2);

        Glide.with(Main)
                .load(Product.IMAGE3)
                .into(holder.IMG3);

        Glide.with(Main)
                .load(Product.IMAGE4)
                .into(holder.IMG4);

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        private ImageView IMG1;
        private ImageView IMG2;
        private ImageView IMG3;
        private ImageView IMG4;
        private TextView Price;
        private TextView ProductName;
        private TextView Description;
        private Button Buy;

        public Holder(View itemView) {
            super(itemView);


            IMG1=(ImageView) itemView.findViewById(R.id.IMAGE_1);
            IMG2=(ImageView) itemView.findViewById(R.id.IMAGE_2);
            IMG3=(ImageView) itemView.findViewById(R.id.IMAGE_3);
            IMG4=(ImageView) itemView.findViewById(R.id.IMAGE_4);
            Price=(TextView) itemView.findViewById(R.id.PRODUCT_PRICE_ID);
            ProductName=(TextView) itemView.findViewById(R.id.PRODUCT_NAME_ID);
            Description=(TextView) itemView.findViewById(R.id.PRODUCT_DESCRIPTION_ID);
            Buy=(Button) itemView.findViewById(R.id.BUY_BUTTON);


            Buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to Buy Page
                }
            });

        }

    }


}