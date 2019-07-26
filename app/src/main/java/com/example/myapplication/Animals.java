package com.example.myapplication;

public class Animals {

    private String mName;
    private String gender;
    private String Breed;
    private String district;
    private String Photo;
    private String spinner;

    public  Animals(){

    }

    public Animals(String mName, String gender, String breed, String district, String photo, String spinner) {
        this.mName = mName;
        this.gender = gender;
        Breed = breed;
        this.district = district;
        Photo = photo;
        this.spinner = spinner;
    }



    public String getmName() {
        return mName;
    }

    public String getBreed() {
        return Breed;
    }

    public String getPhoto() {
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

    public void setPhoto(String photo) {
        this.Photo = photo;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }
}
