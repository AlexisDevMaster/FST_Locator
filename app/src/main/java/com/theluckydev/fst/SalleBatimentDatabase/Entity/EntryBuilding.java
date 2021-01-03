package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "EntreeBatiment",
        foreignKeys = {
                @ForeignKey(
                        entity = Building.class,
                        parentColumns = "idBatiment",
                        childColumns = "idBatiment",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = GraphNode.class,
                        parentColumns = "idNode",
                        childColumns = "idNode",
                        onDelete = CASCADE
                )})
public class EntryBuilding extends Objet implements Serializable, SalleBatimentInterface {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEntreeBatiment", index = true)
    private int idEntryBuilding;

    @ColumnInfo(name = "idBatiment", index = true)
    private int idBuilding;

    @ColumnInfo(name = "idNode", index = true)
    private int idNode;

    public EntryBuilding(int idEntryBuilding, int idBuilding, int idNode) {
        this.idEntryBuilding = idEntryBuilding;
        this.idBuilding = idBuilding;
        this.idNode = idNode;
    }

    public int getIdEntryBuilding() {
        return idEntryBuilding;
    }

    public void setIdEntryBuilding(int idEntryBuilding) {
        this.idEntryBuilding = idEntryBuilding;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }

    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }
}