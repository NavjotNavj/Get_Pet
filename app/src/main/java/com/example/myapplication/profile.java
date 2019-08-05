package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.SlidingViewAdapter;
import com.example.myapplication.Moldels.AnimalsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class profile extends AppCompatActivity  {

    //button
    private Button Adoptbtn;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.ic_launcher_background,R.drawable.met_ic_clear,R.drawable.ic_launcher_background,R.drawable.met_ic_clear};
    private ArrayList<String> ImagesArray = new ArrayList<>();  //TODO:

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

            Intent intent = new Intent(profile.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

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
        setContentView(R.layout.activity_profile);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getIncomingIntent();
        setPager();
        init();

    }

    private void setPager() {


        // Pager listener over indicator

    }


    private void init() {
        Adoptbtn=findViewById(R.id.Adoptbtn);
    }

    //getting data from each card
    private void getIncomingIntent(){
        if(getIntent().hasExtra("image_url") &&getIntent().hasExtra("name")&&getIntent().hasExtra("gender")&&getIntent().hasExtra("breed")&&getIntent().hasExtra("district") ){

            ArrayList<String> image =(ArrayList<String>) getIntent().getSerializableExtra("image_url");
            String mName = getIntent().getStringExtra("name");
            String gender = getIntent().getStringExtra("gender");
            String breed = getIntent().getStringExtra("breed");
            String district = getIntent().getStringExtra("district");

            setContents(image, mName, gender , breed, district );
        }
    }

    //setting that data
    private void  setContents(ArrayList<String> imageUrl , String mName , String breed , String gender, String district){

        TextView name  = findViewById(R.id.nametv);
        name.setText(mName);

        TextView mgender  = findViewById(R.id.gendertv);
        mgender.setText(gender);

        TextView Breed = findViewById(R.id.breedtv);
        Breed.setText(breed);

        TextView mdistrict = findViewById(R.id.districttv);
        mdistrict.setText(district);

        for(int i=0;i<imageUrl.size();i++)
            ImagesArray.add(imageUrl.get(i));

        mPager = findViewById(R.id.pager);


        mPager.setAdapter(new SlidingViewAdapter(profile.this,ImagesArray));




        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
//        ImageView image = findViewById(R.id.pager);
//        Glide.with(this)
//                .asBitmap()
//                .load(imageUrl)
//                .into(image);

    }
    //on click events are done
    public void openHappyCart(View view) {

        TextView t1 = findViewById(R.id.nametv);
        String name = t1.getText().toString();

        TextView t2 = findViewById(R.id.breedtv);
        String breed = t2.getText().toString();

        TextView t3 = findViewById(R.id.gendertv);
        String gender = t3.getText().toString();

        TextView t4= findViewById(R.id.districttv);
        String district = t4.getText().toString();


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.imagetv);

        Intent intent = new Intent(this,HappyCart.class);
        intent.putExtra("name" , name);
        intent.putExtra("breed", breed);
        intent.putExtra("gender", gender);
        intent.putExtra("district", district);
        intent.putExtra("Bitmap",bitmap);
        startActivity(intent);


        Toast.makeText(this,"Added to your cart", Toast.LENGTH_SHORT).show();
    }




}
