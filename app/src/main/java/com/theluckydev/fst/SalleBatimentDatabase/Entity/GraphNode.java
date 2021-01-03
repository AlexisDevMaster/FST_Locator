package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "GraphNode",
        foreignKeys = {
                @ForeignKey(
                        entity = GraphNodeType.class,
                        parentColumns = "idGraphNodeType",
                        childColumns = "idGraphNodeType",
                        onDelete = CASCADE
                )})

public class GraphNode extends Objet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idNode", index = true)
    private int idNode;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "weight")
    private String weight;

    @ColumnInfo(name = "idGraphNodeType", index = true)
    private int idGraphType;

    public GraphNode(int idNode, String name, String latitude, String longitude, String weight, int idGraphType) {
        this.idNode = idNode;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weight = weight;
        this.idGraphType = idGraphType;
    }

    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getIdGraphType() {
        return idGraphType;
    }

    public void setIdGraphType(int idGraphType) {
        this.idGraphType = idGraphType;
    }
}
