package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "SalleLogiciel",
        primaryKeys = {"idSalle", "idLogiciel"},
        foreignKeys = {
        @ForeignKey(
                entity = Room.class,
                parentColumns = "idSalle",
                childColumns = "idSalle",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Software.class,
                parentColumns = "idLogiciel",
                childColumns = "idLogiciel",
                onDelete = CASCADE
        )
        })

public class RoomSoftware implements Serializable {


    @ColumnInfo(name = "idSalle")
    private int idRoom;

    @ColumnInfo(name = "idLogiciel", index = true)
    private int idSoftware;

    public RoomSoftware(int idRoom, int idSoftware) {
        //this.idSalleLogiciel = idSalleLogiciel;
        this.idRoom = idRoom;
        this.idSoftware = idSoftware;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(int idSoftware) {
        this.idSoftware = idSoftware;
    }
}
