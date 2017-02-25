package com.kiit.klx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.*;

public class Display_Details_Change extends Activity {

    private ImageView PROFILE_PIC;
    private EditText DISPLAY_NAME;
    private EditText PHONE_NUMBER;
    private Button LOOKS_GOOD;
    private final int GALLERY_OPEN=90;
    private final Integer CAMERA = 0x4;
    private final int PROFILE_PHOTO=1321;
    private Uri profilephoto;
    private String DisplayName;
    private FirebaseAuth mauth;
    private DatabaseReference Users;
    private StorageReference storageReference;
    private byte[] byteArray;
    private Uri scaniduri;
    private final int SCAN_ID=12241;
    private ProgressDialog progressDialog;
    private  File  file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__details__change);
        if(Build.VERSION.SDK_INT>=23)
        {

            askForPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE,GALLERY_OPEN);


        }
        file=null;
        byteArray=null;
        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        PROFILE_PIC=(ImageView) findViewById(R.id.PROFILE_PICTURE);
        DISPLAY_NAME=(EditText) findViewById(R.id.DISPLAY_NAMES);
        PHONE_NUMBER=(EditText) findViewById(R.id.MOBILE_NUMBER);
        LOOKS_GOOD=(Button) findViewById(R.id.TO_HOME_PAGE);
        Users= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();//.child("Users");
        mauth=FirebaseAuth.getInstance();


        LOOKS_GOOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final StorageReference newusertorage=storageReference.child("Users");
                final DatabaseReference NewUser=Users.child("Users").child(mauth.getCurrentUser().getUid());
                DisplayName=DISPLAY_NAME.getText().toString().trim();
                if(!(((byteArray!=null)||scaniduri!=null)&& !TextUtils.isEmpty(DisplayName)&& !TextUtils.isEmpty(PHONE_NUMBER.getText().toString().trim())
                && TextUtils.isDigitsOnly(PHONE_NUMBER.getText().toString().trim())))
                {
                    Toast.makeText(getApplicationContext(),"Fill details and select a picture",Toast.LENGTH_SHORT).show();

                }
                else
                {


                    progressDialog.setMessage("Updating your information");
                    progressDialog.show();
                    try {


                        if (scaniduri==null)
                        {
                                   newusertorage.child(mauth.getCurrentUser().getUid()).child("Profile_Photo").putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                    NewUser.child("DisplayName").setValue(DisplayName);
                                    NewUser.child("Image").setValue(taskSnapshot.getDownloadUrl().toString());
                                    NewUser.child("Email").setValue(mauth.getCurrentUser().getEmail());
                                    NewUser.child("Uploads").setValue("0");
                                    NewUser.child("Mobile").setValue(PHONE_NUMBER.getText().toString().trim());
                                    NewUser.child("Uid").setValue(mauth.getCurrentUser().getUid());
                                    NewUser.child("Bought").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"Get down to buissness",Toast.LENGTH_SHORT).show();
                                            Intent Account=new Intent(Display_Details_Change.this, com.kiit.klx.Account.class);
                                            finish();
                                            startActivity(Account);
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Snackbar.make((View) findViewById(R.id.activity_display__details__change),"Try again",Snackbar.LENGTH_LONG).show();
                                }
                            });

                        }
                        else
                        {
                            newusertorage.child(mauth.getCurrentUser().getUid()).child("Profile_Photo").putFile(scaniduri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                    NewUser.child("DisplayName").setValue(DisplayName);
                                    NewUser.child("Image").setValue(taskSnapshot.getDownloadUrl().toString());
                                    NewUser.child("Email").setValue(mauth.getCurrentUser().getEmail());
                                    NewUser.child("Uploads").setValue("0");
                                    NewUser.child("Uid").setValue(mauth.getCurrentUser().getUid());
                                    NewUser.child("Bought").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"Get down to buissness",Toast.LENGTH_SHORT).show();
                                            Intent Account=new Intent(Display_Details_Change.this, com.kiit.klx.Account.class);
                                            finish();
                                            startActivity(Account);
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Snackbar.make((View) findViewById(R.id.activity_display__details__change),"Try again",Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }




                    }catch (Exception e)
                    {
                        Log.v("Exception",e.getLocalizedMessage());
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


        PROFILE_PIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             AlertDialog.Builder builder=   new AlertDialog.Builder(Display_Details_Change.this)
                        .setTitle("Upload picture")
                        .setMessage("Select the mode of uploading picture")
                        .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                intent.putExtra("return-data", true);
                                startActivityForResult(intent,GALLERY_OPEN);
                                // continue with
                            }
                        })
                        .setNegativeButton("Click a picture", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, PROFILE_PHOTO);

                            }
                        });
                        AlertDialog dialog= builder.create();
                        dialog.show();

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PROFILE_PHOTO) {
            //When Applicant photo is selected

            profilephoto = data.getData();



            Bitmap photo = (Bitmap) data.getExtras().get("data");


            int p[] = getResolution(photo.getWidth(), photo.getHeight());
            photo = Bitmap.createScaledBitmap(photo, p[0], p[1], true);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
              byteArray = stream.toByteArray();




            PROFILE_PIC.setImageBitmap(photo);
            scaniduri=null;
            // Profile.setImageURI(profilephoto);


        }
        else if(resultCode==RESULT_OK && requestCode==GALLERY_OPEN)
        {

            scaniduri=data.getData();
           String ImagePath=scaniduri.getPath();


            try {
                //Getting the Bitmap from Gallery
                file=new File(getPath(scaniduri));

                byte[]    b = new byte[(int) file.length()];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(b);
                for (int i = 0; i < b.length; i++) {
                    System.out.print((char)b[i]);
                }
                Log.d("file",scaniduri.toString()+" vf");
                Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), scaniduri);
                //Setting the Bitmap to ImageView
                int p[]=getResolution(bitmap.getWidth(),bitmap.getHeight());
               Bitmap scaled=Bitmap.createScaledBitmap(bitmap,p[0],p[1],true);


                PROFILE_PIC.setImageBitmap(scaled);

                byteArray=null;

            } catch (IOException e) {

                Log.v("Dinkus25",e.getLocalizedMessage());

                Toast.makeText(getApplicationContext(),"FILE ERROR",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }


        }

    }
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    int[] getResolution(int a,int b)
    {   int[] p=new int[2];
        if(a>950&&a<1900)
        {
            p[0]=a/2;
            p[1]=b/2;
        }
        else if (a>=1900&&a<3800)
        {
            p[0]=a/4;
            p[1]=b/4;
        }
        else if (a>=3800&&a<7600)
        {
            p[0]=a/8;
            p[1]=b/8;
        }
        else
        {
            p[0]=a;
            p[1]=b;
        }
        return p;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 90:
                askForPermission(android.Manifest.permission.CAMERA,CAMERA);
                break;

            case 0x4:

                break;
        }


    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(Display_Details_Change.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Display_Details_Change.this, permission)) {


                ActivityCompat.requestPermissions(Display_Details_Change.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(Display_Details_Change.this, new String[]{permission}, requestCode);
            }
        }


    }


}
