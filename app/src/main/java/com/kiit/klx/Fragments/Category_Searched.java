package com.kiit.klx.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kiit.klx.Activities.Account;
import com.kiit.klx.Adapter.RecyclerViewAdapter;
import com.kiit.klx.Constants.Constants;
import com.kiit.klx.Model.Items;
import com.kiit.klx.R;

import java.util.ArrayList;


public class Category_Searched extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference productlist;
    private ArrayList<Items> PRODUCTS=new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category__searched, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.CATEGORYSEARCHED_RECYCLERVIEW_ID);
        recyclerView.setLayoutManager(new LinearLayoutManager(Account.MainContext));
        productlist= FirebaseDatabase.getInstance().getReference().child("Category").child(Account.CATEGORY);
        PRODUCTS.clear();

            productlist.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot DF:dataSnapshot.getChildren())
                    {
                        Items Product=DF.getValue(Items.class);
                        PRODUCTS.add(Product);

                    }
                    recyclerViewAdapter=new RecyclerViewAdapter(PRODUCTS,Account.MainContext);
                    recyclerView.setAdapter(recyclerViewAdapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





        return view;
    }


}
