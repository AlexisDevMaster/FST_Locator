package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphEdge;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNode;

import java.util.List;

@Dao
public interface GraphEdgeDAO {

    @Query("SELECT * FROM GraphEdge")
    List<GraphEdge> getAll();

    @Query("SELECT * FROM GraphEdge where  idEdge = :idEdge")
    GraphEdge getEdgeById(int idEdge);

    @Query("SELECT * FROM GraphEdge where  idNode1 = :idNode1")
    GraphEdge getEdgeByNode1Id(int idNode1);

    @Query("SELECT * FROM GraphEdge where  idNode2 = :idNode2")
    GraphEdge getEdgeByNode2Id(int idNode2);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGraphEdge(GraphEdge graphEdge);

    @Delete
    void delete(GraphEdge graphEdge);
}
