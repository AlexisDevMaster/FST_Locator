package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface DeleteDatabaseDAO {

    @Query("DELETE FROM ")
    public void nukeTable();

}
