package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.TypeRoom;

import java.util.List;

@Dao
public interface TypeSalleDAO {

    /**
     * Récupère toutes les type de salle
     * @return
     */
    @Query("SELECT * FROM TypeSalle")
    List<TypeRoom> getAll();

    /**
     * Recupère le type de salle par id
     * @param idType
     * @return
     */
    @Query("SELECT * FROM TypeSalle WHERE idTypeSalle = :idType")
    TypeRoom loadTypeSalleById(int idType);

    /**
     * Recupère la liste d'idType par l'idsalle
     * @param idRoom
     * @return
     */
    @Query("SELECT idTypeSalle FROM TypeSalle WHERE idSalle = :idRoom")
    List<Integer> getIdTypeByIdRoom(int idRoom);

    /**
     * Recupère le type de salle par nom
     * @param nameType
     * @return
     */
    @Query("SELECT * FROM TypeSalle WHERE idTypeSalle = :nameType")
    TypeRoom loadTypeRoomByNameType(String nameType);

    /**
     * Insère un type de salle
     * @param aT
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTypeRoom(TypeRoom aT);

    /**
     * Supprime un type de salle
     * @param aT
     */
    @Delete
    void delete(TypeRoom aT);
}
