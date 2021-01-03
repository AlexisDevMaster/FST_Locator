package com.theluckydev.fst.SalleBatimentDatabase;

public class CleStatic {

    public static final String KEY_SUCCESS = "success";
    public static final String KEY_MAJ = "maj";
    public static final String KEY_DATA = "data";
    public static final String KEY_SALLE = "salle";
    public static final String KEY_BATIMENT = "batiment";
    public static final String KEY_EQUIPEMENT = "equipement";
    public static final String KEY_LOGICIEL = "logiciel";
    public static final String KEY_SALLEEQUIPEMENT = "salleEquipement";
    public static final String KEY_SALLELOGICIEL = "salleLogiciel";
    public static final String KEY_SURFACEPOINT = "surfacePoint";

    //----------------------------MISE A JOUR---------------------------------------
    public static final String KEY_MAJ_ID = "idMiseAJour";
    public static final String KEY_MAJ_DATE = "dateMiseAJour";
    //-------------------------------------------------------------------------


    //----------------------------SALLE---------------------------------------
    public static final String KEY_SALLE_ID = "idSalle";
    public static final String KEY_CODE_SALLE = "codeSalle";
    public static final String KEY_LATITUDE_SALLE = "latitude";
    public static final String KEY_LONGITUDE_SALLE = "longitude";
    public static final String KEY_NOM_SALLE = "nomSalle";
    public static final String KEY_CAPACITE_SALLE = "capaciteSalle";
    public static final String KEY_ETAGE_SALLE = "etageSalle";
    public static final String KEY_TYPE_SALLE = "typeSalle";
    public static final String KEY_ID_BATIMENT_SALLE = "idBatiment";
    //-------------------------------------------------------------------------

    //-------------------------BATIMENT----------------------------------------
    public static final String KEY_BATIMENT_ID = "idBatiment";
    public static final String KEY_CODE_BATIMENT = "codeBatiment";
    public static final String KEY_LATITUDE_BATIMENT = "latitude";
    public static final String KEY_LONGITUDE_BATIMENT = "longitude";
    public static final String KEY_NOM_BATIMENT = "nomBatiment";
    public static final String KEY_NOM_COMPLET_BATIMENT = "nomBatimentComplet";
    //-------------------------------------------------------------------------

    //-------------------------TOILETTE----------------------------------------
    public static final String KEY_TOILETTE_ID = "idToilette";
    public static final String KEY_LATITUDE_TOILETTE = "latitude";
    public static final String KEY_LONGITUDE_TOILETTE = "longitude";
    //-------------------------------------------------------------------------

    //-------------------------EQUIPEMENT---------------------------------------
    public static final String KEY_EQUIPEMENT_ID = "idEquipement";
    public static final String KEY_NOM_EQUIPEMENT = "nomEquipement";
    //-------------------------------------------------------------------------

    //-------------------------LOGICIEL----------------------------------------
    public static final String KEY_LOGICIEL_ID = "idLogiciel";
    public static final String KEY_NOM_LOGICIEL = "nomLogiciel";
    public static final String KEY_VERSION_LOGICIEL = "versionLogiciel";
    public static final String KEY_ICONE_LOGICIEL = "iconeLogiciel";
    //-------------------------------------------------------------------------

    //-------------------------TYPE----------------------------------------
    public static final String KEY_TYPE_ID = "idType";
    public static final String KEY_NOM_TYPE = "typeSalle";
    //-------------------------------------------------------------------------

    //-------------------------TYPESALLE----------------------------------------
    public static final String KEY_TYPESALLE_TYPE_ID = "idType";
    public static final String KEY_TYPESALLE_SALLE_ID = "idSalle";
    //-------------------------------------------------------------------------

    //-------------------------SALLE-EQUIPEMENT----------------------------------------
    public static final String KEY_SALLEEQUIPEMENT_SALLE_ID = "idSalle";
    public static final String KEY_SALLEEQUIPEMENT_EQUIPEMENT_ID = "idEquipement";
    //-------------------------------------------------------------------------

    //-------------------------SALLE-LOGICIEL----------------------------------------
    public static final String KEY_SALLELOGICIEL_SALLE_ID = "idSalle";
    public static final String KEY_SALLELOGICIEL_LOGICIEL_ID = "idLogiciel";
    //-------------------------------------------------------------------------

    //-------------------------SURFACE-POINT----------------------------------------
    public static final String KEY_SURFACEPOINT_ID ="idPoint";
    public static final String KEY_SURFACEPOINT_LATITUDE = "latitude";
    public static final String KEY_SURFACEPOINT_LONGITUDE = "longitude";
    public static final String KEY_SURFACEPOINT_ORDRE = "ordre";
    public static final String KEY_SURFACEPOINT_IDBATIMENT = "idBatiment";
    //-------------------------------------------------------------------------

    //-------------------------ALIASSALLERECHERCHE----------------------------------------
    public static final String KEY_ALIASSALLERECHERCHE_ID ="idAliasSalleRecherche";
    public static final String KEY_ALIASSALLERECHERCHE_ALIAS = "alias";
    public static final String KEY_ALIASSALLERECHERCHE_IDSALLE = "idSalle";
    public static final String KEY_ALIASRECHERCHE_IDBATIMENT = "idBatiment";
    //-------------------------------------------------------------------------

    //-------------------------ALIASBATIMENTRECHERCHE----------------------------------------
    public static final String KEY_ALIASBATIMENTRECHERCHE_ID ="idAliasBatimentRecherche";
    public static final String KEY_ALIASBATIMENTRECHERCHE_ALIAS = "alias";
    public static final String KEY_ALIASBATIMENTRECHERCHE_IDBATIMENT = "idBatiment";
    //-------------------------------------------------------------------------

    //-------------------------ENTREESALLE----------------------------------------
    public static final String KEY_ENTREESALLE_ID ="idEntreeSalle";
    public static String KEY_ENTREESALLE_IDNODE = "idNode";
    public static final String KEY_ENTREESALLE_IDSALLE = "idSalle";
    //-------------------------------------------------------------------------

    //-------------------------ENTREEBATIMENT----------------------------------------
    public static final String KEY_ENTREEBATIMENT_ID ="idEntreeBatiment";
    public static String KEY_ENTREEBATIMENT_IDNODE = "idNode";
    public static final String KEY_ENTREEBATIMENT_IDBATIMENT = "idBatiment";
    //-------------------------------------------------------------------------

    //-------------------------NODE----------------------------------------
    public static final String KEY_NODE_ID ="idNode";
    public static final String KEY_NODE_NAME ="name";
    public static final String KEY_NODE_LATITUDE = "latitude";
    public static final String KEY_NODE_LONGITUDE = "longitude";
    public static final String KEY_NODE_WEIGHT ="weight";
    public static final String KEY_NODE_IDGRAPHNODETYPEID = "idGraphNodeType";
    //-------------------------------------------------------------------------

    //-------------------------NODETYPE----------------------------------------
    public static final String KEY_NODETYPE_ID ="idGraphNodeType";
    public static final String KEY_NODETYPE_NAME ="graphNodeType";
    //-------------------------------------------------------------------------

    //-------------------------EDGE----------------------------------------
    public static final String KEY_EDGE_ID ="idEdge";
    public static final String KEY_EDGE_NODE1 ="idNode1";
    public static final String KEY_EDGE_NODE2 ="idNode2";

    //-------------------------------------------------------------------------
}
