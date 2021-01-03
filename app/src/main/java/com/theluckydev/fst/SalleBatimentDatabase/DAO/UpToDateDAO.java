package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.UpToDate;

@Dao
public interface UpToDateDAO {

    /**
     * Récupère toutes les dates de maj
     * @return
     */
    @Query("SELECT * FROM UpToDate")
    UpToDate getAll();

    /**
     * Insère une relation salle-equipement
     * @param aT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUpToDate(UpToDate aT);

    /**
     * Supprime une relation salle-equipement
     * @param aT
     */
    @Delete
    void delete(UpToDate aT);
}
