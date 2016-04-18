package com.mycompany.fureverfriend;

/**
 * Created by Jordan on 2016-04-18.
 */
public class Pet {

    private int _id;
    private String _number;

    public Pet() {

    }

    public Pet(String number) {
        this._number = number;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setNumber(String number) {
        this._number = number;
    }

    public String getNumber() {
        return this._number;
    }

}
