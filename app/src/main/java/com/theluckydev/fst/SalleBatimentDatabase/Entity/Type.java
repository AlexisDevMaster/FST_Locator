package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Type")

public class Type extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idType;


    @ColumnInfo(name = "typeSalle")
    private String typeSalle;

    public Type(int idType, String typeSalle) {
        this.idType = idType;
        this.typeSalle = typeSalle;
    }


    public int getIdTypeSalle() {
        return idType;
    }

    public void setIdTypeSalle(int idTypeSalle) {
        this.idType = idTypeSalle;
    }

    public String getTypeSalle() {
        return typeSalle;
    }

    public void setTypeSalle(String typeSalle) {
        this.typeSalle = typeSalle;
    }

    public int getIdType() {
        return idType;
    }

}
