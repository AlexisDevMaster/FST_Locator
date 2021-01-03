package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Software;

import java.util.List;

@Dao
public interface SoftwareDAO {

    @Query("SELECT * FROM Logiciel")
    List<Software> getAll();

    @Query("SELECT * FROM Logiciel where  idLogiciel = :idLogiciel")
    Software getSoftware(int idLogiciel);

    @Query("SELECT Count(*) FROM Logiciel")
    int loadAllTable();

    @Query("SELECT * FROM Logiciel WHERE idLogiciel IN (:logicielId)")
    List<Software> loadAllByIds(int[] logicielId);

    /*@Query("SELECT * FROM pays WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Pays findByName(String first, String last);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertLogiciel(Software aT);

    @Delete
    void delete(Software aT);
}
