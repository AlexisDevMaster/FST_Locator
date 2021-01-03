package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.EntreeSalle;

import java.util.List;

@Dao
public interface EntryRoomDAO {

    @Query("SELECT * FROM EntreeSalle")
    List<EntreeSalle> getAll();

    @Query("SELECT * FROM EntreeSalle WHERE idEntreeSalle =:idEntryRoom")
    EntreeSalle getEntryRoomById(int idEntryRoom);

    @Query("SELECT * FROM EntreeSalle WHERE idSalle =:idRoom")
    List<EntreeSalle> getAllEntryRoomByIdRoom(int idRoom);

    @Query("SELECT idNode FROM EntreeSalle WHERE idSalle =:idRoom")
    List<Integer> getAllIdNodeByIdRoom(int idRoom);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntryRoom(EntreeSalle aT);

    @Delete
    void delete(EntreeSalle aT);
}
