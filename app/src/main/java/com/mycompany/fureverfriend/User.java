package com.mycompany.fureverfriend;

/**
 * Created by Jordan on 2016-04-18.
 */
public class User {

    private int _id;
    private String _name;

    public User() {

    }

    public User(int id, String name) {
        this._id = id;
        this._name = name;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setName(String name) {
        this._name = name;
    }

}
