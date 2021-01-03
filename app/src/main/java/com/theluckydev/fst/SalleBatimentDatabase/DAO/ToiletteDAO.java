package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Toilette;

import java.util.List;

@Dao
public interface ToiletteDAO {

    /**
     * Requete qui charge toutes les toilettes
     * @return
     */
    @Query("SELECT * FROM Toilette")
    List<Toilette> getAll();


    /**
     * Récupère une toilette par son id
     * @param idToilette
     * @return
     */
    @Query("SELECT * FROM Toilette where  idToilette = :idToilette")
    Toilette getToilette(int idToilette);


    /**
     * Compte les toilettes présentent dans la BDD
     * @return
     */
    @Query("SELECT Count(*) FROM Toilette")
    int loadAllTable();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertToilette(Toilette aT);

    @Delete
    void delete(Toilette aT);
}
