package com.kiit.klx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment

        view =inflater.inflate(R.layout.fragment_sell, container, false);
        CATEGORY_SELECTED=null;
        IMG1=null;
        IMG2=null;
        IMG3=null;
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









        return view;
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
                                break;
                case IMAGE2_REQ:
                                IMG2=stream.toByteArray();
                                IMAGE2.setImageBitmap(Picture);
                                break;
                case IMAGE3_REQ:
                                IMG3=stream.toByteArray();
                                IMAGE3.setImageBitmap(Picture);
                                break;
                case IMAGE4_REQ:
                                IMG4=stream.toByteArray();
                                IMAGE4.setImageBitmap(Picture);
                                break;


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
        if(TextUtils.isEmpty(price)||TextUtils.isEmpty(name)||TextUtils.isDigitsOnly(description)||TextUtils.isEmpty(CATEGORY_SELECTED)
              ||IMG1==null ||IMG2==null ||IMG3==null||IMG4==null  )
        {
            return false;
        }
        else
            return true;



    }

}
