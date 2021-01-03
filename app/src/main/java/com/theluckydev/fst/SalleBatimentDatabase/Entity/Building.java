package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Batiment")
public class Building extends Objet implements Serializable, SalleBatimentInterface{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="idBatiment", index = true)
    private int idBuilding;

    @ColumnInfo(name = "codeBatiment")
    private String codeBuilding;

    @ColumnInfo(name = "nomBatiment")
    private String nameBuilding;

    @ColumnInfo(name = "nomBatimentComplet")
    private String completeNameBuilding;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @Ignore
    private List<AliasBuildingSearch> aliasList;

    public Building(int idBuilding, String codeBuilding, String nameBuilding, String completeNameBuilding, String latitude, String longitude) {
        this.idBuilding = idBuilding;
        this.codeBuilding = codeBuilding;
        this.nameBuilding = nameBuilding;
        this.latitude = latitude;
        this.longitude = longitude;
        this.completeNameBuilding = completeNameBuilding;
        aliasList = new ArrayList<>();
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }

    public String getCodeBuilding() {
        return codeBuilding;
    }

    public void setCodeBuilding(String codeBuilding) {
        this.codeBuilding = codeBuilding;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public String getCompleteNameBuilding() {
        return completeNameBuilding;
    }

    public void setCompleteNameBuilding(String completeNameBuilding) {
        this.completeNameBuilding = completeNameBuilding;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<AliasBuildingSearch> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<AliasBuildingSearch> aliasList) {
        this.aliasList = aliasList;
        aliasList.add(new AliasBuildingSearch(-1, this.codeBuilding, -1));
        aliasList.add(new AliasBuildingSearch(-1, this.completeNameBuilding, -1));
    }

    @Override
    public String getStandardName() {
        return nameBuilding;
    }

    @Override
    public String getType(){return "building";}

    @Override
    public boolean hasForAlias(String textToFound){
        for(AliasBuildingSearch alias : aliasList){
            if(alias.getAlias().toLowerCase().contains(textToFound))
                return true;
        }
        return false;
    }}
