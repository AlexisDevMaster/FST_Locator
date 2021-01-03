package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "GraphEdge",
        foreignKeys = {
        @ForeignKey(
                entity = GraphNode.class,
                parentColumns = "idNode",
                childColumns = "idNode1",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = GraphNode.class,
                parentColumns = "idNode",
                childColumns = "idNode2",
                onDelete = CASCADE
        )})

public class GraphEdge extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idEdge", index = true)
    private int idEdge;


    @ColumnInfo(name = "idNode1", index = true)
    private int idNode1;

    @ColumnInfo(name = "idNode2", index = true)
    private int idNode2;

    public GraphEdge(int idEdge, int idNode1, int idNode2) {
        this.idEdge = idEdge;
        this.idNode1 = idNode1;
        this.idNode2 = idNode2;
    }

    public int getIdEdge() {
        return idEdge;
    }

    public void setIdEdge(int idEdge) {
        this.idEdge = idEdge;
    }

    public int getIdNode1() {
        return idNode1;
    }

    public void setIdNode1(int idNode1) {
        this.idNode1 = idNode1;
    }

    public int getIdNode2() {
        return idNode2;
    }

    public void setIdNode2(int idNode2) {
        this.idNode2 = idNode2;
    }
}
