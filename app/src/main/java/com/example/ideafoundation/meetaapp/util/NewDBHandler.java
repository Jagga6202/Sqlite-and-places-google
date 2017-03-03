package com.example.ideafoundation.meetaapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.ideafoundation.meetaapp.model.Contact;
import com.example.ideafoundation.meetaapp.model.User;

import java.util.ArrayList;

/**
 * Created by ideafoundation on 09/02/17.
 */

public class NewDBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "mapdata";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LATLAN = "latlan";
    private static final String KEY_FIRST= "firstword";

    private static final String KEY_ID1 = "id";
    private static final String KEY_USERNAME1 = "username";
    private static final String KEY_USEREMAIL1 = "useremail";

    private static final String TABLE_CONTACTS1 = "userlogin";
    Context context;

    public NewDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " VARCHAR(1000) NOT NULL unique,"
                + KEY_LATLAN + " VARCHAR(1000) NOT NULL unique,"
                + KEY_FIRST + " VARCHAR(1000) NOT NULL);";

        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        //checking if data already exist before entering in database
        Cursor cursor = null;
        String sql ="SELECT "+ KEY_NAME + " FROM "+ TABLE_CONTACTS + " WHERE " +KEY_NAME +" = '"+contact.getName() + "'";
        cursor= db.rawQuery(sql,null);
        Log.e("Cursor Count : "," " + cursor.getCount());

        if(cursor.getCount()>0){
            Toast.makeText(context,"Place already added to favourite",Toast.LENGTH_SHORT).show();
        }else{
            //PID Not Found
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_LATLAN, contact.getLatLng().toString());// Contact Name
            values.put(KEY_FIRST, contact.getFirstWord()); // Contact Phone
            // Inserting Row
            db.insert(TABLE_CONTACTS, null, values);
            Toast.makeText(context,"Place added to favourite",Toast.LENGTH_SHORT).show();
            db.close();
        }
        // Closing database connection
    }

    // Getting single contact
    public  Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setLatLng(cursor.getString(2));
                contact.setFirstWord(cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_LATLAN, contact.getLatLng().toString());// Contact Name
        values.put(KEY_FIRST, contact.getFirstWord()); // Contact Phone
        // values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public void deleteall()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,null,null);
        db.close();
    }



}

