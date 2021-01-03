package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.SurfacePoint;

import java.util.List;

@Dao
public interface SurfacePointDAO {

    /**
     * Récupère tius les points de la table
     * @return
     */
    @Query("SELECT * FROM SurfacePoint")
    List<SurfacePoint> getAll();

    /**
     * Récupère
     * @param idBatiment
     * @return
     */
    @Query("SELECT * FROM SurfacePoint where  idBatiment = :idBatiment")
    List<SurfacePoint> getSurfacePoint(int idBatiment);


    @Query("SELECT * FROM SurfacePoint WHERE idBatiment IN (:idBatiment)")
    List<SurfacePoint> loadAllByIds(int[] idBatiment);

    /*@Query("SELECT * FROM pays WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Pays findByName(String first, String last);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSurfacePoint(SurfacePoint aT);

    @Delete
    void delete(SurfacePoint aT);
}
