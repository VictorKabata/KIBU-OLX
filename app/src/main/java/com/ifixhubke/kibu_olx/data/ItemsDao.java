package com.ifixhubke.kibu_olx.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ifixhubke.kibu_olx.models.Products;

import java.util.List;

@Dao
public interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Products products);

    @Delete
    void delete(Products products);

    @Query("SELECT *FROM Products ORDER BY datePosted DESC")
    LiveData<List<Products>> getAllItems();

    @Query("UPDATE Products SET isSoldOut = :soldOut WHERE id = :id")
    void updateItemSoldOut(Boolean soldOut, int id);
}
