package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "TypeSalle",
        primaryKeys = {"idTypeSalle", "idSalle"},
        foreignKeys = {
                @ForeignKey(
                        entity = Room.class,
                        parentColumns = "idSalle",
                        childColumns = "idSalle",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Type.class,
                        parentColumns = "idType",
                        childColumns = "idTypeSalle",
                        onDelete = CASCADE
                )
        })
public class TypeRoom extends Objet implements Serializable {

    @ColumnInfo(name = "idTypeSalle", index = true)
    private int idTypeRoom;


    @ColumnInfo(name = "idSalle", index = true)
    private int idRoom;

    public TypeRoom(int idTypeRoom, int idRoom) {
        this.idTypeRoom = idTypeRoom;
        this.idRoom = idRoom;
    }


    public int getIdTypeRoom() {
        return idTypeRoom;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdTypeRoom(int idTypeRoom) {
        this.idTypeRoom = idTypeRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }
}
