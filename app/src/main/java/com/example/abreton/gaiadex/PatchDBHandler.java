/**
 * Created by Owner on 6/10/2016.
 */
package com.example.abreton.gaiadex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Owner on 3/8/2016.
 */
//SQLite Database for signs created and existing within HearMe
public class PatchDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patchDB.db";

    //creating SQL variables from java
    private static final String TABLE_PATCHES = "Patches";
    private static final String COLUMN_PATCHID = "patch_id";
    private static final String COLUMN_PATCHBANK = "patch_bank";
    private static final String COLUMN_PATCHNUM = "patch_num";
    private static final String COLUMN_PATCHTITLE = "patch_title";
    private static final String COLUMN_PATCHCATEGORY = "patch_category";

    private String bank;
    private int num;


    public PatchDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //SQL command for the signs table
        String CREATE_PATCH_TABLE = "CREATE TABLE "+
                TABLE_PATCHES + "(" +
                COLUMN_PATCHID + " INTEGER PRIMARY KEY, "+
                COLUMN_PATCHBANK + " TEXT, " +
                COLUMN_PATCHNUM + " TEXT, " +
                COLUMN_PATCHTITLE + " TEXT, " +
                COLUMN_PATCHCATEGORY + " TEXT)";

        //execute the SQL command. no return value
        db.execSQL(CREATE_PATCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // exception handling if upgrade doesn't work
        try {
            //drop table if it exists
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATCHES);

            //recreate by calling onCreate
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean searchPatchTitle(String title){
        SQLiteDatabase db = this.getWritableDatabase();

        String mysqlQuery = "SELECT * FROM " + TABLE_PATCHES +
                " WHERE " + COLUMN_PATCHTITLE + " = \'" + title.replace("'","''") +"\'";

        Cursor cursor = db.rawQuery(mysqlQuery, null);

        if(!cursor.moveToFirst()){
            return false;
        }

        return true;
    }

    public void deleteRows(){
        SQLiteDatabase db = this.getWritableDatabase();

        onUpgrade(db, 0, 1);
    }

    public void addEmptyPatches(){
        SQLiteDatabase db = this.getWritableDatabase();
        bank = "A";

        for(int i = 1; i <= 64; i++){
            num = i % 8;

            ContentValues values = new ContentValues();
            values.put(COLUMN_PATCHBANK, bank);

            if(num == 0) {
                num = 8;
                if(bank == "A"){
                    bank = "B";
                } else if (bank == "B"){
                    bank = "C";
                } else if (bank == "C"){
                    bank = "D";
                } else if (bank == "D"){
                    bank = "E";
                } else if (bank == "E"){
                    bank = "F";
                } else if (bank == "F"){
                    bank = "G";
                } else if (bank == "G"){
                    bank = "H";
                }
            }
            values.put(COLUMN_PATCHID, i);
            values.put(COLUMN_PATCHNUM, num);
            values.put(COLUMN_PATCHTITLE, "<patch>"+i);
            values.put(COLUMN_PATCHCATEGORY, "<category>");

            db.insert(TABLE_PATCHES, null, values);
        }

        db.close();
    }

    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PATCHES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public String getPatchItem(String item, String title, String category) {
        SQLiteDatabase db = this.getWritableDatabase();

        String mysqlQuery = "SELECT * FROM " + TABLE_PATCHES +
                " WHERE " + COLUMN_PATCHTITLE + " = \'" + title.replace("'","''") +"\' AND " +
                COLUMN_PATCHCATEGORY + " = \'" + category.replace("'", "''") + "\'";

        Cursor cursor = db.rawQuery(mysqlQuery, null);

        if(cursor.moveToFirst()){
            switch(item){
                case "bank":
                    item = cursor.getString(cursor.getColumnIndex(COLUMN_PATCHBANK));

                case "num":
                    item = cursor.getString(cursor.getColumnIndex(COLUMN_PATCHNUM));

                case "id":
                    item = cursor.getString(cursor.getColumnIndex(COLUMN_PATCHID));

                default:
                    System.out.println("Error occurred in switch statement");
            }
        }

        return item;
    }


    public String getPatchTitle(String bank, int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        String title = "null";

        String mysqlQuery = "SELECT * FROM " + TABLE_PATCHES +
                " WHERE " + COLUMN_PATCHBANK + " = \'" + bank.replace("'","''") +"\' AND " +
                COLUMN_PATCHNUM + " = " + pos;

        Cursor cursor = db.rawQuery(mysqlQuery, null);

        if(cursor.moveToFirst()){
            title = cursor.getString(cursor.getColumnIndex(COLUMN_PATCHTITLE));
        }

        return title;
    }

    public String getPatchCategory(String bank, int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        String category = "null";

        String mysqlQuery = "SELECT * FROM " + TABLE_PATCHES +
                " WHERE " + COLUMN_PATCHBANK + " = \'" + bank.replace("'","''") +"\' AND " +
                COLUMN_PATCHNUM + " = " + pos;

        Cursor cursor = db.rawQuery(mysqlQuery, null);

        try {
            if(cursor.moveToFirst()){
                category = cursor.getString(cursor.getColumnIndex(COLUMN_PATCHCATEGORY));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return category;
    }

    public ArrayList<Patch> getPatchList(String bank) {
        ArrayList<Patch> Patches = new ArrayList<Patch>();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String mysqlQuery = "SELECT * FROM " + TABLE_PATCHES +
                " WHERE " + COLUMN_PATCHBANK + " = \'" + bank.replace("'","''") +"\'";

        Cursor cursor = db.rawQuery(mysqlQuery, null);

        if (cursor.moveToFirst()) {
            int position = 1;
            do {
                Patch patch = new Patch();

                patch.setPatchID(cursor.getInt(cursor.getColumnIndex(COLUMN_PATCHID)));
                patch.setPatchBank(bank);
                patch.setPatchNum(position);
                patch.setPatchTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PATCHTITLE)));
                patch.setPatchCategory(cursor.getString(cursor.getColumnIndex(COLUMN_PATCHCATEGORY)));

                Patches.add(patch);
                position++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Patches;
    }

    public void addPatch(Patch customPatch){
        //getting values for particular sign
        ContentValues values = new ContentValues();

        values.put(COLUMN_PATCHID, customPatch.getPatchID());
        values.put(COLUMN_PATCHBANK, customPatch.getPatchBank());
        values.put(COLUMN_PATCHNUM, customPatch.getPatchNum());
        values.put(COLUMN_PATCHTITLE, customPatch.getPatchTitle());
        values.put(COLUMN_PATCHCATEGORY, customPatch.getPatchCategory());

        //exception handling to open the DB and add sign to DB
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //insert values into the signDB Table 'TABLES_SIGNS'
            db.insert(TABLE_PATCHES, null, values);

            //close the DB
            db.close();
        } catch (Exception e) {
            System.out.println("Could not open database");
        }
    }

    public void editPatch(Patch patch) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PATCHBANK, patch.getPatchBank());
        values.put(COLUMN_PATCHNUM, patch.getPatchNum());
        values.put(COLUMN_PATCHTITLE, patch.getPatchTitle());
        values.put(COLUMN_PATCHCATEGORY, patch.getPatchCategory());

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_PATCHES, values, COLUMN_PATCHID + "= ?", new String[]{String.valueOf(patch.getPatchID())});
        db.close(); // Closing database connection
    }


    public void clearPatch(Patch patch) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PATCHTITLE, "<patch>"+patch.getPatchID());
        values.put(COLUMN_PATCHCATEGORY, "<category>");

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_PATCHES, values, COLUMN_PATCHID + "= ?", new String[] { String.valueOf(patch.getPatchID())});
        db.close(); // Closing database connection
    }
}

