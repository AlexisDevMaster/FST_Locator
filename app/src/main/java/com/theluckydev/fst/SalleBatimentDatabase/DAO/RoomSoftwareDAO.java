package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.RoomSoftware;

import java.util.List;

@Dao
public interface RoomSoftwareDAO {

    /**
     * Récupère toutes les correspondance salle-logiciel
     * @return
     */
    @Query("SELECT * FROM SalleLogiciel")
    List<RoomSoftware> getAll();

    /**
     * Compte toutes les correspondance salle-logiciel
     * @return
     */
    @Query("SELECT Count(*) FROM SalleLogiciel")
    int loadAllTable();

    /**
     * Recupère toutes les association salle-logiciel
     * @param salleId
     * @return
     */
    @Query("SELECT * FROM SalleLogiciel WHERE idSalle IN (:salleId)")
    List<RoomSoftware> loadAllRommSoftwareByIdRoom(int salleId);

    /**
     * Recupère tous les idLogiciel associé à idSalle
     * @param idRoom
     * @return
     */
    @Query("SELECT idLogiciel FROM SalleLogiciel WHERE idSalle IN (:idRoom)")
    List<Integer> getIdSoftwareByIdRoom(int idRoom);


    /**
     * Insère une relation salle-equipement
     * @param aT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRoomSoftware(RoomSoftware aT);

    /**
     * Supprime une relation salle-equipement
     * @param aT
     */
    @Delete
    void delete(RoomSoftware aT);
}
