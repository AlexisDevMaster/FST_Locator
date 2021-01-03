package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "SurfacePoint",
        foreignKeys = {
            @ForeignKey(
                    entity = Building.class,
                    parentColumns = "idBatiment",
                    childColumns = "idBatiment",
                    onDelete = CASCADE
            )
        })
public class SurfacePoint implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private int idPoint;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "ordre")
    private int ordre;

    @ColumnInfo(name = "idBatiment", index = true)
    private int idBatiment;



    public SurfacePoint(int idPoint, String latitude, String longitude, int ordre, int idBatiment) {
        this.idPoint = idPoint;
        this.ordre = ordre;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idBatiment = idBatiment;
    }

    public int getIdPoint() {
        return idPoint;
    }

    public void setIdPoint(int idPoint) {
        this.idPoint = idPoint;
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

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public int getIdBatiment() {
        return idBatiment;
    }

    public void setIdBatiment(int idBatiment) {
        this.idBatiment = idBatiment;
    }
}
