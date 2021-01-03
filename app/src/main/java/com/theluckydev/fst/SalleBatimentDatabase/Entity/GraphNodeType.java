package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "GraphNodeType")

public class GraphNodeType extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idGraphNodeType", index = true)
    private int idGraphNodeType;

    @ColumnInfo(name = "graphNodeType")
    private String graphNodeType;

    public GraphNodeType(int idGraphNodeType, String graphNodeType) {
        this.idGraphNodeType = idGraphNodeType;
        this.graphNodeType = graphNodeType;
    }

    public int getIdGraphNodeType() {
        return idGraphNodeType;
    }

    public void setIdGraphNodeType(int idGraphNodeType) {
        this.idGraphNodeType = idGraphNodeType;
    }

    public String getGraphNodeType() {
        return graphNodeType;
    }

    public void setGraphNodeType(String graphNodeType) {
        this.graphNodeType = graphNodeType;
    }
}


