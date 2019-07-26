package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class profile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("image_url") &&getIntent().hasExtra("name")&&getIntent().hasExtra("gender")&&getIntent().hasExtra("breed")&&getIntent().hasExtra("district") ){
            String image = getIntent().getStringExtra("image_url");
            String mName = getIntent().getStringExtra("name");
            String gender = getIntent().getStringExtra("gender");
            String breed = getIntent().getStringExtra("breed");
            String district = getIntent().getStringExtra("district");

            setContents(image, mName, gender , breed, district );

        }


    }

    private void  setContents(String imageUrl , String mName , String breed , String gender, String district){


        TextView name  = findViewById(R.id.nametv);
        name.setText(mName);

        TextView mgender  = findViewById(R.id.gendertv);
        mgender.setText(gender);

        TextView Breed = findViewById(R.id.breedtv);
        Breed.setText(breed);

        TextView mdistrict = findViewById(R.id.districttv);
        mdistrict.setText(district);


        ImageView image = findViewById(R.id.imagetv);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);




    }

    public void openListAnimals(View view){
        Intent intent  = new Intent(this, listing_animals.class);
        startActivity(intent);

    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this,MainActivity.class));

    }
}
