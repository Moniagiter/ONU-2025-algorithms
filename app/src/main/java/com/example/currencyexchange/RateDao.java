package com.example.currencyexchange;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRates(List<Rate> rates);

    @Query("DELETE FROM rates")
    void deleteAll();

    @Query("SELECT * FROM rates")
    LiveData<List<Rate>> getAllRatesLive();
}
