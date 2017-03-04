package com.kiit.klx.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiit.klx.Adapter.RecyclerViewAdapter;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.Fragments.MyBag;
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
    private Button CONTACTSELLER;
    private Dialog dialog;
    private ImageView ZOOMED;
    private TextView Seller_email;
    private TextView Seller_Mobile;
    private DatabaseReference databaseReference;
    public User SELLER;
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
        KARTBUY=(Button) findViewById(R.id.BAG_ON_BUY);
        CONTACTSELLER=(Button) findViewById(R.id.BUY_NOW_ON_BUY);
        Seller_email=(TextView) findViewById(R.id.SELLER_ID);
        Seller_Mobile=(TextView) findViewById(R.id.SELLER_MOBILE);

        Price.setText(RecyclerViewAdapter.ITEM_SELECTED.Price);
        ProductName.setText(RecyclerViewAdapter.ITEM_SELECTED.ProductName);
        Description.setText(RecyclerViewAdapter.ITEM_SELECTED.Description);

        databaseReference.child(RecyclerViewAdapter.ITEM_SELECTED.UploaderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SELLER= dataSnapshot.getValue(User.class);
                Seller_email.setText(SELLER.DisplayName);
                Seller_Mobile.setText(SELLER.Mobile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if(MyBag.VISIBILITY_FLAG.equals("0"))
        {

            KARTBUY.setText("REMOVE");
            KARTBUY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("Clicked","Click");

                     Account.builders=new AlertDialog.Builder(On_Buy.this);
                    Account.builders.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl(RecyclerViewAdapter.ITEM_SELECTED.BAGValue);
                            databaseReference.setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(Account.MainContext,"Item Removed from Bag",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Account.MainContext,Account.class);
                                    finish();
                                    Account.MainContext.startActivity(i);

                                }
                            });

                        }
                    });
                    Account.builders.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    Account.builders.setMessage("Confirm your choice");
                    Account.builders.setTitle("Remove Confirm");

                   final AlertDialog dialog2=Account.builders.create();
                    dialog2.show();



                }
            });

        }
        else
        {

            KARTBUY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference KART= FirebaseDatabase.getInstance().getReference().child("Bag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push();
                    KART.child("Price").setValue(RecyclerViewAdapter.ITEM_SELECTED.Price);
                    KART.child("ProductName").setValue(RecyclerViewAdapter.ITEM_SELECTED.ProductName);
                    KART.child("Description").setValue(RecyclerViewAdapter.ITEM_SELECTED.Description);
                    KART.child("IMAGE1").setValue(RecyclerViewAdapter.ITEM_SELECTED.IMAGE1);
                    KART.child("IMAGE2").setValue(RecyclerViewAdapter.ITEM_SELECTED.IMAGE2);
                    KART.child("IMAGE3").setValue(RecyclerViewAdapter.ITEM_SELECTED.IMAGE3);
                    KART.child("IMAGE4").setValue(RecyclerViewAdapter.ITEM_SELECTED.IMAGE4);
                    KART.child("BAGValue").setValue(KART.getRef().toString());
                    KART.child("UploaderID").setValue(RecyclerViewAdapter.ITEM_SELECTED.UploaderID).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(Account.MainContext,"Item added in your bag",Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });


        }



        CONTACTSELLER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog1=new Dialog(On_Buy.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.contact);

                ImageView Calling=(ImageView) dialog1.findViewById(R.id.CALL);
                ImageView Email=(ImageView) dialog1.findViewById(R.id.EMAIL);

                Calling.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +SELLER.Mobile ));
                        startActivity(intent);
                    }
                });

                Email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto",SELLER.Email, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Buy From KLX");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "I would like to disucuss with you about your ad on KLx with Product name :"+RecyclerViewAdapter.ITEM_SELECTED.ProductName);
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }
                });

                dialog1.show();


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
