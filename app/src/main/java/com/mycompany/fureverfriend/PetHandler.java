package com.mycompany.fureverfriend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jordan on 2016-04-18.
 */
public class PetHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "petDB.db";
    private static final String TABLE_PETS = "pets";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "_number";

    public PetHandler(Context context, String name,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PETS_TABLE = "CREATE TABLE " +
                TABLE_PETS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NUMBER + " TEXT," + ");";
        db.execSQL(CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        onCreate(db);
    }

    public void addPet(Pet pet) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, pet.getNumber());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PETS, null, values);
        db.close();
    }

    public Pet getAllPets() {
        String query = "SELECT number FROM " + TABLE_PETS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Pet pet = new Pet();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            pet.setID(Integer.parseInt(cursor.getString(0)));
            pet.setNumber(cursor.getString(1));
            cursor.close();
        } else {
            pet = null;
        }

        db.close();

        return pet;
    }

}
