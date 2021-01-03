package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.AliasBuildingSearch;

import java.util.List;

@Dao
public interface AliasBuildingSearchDAO {

    @Query("SELECT * FROM AliasBatimentRecherche")
    List<AliasBuildingSearch> getAll();

    @Query("SELECT * FROM AliasBatimentRecherche WHERE idAliasRecherche =:idAlias")
    AliasBuildingSearch getAliasSearchByIdAlias(int idAlias);

    @Query("SELECT * FROM AliasBatimentRecherche WHERE idBatiment =:idBuilding")
    List<AliasBuildingSearch> getAllAliasSearchByIdBuilding(int idBuilding);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAliasBuildingSearch(AliasBuildingSearch aT);

    @Delete
    void delete(AliasBuildingSearch aT);
}
