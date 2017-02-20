package com.kiit.klx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class userprofile extends Fragment  {


    private AlertDialog.Builder builder;
    private FirebaseAuth mAuth;
    private TextView Name;
    private TextView Email;
    private TextView Uploads;
    private TextView Bought;
    private TextView Reset_Email;
    private ImageView Profile_pic;
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
        Profile_pic=(ImageView) view.findViewById(R.id.USERPROFILE_IMAGE_VIEW_ID);

        Name.setText(Account.LOGGED_IN_USER_DETAIL.DisplayName);
        Email.setText(Account.LOGGED_IN_USER_DETAIL.Email);
        Uploads.setText(Account.LOGGED_IN_USER_DETAIL.Uploads);
        Bought.setText(Account.LOGGED_IN_USER_DETAIL.Bought);


        Glide.with(userprofile.this)
                .load(Account.LOGGED_IN_USER_DETAIL.Image)
                .into(Profile_pic);


        if (Account.LOGGED_IN_USER_DETAIL.Email.equals(Constants.GUEST_EMAIL))
        {
            Reset_Email.setVisibility(View.INVISIBLE);
            Reset_Email.setEnabled(false);
        }
        else {

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
        }


        return view;
    }


}
