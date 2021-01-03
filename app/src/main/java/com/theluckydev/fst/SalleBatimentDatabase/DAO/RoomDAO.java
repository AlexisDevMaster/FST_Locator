package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;

import java.util.List;
@Dao
public interface RoomDAO {

    /**
     * Requete qui charge toutes les salles
     * @return
     */
    @Query("SELECT * FROM Salle")
    List<Room> getAll();


    /**
     * Récupère une salle par son id
     * @param idSalle
     * @return
     */
    @Query("SELECT * FROM Salle where  idSalle = :idSalle")
    Room getSalle(int idSalle);


    /**
     * Compte les salles présent dans la BDD
     * @return
     */
    @Query("SELECT Count(*) FROM Salle")
    int loadAllTable();


    @Query("SELECT * FROM Equipement INNER JOIN SalleEquipement WHERE idSalle IN (:salleId)")
    List<Equipment> loadAllEquipementByIdSalle(int salleId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSalle(Room aT);

    @Delete
    void delete(Room aT);
}
