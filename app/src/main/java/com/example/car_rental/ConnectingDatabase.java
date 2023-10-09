package com.example.car_rental;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ConnectingDatabase extends SQLiteOpenHelper {


    public static String DB_PATH, DB_TEST;

    public static String DB_NAME;
    public SQLiteDatabase database;
    public final Context context;
    private static final int DATABASE_VERSION = 13;

    public SQLiteDatabase getDb() {
        return database;
    }

    public ConnectingDatabase(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
        this.context = context;

        String packageName = context.getPackageName();
        System.out.println("package : " + context.getPackageName());
        DB_TEST = String.format("//data//data//%s//databases//", packageName);
        DB_NAME = databaseName;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        Log.e("TEST DB", DB_TEST);
        Log.e("DB PATH", DB_PATH);

        openDataBase();
    }


    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database!");
            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }

        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    private void copyDataBase() throws IOException {

        InputStream externalDbStream = context.getAssets().open(DB_NAME);


        String outFileName = DB_PATH;


        OutputStream localDbStream = new FileOutputStream(outFileName);


        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }

        localDbStream.close();
        externalDbStream.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:

                    context.deleteDatabase(DB_NAME);
                    createDataBase();
                    break;
            }
        }
    }


}