package com.example.myapplication.Moldels;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class AnimalsModel {

    private String mName;
    private String gender;
    private String Breed;
    private String district;
    private ArrayList<AnimalImages> Photo;
    private String spinner;
    private String circleText;

    public AnimalsModel(String trim, String gender, String breed, String district, String s, String spinner, String substring){

    }
    public AnimalsModel(String mName, String gender, String breed, String district, ArrayList<AnimalImages> photo, String spinner, String circleText) {
        this.mName = mName;
        this.gender = gender;
        Breed = breed;
        this.district = district;
        Photo = photo;
        this.spinner = spinner;
        this.circleText = circleText;
    }

    public String getCircleText() {
        return circleText;
    }

    public String getmName() {
        return mName;
    }

    public String getBreed() {
        return Breed;
    }

    public ArrayList<AnimalImages> getPhoto() {
        return Photo;
    }

    public String getGender() {
        return gender;
    }

    public String getDistrict() {
        return district;
    }

    public String getSpinner() {
        return spinner;
    }


    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public void setDistrict(String district) {
        this.district = district;
    }



    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }}


