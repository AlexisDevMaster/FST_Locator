package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "GraphNodeRoom",
        foreignKeys = {
                @ForeignKey(
                        entity = Room.class,
                        parentColumns = "idSalle",
                        childColumns = "idSalle",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = GraphNode.class,
                        parentColumns = "idNode",
                        childColumns = "idNode",
                        onDelete = CASCADE
                )})

public class GraphNodeRoom extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idNodeSalle", index = true)
    private int idNodeRoom;

    @ColumnInfo(name = "idNode", index = true)
    private int idNode;

    @ColumnInfo(name = "idSalle", index = true)
    private int idRoom;

    public GraphNodeRoom(int idNodeRoom, int idRoom) {
        this.idNodeRoom = idNodeRoom;
        this.idRoom = idRoom;
    }

    public int getIdNodeRoom() {
        return idNodeRoom;
    }

    public void setIdNodeRoom(int idNodeRoom) {
        this.idNodeRoom = idNodeRoom;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }
}
