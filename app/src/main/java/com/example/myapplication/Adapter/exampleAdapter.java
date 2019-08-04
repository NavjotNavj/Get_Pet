package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Moldels.AnimalsModel;
import com.example.myapplication.R;
import com.example.myapplication.profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


public class exampleAdapter extends RecyclerView.Adapter<exampleAdapter.ExampleViewHolder> {



    private Context context;
    private ArrayList<AnimalsModel> mAnimals;

    public exampleAdapter(Context context, ArrayList<AnimalsModel> mAnimals) {
        this.context = context;
        this.mAnimals = mAnimals;
    }

    //1
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        //4
        public ImageView imageView;
        public TextView name, breed,gender,district,Dogname,textCircle;
        public CardView linearLayout;

        //2
        public ExampleViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            Dogname = itemView.findViewById(R.id.Dogname);
            name = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
            breed = itemView.findViewById(R.id.breed);
            district = itemView.findViewById(R.id.district);
            linearLayout= itemView.findViewById(R.id.linear);
            textCircle = itemView.findViewById(R.id.circleText);





        }
    }

    //6.1



    //3
    @NonNull
    @Override
    public exampleAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //5
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_animal,viewGroup,false);
        return new ExampleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder,final int i) {

        final AnimalsModel uploadCurrent = mAnimals.get(i);
        //7

        Picasso.get()
                .load(uploadCurrent.getPhoto())
                .into(exampleViewHolder.imageView);
        exampleViewHolder.textCircle.setText(uploadCurrent.getCircleText());
        exampleViewHolder.Dogname.setText(uploadCurrent.getSpinner());
        exampleViewHolder.name.setText(uploadCurrent.getmName());
        exampleViewHolder.gender.setText(uploadCurrent.getGender());
        exampleViewHolder.breed.setText(uploadCurrent.getBreed());
        exampleViewHolder.district.setText(uploadCurrent.getDistrict());




        exampleViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent (context, profile.class);
                intent.putExtra("image_url", uploadCurrent.getPhoto());
                intent.putExtra("name", uploadCurrent.getmName());
                intent.putExtra("gender", uploadCurrent.getGender());
                intent.putExtra("breed", uploadCurrent.getBreed());
                intent.putExtra("district", uploadCurrent.getDistrict());
                intent.putExtra("DogName",uploadCurrent.getSpinner());
                context.startActivity(intent);
            }
        });











    }
    @Override
    public int getItemCount() {
        return mAnimals.size();

    }


}
