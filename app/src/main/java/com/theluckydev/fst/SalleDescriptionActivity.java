package com.theluckydev.fst;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.theluckydev.fst.SalleBatimentDatabase.Database.AppDatabase;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Building;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Type;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SalleDescriptionActivity extends AppCompatActivity {

    //---------------------------------Room Attributs-----------------------------------------------
    private AppDatabase mDb;
    //-----------------------------------Fin--------------------------------------------------------
    private TextView mTextMessage;
    private NestedScrollView scrollView;
    private ViewPager viewPager;

    private int selecteur=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   scrollView.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_logiciels:
                    scrollView.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);

                    return true;
                case R.id.navigation_plan:
                    scrollView.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDescription);
        //Mise en place de la toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mDb = AppDatabase.getInstance(this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.bringToFront();
        viewPager.setVisibility(View.GONE);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.i("SCROLL", "Scrolled");
            }

            @Override
            public void onPageSelected(int i) {
                Log.i("SCROLL", "selected");
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.i("SCROLL", "Scrolled state changed");
            }
        });

        //viewPager.setVisibility(View.GONE);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabdots);
        tabLayout.setupWithViewPager(viewPager, true);
        Intent i = getIntent();
        //Revoir les données données par Salle lors du clique sur Popup
        if(i.getSerializableExtra("salleDescription") != null) {
            selecteur = 0;
            try {
                genererSalleDescription(i, navigation);
            } catch (IOException e) {
                Toast.makeText(SalleDescriptionActivity.this, "Erreur lors de l'ouverture des plans", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        if(i.getSerializableExtra("salleDetails") != null){
            selecteur = 0;
            try {
                genererSalleDetail(navigation);
            } catch (IOException e) {
                Toast.makeText(SalleDescriptionActivity.this, "Erreur lors de l'ouverture des plans", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        if(i.getSerializableExtra("batimentDescription") != null){
            selecteur  = 1;
            try {
                genererBatimentDetail(navigation);
            } catch (IOException e) {
                Toast.makeText(SalleDescriptionActivity.this, "Erreur lors de l'ouverture des plans", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }


    }


    private void genererSalleDetail(BottomNavigationView navigation) throws IOException {

        Log.i("DESC", "Salle Details type");
        scrollView = findViewById(R.id.home_description);
        Bundle salleExtra = getIntent().getExtras();
        int salleId = salleExtra.getInt("salleDetails");

        Room room = mDb.roomModel().getSalle(salleId);
        Building building = mDb.buildingModel().getBatiment(room.getIdBuilding());
        List<Integer> typeListId = mDb.typeRoomModel().getIdTypeByIdRoom(salleId);
        List<Integer> equipementListId = mDb.roomEquipmentModel().getIdEquipementByIdSalle(salleId);

        List<Type> typeList = new ArrayList<>();
        for(Integer idType : typeListId){
            typeList.add(mDb.typeModel().loadTypeById(idType));
        }
        if(!typeListId.contains(5)) {
            Menu menuNav = navigation.getMenu();
            MenuItem nav_item2 = menuNav.findItem(R.id.navigation_logiciels);
            nav_item2.setEnabled(false);
        }
        List<Equipment> equipementsList = new ArrayList<>();
        for(Integer idEquipement : equipementListId){
            equipementsList.add(mDb.equipmentModel().getEquipmentByIdEquipment(idEquipement));
        }
        TextView titreComplementaire = findViewById(R.id.information_complementaire);

        TextView nomSalle = findViewById(R.id.nom_salle_desc);
        nomSalle.setText(room.getNameRoom());
        TextView codeSalle = findViewById(R.id.code_desc);
        codeSalle.setText(room.getCodeRoom());

        TextView titreSubTitre1 = findViewById(R.id.sub_titre1);
        titreSubTitre1.setText("Bâtiment");

        TextView paraInfoComp1 = findViewById(R.id.para_info_comp1);
        paraInfoComp1.setText(building.getNameBuilding());

        TextView titreSubTitre2 = findViewById(R.id.sub_titre2);
        titreSubTitre2.setText("Code du bâtiment");

        TextView paraInfoComp2 = findViewById(R.id.para_info_comp2);
        paraInfoComp2.setText(building.getCodeBuilding());

        TextView titreSubTitre3 = findViewById(R.id.sub_titre3);
        titreSubTitre3.setText("Nom complet du bâtiment");

        TextView paraInfoComp3 = findViewById(R.id.para_info_comp3);
        paraInfoComp3.setText(building.getCompleteNameBuilding());


        CardView card = new CardView(this);

        // Set the CardView layoutParams
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.android_card_view_batiment);
        params.setMargins(0,0,0,4);
        card.setId(R.id.android_card_view_complement);
        card.setLayoutParams(params);

        //card.setCardElevation(9);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        relativeLayout.setLayoutParams(relativeLayoutParam);

        //TextView titre
        TextView tv = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam.setMargins(5,0,0,0);
        textViewLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tv.setLayoutParams(textViewLayoutParam);
        tv.setPadding(titreComplementaire.getPaddingLeft(),titreComplementaire.getPaddingTop(),titreComplementaire.getPaddingRight(),titreComplementaire.getPaddingBottom());
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText("Caractéristiques de la salle");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setId(R.id.caracteristiqueSalle);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv);

        View line = new View(this);
        RelativeLayout.LayoutParams lineViewLayoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lineViewLayoutParam.addRule(RelativeLayout.BELOW, R.id.caracteristiqueSalle);
        lineViewLayoutParam.height = (int)(convertDpToPx(this,2f ));
        lineViewLayoutParam.setMargins(0,(int)(convertDpToPx(this,5f )),0,0);
        line.setBackground(new ColorDrawable(Color.BLACK));
        line.setId(R.id.lineSepCarac);
        line.setLayoutParams(lineViewLayoutParam);

        relativeLayout.addView(line);

        //Titre type salle
        TextView tv2 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam2.addRule(RelativeLayout.BELOW, R.id.lineSepCarac);
        tv2.setLayoutParams(textViewLayoutParam2);
        tv2.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv2.setTextColor(Color.BLACK);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2.setText("Type de salle");
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setId(R.id.typeSalleTitre);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv2);

        //RelativeLayout contenant le linearLayout des textview des types de salle
        RelativeLayout relativeLayoutContainerTxt = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutContainerTxtParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        relativeLayoutContainerTxtParam.addRule(RelativeLayout.BELOW,
                R.id.typeSalleTitre);
        relativeLayoutContainerTxt.setId(R.id.relativeTypeTextContainer);
        relativeLayoutContainerTxt.setLayoutParams(relativeLayoutContainerTxtParam);

        //LinearLayout contenant les textview des types de salle
        LinearLayout linearLayoutTxt = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutTxtParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutTxt.setLayoutParams(linearLayoutTxtParam);
        linearLayoutTxt.setId(R.id.typeTxtViewContainer);
        linearLayoutTxt.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout.LayoutParams textViewLayoutParam3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam3.setMargins(5,5,5,5);
        for(Type t: typeList) {
            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(textViewLayoutParam3);
            tv3.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
            tv3.setTextColor(Color.BLACK);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setText(t.getTypeSalle());
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            // Put the TextView in RelativeLayout
            linearLayoutTxt.addView(tv3);
        }

        relativeLayoutContainerTxt.addView(linearLayoutTxt);
        relativeLayout.addView(relativeLayoutContainerTxt);

        TextView tv4 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam4.addRule(RelativeLayout.BELOW, R.id.relativeTypeTextContainer);
        tv4.setLayoutParams(textViewLayoutParam4);
        tv4.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv4.setTextColor(Color.BLACK);
        tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv4.setText("Étage de la salle");
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv4.setTypeface(null, Typeface.BOLD);
        tv4.setId(R.id.etageSalleTitre);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv4);

        TextView tv5 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam5 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam5.addRule(RelativeLayout.BELOW, R.id.etageSalleTitre);
        tv5.setLayoutParams(textViewLayoutParam5);
        tv5.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
        tv5.setTextColor(Color.BLACK);
        tv5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv5.setText(room.getLevelRoom());
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv5.setId(R.id.etageSalleContenu);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv5);


        //Titre type salle
        TextView tv7 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParamEquip = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParamEquip.addRule(RelativeLayout.BELOW, R.id.etageSalleContenu);
        tv7.setLayoutParams(textViewLayoutParamEquip);
        tv7.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv7.setTextColor(Color.BLACK);
        tv7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv7.setText("Equipements de salle");
        tv7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv7.setTypeface(null, Typeface.BOLD);
        tv7.setId(R.id.equipementSalleTitre);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv7);

        //RelativeLayout contenant le linearLayout des textview des equipements de salle
        RelativeLayout relativeLayoutContainerEquipTxt = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutContainerEquipTxtParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        relativeLayoutContainerEquipTxtParam.addRule(RelativeLayout.BELOW,
                R.id.equipementSalleTitre);

        relativeLayoutContainerEquipTxt.setId(R.id.relativeEquipementTextContainer);
        relativeLayoutContainerEquipTxt.setLayoutParams(relativeLayoutContainerEquipTxtParam);

        //LinearLayout contenant les textview des equipements de salle
        LinearLayout linearLayoutEquipementTxt = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutEquipementTxtParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutEquipementTxt.setLayoutParams(linearLayoutEquipementTxtParam);
        linearLayoutEquipementTxt.setId(R.id.equipementTxtViewContainer);
        linearLayoutEquipementTxt.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout.LayoutParams textViewLayoutEquipParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutEquipParam.setMargins(5,5,5,5);
        for(Equipment t: equipementsList) {
            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(textViewLayoutEquipParam);
            tv3.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
            tv3.setTextColor(Color.BLACK);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setText(t.getNameEquipment());
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            // Put the TextView in RelativeLayout
            linearLayoutEquipementTxt.addView(tv3);
        }

        relativeLayoutContainerEquipTxt.addView(linearLayoutEquipementTxt);
        relativeLayout.addView(relativeLayoutContainerEquipTxt);


        RelativeLayout.LayoutParams relativeLayoutSeparatorParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        TextView tv6 = new TextView(this);
        relativeLayoutSeparatorParam.addRule(RelativeLayout.BELOW, R.id.relativeEquipementTextContainer);
        tv6.setLayoutParams(relativeLayoutSeparatorParam);
        tv6.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
        tv6.setTextColor(Color.BLACK);
        tv6.setText("\n");
        tv6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv6);

        card.addView(relativeLayout);

        // Finally, add the CardView in root layout
        RelativeLayout mRelativeLayout = findViewById(R.id.cardContainer);
        mRelativeLayout.addView(card);
        Log.i("DESC", "Fin de la construction de la card view");




        CardView cardDispo = new CardView(this);

        // Set the CardView layoutParams
        RelativeLayout.LayoutParams paramsDispo = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        paramsDispo.addRule(RelativeLayout.BELOW, R.id.android_card_view_complement);
        paramsDispo.setMargins(0,0,0,4);

        cardDispo.setLayoutParams(paramsDispo);

        //card.setCardElevation(9);

        RelativeLayout relativeLayoutDispo = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutParamDispo = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        relativeLayoutDispo.setLayoutParams(relativeLayoutParamDispo);

        //TextView titre
        TextView tvDispoTitre = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParamDispo = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParamDispo.setMargins(5,0,0,0);
        textViewLayoutParamDispo.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewLayoutParamDispo.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tvDispoTitre.setLayoutParams(textViewLayoutParamDispo);
        tvDispoTitre.setPadding(titreComplementaire.getPaddingLeft(),titreComplementaire.getPaddingTop(),titreComplementaire.getPaddingRight(),titreComplementaire.getPaddingBottom());
        tvDispoTitre.setTextColor(Color.BLACK);
        tvDispoTitre.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvDispoTitre.setText("Disponibilité");
        tvDispoTitre.setGravity(Gravity.CENTER);
        tvDispoTitre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        tvDispoTitre.setTypeface(null, Typeface.BOLD);
        tvDispoTitre.setId(R.id.disponibiliteTitreSalle);
        // Put the TextView in RelativeLayout
        relativeLayoutDispo.addView(tvDispoTitre);

        View lineDispo = new View(this);
        RelativeLayout.LayoutParams lineViewLayoutParamDispo = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lineViewLayoutParamDispo.addRule(RelativeLayout.BELOW, R.id.disponibiliteTitreSalle);
        lineViewLayoutParamDispo.height = (int)(convertDpToPx(this,2f ));
        lineViewLayoutParamDispo.setMargins(0,(int)(convertDpToPx(this,5f )),0,0);
        lineDispo.setBackground(new ColorDrawable(Color.BLACK));
        lineDispo.setId(R.id.lineSepDispo);
        lineDispo.setLayoutParams(lineViewLayoutParamDispo);

        relativeLayoutDispo.addView(lineDispo);

        //Titre type salle
        TextView tv2Dispo = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam2Dispo = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam2Dispo.addRule(RelativeLayout.BELOW, R.id.lineSepDispo);
        tv2Dispo.setLayoutParams(textViewLayoutParam2Dispo);
        tv2Dispo.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv2Dispo.setTextColor(Color.BLACK);
        tv2Dispo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2Dispo.setText("Etat actuel (h à h)");
        tv2Dispo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv2Dispo.setTypeface(null, Typeface.BOLD);
        tv2Dispo.setId(R.id.etatDispoTitre);
        // Put the TextView in RelativeLayout
        relativeLayoutDispo.addView(tv2Dispo);

        TextView tvDispo = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParamtvDispo = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParamtvDispo.addRule(RelativeLayout.BELOW, R.id.etatDispoTitre);
        tvDispo.setLayoutParams(textViewLayoutParamtvDispo);
        tvDispo.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
        tvDispo.setTextColor(Color.BLACK);
        tvDispo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvDispo.setText("Salle ");
        tvDispo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvDispo.setId(R.id.etatDispoSalle);
        // Put the TextView in RelativeLayout
        relativeLayoutDispo.addView(tvDispo);

        TextView tvDispoLien = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParamtvDispoEtat = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParamtvDispoEtat.addRule(RelativeLayout.BELOW, R.id.etatDispoSalle);
        tvDispoLien.setLayoutParams(textViewLayoutParamtvDispoEtat);
        tvDispoLien.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
        tvDispoLien.setTextColor(Color.BLACK);
        tvDispoLien.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvDispoLien.setText("Lien salle disponible");
        tvDispoLien.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvDispoLien.setId(R.id.etatDispoLien);
        // Put the TextView in RelativeLayout
        relativeLayoutDispo.addView(tvDispoLien);


        cardDispo.addView(relativeLayoutDispo);

        // Finally, add the CardView in root layout
        //RelativeLayout mRelativeLayoutDispo = findViewById(R.id.cardContainer);
        mRelativeLayout.addView(cardDispo);

        viewPager.setAdapter(new SamplePagerAdapter(SalleDescriptionActivity.this.getApplicationContext(),selecteur, salleId));
    }

    private void genererSalleDescription(Intent i, BottomNavigationView navigation) throws IOException {
        Log.i("DESC", "Salle Univ type");
        Room room = (Room) i.getSerializableExtra("salleDescription");
        Building building = mDb.buildingModel().getBatiment(room.getIdBuilding());
        List<Integer> typeListId = mDb.typeRoomModel().getIdTypeByIdRoom(room.getIdRoom());
        List<Integer> equipementListId = mDb.roomEquipmentModel().getIdEquipementByIdSalle(room.getIdRoom());

        List<Type> typeList = new ArrayList<>();
        for(Integer idType : typeListId){
            typeList.add(mDb.typeModel().loadTypeById(idType));
        }
        if(!typeListId.contains(5)) {
            Menu menuNav = navigation.getMenu();
            MenuItem nav_item2 = menuNav.findItem(R.id.navigation_logiciels);
            nav_item2.setEnabled(false);
        }
        List<Equipment> equipementsList = new ArrayList<>();
        if(equipementListId.size()>0) {
            for (Integer idEquipement : equipementListId) {
                equipementsList.add(mDb.equipmentModel().getEquipmentByIdEquipment(idEquipement));
            }
        }else {
            equipementsList.add(new Equipment(0,"Aucun équipement trouvé pour cette salle"));
        }
        scrollView = findViewById(R.id.home_description);

        TextView nomSalle = findViewById(R.id.nom_salle_desc);
        nomSalle.setText(room.getNameRoom());
        TextView codeSalle = findViewById(R.id.code_desc);
        codeSalle.setText(room.getCodeRoom());
        TextView titreComplementaire = findViewById(R.id.information_complementaire);//467 - 511 = > 45
        TextView titreSubTitre1 = findViewById(R.id.sub_titre1);
        titreSubTitre1.setText("Bâtiment");

        TextView paraInfoComp1 = findViewById(R.id.para_info_comp1);
        paraInfoComp1.setText(building.getNameBuilding());

        TextView titreSubTitre2 = findViewById(R.id.sub_titre2);
        titreSubTitre2.setText("Code du bâtiment");

        TextView paraInfoComp2 = findViewById(R.id.para_info_comp2);
        paraInfoComp2.setText(building.getCodeBuilding());

        TextView titreSubTitre3 = findViewById(R.id.sub_titre3);
        titreSubTitre3.setText("Nom complet du bâtiment");

        TextView paraInfoComp3 = findViewById(R.id.para_info_comp3);
        paraInfoComp3.setText(building.getCompleteNameBuilding());

        CardView card = new CardView(this);

        // Set the CardView layoutParams
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.android_card_view_batiment);
        params.setMargins(0,0,0,4);

        card.setLayoutParams(params);

        //card.setCardElevation(9);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        relativeLayout.setLayoutParams(relativeLayoutParam);

        //TextView titre
        TextView tv = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam.setMargins(5,0,0,0);
        textViewLayoutParam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewLayoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tv.setLayoutParams(textViewLayoutParam);
        tv.setPadding(titreComplementaire.getPaddingLeft(),titreComplementaire.getPaddingTop(),titreComplementaire.getPaddingRight(),titreComplementaire.getPaddingBottom());
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText("Caractéristiques de la salle");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setId(R.id.caracteristiqueSalle);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv);

        View line = new View(this);
        RelativeLayout.LayoutParams lineViewLayoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        lineViewLayoutParam.addRule(RelativeLayout.BELOW, R.id.caracteristiqueSalle);
        lineViewLayoutParam.height = (int)(convertDpToPx(this,2f ));
        lineViewLayoutParam.setMargins(0,(int)(convertDpToPx(this,5f )),0,0);
        line.setBackground(new ColorDrawable(Color.BLACK));
        line.setId(R.id.lineSepCarac);
        line.setLayoutParams(lineViewLayoutParam);

        relativeLayout.addView(line);

        //Titre type salle
        TextView tv2 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam2.addRule(RelativeLayout.BELOW, R.id.lineSepCarac);
        tv2.setLayoutParams(textViewLayoutParam2);
        tv2.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv2.setTextColor(Color.BLACK);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv2.setText("Type de salle");
        tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv2.setTypeface(null, Typeface.BOLD);
        tv2.setId(R.id.typeSalleTitre);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv2);

        //RelativeLayout contenant le linearLayout des textview des types de salle
        RelativeLayout relativeLayoutContainerTxt = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutContainerTxtParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        relativeLayoutContainerTxtParam.addRule(RelativeLayout.BELOW,
                R.id.typeSalleTitre);
        relativeLayoutContainerTxt.setId(R.id.relativeTypeTextContainer);
        relativeLayoutContainerTxt.setLayoutParams(relativeLayoutContainerTxtParam);

        //LinearLayout contenant les textview des types de salle
        LinearLayout linearLayoutTxt = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutTxtParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutTxt.setLayoutParams(linearLayoutTxtParam);
        linearLayoutTxt.setId(R.id.typeTxtViewContainer);
        linearLayoutTxt.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout.LayoutParams textViewLayoutParam3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam3.setMargins(5,5,5,5);
        for(Type t: typeList) {
            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(textViewLayoutParam3);
            tv3.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
            tv3.setTextColor(Color.BLACK);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setText(t.getTypeSalle());
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            // Put the TextView in RelativeLayout
            linearLayoutTxt.addView(tv3);
        }

        relativeLayoutContainerTxt.addView(linearLayoutTxt);
        relativeLayout.addView(relativeLayoutContainerTxt);

        TextView tv4 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam4.addRule(RelativeLayout.BELOW, R.id.relativeTypeTextContainer);
        tv4.setLayoutParams(textViewLayoutParam4);
        tv4.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv4.setTextColor(Color.BLACK);
        tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv4.setText("Étage de la salle");
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv4.setTypeface(null, Typeface.BOLD);
        tv4.setId(R.id.etageSalleTitre);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv4);

        TextView tv5 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParam5 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParam5.addRule(RelativeLayout.BELOW, R.id.etageSalleTitre);
        tv5.setLayoutParams(textViewLayoutParam5);
        tv5.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
        tv5.setTextColor(Color.BLACK);
        tv5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv5.setText(room.getLevelRoom());
        tv5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv5.setId(R.id.etageSalleContenu);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv5);


        //Titre type salle
        TextView tv7 = new TextView(this);
        RelativeLayout.LayoutParams textViewLayoutParamEquip = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutParamEquip.addRule(RelativeLayout.BELOW, R.id.etageSalleContenu);
        tv7.setLayoutParams(textViewLayoutParamEquip);
        tv7.setPadding(titreSubTitre1.getPaddingLeft(),titreSubTitre1.getPaddingTop(),titreSubTitre1.getPaddingRight(),titreSubTitre1.getPaddingBottom());
        tv7.setTextColor(Color.BLACK);
        tv7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv7.setText("Equipements de salle");
        tv7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv7.setTypeface(null, Typeface.BOLD);
        tv7.setId(R.id.equipementSalleTitre);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv7);

        //RelativeLayout contenant le linearLayout des textview des equipements de salle
        RelativeLayout relativeLayoutContainerEquipTxt = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeLayoutContainerEquipTxtParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        relativeLayoutContainerEquipTxtParam.addRule(RelativeLayout.BELOW,
                R.id.equipementSalleTitre);

        relativeLayoutContainerEquipTxt.setId(R.id.relativeEquipementTextContainer);
        relativeLayoutContainerEquipTxt.setLayoutParams(relativeLayoutContainerEquipTxtParam);

        //LinearLayout contenant les textview des equipements de salle
        LinearLayout linearLayoutEquipementTxt = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutEquipementTxtParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutEquipementTxt.setLayoutParams(linearLayoutEquipementTxtParam);
        linearLayoutEquipementTxt.setId(R.id.equipementTxtViewContainer);
        linearLayoutEquipementTxt.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout.LayoutParams textViewLayoutEquipParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewLayoutEquipParam.setMargins(5,5,5,5);
        for(Equipment t: equipementsList) {
            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(textViewLayoutEquipParam);
            tv3.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
            tv3.setTextColor(Color.BLACK);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setText(t.getNameEquipment());
            tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            // Put the TextView in RelativeLayout
            linearLayoutEquipementTxt.addView(tv3);
        }

        relativeLayoutContainerEquipTxt.addView(linearLayoutEquipementTxt);
        relativeLayout.addView(relativeLayoutContainerEquipTxt);


        RelativeLayout.LayoutParams relativeLayoutSeparatorParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        TextView tv6 = new TextView(this);
        relativeLayoutSeparatorParam.addRule(RelativeLayout.BELOW, R.id.relativeEquipementTextContainer);
        tv6.setLayoutParams(relativeLayoutSeparatorParam);
        tv6.setPadding(paraInfoComp1.getPaddingLeft(), paraInfoComp1.getPaddingTop(), paraInfoComp1.getPaddingRight(), paraInfoComp1.getPaddingBottom());
        tv6.setTextColor(Color.BLACK);
        tv6.setText("\n");
        tv6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        // Put the TextView in RelativeLayout
        relativeLayout.addView(tv6);

        card.addView(relativeLayout);

        // Finally, add the CardView in root layout
        RelativeLayout mRelativeLayout = findViewById(R.id.cardContainer);
        mRelativeLayout.addView(card);

        viewPager.setAdapter(new SamplePagerAdapter(SalleDescriptionActivity.this.getApplicationContext(),selecteur, room.getIdRoom()));
    }

    private void genererBatimentDetail(BottomNavigationView navigation) throws IOException {
        Log.i("DESC", "Batiment type");

        Menu menuNav=navigation.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.navigation_logiciels);
        nav_item2.setEnabled(false);

        Building building = (Building) getIntent().getSerializableExtra("batimentDescription");
        scrollView = findViewById(R.id.home_description);

        TextView nomBatiment = findViewById(R.id.nom_salle_desc);
        nomBatiment.setText(building.getCompleteNameBuilding());
        TextView nomCompletBatiment = findViewById(R.id.code_desc);
        nomCompletBatiment.setText(building.getNameBuilding());

        TextView titreSubTitre1 = findViewById(R.id.sub_titre1);
        titreSubTitre1.setText("Bâtiment");

        TextView paraInfoComp1 = findViewById(R.id.para_info_comp1);
        paraInfoComp1.setText(building.getNameBuilding());

        TextView titreSubTitre2 = findViewById(R.id.sub_titre2);
        titreSubTitre2.setText("Code du bâtiment");

        TextView paraInfoComp2 = findViewById(R.id.para_info_comp2);
        paraInfoComp2.setText(building.getCodeBuilding());

        TextView titreSubTitre3 = findViewById(R.id.sub_titre3);
        titreSubTitre3.setText("Nom complet du bâtiment");

        TextView paraInfoComp3 = findViewById(R.id.para_info_comp3);
        paraInfoComp3.setText(building.getCompleteNameBuilding());

        viewPager.setAdapter(new SamplePagerAdapter(SalleDescriptionActivity.this.getApplicationContext(), selecteur, building.getIdBuilding()));

            /*
            int nbPlans = 2;

            LinearLayout grid_map = findViewById(R.id.mapContainer);

            if(nbPlans >0) {
                //grid_map.setRowCount(nbPlans);
                for (int indicePlan = 0; indicePlan < nbPlans; indicePlan++) {
                    PhotoView p = new PhotoView(this);
                    p.setVisibility(View.GONE);
                    p.setImageResource(R.drawable.ic_launcher_background);
                    photoViewList.add(p);
                    grid_map.addView(p);
                }
            }else{
                //grid_map.setRowCount(1);
                PhotoView p = new PhotoView(this);
                p.setVisibility(View.GONE);
                p.setImageResource(R.drawable.pas_image_disponible);
                photoViewList.add(p);
                grid_map.addView(p);
            }*/


    }

    @Override
    public void onBackPressed() {
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

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


    static class SamplePagerAdapter extends PagerAdapter {

        private static Drawable[] sDrawables;

        SamplePagerAdapter(){
            super();
        }

        SamplePagerAdapter(Context context, int selecteurType, int id) {
            super();
            sDrawables = null;
            AssetManager am = context.getAssets();
            String path;
            switch(selecteurType){
                //Salle
                case 0:
                    path = "plan/salles/";
                    setPlan(am, path,context,"");
                    break;
                //Batiment
                case 1:

                    path = "plan/batiments/";

                    switch(id){
                        case 1:
                            setPlan(am, path,context, "");
                            break;
                        case 2:
                            setPlan(am, path,context, "batiment_c");
                            break;
                        case 3:
                            setPlan(am, path,context, "");
                            break;
                        case 4:
                            setPlan(am, path,context, "");
                            break;
                        case 5:
                            setPlan(am, path,context, "");
                            break;
                        case 6:
                            setPlan(am, path,context, "");
                            break;
                        case 7:
                            setPlan(am, path,context, "");
                            break;
                        case 8:
                            setPlan(am, path,context, "");
                            break;
                        case 9:
                            setPlan(am, path,context, "");
                            break;
                        case 10:
                            setPlan(am, path,context, "");
                            break;
                        case 11:
                            setPlan(am, path,context, "");
                            break;
                        case 12:
                            setPlan(am, path,context, "");
                            break;
                        case 13:
                            setPlan(am, path,context, "");
                            break;
                        case 14:
                            setPlan(am, path,context, "");
                            break;
                        case 15:
                            setPlan(am, path,context, "");
                            break;
                    }
            }
        }

        private void setPlan(AssetManager am, String path, Context context, String nom){
            try {
                path += nom;
                String[] files = am.list(path);
                sDrawables = new Drawable[files.length];
                Log.i("DRAWABLE", "nb de fichiers : " + files.length);
                InputStream inputStream;
                int i = 0;
                for (String file : files) {
                    Log.i("DRAWABLE", ": " + path + "/" + file);
                    inputStream = am.open(path + "/" + file);
                    Drawable d = Drawable.createFromStream(inputStream, null);
                    Log.i("DRAWABLE", ": " + d);
                    sDrawables[i] = d;
                    i++;
                }
                if (files.length==0) {
                    sDrawables = new Drawable[1];
                    Log.i("DRAWABLE",""+sDrawables);
                    Drawable myIcon = context.getResources().getDrawable(R.drawable.pas_image_disponible);
                    Log.i("DRAWABLE", ""+myIcon);
                    sDrawables[0] = myIcon;
                }
            }catch(IOException io){
                Drawable myIcon = context.getResources().getDrawable(R.drawable.pas_image_disponible);
                sDrawables[0] = myIcon;
            }
        }

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            //photoView.setImageResource(sDrawables[position]);
            photoView.setImageDrawable(sDrawables[position]);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
