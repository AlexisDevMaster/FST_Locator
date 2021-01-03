package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;

import java.util.List;

@Dao
public interface EquipmentDAO {

    @Query("SELECT * FROM Equipement")
    List<Equipment> getAll();

    @Query("SELECT * FROM Equipement where  idEquipement = :idEquipement")
    Equipment getEquipmentByIdEquipment(int idEquipement);

    @Query("SELECT Count(*) FROM Equipement")
    int loadAllTable();

    @Query("SELECT * FROM Equipement WHERE idEquipement IN (:equipementId)")
    List<Equipment> loadAllByIds(int[] equipementId);

    /*@Query("SELECT * FROM pays WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Pays findByName(String first, String last);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEquipment(Equipment aT);

    @Delete
    void delete(Equipment aT);
}
