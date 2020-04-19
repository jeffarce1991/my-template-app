package com.jeff.project420.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jeff.project420.database.local.Photo;
import com.jeff.project420.database.room.dao.PhotoDao;

import kotlin.jvm.Synchronized;

@Database(entities = Photo.class, version = 2)
public abstract class PhotoDatabase extends RoomDatabase {

    private static final String DB_NAME = "photo_db";
    private static PhotoDatabase instance = null;

    @Synchronized
    public static synchronized PhotoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PhotoDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract PhotoDao photoDao();
}
