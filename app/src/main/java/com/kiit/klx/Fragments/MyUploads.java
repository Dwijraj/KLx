package com.kiit.klx.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiit.klx.Activities.Account;
import com.kiit.klx.Adapter.RecyclerViewAdapter;
import com.kiit.klx.Model.Items;
import com.kiit.klx.R;

import java.util.ArrayList;

public class MyUploads extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private DatabaseReference productlist;
    private ArrayList<Items> PRODUCTS=new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    private FirebaseAuth mauth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mauth=FirebaseAuth.getInstance();
        view= inflater.inflate(R.layout.fragment_my_uploads, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.CATEGORYSEARCHED_ID);
        productlist= FirebaseDatabase.getInstance().getReference().child("Uploads").child(mauth.getCurrentUser().getUid());
        MyBag.VISIBILITY_FLAG="2";

        productlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot DF:dataSnapshot.getChildren())
                {
                    Items Product=DF.getValue(Items.class);
                    PRODUCTS.add(Product);

                }
                recyclerViewAdapter=new RecyclerViewAdapter(PRODUCTS, Account.MainContext,mauth);
                recyclerView.setAdapter(recyclerViewAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
