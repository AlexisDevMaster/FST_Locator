package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "UpToDate")
public class UpToDate implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idUpdtoDate;


    @ColumnInfo(name = "date")
    private String date;

    public UpToDate(int idUpdtoDate, String date) {
        this.idUpdtoDate = idUpdtoDate;
        this.date = date;
    }

    public int getIdUpdtoDate() {
        return idUpdtoDate;
    }

    public void setIdUpdtoDate(int idUpdtoDate) {
        this.idUpdtoDate = idUpdtoDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
