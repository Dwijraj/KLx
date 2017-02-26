package com.kiit.klx.Servicenbroadcastreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kiit.klx.Activities.Account;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.R;

import java.util.HashMap;

import static com.kiit.klx.Constants.Constants.UNIQUE_ID;

/**This service checks for change in application status of the logged in user if there is change in any of
 * his applied pass
 * this service sends a croadcast with a definite action which intern is responsible to firing
 * notifications
 */


public class MyService extends Service {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    public MyService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {       //Runs for the very first time the service runs
        super.onStart(intent, startId);



    }

    @Override
    public void onDestroy() {                               //Runs when service is destroyed
        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {  //Runs everytime the service starts




        mAuth = FirebaseAuth.getInstance();                 //Gets the firebase auth

        if(mAuth.getCurrentUser()!=null) {

            //It has the reference to the applications of particular user
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Uploads").child(mAuth.getCurrentUser().getUid());

            databaseReference.keepSynced(true);

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

//                   Comment newComment = dataSnapshot.getValue(Comment.class);
                    //Gets the key of what is changed

                    String commentKey = "Your Product has been viewed";




                    NotificationCompat.Builder notification;

                    notification= new NotificationCompat.Builder(Account.MainContext);
                    notification.setAutoCancel(true);


                    notification.setSmallIcon(R.mipmap.ic_add_shopping_cart_white_24dp);
                    notification.setTicker("Notification from KLX");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("KLX");
                    notification.setContentText(commentKey+"Status changed");

                    Intent intents=new  Intent(Account.MainContext,Account.class);
                    PendingIntent pendingIntent=PendingIntent.getActivity(Account.MainContext,0,intents,PendingIntent.FLAG_NO_CREATE);
                    notification.setContentIntent(pendingIntent);

                    NotificationManager nm=(NotificationManager) Account.MainContext.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(UNIQUE_ID,notification.build());




                    databaseReference.keepSynced(true);

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}

