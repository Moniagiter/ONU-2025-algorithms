package com.example.currencyexchange;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRates(List<Rate> rates);

    @Query("SELECT * FROM rates")
    List<Rate> getAllRates();

    @Query("DELETE FROM rates")
    void deleteAll();
}
