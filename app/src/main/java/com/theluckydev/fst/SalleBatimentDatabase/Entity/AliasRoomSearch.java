package com.theluckydev.fst.SalleBatimentDatabase.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.outils.SalleBatimentInterface;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "AliasSalleRecherche",
        foreignKeys = {
                @ForeignKey(
                        entity = Room.class,
                        parentColumns = "idSalle",
                        childColumns = "idSalle",
                        onDelete = CASCADE
                )})
public class AliasRoomSearch extends Objet implements Serializable, SalleBatimentInterface {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idAliasRecherche", index = true)
    private int idAliasSearch;

    @ColumnInfo(name = "alias")
    private String alias;

    @ColumnInfo(name = "idSalle", index = true)
    private int idRoom;

    public AliasRoomSearch(int idAliasSearch, String alias, int idRoom) {
        this.idAliasSearch = idAliasSearch;
        this.alias = alias;
        this.idRoom = idRoom;
    }

    public int getIdAliasSearch() {
        return idAliasSearch;
    }

    public void setIdAliasSearch(int idAliasSearch) {
        this.idAliasSearch = idAliasSearch;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }



    @Override
    public String getStandardName() {
        return this.alias;
    }

}