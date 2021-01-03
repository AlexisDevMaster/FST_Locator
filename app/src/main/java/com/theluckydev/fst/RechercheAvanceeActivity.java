package com.theluckydev.fst;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.theluckydev.fst.SalleBatimentDatabase.Database.AppDatabase;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Building;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Software;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Type;
import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.objet.adapter.search.advancedsearch.AdvancedSearchAdapter;
import com.theluckydev.fst.objet.adapter.search.advancedsearch.RoomDetails;
import com.theluckydev.fst.outils.multipleselectspinner.MultipleSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RechercheAvanceeActivity extends AppCompatActivity {

    //---------------------------------Room Attributs-----------------------------------------------
    private AppDatabase mDb;
    //-----------------------------------Fin--------------------------------------------------------

    private ArrayList<Spinner> spinnerList;
    private ArrayList<String> spinnerAssociateVarDB;
    private ListView lvSalle;
    ArrayAdapter<RoomDetails> adapterSalle;
    MultipleSpinnerAdapter equipementAdapter;
    MultipleSpinnerAdapter softwareDataAdapter;

    public int RESULT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_avancee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRechercheAvancee);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        spinnerAssociateVarDB = new ArrayList<>();
        spinnerAssociateVarDB.add("idBatiment");
        spinnerAssociateVarDB.add("idTypeSalle");
        spinnerAssociateVarDB.add("idLogiciel");
        spinnerAssociateVarDB.add("idEquipement");
        //Récupération de l'instnvce de la base de données interne
        mDb = AppDatabase.getInstance(this);

        spinnerList = new ArrayList<>();
        //Mise en place spinner batiment
        Spinner batiment = findViewById(R.id.batiment_spinner);
        List<Building> buildingList = mDb.buildingModel().getAll();
        List<String> batimentNomList = new ArrayList<>();
        batimentNomList.add("Non spécifié");
        for (Building b : buildingList) {
            batimentNomList.add(b.getNameBuilding());
        }
        ArrayAdapter<String> batimentDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, batimentNomList);
        batimentDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batiment.setAdapter(batimentDataAdapter);

        //Mise en place spinner OS
        final Spinner os = findViewById(R.id.systeme_spinner);
        List<String> osList = new ArrayList<>();
        osList.add("Non spécifié");
        osList.add("Windows");
        osList.add("Linux");
        ArrayAdapter<String> osDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, osList);
        osDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        os.setAdapter(osDataAdapter);

        //Mise en place spinner logiciel
        final Spinner software = findViewById(R.id.logiciels_spinner);
        List<Software> softwareList = mDb.softwareModel().getAll();
        List<MultipleSpinnerAdapter.SpinnerItem<Objet>> spinner_items_software = new ArrayList<>();
        Set<Objet> selected_items_software = new HashSet<>();
        for (Software l : softwareList) {
            spinner_items_software.add(new MultipleSpinnerAdapter.SpinnerItem<>((Objet) l, l.getNameSoftware()));
        }
        softwareDataAdapter = new MultipleSpinnerAdapter<>(this, "Non spécifié", spinner_items_software, selected_items_software);
        softwareDataAdapter.notifyDataSetChanged();
        software.setAdapter(softwareDataAdapter);

        //Empêcher le bug du clic-glisse pour sélectionner un item
        software.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
                return true;
            }
        });

        //Mise en place spinner type de salle
        Spinner typeDeSalle = findViewById(R.id.typesalle_spinner);
        List<Type> typeList = mDb.typeModel().getAll();
        List<String> typeNomList = new ArrayList<>();
        typeNomList.add("Non spécifié");
        for (Type t : typeList) {
            typeNomList.add(t.getTypeSalle());
        }
        ArrayAdapter<String> typeSalleDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeNomList);
        typeSalleDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDeSalle.setAdapter(typeSalleDataAdapter);
        typeDeSalle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Si l'item sélectionné est "Informatique"
                if (i == 5) {
                    software.setEnabled(true);
                    os.setEnabled(true);
                    TextView textView = findViewById(R.id.text);
                    textView.setTextColor(Color.BLACK);
                } else {
                    software.setEnabled(false);
                    os.setEnabled(false);
                    TextView textView = findViewById(R.id.text);
                    textView.setTextColor(Color.parseColor("#42000000"));
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //Mise en place spinner equipements
        Spinner equipement = findViewById(R.id.equipements_spinner);

        List<Equipment> equipmentList = mDb.equipmentModel().getAll();
        List<MultipleSpinnerAdapter.SpinnerItem<Objet>> spinner_items_equipement = new ArrayList<>();
        Set<Objet> selected_items_equipement = new HashSet<>();
        for (Equipment e : equipmentList) {
            spinner_items_equipement.add(new MultipleSpinnerAdapter.SpinnerItem<>((Objet) e, e.getNameEquipment()));
        }
        equipementAdapter = new MultipleSpinnerAdapter<>(this, "Non spécifié", spinner_items_equipement, selected_items_equipement);
        equipement.setAdapter(equipementAdapter);
        equipementAdapter.notifyDataSetChanged();

        //Empêcher le bug du clic-glisse pour sélectionner un item
        equipement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.performClick();
                }
                return true;
            }
        });

        Spinner disponible = findViewById(R.id.dispo_spinner);
        List<String> dispoList = new ArrayList<>();
        dispoList.add("Non spécifié");
        dispoList.add("Disponible");
        dispoList.add("Occupée");
        ArrayAdapter<String> dispoDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dispoList);
        dispoDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disponible.setAdapter(dispoDataAdapter);

        spinnerList.add(batiment);
        spinnerList.add(typeDeSalle);
        spinnerList.add(os);
        spinnerList.add(software);
        spinnerList.add(equipement);
        //spinnerList.add(disponible);

        //Evènement sur le bouton de recherhce pour contruire les cartes de résultats (les salles correspondant aux critères)
        Button research = findViewById(R.id.advanced_research_button);
        research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> idSalle = constructQuery();
                lvSalle =(ListView)findViewById(R.id.salleList);
                List<RoomDetails> roomDetailsList = new ArrayList<>();
                for(int i =0; i<idSalle.size();i++){
                    Room room = mDb.roomModel().getSalle(idSalle.get(i));
                    Building building = mDb.buildingModel().getBatiment(room.getIdBuilding());
                    RoomDetails roomDetails = new RoomDetails();
                    roomDetails.setIdRoom(room.getIdRoom());
                    roomDetails.setCodeRoom(room.getCodeRoom());
                    roomDetails.setNameRoom(room.getNameRoom());
                    roomDetails.setCapacityRoom(room.getCapacityRoom());
                    roomDetails.setLevelRoom(room.getLevelRoom());


                    List<Integer> idTypeList = mDb.typeRoomModel().getIdTypeByIdRoom(room.getIdRoom());
                    for(Integer idType : idTypeList){
                        roomDetails.addType(mDb.typeModel().loadTypeById(idType));
                    }

                    roomDetails.setNameBuilding(building.getNameBuilding());
                    roomDetails.setCompleteNameBuilding(building.getCompleteNameBuilding());
                    roomDetails.setCodeBuilding(building.getCodeBuilding());


                    List<Integer> idEquipementList = mDb.roomEquipmentModel().getIdEquipementByIdSalle(room.getIdRoom());
                    for(Integer idEquipement : idEquipementList){
                        roomDetails.addEquipment(mDb.equipmentModel().getEquipmentByIdEquipment(idEquipement));
                    }

                    List<Integer> idSoftwareList = mDb.roomSoftwareModel().getIdSoftwareByIdRoom(room.getIdRoom());
                    for(Integer idSoftware : idSoftwareList){
                        roomDetails.addSoftware(mDb.softwareModel().getSoftware(idSoftware));
                    }
                    roomDetailsList.add(roomDetails);
                }
                if(roomDetailsList.size() == 0){
                    RoomDetails roomDetails = new RoomDetails();
                    roomDetails.setIdRoom(-1);
                    roomDetailsList.add(roomDetails);
                }
                adapterSalle = new AdvancedSearchAdapter(roomDetailsList,RechercheAvanceeActivity.this);
                lvSalle.setAdapter(adapterSalle);
            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public List<Integer> constructQuery() {

        List<StringBuilder> queryList = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        List<Integer> idSalleList = new ArrayList<>();

        for (int i = 0; i < spinnerList.size(); i++) {
            //Pour chaque spinner on crée un rerquete SQL associé à la potentiel condition imposée par le Spinner
            switch (i) {
                case 0:
                    query = query.append("SELECT SALLE.idSalle FROM SALLE LEFT JOIN BATIMENT ON SALLE.idBatimentSalle = BATIMENT.idBatiment ");
                    break;
                case 1:
                    query = query.append("SELECT TYPESALLE.idSalle FROM TYPESALLE LEFT JOIN TYPE ON TYPESALLE.idTypeSalle = TYPE.idType ");
                    break;
                case 2:
                    query = query.append("SELECT SALLELOGICIEL.idSalle FROM SALLELOGICIEL LEFT JOIN LOGICIEL ON LOGICIEl.idLogiciel = SALLELOGICIEL.idLogiciel ");
                    break;
                case 3:
                    query = query.append("SELECT SALLELOGICIEL.idSalle FROM SALLELOGICIEL LEFT JOIN LOGICIEL ON LOGICIEL.idLogiciel = SALLELOGICIEL.idLogiciel ");
                    break;
                case 4:
                    query = query.append("SELECT SALLEEQUIPEMENT.idSalle FROM SALLEEQUIPEMENT LEFT JOIN EQUIPEMENT  ON EQUIPEMENT.idEquipement = SALLEEQUIPEMENT.idEquipement ");
                    break;
            }
            if (spinnerList.get(i) != null && spinnerList.get(i).getSelectedItem() != null && spinnerList.get(i).isEnabled()) {
                if (!spinnerList.get(i).getSelectedItem().equals("Non spécifié")) {
                    if(i==2){
                        query.append("WHERE SALLELOGICIEL.").append(spinnerAssociateVarDB.get(i)).append("=").append(spinnerList.get(i).getSelectedItemPosition());
                    }else{
                        query.append("WHERE ").append(spinnerAssociateVarDB.get(i)).append("=").append(spinnerList.get(i).getSelectedItemPosition());
                    }
                }
            }

            if(i==3){
                if(softwareDataAdapter.getSelected_items().size()>0 && spinnerList.get(i).isEnabled()) {
                    query.append("WHERE ");
                    int z = 0;
                    for (Object e :softwareDataAdapter.getSelected_items()) {
                        Log.i("QUERY", "equipement selectionné : " + i);
                        query.append("SALLELOGICIEL.").append(spinnerAssociateVarDB.get(i-1)).append("=").append(((Software) e).getIdSoftware()).append(" ");
                        if(z != softwareDataAdapter.getSelected_items().size()-1){
                            query.append("AND ");
                        }
                        z++;
                    }
                }
            }
            //Condition multiselection d'équipement
            if(i==4){
                if(equipementAdapter.getSelected_items().size()>0) {
                    query.append("WHERE ");
                    int z = 0;
                    for (Object e : equipementAdapter.getSelected_items()) {
                        Log.i("QUERY", "equipement selectionné : " + i);
                        query.append("SALLEEQUIPEMENT.").append(spinnerAssociateVarDB.get(i-1)).append("=").append(((Equipment) e).getIdEquipment()).append(" ");
                        if(z != equipementAdapter.getSelected_items().size()-1){
                            query.append("AND ");
                        }
                        z++;
                    }
                }
            }
            //On enregistre la requete SQL
            queryList.add(query);
            query = new StringBuilder();
        }

        //Trie des id des salles correspondant à toutes les conditions
        for(int i =0; i< queryList.size(); i++) {
            List<Integer> tmpIdSalleList = new ArrayList<>();
            Log.i("QUERY", queryList.get(i).toString());
            if(i == 0) {
                for (Integer s : mDb.complexeModel().getByParamDynamic(new SimpleSQLiteQuery(queryList.get(i).toString()))) {
                    idSalleList.add( mDb.roomModel().getSalle(s).getIdRoom());
                }
            }else{
                for (Integer s : mDb.complexeModel().getByParamDynamic(new SimpleSQLiteQuery(queryList.get(i).toString()))) {
                    tmpIdSalleList.add( mDb.roomModel().getSalle(s).getIdRoom());
                }
                if (spinnerList.get(i) != null && spinnerList.get(i).getSelectedItem() != null && spinnerList.get(i).isEnabled()) {
                    if (!spinnerList.get(i).getSelectedItem().equals("Non spécifié")) {
                        idSalleList.retainAll(tmpIdSalleList);
                    }
                }
                //Condition multiselection d'équipement pour tris des id et liste final des salles
                if(i==3){
                    if(softwareDataAdapter.getSelected_items().size()>0) {
                        idSalleList.retainAll(tmpIdSalleList);
                    }
                }
                //Condition multiselection d'équipement pour tris des id et liste final des salles
                if(i==4){
                    if(equipementAdapter.getSelected_items().size()>0) {
                        idSalleList.retainAll(tmpIdSalleList);
                    }
                }

            }
        }
        // On possède les ids des salles associées à toutes les conditions de tous les spinners
        Log.i("QUERY", "Nombre de salle : "+idSalleList.size());
        return idSalleList;
    }
}
