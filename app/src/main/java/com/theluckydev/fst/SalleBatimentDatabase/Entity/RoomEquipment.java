package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;
@Entity(tableName = "SalleEquipement",
        primaryKeys = {"idSalle", "idEquipement"},
        foreignKeys = {
                @ForeignKey(
                        entity = Room.class,
                        parentColumns = "idSalle",
                        childColumns = "idSalle",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Equipment.class,
                        parentColumns = "idEquipement",
                        childColumns = "idEquipement",
                        onDelete = CASCADE
                )
        })
public class RoomEquipment implements Serializable {

    @ColumnInfo(name = "idSalle", index = true)
    private int idRoom;

    @ColumnInfo(name = "idEquipement", index = true)
    private int idEquipment;

    public RoomEquipment(int idRoom, int idEquipment) {
        this.idRoom = idRoom;
        this.idEquipment = idEquipment;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }
}
