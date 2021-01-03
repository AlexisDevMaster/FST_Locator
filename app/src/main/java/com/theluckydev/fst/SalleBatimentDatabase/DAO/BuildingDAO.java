package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Building;

import java.util.List;

@Dao
public interface BuildingDAO {

    @Query("SELECT * FROM Batiment")
    List<Building> getAll();

    @Query("SELECT * FROM Batiment where  idBatiment = :idBatiment")
    Building getBatiment(int idBatiment);


    @Query("SELECT * FROM Batiment WHERE idBatiment IN (:paysId)")
    List<Building> loadAllByIds(int[] paysId);

    /*@Query("SELECT * FROM pays WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Pays findByName(String first, String last);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBatiment(Building aT);

    @Delete
    void delete(Building aT);
}
