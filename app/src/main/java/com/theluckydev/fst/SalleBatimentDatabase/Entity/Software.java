package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

@Entity(tableName = "Logiciel")
public class Software extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idLogiciel", index = true)
    private int idSoftware;

    @ColumnInfo(name = "nomLogiciel")
    private String nameSoftware;

    @ColumnInfo(name = "versionLogiciel")
    private String versionSoftware;

    @ColumnInfo(name = "icnoneLogiciel")
    private String iconeSoftware;


    public Software(int idSoftware, String nameSoftware, String versionSoftware, String iconeSoftware) {
        this.idSoftware = idSoftware;
        this.nameSoftware = nameSoftware;
        this.versionSoftware = versionSoftware;
        this.iconeSoftware = iconeSoftware;
    }

    @Ignore
    public Software(String nameSoftware, String versionSoftware, String iconeSoftware) {
        this.nameSoftware = nameSoftware;
        this.versionSoftware = versionSoftware;
        this.iconeSoftware = iconeSoftware;
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getNameSoftware() {
        return nameSoftware;
    }

    public void setNameSoftware(String nameSoftware) {
        this.nameSoftware = nameSoftware;
    }

    public String getVersionSoftware() {
        return versionSoftware;
    }

    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    public String getIconeSoftware() {
        return iconeSoftware;
    }

    public void setIconeSoftware(String iconeSoftware) {
        this.iconeSoftware = iconeSoftware;
    }
}
