package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.exampleAdapter;
import com.example.myapplication.Moldels.AnimalsModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HappyCart extends AppCompatActivity {


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

            Intent intent = new Intent(HappyCart.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            return true;
        }
        //to exit if user clicks back button on toolbar
        if(id == android.R.id.home){
            Intent intent = new Intent(this,listing_animals.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Happy Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("Bitmap") &&getIntent().hasExtra("name")&&getIntent().hasExtra("gender")&&getIntent().hasExtra("breed")&&getIntent().hasExtra("district") ){
            Bitmap bitmap = getIntent().getParcelableExtra("Bitmap");
            String mName = getIntent().getStringExtra("name");
            String gender = getIntent().getStringExtra("gender");
            String breed = getIntent().getStringExtra("breed");
            String district = getIntent().getStringExtra("district");

            setContents( bitmap, mName, gender , breed, district);
        }
    }

    //setting that data
    private void  setContents(Bitmap bit , String mName , String breed , String gender, String district ){

        TextView name  = findViewById(R.id.name1);
        name.setText(mName);

        TextView mgender  = findViewById(R.id.gender1);
        mgender.setText(gender);

        TextView Breed = findViewById(R.id.breed1);
        Breed.setText(breed);

        TextView mdistrict = findViewById(R.id.district1);
        mdistrict.setText(district);

        ImageView image = findViewById(R.id.imageView1);
        image.setImageBitmap(bit);




}

    public void lastActivity(View view) {
        Intent intent = new Intent(this,Last_Activity.class);
        startActivity(intent);
    }
}
