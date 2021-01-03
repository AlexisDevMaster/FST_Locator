package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Type;

import java.util.List;

@Dao
public interface TypeDAO {

    /**
     * Récupère toutes les type de salle
     * @return
     */
    @Query("SELECT * FROM Type")
    List<Type> getAll();

    /**
     * Recupère le type de salle par id
     * @param idType
     * @return
     */
    @Query("SELECT * FROM Type WHERE idType = :idType")
    Type loadTypeById(int idType);

    /**
     * Recupère le type de salle par nom
     * @param nomType
     * @return
     */
    @Query("SELECT * FROM Type WHERE idType = :nomType")
    Type loadTypeByNom(String nomType);

    /**
     * Insère un type de salle
     * @param aT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertType(Type aT);

    /**
     * Supprime un type de salle
     * @param aT
     */
    @Delete
    void delete(Type aT);
}
