package com.kiit.klx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

      //  Complains list_item=ListData.get(position);
        //  holder.textView.setText("Description : "+list_item.getDescription());
        // holder.Time.setText("Time : "+list_item.getDateTime());
        //   holder.Status.setText("Status : "+list_item.getStatus());
        // holder.Address.setText("Address : "+list_item.getAddress());
        //   holder.IssueNo.setText("IssueNo : " +list_item.getIssueNo());
        //  holder.IssueDescription.setText("Issue : "+list_item.getIssueDescprition());
        // holder.icon.setImageResource(list_item.getResid());


      /*  Glide.with(Main)
                .load(glideUrl)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .into(holder.icon); */

    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
       /* private TextView textView;
        private TextView IssueNo;
        private TextView Status;
        private TextView Address;
        private TextView Time;
        private ImageView icon;
        private TextView IssueDescription;
        private View container;
        private ImageView Edit; */

        public Holder(View itemView) {
            super(itemView);



            //  IssueNo=(TextView) itemView.findViewById(R.id.list_item_issueno);
            //   Status=(TextView) itemView.findViewById(R.id.list_item_status);
            //  Address=(TextView) itemView.findViewById(R.id.list_item_address);
            //  Time=(TextView) itemView.findViewById(R.id.list_item_time);
            //  IssueDescription=(TextView) itemView.findViewById(R.id.list_item_issuedescription);
            //  textView=(TextView) itemView.findViewById(R.id.list_item_description);
           // icon=(ImageView) itemView.findViewById(R.id.item_icon);
           // container=itemView.findViewById(R.id.container_item_root);

          //  icon.setOnClickListener(this);
            //  Edit=(ImageView)itemView.findViewById(R.id.item_edit);

            // Edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



        }
    }


}