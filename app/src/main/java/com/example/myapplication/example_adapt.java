package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.List;


public class example_adapt extends RecyclerView.Adapter<example_adapt.ExampleViewHolder> {



    private Context context;
    private List<Animals> mAnimals;

    public example_adapt(Context context, List<Animals> mAnimals) {
        this.context = context;
        this.mAnimals = mAnimals;
    }

    //1
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        //4
        public ImageView imageView;
        public TextView name, breed,gender,district,Dogname;
        public LinearLayout linearLayout;
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




        }
    }

    //6.1



    //3
    @NonNull
    @Override
    public example_adapt.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //5
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_animal,viewGroup,false);
        return new ExampleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder,final int i) {

        final Animals uploadCurrent = mAnimals.get(i);
        //7
        Picasso.get()
                .load(uploadCurrent.getPhoto())
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(exampleViewHolder.imageView);

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
                context.startActivity(intent);
            }
        });







    }

    @Override
    public int getItemCount() {
        return mAnimals.size();
    }


}
