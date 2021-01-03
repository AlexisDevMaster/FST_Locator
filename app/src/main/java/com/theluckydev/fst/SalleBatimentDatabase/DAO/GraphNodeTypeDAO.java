package com.theluckydev.fst.SalleBatimentDatabase.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNode;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNodeType;

import java.util.List;

@Dao
public interface GraphNodeTypeDAO {

    @Query("SELECT * FROM GraphNodeType")
    List<GraphNodeType> getAll();

    @Query("SELECT * FROM GraphNodeType where  idGraphNodeType = :idGraphNodeType")
    GraphNodeType getNodeTypeById(int idGraphNodeType);
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGraphNodeType(GraphNodeType graphNodeType);

    @Delete
    void delete(GraphNodeType graphNodeType);
}
