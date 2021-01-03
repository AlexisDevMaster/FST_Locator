package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;

import java.util.List;

@Dao
public interface ComplexeDAO {

    /**
     * Récupère toutes les salles avec toutes les contraintes
     * @return
     */
    @RawQuery
    List<Integer> getByParamDynamic(SupportSQLiteQuery query);


    /**
     * Récupère toutes les salles avec toutes les contraintes
     * @param idBatiment
     * @param Type
     * @param idLogiciels
     * @param idEquipement
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE idBatiment = :idBatiment " +
            "AND idType = :Type " +
            "AND idLogiciel IN (:idLogiciels)"+
            "AND idEquipement IN (:idEquipement)")
    List<Room> getByAllParam(int idBatiment, String Type, int[] idLogiciels, int[] idEquipement);

    /**
     * Ré"cupère les salles par Batiments
     * @param idBatiment
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idBatiment = :idBatiment ")
    List<Room> getByBatiment(int idBatiment);

    /**
     * Récupère les salles par Equipements
     * @param idEquipements
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel " +
            "NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idEquipement IN (:idEquipements)")
    List<Room> getByEquipement(int[] idEquipements);


    /**
     * Récupère les salles par Type de Salle
     * @param idType
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel " +
            "NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idType = :idType")
    List<Room> getByType(int idType);


    /**
     * Récupère les salles par Logiciels / OS
     * @param idLogicies
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idLogiciel = :idLogicies")
    List<Room> getByLogiciels(int[] idLogicies);

    /**
     * Récupère les salles par batiment / type salle
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idBatiment = :idBatiment " +
            "AND idType = :idType")
    List<Room> getByBatimentType(int[] idBatiment, int idType);


    /**
     * Récupère les salles par batiment / type salle
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idBatiment = :idBatiment " +
            "AND idLogiciel = :idLogiciels")
    List<Room> getByBatimentLogiciels(int[] idBatiment, int[] idLogiciels);



    /**
     * Récupère les salles par batiment / equipements
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idBatiment = :idBatiment " +
            "AND idLogiciel = :idEquipements")
    List<Room> getByBatimentEquipements(int[] idBatiment, int[] idEquipements);

    /**
     * Récupère les salles par batiment / type salle / OS - Logiciels
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idBatiment = :idBatiment " +
            "AND idType = :idType " +
            "AND idLogiciel IN (:idLogiciels)" +
            "")
    List<Room> getByBatimentTypeLogiciels(int[] idBatiment, int idType, int[] idLogiciels);

    /**
     * Récupère les salles par batiment / type salle / equipements
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE  idBatiment = :idBatiment " +
            "AND idType = :idType " +
            "AND idEquipement IN (:idEquipements)" +
            "")
    List<Room> getByBatimentTypeEquipements(int[] idBatiment, int idType, int[] idEquipements);


    /**
     * Récupère les salles sans conditions
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement")
    List<Room> getAll();

    /**
     * Récupère les salles par type de salle / equipement
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE idType = :idType " +
            "AND idEquipement IN (:idEquipements)" +
            "")
    List<Room> getByTypeEquipement(int idType, int[] idEquipements);

    /**
     * Récupère les salles par type de salle / Logiciels
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE idType = :idType " +
            "AND idLogiciel IN (:idLogiciels)" +
            "")
    List<Room> getByTypeLogiciel(int idType, int[] idLogiciels);


    /**
     * Récupère les salles par type de salle / Logiciels / equipements
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE idType = :idType " +
            "AND idLogiciel IN (:idLogiciels) " +
            "AND idEquipement IN (:idEquipements)" +
            "")
    List<Room> getByTypeLogicielEquipement(int idType, int[] idLogiciels, int[] idEquipements);

    /**
     * Récupère les salles par type de salle / Logiciels
     * @param
     * @return
     */
    @Query("SELECT * FROM Salle NATURAL JOIN Batiment " +
            "NATURAL JOIN Equipement " +
            "NATURAL JOIN Type "+
            "NATURAL JOIN Logiciel" +
            " NATURAL JOIN SalleLogiciel " +
            "NATURAL JOIN SalleEquipement " +
            "WHERE idLogiciel IN (:idLogiciels) " +
            "AND idEquipement IN (:idEquipements)" +
            "")
    List<Room> getByLogicielEquipement(int[] idLogiciels, int[] idEquipements);


}
