package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.RoomEquipment;

import java.util.List;

@Dao
public interface RoomEquipmentDAO {

    /**
     * Récupère toutes les correspondance salle-equipement
     * @return
     */
    @Query("SELECT * FROM SalleEquipement")
    List<RoomEquipment> getAll();

    /**
     * Compte toutes les correspondance salle-equipement
     * @return
     */
    @Query("SELECT Count(*) FROM SalleEquipement")
    int loadAllTable();

    /**
     * Recupère toutes les association salle-equipement
     * @param salleId
     * @return
     */
    @Query("SELECT * FROM SalleEquipement WHERE idSalle IN (:salleId)")
    List<RoomEquipment> loadAllSalleEquipementByIdSalle(int salleId);

    /**
     * Recupère tous les idEquipement par idSalle
     * @param salleId
     * @return
     */
    @Query("SELECT idEquipement FROM SalleEquipement WHERE idSalle IN (:salleId)")
    List<Integer> getIdEquipementByIdSalle(int salleId);

    /**
     * Insère une relation salle-equipement
     * @param aT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSalleEquipement(RoomEquipment aT);

    /**
     * Supprime une relation salle-equipement
     * @param aT
     */
    @Delete
    void delete(RoomEquipment aT);
}
