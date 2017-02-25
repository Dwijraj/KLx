package com.kiit.klx.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.Fragments.Display_Details_Change;
import com.kiit.klx.R;

public class HomeActivity extends AppCompatActivity {

    private EditText EMAIL_ADDRESS_INPUT;
    private EditText PASSWORD_INPUT;
    private String Email_entered;
    private String Password_entered;
    private TextView RESET_PASSWORD;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private Button Proceed;
    private TextView LOGIN_AS_GUEST;
    private DatabaseReference USER_REF;
    private LinearLayout ROOT_LAYOUT;
    private View ROOT_VIEW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);





        progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth=FirebaseAuth.getInstance();
        LOGIN_AS_GUEST=(TextView) findViewById(R.id.GUEST_LOGIN);
        ROOT_LAYOUT=(LinearLayout) findViewById(R.id.activity_home);
        RESET_PASSWORD=(TextView) findViewById(R.id.RESETMAIL);
        EMAIL_ADDRESS_INPUT=(EditText) findViewById(R.id.LOGIN_EMAIL);
        PASSWORD_INPUT=(EditText) findViewById(R.id.LOGIN_PASSWORD);
        Proceed=(Button) findViewById(R.id.LOGIN_BUTTON);
        USER_REF= FirebaseDatabase.getInstance().getReference();
        ROOT_VIEW=findViewById(R.id.activity_home);


            if(mAuth.getCurrentUser()!=null)
        {
            Intent Account=new Intent(HomeActivity.this, com.kiit.klx.Activities.Account.class);
           // Intent Account=new Intent(HomeActivity.this, Display_Details_Change.class);
            finish();
            startActivity(Account);
        }
        RESET_PASSWORD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (!TextUtils.isEmpty(EMAIL_ADDRESS_INPUT.getText().toString().trim()))
              {
                  mAuth.sendPasswordResetEmail(EMAIL_ADDRESS_INPUT.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {

                          Snackbar.make(ROOT_LAYOUT,"Check mail for reset password",Snackbar.LENGTH_INDEFINITE).show();

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Snackbar.make(ROOT_LAYOUT,"Couldn't send reset password link",Snackbar.LENGTH_INDEFINITE).show();

                      }
                  });

              }
                else
              {
                  Snackbar.make(ROOT_LAYOUT,"Please enter email in email field",Snackbar.LENGTH_INDEFINITE).show();
              }

            }
        });

        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email_entered=EMAIL_ADDRESS_INPUT.getText().toString().trim();
                Password_entered=PASSWORD_INPUT.getText().toString().trim();



                if(!(TextUtils.isEmpty(Email_entered)||TextUtils.isEmpty(Password_entered)))
                {
                    progressDialog.setMessage("Hold on...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(Email_entered,Password_entered).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            progressDialog.dismiss();
                            Toast.makeText(HomeActivity.this,"Registered with us !",Toast.LENGTH_SHORT).show();

                            Intent DISPLAY_DETAILS=new Intent(HomeActivity.this,Display_Details_Change.class);
                            finish();
                            startActivity(DISPLAY_DETAILS);





                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            mAuth.signInWithEmailAndPassword(Email_entered,Password_entered).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {


                                    progressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(),"Signed in successfully",Toast.LENGTH_SHORT).show();

                                    Intent DISPLAY_DETAILS=new Intent(HomeActivity.this,Account.class);
                                    finish();
                                    startActivity(DISPLAY_DETAILS);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    progressDialog.dismiss();

                                    Log.v("Tag",e.getLocalizedMessage());
                                    if (e.getLocalizedMessage().contains("invalid")) {
                                        Snackbar.make(ROOT_VIEW, "Wrong Password", Snackbar.LENGTH_LONG)
                                                .show();
                                    }
                                        else
                                    {
                                        Snackbar.make(ROOT_VIEW, "Sorry couldn't procced at this moment please try latter", Snackbar.LENGTH_LONG)
                                                .show();


                                    }

                                }
                            });

                        }
                    });

                }
                else
                {


                    Snackbar.make(ROOT_VIEW, "Invalid Credentials", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

                }



        });
        LOGIN_AS_GUEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Hold on...");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(Constants.GUEST_EMAIL,Constants.GUEST_PASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        final DatabaseReference NewUser=USER_REF.child("Users").child(mAuth.getCurrentUser().getUid());
                        NewUser.child("DisplayName").setValue("GUEST");
                        NewUser.child("Image").setValue(Constants.GUEST_IMAGE_LINK);
                        NewUser.child("Email").setValue(Constants.GUEST_EMAIL);
                        NewUser.child("Uploads").setValue("0");
                        NewUser.child("Uid").setValue(mAuth.getCurrentUser().getUid());
                        NewUser.child("Bought").setValue("0").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                mAuth.signInWithEmailAndPassword(Constants.GUEST_EMAIL,Constants.GUEST_PASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {

                                        Intent Account=new Intent(HomeActivity.this, com.kiit.klx.Activities.Account.class);
                                        finish();
                                        startActivity(Account);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Snackbar.make(ROOT_LAYOUT,"Failed to sign you in",Snackbar.LENGTH_INDEFINITE).show();

                                    }
                                });
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mAuth.signInWithEmailAndPassword(Constants.GUEST_EMAIL,Constants.GUEST_PASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Intent Account=new Intent(HomeActivity.this, com.kiit.klx.Activities.Account.class);
                                finish();
                                startActivity(Account);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Snackbar.make(ROOT_LAYOUT,"Failed to sign you in",Snackbar.LENGTH_INDEFINITE).show();

                            }
                        });
                    }
                });


            }
        });



    }
}
