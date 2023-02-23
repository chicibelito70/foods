package com.flintcore.food_recycler_list_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Food implements Serializable {
    public static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private byte[] image;

    public Food() {
    }

    public Food(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

//    protected Food(Parcel in) {
//        id = in.readInt();
//        name = in.readString();
//        in.readByteArray(image);
//    }
//
//    public static final Creator<Food> CREATOR = new Creator<Food>() {
//        @Override
//        public Food createFromParcel(Parcel in) {
//            return new Food(in);
//        }
//
//        @Override
//        public Food[] newArray(int size) {
//            return new Food[size];
//        }
//    };
//
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
//
//    @Override
//    public int describeContents() {
//        return Parcelable.CONTENTS_FILE_DESCRIPTOR;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(id);
//        parcel.writeString(name);
//        parcel.writeByteArray(image);
//    }
}
