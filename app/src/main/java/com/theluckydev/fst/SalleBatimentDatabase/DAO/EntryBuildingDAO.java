package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.EntreeSalle;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.EntryBuilding;

import java.util.List;

@Dao
public interface EntryBuildingDAO {

    @Query("SELECT * FROM EntreeBatiment")
    List<EntryBuilding> getAll();

    @Query("SELECT * FROM EntreeBatiment WHERE idEntreeBatiment =:idEntryBuilding")
    EntryBuilding getEntryBuildingById(int idEntryBuilding);

    @Query("SELECT * FROM EntreeBatiment WHERE idBatiment =:idBuilding")
    List<EntryBuilding> getAllEntryBuildingByIdBuilding(int idBuilding);

    @Query("SELECT idNode FROM EntreeBatiment WHERE idBatiment =:idBuilding")
    List<Integer> getAllIdNodeByIdRoom(int idBuilding);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntryBuilding(EntryBuilding aT);

    @Delete
    void delete(EntryBuilding aT);
}
