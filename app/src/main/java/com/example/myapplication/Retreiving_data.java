package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Moldels.AnimalsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Retreiving_data extends AppCompatActivity {
    //spinner
    Spinner sp1;

    //adapter for spinner
    ArrayAdapter<CharSequence> adapter;

    //floating button
    FloatingActionButton floatingActionButton;

    //layout
    ImageView img;
    EditText mName , mGenger , mBreed , mDidtrict;
    ProgressBar mProgressBar;
    Button upload;
    TextView t1;

    //uri
    private Uri mImageUri;

    //firebase reference
    private StorageReference mStorageReference;
    private DatabaseReference databaseAnimals;
    private StorageTask mUploadTask;

    //for selecting we use a code
    private  static final int IMAGE_PICK_CODE= 1;

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

            Intent intent = new Intent(Retreiving_data.this,MainActivity.class);
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
        setContentView(R.layout.activity_retreiving_data);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fill In The Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //firebase instance
        databaseAnimals = FirebaseDatabase.getInstance().getReference("uploads");
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");

        //layout
        t1 = findViewById(R.id.showUploads);
        floatingActionButton = findViewById(R.id.choose_fromGallery_btn);
        img =findViewById(R.id.galler_image);
        sp1 = findViewById(R.id.sp1);
        mProgressBar = findViewById(R.id.progressBar);
        mName =findViewById(R.id.name);
        mGenger = findViewById(R.id.gender);
        mBreed = findViewById(R.id.breed);
        mDidtrict = findViewById(R.id.district);
        upload = findViewById(R.id.Uploadbtn);

        //initialisation is finished, now we use adapter for spinner

        //spinner activity starts
        adapter = ArrayAdapter.createFromResource(this,R.array.species_name,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);

        //upload button is clicked
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(Retreiving_data.this,"upload in progress", Toast.LENGTH_SHORT).show();
                }
                addContents();
            }
        });

        //floating butto is clicked
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
    }

    //ON CREATE finishes here

    //getting file extention using uri
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    //for uploading contents to firebase
    private void addContents(){
        if(mImageUri != null){
            final StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

           mUploadTask =  fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Toast.makeText(Retreiving_data.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                    AnimalsModel animals = new AnimalsModel(mName.getText().toString().trim(),

                                            mGenger.getText().toString().trim(),
                                            mBreed.getText().toString().trim(),
                                            mDidtrict.getText().toString().trim(),
                                            uri.toString(),
                                            sp1.getSelectedItem().toString(),
                                            mBreed.getText().toString().substring(0,1));
                                    String uploadId = databaseAnimals.push().getKey();
                                    databaseAnimals.child(uploadId).setValue(animals);
                                }
                            });
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Retreiving_data.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Retreiving_data.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
        else {
            Toast.makeText(this,"No File Selected",Toast.LENGTH_SHORT).show();
        }
    }

    //picking image from gallery
    private void pickImageFromGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data!= null &&data.getData() !=null){
           mImageUri = data.getData();
            Glide.with(this)
                    .load(mImageUri)
                    .into(img);
        }
    }

    //intent to open listing animals activity
    public void openListing_animals(View view){
        Intent intent  = new Intent(this, listing_animals.class);
        startActivity(intent);
    }


}
