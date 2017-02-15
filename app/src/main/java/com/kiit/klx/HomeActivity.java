package com.kiit.klx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private EditText EMAIL_ADDRESS_INPUT;
    private EditText PASSWORD_INPUT;
    private String Email_entered;
    private String Password_entered;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private Button Proceed;
    private View ROOT_VIEW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        EMAIL_ADDRESS_INPUT=(EditText) findViewById(R.id.LOGIN_EMAIL);
        PASSWORD_INPUT=(EditText) findViewById(R.id.LOGIN_PASSWORD);
        Proceed=(Button) findViewById(R.id.LOGIN_BUTTON);
        ROOT_VIEW=findViewById(R.id.activity_home);


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

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    progressDialog.dismiss();

                                    Snackbar.make(ROOT_VIEW, "Sorry couldn't procced at this moment please try latter", Snackbar.LENGTH_LONG)
                                            .show();

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



    }
}
