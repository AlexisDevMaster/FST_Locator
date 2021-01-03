package com.theluckydev.fst.SalleBatimentDatabase.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.theluckydev.fst.SalleBatimentDatabase.DAO.AliasBuildingSearchDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.AliasRoomSearchDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.BuildingDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.ComplexeDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.EntryBuildingDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.EntryRoomDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.EquipmentDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.GraphEdgeDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.GraphNodeDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.GraphNodeTypeDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.RoomDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.RoomEquipmentDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.SoftwareDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.RoomSoftwareDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.SurfacePointDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.ToiletteDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.TypeDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.TypeSalleDAO;
import com.theluckydev.fst.SalleBatimentDatabase.DAO.UpToDateDAO;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.AliasBuildingSearch;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.AliasRoomSearch;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Building;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.EntreeSalle;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.EntryBuilding;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphEdge;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNode;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNodeType;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Software;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.RoomEquipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.RoomSoftware;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.SurfacePoint;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Toilette;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Type;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.TypeRoom;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.UpToDate;

@Database(entities = {Room.class, Building.class, Equipment.class, Software.class,
        RoomEquipment.class, RoomSoftware.class, UpToDate.class, SurfacePoint.class, Type.class,
        TypeRoom.class, AliasRoomSearch.class, AliasBuildingSearch.class, EntreeSalle.class,
        EntryBuilding.class, GraphNode.class, GraphNodeType.class, GraphEdge.class, Toilette.class},
        version = 16)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;

    private static final String DATABASE_NAME = "FST_Database";


    public abstract RoomDAO roomModel();
    public abstract BuildingDAO buildingModel();
    public abstract EquipmentDAO equipmentModel();
    public abstract SoftwareDAO softwareModel();
    public abstract RoomEquipmentDAO roomEquipmentModel();
    public abstract RoomSoftwareDAO roomSoftwareModel();
    public abstract UpToDateDAO upToDateModel();
    public abstract SurfacePointDAO surfacePointModel();
    public abstract TypeDAO typeModel();
    public abstract TypeSalleDAO typeRoomModel();
    public abstract ComplexeDAO complexeModel();
    public abstract AliasRoomSearchDAO aliasRoomSearchModel();
    public abstract AliasBuildingSearchDAO aliasBuildingSearchModel();
    public abstract EntryRoomDAO entryRoomModel();
    public abstract EntryBuildingDAO entryBuildingModel();
    public abstract GraphNodeDAO graphNodeDAO();
    public abstract GraphNodeTypeDAO graphNodeTypeDAO();
    public abstract GraphEdgeDAO graphEdgeDAO();
    public abstract ToiletteDAO toiletteModel();

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance =
                    androidx.room.Room.databaseBuilder(context,AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
        }

        return mInstance;
    }
}