package com.kiit.klx.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kiit.klx.Activities.Account;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class userprofile extends Fragment  {


    private AlertDialog.Builder builder;
    private FirebaseAuth mAuth;
    private TextView Name;
    private TextView Email;
    private TextView Uploads;
    private TextView Bought;
    private TextView Reset_Email;
    private TextView Mobile;
    private ImageView EDIT;
    private CircleImageView Profile_pic;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_userprofile, container, false);

        builder=new AlertDialog.Builder(getContext());
        mAuth=FirebaseAuth.getInstance();
        Name=(TextView) view.findViewById(R.id.USERPROFILE_NAME_ID);
        Email=(TextView) view.findViewById(R.id.USERPROFILE_EMAIL_ID);
        Uploads=(TextView) view.findViewById(R.id.USERPROFILE_UPLOADS_ID);
        Bought=(TextView) view.findViewById(R.id.USERPROFILE_BOUGHT_ID);
        Reset_Email=(TextView) view.findViewById(R.id.USERPROFILE_RESET_PASSWORD_ID);
        Profile_pic=(CircleImageView) view.findViewById(R.id.USERPROFILE_IMAGE_VIEW_ID);
        Mobile=(TextView) view.findViewById(R.id.MOBILE_NUMBER_ID);
        EDIT=(ImageView) view.findViewById(R.id.EDIT_INFO);


        Name.setText(Account.LOGGED_IN_USER_DETAIL.DisplayName);
        Email.setText(Account.LOGGED_IN_USER_DETAIL.Email);

        if (Account.LOGGED_IN_USER_DETAIL.Email.equals(Constants.GUEST_EMAIL))
        {
            EDIT.setVisibility(View.INVISIBLE);
            EDIT.setEnabled(false);

            Reset_Email.setVisibility(View.INVISIBLE);
            Reset_Email.setEnabled(false);

            Uploads.setText("N/A");
            Bought.setText("N/A");
            Mobile.setText("N/A");

        }
        else
        {

            Uploads.setText(Account.LOGGED_IN_USER_DETAIL.Uploads);
            Bought.setText(Account.LOGGED_IN_USER_DETAIL.Bought);
            Mobile.setText(Account.LOGGED_IN_USER_DETAIL.Mobile);

        }




        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Mobile");
        EDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog=new Dialog(Account.MainContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.change_details);



                final EditText CHANGE=(EditText) dialog.findViewById(R.id.CHANGE_EDIT_TEXT);
                Button   SUBMIT=(Button)   dialog.findViewById(R.id.CONFIRM_CHANGES);
                LinearLayout MAIN=(LinearLayout) dialog.findViewById(R.id.CHANGE_LAYOUT);
                MAIN.setLayoutParams(new LinearLayout.LayoutParams((Constants.SCREEN_WIDTH*3),150));

                SUBMIT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!TextUtils.isEmpty(CHANGE.getText().toString().trim())&&TextUtils.isDigitsOnly(CHANGE.getText().toString().trim()))
                        {
                            databaseReference.setValue(CHANGE.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Account.MainContext,"Changes made successfully",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    Account.fragmentManager.beginTransaction().replace(R.id.content_frame, new userprofile()).commit();

                                }
                            });

                        }
                    }
                });

                dialog.show();


            }
        });


        Glide.with(userprofile.this)
                .load(Account.LOGGED_IN_USER_DETAIL.Image)
                .into(Profile_pic);






            Reset_Email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    builder.setMessage("Confirm password change ?");
                    builder.setTitle("Password change");
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            mAuth.sendPasswordResetEmail(Account.LOGGED_IN_USER_DETAIL.Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(Account.MainContext, "Successfully sent password reset email check inbox", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    Toast.makeText(Account.MainContext, "Couldn't send password reset mail", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            });



        return view;
    }


}
