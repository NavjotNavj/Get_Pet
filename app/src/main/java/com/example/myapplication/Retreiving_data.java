package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.squareup.picasso.Picasso;

public class Retreiving_data extends AppCompatActivity {
    Spinner sp1;
    ArrayAdapter<CharSequence> adapter;
    FloatingActionButton floatingActionButton;
    ImageView img;
    EditText mName , mGenger , mBreed , mDidtrict;
    ProgressBar mProgressBar;
    private Uri mImageUri;
    Button upload;
    TextView t1;

    private StorageReference mStorageReference;

    private DatabaseReference databaseAnimals;

    private StorageTask mUploadTask;

    private  static final int IMAGE_PICK_CODE= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreiving_data);
        t1 = findViewById(R.id.showUploads);

        databaseAnimals = FirebaseDatabase.getInstance().getReference("uploads");
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");

        floatingActionButton = findViewById(R.id.choose_fromGallery_btn);
        img =findViewById(R.id.galler_image);
        sp1 = findViewById(R.id.sp1);
        mProgressBar = findViewById(R.id.progressBar);
        mName =findViewById(R.id.name);
        mGenger = findViewById(R.id.gender);
        mBreed = findViewById(R.id.breed);
        mDidtrict = findViewById(R.id.district);
        upload = findViewById(R.id.Uploadbtn);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(Retreiving_data.this,"upload in progress", Toast.LENGTH_SHORT).show();
                }
                addContents();
            }
        });



        adapter = ArrayAdapter.createFromResource(this,R.array.species_name,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+ "isSelected" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void addContents(){

        if(mImageUri != null){
            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));

           mUploadTask =  fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Retreiving_data.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            Animals animals = new Animals(mName.getText().toString().trim(),
                                    mGenger.getText().toString().trim(),
                                    mBreed.getText().toString().trim(),
                                    mDidtrict.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),
                                    sp1.getSelectedItem().toString());
                            String uploadId  = databaseAnimals.push().getKey();
                            databaseAnimals.child(uploadId).setValue(animals);
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
            Picasso.get()
                    .load(mImageUri)
                    .into(img);
        }
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

    public void openListing_animals(View view){
        Intent intent  = new Intent(this, listing_animals.class);
        startActivity(intent);
    }


}
