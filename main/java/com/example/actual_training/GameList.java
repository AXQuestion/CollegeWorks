package com.example.actual_training;

import android.os.Parcel;
import android.os.Parcelable;

public class GameList implements Parcelable {
    String name;
    int imageId;

    public GameList(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    protected GameList(Parcel in) {
        name = in.readString();
        imageId = in.readInt();
    }

    public static final Creator<GameList> CREATOR = new Creator<GameList>() {
        @Override
        public GameList createFromParcel(Parcel in) {
            return new GameList(in);
        }

        @Override
        public GameList[] newArray(int size) {
            return new GameList[size];
        }
    };

    public int getImageId(){
        return imageId;
    }
    public String getName(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(imageId);
    }
}
