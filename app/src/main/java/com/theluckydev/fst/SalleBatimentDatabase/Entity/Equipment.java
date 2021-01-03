package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

@Entity(tableName = "Equipement")
public class Equipment extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEquipement", index = true)
    private int idEquipment;


    @ColumnInfo(name = "nomEquipement")
    private String nameEquipment;


    public Equipment(int idEquipment, String nameEquipment) {
        this.idEquipment = idEquipment;
        this.nameEquipment = nameEquipment;

    }

    @Ignore
    public Equipment(String nameEquipment) {
        this.nameEquipment = nameEquipment;
    }

    public int getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }

    public String getNameEquipment() {
        return nameEquipment;
    }

    public void setNameEquipment(String nameEquipment) {
        this.nameEquipment = nameEquipment;
    }
}
