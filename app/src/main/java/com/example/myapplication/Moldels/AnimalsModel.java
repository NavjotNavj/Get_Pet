package com.example.myapplication.Moldels;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class AnimalsModel {

    private String mName;
    private String gender;
    private String Breed;
    private String district;
    private String Photo1;
    private String Photo2;
    private String Photo3;
    private String Photo4;
    private String spinner;
    private String circleText;

    public AnimalsModel() {

    }

    public AnimalsModel(String mName, String gender, String breed, String district, String photo1, String photo2, String photo3, String photo4, String spinner, String circleText) {
        this.mName = mName;
        this.gender = gender;
        Breed = breed;
        this.district = district;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        Photo4 = photo4;
        this.spinner = spinner;
        this.circleText = circleText;

    }


    public String getmName() {
        return mName;
    }

    public String getGender() {
        return gender;
    }

    public String getBreed() {
        return Breed;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public String getPhoto4() {
        return Photo4;
    }

    public String getSpinner() {
        return spinner;
    }

    public String getCircleText() {
        return circleText;
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

    public void setPhoto1(String photo1) {
        Photo1 = photo1;
    }

    public void setPhoto2(String photo2) {
        Photo2 = photo2;
    }

    public void setPhoto3(String photo3) {
        Photo3 = photo3;
    }

    public void setPhoto4(String photo4) {
        Photo4 = photo4;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public void setCircleText(String circleText) {
        this.circleText = circleText;
    }
}
