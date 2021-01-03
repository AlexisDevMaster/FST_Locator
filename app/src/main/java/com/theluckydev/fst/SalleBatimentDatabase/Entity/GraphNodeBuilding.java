package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "GraphNodeBuilding",
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

public class GraphNodeBuilding extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idNodeBatiment", index = true)
    private int idNodeBuilding;

    @ColumnInfo(name = "idNode", index = true)
    private int idNode;

    @ColumnInfo(name = "idBatiment", index = true)
    private int idBuilding;

    public GraphNodeBuilding(int idNodeBuilding, int idBuilding) {
        this.idNodeBuilding = idNodeBuilding;
        this.idBuilding = idBuilding;
    }

    public int getIdNodeBuilding() {
        return idNodeBuilding;
    }

    public void setIdNodeBuilding(int idNodeBuilding) {
        this.idNodeBuilding = idNodeBuilding;
    }

    public int getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding = idBuilding;
    }
}
