package com.theluckydev.fst.objet.adapter.search.advancedsearch;

import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Software;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomDetails implements Serializable {

    private Integer idRoom;
    private String codeRoom;
    private String nameRoom;
    private Integer capacityRoom;
    private String levelRoom;
    private List<Type> typeSalleList= new ArrayList<>();
    private String nameBuilding;
    private String completeNameBuilding;
    private String codeBuilding;
    private List<Equipment> equipmentList = new ArrayList<>();
    private List<Software> softwareList = new ArrayList<>();



    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getCodeRoom() {
        return codeRoom;
    }

    public void setCodeRoom(String codeRoom) {
        this.codeRoom = codeRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public Integer getCapacityRoom() {
        return capacityRoom;
    }

    public void setCapacityRoom(Integer capacityRoom) {
        this.capacityRoom = capacityRoom;
    }

    public String getLevelRoom() {
        return levelRoom;
    }

    public void setLevelRoom(String levelRoom) {
        this.levelRoom = levelRoom;
    }

    public List<Type> getTypeSalleList() {
        return typeSalleList;
    }

    public void setTypeSalleList(List<Type> typeSalleList) {
        this.typeSalleList = typeSalleList;
    }

    public void addType(Type type){
        this.typeSalleList.add(type);
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public String getCompleteNameBuilding() {
        return completeNameBuilding;
    }

    public void setCompleteNameBuilding(String completeNameBuilding) {
        this.completeNameBuilding = completeNameBuilding;
    }

    public String getCodeBuilding() {
        return codeBuilding;
    }

    public void setCodeBuilding(String codeBuilding) {
        this.codeBuilding = codeBuilding;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void addSoftware(Software software){
        this.softwareList.add(software);
    }

    public void addEquipment(Equipment equipment){
        this.equipmentList.add(equipment);
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }
}
