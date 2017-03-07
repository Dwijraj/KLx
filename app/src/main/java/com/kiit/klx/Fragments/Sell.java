package com.kiit.klx.Fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kiit.klx.Activities.Account;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.R;
import com.kiit.klx.Servicenbroadcastreceiver.Notify;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.kiit.klx.Constants.Constants.UNIQUE_ID;


public class Sell extends Fragment {

    private String price;
    private String name;
    private String description;
    private View view;
    private final int IMAGE1_REQ=1880;
    private final int IMAGE2_REQ=1885;
    private final int IMAGE3_REQ=1890;
    private final int IMAGE4_REQ=1895;
    private ImageView IMAGE1;
    private ImageView IMAGE2;
    private ImageView IMAGE3;
    private ImageView IMAGE4;
    private EditText  PRICE;
    private EditText  PRODUCTNAME;
    private EditText  DESCRIPTION;
    private Button    SELL;
    private byte[] IMG1;
    private byte[] IMG2;
    private byte[] IMG3;
    private byte[] IMG4;
    private Spinner CATEGORY_LIST;
    private String CATEGORY_SELECTED;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        view =inflater.inflate(R.layout.fragment_sell, container, false);
        CATEGORY_SELECTED=Constants.CATEGORY_ELECTRONICS;
        IMG1=null;
        IMG2=null;
        IMG3=null;
        progressDialog=new ProgressDialog(Account.MainContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Uploading your product");
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        IMAGE1=(ImageView) view.findViewById(R.id.IMAGE_1_SELL);
        IMAGE2=(ImageView) view.findViewById(R.id.IMAGE_2_SELL);
        IMAGE3=(ImageView) view.findViewById(R.id.IMAGE_3_SELL);
        IMAGE4=(ImageView) view.findViewById(R.id.IMAGE_4_SELL);
        PRICE=(EditText)   view.findViewById(R.id.PRODUCT_PRICE_ID_EDIT_TEXT);
        PRODUCTNAME=(EditText) view.findViewById(R.id.PRODUCT_NAME_ID_EDIT_TEXT);
        DESCRIPTION=(EditText) view.findViewById(R.id.PRODUCT_DESCRIPTION_ID_EDIT_TEXT);
        SELL=(Button)      view.findViewById(R.id.SELL_BUTTON);
        CATEGORY_LIST=(Spinner) view.findViewById(R.id.SPIINER_CATEGORY);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Account.MainContext, android.R.layout.simple_spinner_dropdown_item, Constants.CATEGORIES);
        CATEGORY_LIST.setAdapter(adapter);



        IMAGE1.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        IMAGE1.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMAGE2.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        IMAGE2.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMAGE3.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        IMAGE3.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMAGE4.setLayoutParams(new LinearLayout.LayoutParams(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT));
        IMAGE4.setBackground(getResources().getDrawable(R.mipmap.ic_photo_camera_black_24dp));
        IMAGE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                STARTINTENT(IMAGE1_REQ);
            }
        });
        IMAGE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                STARTINTENT(IMAGE2_REQ);
            }
        });
        IMAGE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                STARTINTENT(IMAGE3_REQ);
            }
        });
        IMAGE4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                STARTINTENT(IMAGE4_REQ);
            }
        });

        SELL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.v("Point","1");
                if(check())
                {

                    Log.v("Point","3");
                    startPosting();
                }
                else
                {

                }


            }
        });

        CATEGORY_LIST.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CATEGORY_SELECTED=Constants.CATEGORIES[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        return view;
    }
    public void startPosting()
    {

        Log.v("Point","4");
        progressDialog.show();
        final DatabaseReference DF=databaseReference.child("Category").child(CATEGORY_SELECTED).push();


        Log.v("PUSHING",DF.getKey());

        final StorageReference ST=storageReference.child("Uploads").child(mAuth.getCurrentUser().getUid()).child(DF.getRef().toString());

        ST.child("IMAGE1").putBytes(IMG1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot1) {

                ST.child("IMAGE2").putBytes(IMG2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot2) {

                        ST.child("IMAGE3").putBytes(IMG3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot3) {

                                ST.child("IMAGE4").putBytes(IMG4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot4) {




                                                DatabaseReference ST =databaseReference.child("Uploads").child(mAuth.getCurrentUser().getUid()).child(DF.getKey());

                                                ST.child("Price").setValue(price);
                                                ST.child("ProductName").setValue(name);
                                                ST.child("Description").setValue(description);
                                                ST.child("IMAGE1").setValue(taskSnapshot1.getDownloadUrl().toString());
                                                ST.child("IMAGE2").setValue(taskSnapshot2.getDownloadUrl().toString());
                                                ST.child("IMAGE3").setValue(taskSnapshot3.getDownloadUrl().toString());
                                                ST.child("IMAGE4").setValue(taskSnapshot4.getDownloadUrl().toString());
                                                ST.child("UploaderID").setValue(mAuth.getCurrentUser().getUid());
                                                ST.child("Category").setValue(CATEGORY_SELECTED);




                                        int Uploads_no=Integer.parseInt(Account.LOGGED_IN_USER_DETAIL.Uploads);
                                        Uploads_no=Uploads_no+1;
                                        Log.v("Point2.1",Uploads_no+"");
                                        databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("Uploads").setValue(Uploads_no+"");
                                        DF.child("Price").setValue(price);
                                        DF.child("ProductName").setValue(name);
                                        DF.child("Description").setValue(description);
                                        DF.child("IMAGE1").setValue(taskSnapshot1.getDownloadUrl().toString());
                                        DF.child("IMAGE2").setValue(taskSnapshot2.getDownloadUrl().toString());
                                        DF.child("IMAGE3").setValue(taskSnapshot3.getDownloadUrl().toString());
                                        DF.child("IMAGE4").setValue(taskSnapshot4.getDownloadUrl().toString());
                                        DF.child("UploaderID").setValue(mAuth.getCurrentUser().getUid());


                                        NotificationCompat.Builder notification;

                                        notification= new NotificationCompat.Builder(Account.MainContext);
                                        notification.setAutoCancel(true);


                                        notification.setSmallIcon(R.mipmap.ic_add_shopping_cart_white_24dp);
                                        notification.setTicker("Notification from KLX");
                                        notification.setWhen(System.currentTimeMillis());
                                        notification.setContentTitle("KLX");
                                        notification.setContentText("Uploaded successfully");

                                        Intent intents=new  Intent(Account.MainContext,Account.class);
                                        PendingIntent pendingIntent=PendingIntent.getActivity(Account.MainContext,0,intents,PendingIntent.FLAG_NO_CREATE);
                                        notification.setContentIntent(pendingIntent);

                                        NotificationManager nm=(NotificationManager) Account.MainContext.getSystemService(NOTIFICATION_SERVICE);
                                        nm.notify(UNIQUE_ID,notification.build());

                                        progressDialog.dismiss();



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        progressDialog.dismiss();

                                        Toast.makeText(Account.MainContext,Constants.PRODUCT_COULDNOT_BE_UPLOAD,Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                progressDialog.dismiss();

                                Toast.makeText(Account.MainContext,Constants.PRODUCT_COULDNOT_BE_UPLOAD,Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        progressDialog.dismiss();

                        Toast.makeText(Account.MainContext,Constants.PRODUCT_COULDNOT_BE_UPLOAD,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();

                Toast.makeText(Account.MainContext,Constants.PRODUCT_COULDNOT_BE_UPLOAD,Toast.LENGTH_SHORT).show();
            }
        })  ;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {

            Bitmap Picture= (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            Picture.compress(Bitmap.CompressFormat.PNG,100,stream);
            switch (requestCode) {

                case IMAGE1_REQ:
                                IMG1=stream.toByteArray();
                                IMAGE1.setImageBitmap(Picture);
                              //  break;
                case IMAGE2_REQ:
                                IMG2=stream.toByteArray();
                                IMAGE2.setImageBitmap(Picture);
                              //  break;
                case IMAGE3_REQ:
                                IMG3=stream.toByteArray();
                                IMAGE3.setImageBitmap(Picture);
                              //  break;
                case IMAGE4_REQ:
                                IMG4=stream.toByteArray();
                                IMAGE4.setImageBitmap(Picture);
                              //  break;


            }
        }
    }
    public void STARTINTENT(int requestcode)
    {
        Intent CAPTURE_IMAGE=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(CAPTURE_IMAGE,requestcode);

    }
    public  boolean check()
    {
         price=PRICE.getText().toString().trim();
         name=PRODUCTNAME.getText().toString().trim();
         description=DESCRIPTION.getText().toString().trim();
        if(TextUtils.isEmpty(price)||TextUtils.isEmpty(name)||TextUtils.isEmpty(description)||TextUtils.isEmpty(CATEGORY_SELECTED)
              ||IMG1==null ||IMG2==null ||IMG3==null||IMG4==null  )
        {
            Log.v("Point","2");
            Log.v("Point2",price+" --");
            Log.v("Point2",name+" --");
            Log.v("Point2",description+" --");
            Log.v("Point2",CATEGORY_SELECTED+" --");
            Log.v("Point2",IMG1+" --");
            Log.v("Point2",IMG2+" --");
            Log.v("Point2",IMG3+" --");
            Log.v("Point2",IMG4+" --");

            Toast.makeText(Account.MainContext,"Check Params",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;



    }

}
