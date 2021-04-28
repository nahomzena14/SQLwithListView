package com.example.listviewwithsql.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.listviewwithsql.util.Position;

public class User implements Parcelable {

    private String name;
    private int id;
    private Position position;

    public User(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public User(String name, int id, Position position) {
        this.name = name;
        this.id = id;
        this.position = position;
    }

    protected User(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Name: "+ name+"\nID: "+id+"\nPosition = "+ position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeString(position.name());
    }
}
