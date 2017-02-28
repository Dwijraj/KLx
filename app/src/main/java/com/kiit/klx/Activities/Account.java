package com.kiit.klx.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiit.klx.Fragments.Category_Searched;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.Fragments.Display_Details_Change;
import com.kiit.klx.Model.User;
import com.kiit.klx.R;
import com.kiit.klx.Fragments.Sell;
import com.kiit.klx.Fragments.userprofile;

import de.hdodenhof.circleimageview.CircleImageView;

public class Account extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View VIEW_OF_NAVIGATION_DRAWER_HEADER;
    private CircleImageView USER_IMAGE_NAVIGATION_DRAWER;
    protected DrawerLayout drawer;
    private TextView USER_NAME;
    private TextView USER_EMAIL;
    private DatabaseReference USER_DETAILS;
    private FirebaseAuth mAuth;
    public static User LOGGED_IN_USER_DETAIL;
    private ImageView imageView;
    private AlertDialog.Builder builders;
    public static LinearLayout MAIN_LAYOUT_ACCOUNT;
    public static String CATEGORY;
    public static Context MainContext;
    public static  DisplayMetrics displayMetrics;
    public static  android.support.v4.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels/3;
        Constants.SCREEN_HEIGHT = Constants.SCREEN_WIDTH;



        fragmentManager= getSupportFragmentManager();
        LOGGED_IN_USER_DETAIL=new User();
        LOGGED_IN_USER_DETAIL.Email= Constants.NOT_LOGGEDIN;
        MainContext=Account.this;
        imageView=(ImageView) findViewById(R.id.imageView2);
        MAIN_LAYOUT_ACCOUNT=(LinearLayout) findViewById(R.id.content_account);
        mAuth=FirebaseAuth.getInstance();
        USER_DETAILS=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        USER_DETAILS.keepSynced(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        imageView.setBackgroundColor(Color.WHITE);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.shopping).into(imageViewTarget);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem tools= menu.findItem(R.id.Buy);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);



        VIEW_OF_NAVIGATION_DRAWER_HEADER= navigationView.getHeaderView(0);
        USER_IMAGE_NAVIGATION_DRAWER=(CircleImageView) VIEW_OF_NAVIGATION_DRAWER_HEADER.findViewById(R.id.imageView);
        USER_NAME=(TextView) VIEW_OF_NAVIGATION_DRAWER_HEADER.findViewById(R.id.USER_NAME_NAV_HEADER);
        USER_EMAIL=(TextView) VIEW_OF_NAVIGATION_DRAWER_HEADER.findViewById(R.id.USER_EMAIL_NAV_HEADER);

        USER_DETAILS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                LOGGED_IN_USER_DETAIL=dataSnapshot.getValue(User.class);

                try {

                    USER_NAME.setText(LOGGED_IN_USER_DETAIL.DisplayName);
                    USER_NAME.setTextColor(Color.WHITE);
                    USER_EMAIL.setText(LOGGED_IN_USER_DETAIL.Email);
                    USER_EMAIL.setTextColor(Color.WHITE);

                    Glide.with(Account.this)
                            .load(LOGGED_IN_USER_DETAIL.Image)
                            .diskCacheStrategy( DiskCacheStrategy.ALL )
                            .into(USER_IMAGE_NAVIGATION_DRAWER);



                }catch (Exception ex)
                {

                    Intent FILL_DETAILS=new Intent(Account.this,Display_Details_Change.class);
                    finish();
                    startActivity(FILL_DETAILS);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Snackbar.make(MAIN_LAYOUT_ACCOUNT,Constants.CANT_CONNECT,Snackbar.LENGTH_INDEFINITE).show();

            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            builders=new AlertDialog.Builder(this);
            builders.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builders.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(mAuth.getCurrentUser().getEmail().equals(Constants.GUEST_EMAIL))
                    {
                        mAuth.signOut();
                    }
                    finish();


                }
            });
            builders.setTitle("Exit");
            builders.setMessage("Are you sure you want to exit app?");

           // AlertDialog dialogs=builders.create();
            final AlertDialog dialogs=builders.create();
            dialogs.show();
           // super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            mAuth.signOut();
            Intent NEW_SESSION=new Intent(Account.this,HomeActivity.class);
            finish();
            startActivity(NEW_SESSION);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



       if (id == R.id.nav_sell) {

           //fragmentManager.beginTransaction().replace(R.id.content_frame, new Sell()).commit();

           if(!(LOGGED_IN_USER_DETAIL.Email.equals(Constants.NOT_LOGGEDIN))) {
               if (!LOGGED_IN_USER_DETAIL.Email.equals(Constants.GUEST_EMAIL)) {
                   fragmentManager.beginTransaction().replace(R.id.content_frame, new Sell()).commit();



               }
               else
               {
                   Snackbar.make(MAIN_LAYOUT_ACCOUNT,Constants.GUEST_ON_TRY_SELL,Snackbar.LENGTH_INDEFINITE).show();
               }
           } else {
               Snackbar.make(MAIN_LAYOUT_ACCOUNT, Constants.CANT_CONNECT, Snackbar.LENGTH_INDEFINITE).show();

           }
       }
       else if (id == R.id.MyBag) {

           //fragmentManager.beginTransaction().replace(R.id.content_frame, new Sell()).commit();

           if(!(LOGGED_IN_USER_DETAIL.Email.equals(Constants.NOT_LOGGEDIN))) {
               if (!LOGGED_IN_USER_DETAIL.Email.equals(Constants.GUEST_EMAIL)) {
                   fragmentManager.beginTransaction().replace(R.id.content_frame, new Sell()).commit();



               }
               else
               {
                   Snackbar.make(MAIN_LAYOUT_ACCOUNT,Constants.GUEST_ON_TRY_SELL,Snackbar.LENGTH_INDEFINITE).show();
               }
           } else {
               Snackbar.make(MAIN_LAYOUT_ACCOUNT, Constants.CANT_CONNECT, Snackbar.LENGTH_INDEFINITE).show();

           }
       }
        else if (id == R.id.nav_profile) {

           // fragmentManager.beginTransaction().replace(R.id.content_frame, new userprofile()).commit();


            if(!(LOGGED_IN_USER_DETAIL.Email.equals(Constants.NOT_LOGGEDIN))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new userprofile()).commit();
            }
            else
            {
                Snackbar.make(MAIN_LAYOUT_ACCOUNT,"Can't connect at the moment",Snackbar.LENGTH_INDEFINITE).show();
            }



        }
       else
       {
            if (id == R.id.nav_mobile) {

                CATEGORY=Constants.CATEGORY_MOBILE;

            } else if (id==R.id.nav_electronics)
            {
                 CATEGORY=Constants.CATEGORY_ELECTRONICS;
            }
            else if (id == R.id.nav_books) {

                CATEGORY=Constants.CATEGORY_BOOK;

            } else if (id == R.id.nav_vehicle) {


                 CATEGORY=Constants.CATEGORY_VEHICLE;
            }


           fragmentManager.beginTransaction().replace(R.id.content_frame, new Category_Searched()).commit();
       }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
