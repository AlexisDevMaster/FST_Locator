package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.AliasRoomSearch;

import java.util.List;

@Dao
public interface AliasRoomSearchDAO {

    @Query("SELECT * FROM AliasSalleRecherche")
    List<AliasRoomSearch> getAll();

    @Query("SELECT * FROM AliasSalleRecherche WHERE idAliasRecherche =:idAlias")
    AliasRoomSearch getAliasSearchByIdAlias(int idAlias);

    @Query("SELECT * FROM AliasSalleRecherche WHERE idSalle =:idRoom")
    List<AliasRoomSearch> getAllAliasSearchByIdRoom(int idRoom);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAliasSearch(AliasRoomSearch aT);

    @Delete
    void delete(AliasRoomSearch aT);
}
