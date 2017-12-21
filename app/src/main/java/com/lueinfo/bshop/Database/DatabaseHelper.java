package com.lueinfo.bshop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lueinfo.bshop.Adapter.Details;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lue on 28-10-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static String CREATE_TABLE1;

    // Database Name
    public static final String DATABASE_NAME = "databaseCustomer.db";
public static File directory;
    // Contacts table name
   // public static final String TABLE_CONTACTS = "customers";
    public static final String TABLE_ADD_ITEM="additem";
    private static final String ITEM_NAME="Item_Name";
    private static final String ITEM_ID="Item_Id";
    private static final String ITEM_PRICE="Item_Price";
    private static final String ITEM_PRICE_Total="Item_Price_Sale";
    private static final String ITEM_IMAGE="Item_Image";
    private static final String ITEM_QUNTITY="Item_quntity";
    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String COLUMN_CARDID_NO= "CardId_No";
    public static final String COLUMN_CARD_NO= "Card_No";
    public static final String COLUMN_CARD_PRICE= "CardPrice";
    public static final String COLUMN_INTIME_IMAGE  = "in_image";
    public static final String COLUMN_OUTTIME_IMAGE  = "out_image";
    public static final String COLUMN_IN_TIME= "InTime";
    public static final String COLUMN_OUT_TIME= "OutTime";

    public static final String COLUMN_STATUS= "CardStatus";
    public static final String COLUMN_USERNAME="userName";
    public static ContentValues cValues;
    public SQLiteDatabase dataBase = null;
    public static Cursor cursor;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // context.getFilesDir();
       // final File dbfile=new File(context.getFilesDir().getParent()+"/databases/"+DATABASE_NAME);
       // Log.d("fejij"," "+dbfile.getAbsolutePath());
        copyDatabase(context,DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CREATE_TABLE1= " CREATE TABLE " + TABLE_ADD_ITEM + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY," + ITEM_ID + " VARCHAR," +ITEM_NAME + " VARCHAR," + ITEM_PRICE +" VARCHAR," + ITEM_PRICE_Total +" VARCHAR," + ITEM_QUNTITY +" VARCHAR," + ITEM_IMAGE + " VARCHAR )";
        db.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADD_ITEM);

        // Create tables again
        onCreate(db);
    }
    public void deleteRecord(Details contact) {
        dataBase = this.getWritableDatabase();
        dataBase.delete(TABLE_ADD_ITEM, KEY_ID + " = ?", new String[]{String.valueOf(contact.getKeyID())});
        dataBase.close();
    }


    public static void copyDatabase(Context c, String DATABASE_NAME) {
        String databasePath = c.getDatabasePath(DATABASE_NAME).getPath();
        File f = new File(databasePath);
        Log.d("vk",""+databasePath);
        OutputStream myOutput = null;
        InputStream myInput = null;
        Log.d("testing", " testing db path " + databasePath);
        Log.d("testing", " testing db exist " + f.exists());

        if (f.exists()) {
            try {

                 directory = new File("/mnt/sdcard/DB_DEBUG");
                if (!directory.exists())
                    directory.mkdir();

                myOutput = new FileOutputStream(directory.getAbsolutePath()
                        + "/" + DATABASE_NAME);
                myInput = new FileInputStream(databasePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
            } catch (Exception e) {
            } finally {
                try {
                    if (myOutput != null) {
                        myOutput.close();
                        myOutput = null;
                    }
                    if (myInput != null) {
                        myInput.close();
                        myInput = null;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void inserRecord(Details contact) {
        dataBase = getWritableDatabase();
        cValues = new ContentValues();
        cValues.put(ITEM_ID,contact.getItemId());
        cValues.put(ITEM_NAME, contact.getItemName()); // Contact Name
        cValues.put(ITEM_PRICE, contact.getItemPrice());
        cValues.put(ITEM_PRICE_Total,contact.getItemPriceTotal());
        cValues.put(ITEM_QUNTITY,contact.getItemQuntity());
        cValues.put(ITEM_IMAGE, contact.getitemimage()); // Contact Name
       Log.d("dbj","inserting");
        dataBase.insert(TABLE_ADD_ITEM, null, cValues);

        dataBase.close();
    }

    public Cursor selectRecords() {

        dataBase = getWritableDatabase();

//    Getting data from database table
        cursor = dataBase.rawQuery("select * from " + TABLE_ADD_ITEM, null);
        return cursor;
    }
    public ArrayList<Details> getContact() {
        dataBase = getWritableDatabase();

//    Getting data from database table
        cursor = dataBase.rawQuery("select * from " + TABLE_ADD_ITEM, null);

        ArrayList<Details> contact = new ArrayList<Details>();
        Details contact1;
        if (cursor.moveToFirst()) {

            do {
                contact1 = new Details();
                Log.d("djif","jciji");
                contact1.setKeyID(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                Log.d("@","@");
                contact1.setItemId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_ID)));
                contact1.setItemName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_NAME)));
                contact1.setItemPrice(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_PRICE)));
                contact1.setItemPriceTotal(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_PRICE_Total)));
                contact1.setItemQuntity(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_QUNTITY)));
                contact1.setitemimage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ITEM_IMAGE)));
                contact.add(contact1);


            } while (cursor.moveToNext());

        }
        cursor.close();
        return contact;
    }

    public int updateContact(Details contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ITEM_NAME, contact.getItemName());
        values.put(ITEM_PRICE, contact.getItemPrice());
        values.put(ITEM_QUNTITY,contact.getItemQuntity());

        values.put(ITEM_IMAGE, contact.getItemName());

       // values.put(COLUMN_STATUS,contact.getStatus());
        // updating row
        return db.update(TABLE_ADD_ITEM, values, ITEM_NAME + " = ?",
                new String[] {String.valueOf((contact.getItemName()))});

    }
    public boolean updateCartItem(Integer id, String item_name,
                                  String item_price, String itemPrice_total, String item_quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,id);
        contentValues.put(ITEM_NAME, item_name);
        contentValues.put(ITEM_PRICE, item_price);
        contentValues.put(ITEM_PRICE_Total,itemPrice_total);
        contentValues.put(ITEM_QUNTITY, item_quantity);

       db.update(TABLE_ADD_ITEM, contentValues, "id = ? ",
                new String[] { Integer.toString(id) });
        return true;

    }
    public String getTaskCount(String str){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM " + TABLE_ADD_ITEM + " WHERE " + COLUMN_CARDID_NO + "=?", new String[] { str });
        cursor.moveToFirst();
        String count= cursor.getString(0);
        Log.d("tag4"," "+count);
        cursor.close();

        return count;

    }
    public boolean deleteRow(int value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ADD_ITEM, KEY_ID + "=" + value, null) > 0;
    }
    public void Join_table(){
        dataBase = getWritableDatabase();

    }
    public void deleteAllRecords() {
        dataBase = this.getReadableDatabase();
        dataBase.delete(TABLE_ADD_ITEM, null, null);
        dataBase.close();
    }

}
