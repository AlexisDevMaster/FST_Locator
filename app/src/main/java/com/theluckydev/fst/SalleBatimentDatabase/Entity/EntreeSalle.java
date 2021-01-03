package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "EntreeSalle",
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
public class EntreeSalle extends Objet implements Serializable, SalleBatimentInterface {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEntreeSalle", index = true)
    private int idEntryRoom;

    @ColumnInfo(name = "idSalle", index = true)
    private int idRoom;

    @ColumnInfo(name = "idNode", index = true)
    private int idNode;

    public EntreeSalle(int idEntryRoom, int idRoom, int idNode) {
        this.idEntryRoom = idEntryRoom;
        this.idRoom = idRoom;
        this.idNode = idNode;
    }

    public int getIdEntryRoom() {
        return idEntryRoom;
    }

    public void setIdEntryRoom(int idEntryRoom) {
        this.idEntryRoom = idEntryRoom;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }
}