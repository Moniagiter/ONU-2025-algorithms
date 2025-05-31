package com.example.currencyexchange;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Rate.class}, version = 1, exportSchema = false)
public abstract class RateDatabase extends RoomDatabase {

    private static final String DB_NAME = "rate_database.db";
    private static RateDatabase INSTANCE;

    public abstract RateDao rateDao();

    public static synchronized RateDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RateDatabase.class,
                            DB_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
