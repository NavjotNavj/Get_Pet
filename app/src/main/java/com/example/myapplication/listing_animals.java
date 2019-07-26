package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listing_animals extends AppCompatActivity {
    FloatingActionButton  f2;
    LinearLayout l1;
    ImageView i2;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;



     RecyclerView recyclerView;
     RecyclerView.Adapter mAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Animals> mAnimals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_animals);
        f2 = findViewById(R.id.floatingActionButton);
        l1 = findViewById(R.id.linear);
        i2 = findViewById(R.id.logoutbtn);

         firebaseAuth = FirebaseAuth.getInstance();
         firebaseUser  = firebaseAuth.getCurrentUser();

        firebaseUser.getEmail();

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();

                Intent intent = new Intent(listing_animals.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mAnimals = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Animals animals = postSnapshot.getValue(Animals.class);
                    mAnimals.add(animals);

                    mAdapter = new example_adapt(listing_animals.this,mAnimals);
                    recyclerView.setAdapter(mAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(listing_animals.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });








    }


    public void retrieve(View view){
        Intent intent  = new Intent(this, Retreiving_data.class);
        startActivity(intent);
    }


    public void logout(View view){


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        listing_animals.super.onBackPressed();
                        finishAffinity();
                        System.exit(0);

                    }


                }).create().show();


    }
}
