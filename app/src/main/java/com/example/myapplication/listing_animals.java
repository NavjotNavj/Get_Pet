package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.Adapter.exampleAdapter;
import com.example.myapplication.Moldels.AnimalsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listing_animals extends AppCompatActivity {
    //floating button
    FloatingActionButton f2;

    //used for on click linear layout
    LinearLayout l1;

    //firebase
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabaseRef;

    //Recycler View
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;

    //to fetch list in animals model class
    ArrayList<AnimalsModel> mAnimals;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();

            Intent intent = new Intent(listing_animals.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            return true;
        }
        //to exit if user clicks back button on toolbar
        if(id == android.R.id.home){

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_animals);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List Of Animals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser  = firebaseAuth.getCurrentUser();
        firebaseUser.getEmail();

        //button and layout
        f2 = findViewById(R.id.floatingActionButton);
        l1 = findViewById(R.id.linear);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.keepSynced(true);

        mAnimals = new ArrayList<>();

        //recycler view
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new exampleAdapter(listing_animals.this, mAnimals);
        mAdapter.notifyDataSetChanged();
        mAdapter.getItemCount();
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(listing_animals.this);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    AnimalsModel animals = postSnapshot.getValue(AnimalsModel.class);
                    mAnimals.add(animals);
                }

                //on data change we refresh the recycler view
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.refreshDrawableState();
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
