package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Salle",
        foreignKeys = {
        @ForeignKey(
                entity = Building.class,
                parentColumns = "idBatiment",
                childColumns = "idBatimentSalle",
                onDelete = CASCADE
        )})
public class Room extends Objet implements Serializable, SalleBatimentInterface {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="idSalle", index = true)
    private int idRoom;


    @ColumnInfo(name = "codeSalle")
    private String codeRoom;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "nomSalle")
    private String nameRoom;

    @ColumnInfo(name = "capaciteSalle")
    private int capacityRoom;

    @ColumnInfo(name = "etageSalle")
    private String levelRoom;

    @ColumnInfo(name = "idType")
    private int typeRoom;

    @ColumnInfo(name = "idBatimentSalle", index = true)
    private int idBuilding;

    @Ignore
    private List<AliasRoomSearch> aliasList;

    public Room(int idRoom, String codeRoom, String latitude, String longitude, String nameRoom, int capacityRoom, String levelRoom, int typeRoom, int idBuilding) {
        this.idRoom = idRoom;
        this.codeRoom = codeRoom;
        this.idBuilding = idBuilding;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nameRoom = nameRoom;
        this.capacityRoom = capacityRoom;
        this.levelRoom = levelRoom;
        this.typeRoom = typeRoom;
        aliasList = new ArrayList<>();
    }

    public int getIdRoom() {
        return idRoom;
    }

    public String getCodeRoom() {
        return codeRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
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

    public String getNameRoom() {
        return nameRoom;
    }

    public int getCapacityRoom() {
        return capacityRoom;
    }

    public String getLevelRoom() {
        return levelRoom;
    }

    public int getTypeRoom() { return typeRoom; }

    public void setCodeRoom(String codeRoom) {
        this.codeRoom = codeRoom;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public void setCapacityRoom(int capacityRoom) {
        this.capacityRoom = capacityRoom;
    }

    public void setLevelRoom(String levelRoom) {
        this.levelRoom = levelRoom;
    }

    public void setTypeRoom(int typeRoom) {
        this.typeRoom = typeRoom;
    }

    public List<AliasRoomSearch> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<AliasRoomSearch> aliasList) {
        this.aliasList = aliasList;
        aliasList.add(new AliasRoomSearch(-1, this.codeRoom, this.idRoom));
    }

    @Override
    public String getStandardName() {
        return nameRoom;
    }

    @Override
    public String getType(){return "salle";}

    @Override
    public String toString(){return codeRoom;}

    @Override
    public boolean hasForAlias(String textToFound){
        for(AliasRoomSearch alias : aliasList){
            if(alias.getAlias().toLowerCase().contains(textToFound))
                return true;
        }
        return false;
    }
}
