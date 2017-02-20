package com.kiit.klx;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 1405214 on 20-02-2017.
 */

public class KLx extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!FirebaseApp.getApps(this).isEmpty()) {

            //This line is writtern so that the data is stored locally and data in fetched only when there is a change if there are no changes
            //the data needn't be fetched from the net it is retrived from locally stored cache
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

}
