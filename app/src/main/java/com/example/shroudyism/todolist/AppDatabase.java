package com.example.shroudyism.todolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG=AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME="todolist";
    private static AppDatabase sInstance=null;

    public static AppDatabase getsInstance(Context context){

        if(sInstance==null)
        {
            synchronized (LOCK){

                Log.d(LOG_TAG,"Creating new Database");
                sInstance= Room.databaseBuilder(context,
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .build();
            }

        }

return sInstance;
    }

public abstract TaskDao taskDao();
}
