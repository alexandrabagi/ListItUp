package com.bignerdranch.android.listitup.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Room is a database layer on top of an SQLite database
 * Takes care of mundane tasks that you used to handle with an SQLiteOpenHelper
 * Uses the DAO to issue queries to its database
 * No queries on the main thread
 */

@Database(entities = {Item.class}, version = 2, exportSchema = false)
public abstract class ItemRoomDB extends RoomDatabase {

    public abstract ItemDAO itemDao();

    private static volatile ItemRoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // for running DB operations asynchronously in the background
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // returns a singleton
    public static ItemRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDB.class, "item_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
