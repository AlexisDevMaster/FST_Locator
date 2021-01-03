package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNode;

import java.util.List;

@Dao
public interface GraphNodeDAO {

    @Query("SELECT * FROM GraphNode")
    List<GraphNode> getAll();

    @Query("SELECT * FROM GraphNode where  idNode = :idNode")
    GraphNode getNodeById(int idNode);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGraphNode(GraphNode graphNode);

    @Delete
    void delete(GraphNode graphNode);
}
