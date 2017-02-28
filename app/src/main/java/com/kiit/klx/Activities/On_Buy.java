package com.kiit.klx.Activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiit.klx.Adapter.RecyclerViewAdapter;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.Model.User;
import com.kiit.klx.R;

public class On_Buy extends AppCompatActivity {

    private ImageView IMG1;
    private ImageView IMG2;
    private ImageView IMG3;
    private ImageView IMG4;
    private TextView Price;
    private TextView ProductName;
    private TextView Description;
    private Button KARTBUY;
    private Button BUYNOW;
    private Dialog dialog;
    private ImageView ZOOMED;
    private TextView Seller_email;
    private TextView Seller_Mobile;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__buy);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        IMG1=(ImageView) findViewById(R.id.IMAGE_1_ON_BUY);
        IMG2=(ImageView) findViewById(R.id.IMAGE_2_ON_BUY);
        IMG3=(ImageView) findViewById(R.id.IMAGE_3_ON_BUY);
        IMG4=(ImageView) findViewById(R.id.IMAGE_4_ON_BUY);
        IMG1.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        //IMG1.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMG2.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        //IMG2.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMG3.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        //IMG3.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMG4.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        //IMG4.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));

        Glide.with(On_Buy.this)
                .load(RecyclerViewAdapter.ITEM_SELECTED.IMAGE1)
                .into(IMG1);
        Glide.with(On_Buy.this)
                .load(RecyclerViewAdapter.ITEM_SELECTED.IMAGE2)
                .into(IMG2);
        Glide.with(On_Buy.this)
                .load(RecyclerViewAdapter.ITEM_SELECTED.IMAGE3)
                .into(IMG3);
        Glide.with(On_Buy.this)
                .load(RecyclerViewAdapter.ITEM_SELECTED.IMAGE4)
                .into(IMG4);


        IMG1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ZOOM(RecyclerViewAdapter.ITEM_SELECTED.IMAGE1);
            }
        });

        IMG2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ZOOM(RecyclerViewAdapter.ITEM_SELECTED.IMAGE2);
            }
        });

        IMG3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ZOOM(RecyclerViewAdapter.ITEM_SELECTED.IMAGE3);
            }
        });

        IMG4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ZOOM(RecyclerViewAdapter.ITEM_SELECTED.IMAGE4);
            }
        });
        Price=(TextView) findViewById(R.id.PRODUCT_PRICE_ID_ON_BUY);
        ProductName=(TextView) findViewById(R.id.PRODUCT_NAME_ID_ON_BUY);
        Description=(TextView) findViewById(R.id.PRODUCT_DESCRIPTION_ID_ON_BUY);
        KARTBUY=(Button) findViewById(R.id.KART_ON_BUY);
        BUYNOW=(Button) findViewById(R.id.BUY_NOW_ON_BUY);
        Seller_email=(TextView) findViewById(R.id.SELLER_ID);
        Seller_Mobile=(TextView) findViewById(R.id.SELLER_MOBILE);

        Price.setText(RecyclerViewAdapter.ITEM_SELECTED.Price);
        ProductName.setText(RecyclerViewAdapter.ITEM_SELECTED.ProductName);
        Description.setText(RecyclerViewAdapter.ITEM_SELECTED.Description);

        databaseReference.child(RecyclerViewAdapter.ITEM_SELECTED.UploaderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User SELLER= dataSnapshot.getValue(User.class);
                Seller_email.setText(SELLER.DisplayName);
                Seller_Mobile.setText(SELLER.Mobile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        KARTBUY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference KART= FirebaseDatabase.getInstance().getReference().child("Kart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


            }
        });



    }
    public void ZOOM(String Link)
    {
        RelativeLayout relativeLayout;
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.zoomed_image);
        ZOOMED=(ImageView) dialog.findViewById(R.id.ZoomedInImage);
        Glide.with(On_Buy.this)
                .load(Link)
                .into(ZOOMED);

        dialog.show();
    }
}
