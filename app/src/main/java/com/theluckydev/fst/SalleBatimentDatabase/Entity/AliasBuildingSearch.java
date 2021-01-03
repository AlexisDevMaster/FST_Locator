package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "AliasBatimentRecherche",
        foreignKeys = {
                @ForeignKey(
                        entity = Building.class,
                        parentColumns = "idBatiment",
                        childColumns = "idBatiment",
                        onDelete = CASCADE
                )})
public class AliasBuildingSearch extends Objet implements Serializable, SalleBatimentInterface {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idAliasRecherche", index = true)
    private int idAliasSearch;

    @ColumnInfo(name = "alias")
    private String alias;

    @ColumnInfo(name = "idBatiment", index = true)
    private int idBuilding;

    public AliasBuildingSearch(int idAliasSearch, String alias, int idBuilding) {
        this.idAliasSearch = idAliasSearch;
        this.alias = alias;
        this.idBuilding = idBuilding;
    }

    public int getIdAliasSearch() {
        return idAliasSearch;
    }

    public void setIdAliasSearch(int idAliasSearch) {
        this.idAliasSearch = idAliasSearch;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }


    @Override
    public String getStandardName() {
        return this.alias;
    }

}