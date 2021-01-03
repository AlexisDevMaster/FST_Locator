package com.theluckydev.fst;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationCameraTransitionListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.theluckydev.fst.SalleBatimentDatabase.CleStatic;
import com.theluckydev.fst.SalleBatimentDatabase.Database.AppDatabase;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.AliasBuildingSearch;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.AliasRoomSearch;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Building;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.EntreeSalle;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Equipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphEdge;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNode;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.GraphNodeType;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.RoomEquipment;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.RoomSoftware;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Software;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.SurfacePoint;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Toilette;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Type;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.TypeRoom;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.UpToDate;
import com.theluckydev.fst.graph.GraphController;
import com.theluckydev.fst.graph.Vertex;
import com.theluckydev.fst.objet.Objet;
import com.theluckydev.fst.objet.adapter.itineraire.ItineraireAdapter;
import com.theluckydev.fst.objet.adapter.search.SearchAdapter;
import com.theluckydev.fst.outils.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ROTATION_ALIGNMENT_VIEWPORT;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;
import static com.theluckydev.fst.map.CleStatic.MARKER_ICONE;
import static com.theluckydev.fst.map.CleStatic.MARKER_ICONE_PURPLE;
import static com.theluckydev.fst.map.CleStatic.NODE_ICONE_PURPLE;
import static com.theluckydev.fst.map.CleStatic.SAVED_STATE_CAMERA;
import static com.theluckydev.fst.map.CleStatic.SAVED_STATE_LOCATION;
import static com.theluckydev.fst.map.CleStatic.SAVED_STATE_RENDER;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, OnLocationClickListener, OnCameraTrackingChangedListener {

    private static final int PERMISSION_CODE_WRITE = 101;
    private static final int PERMISSION_CODE_LOCATION = 102;
    private MapView mapView;
    private MapboxMap mapboxMap;

    private Button locationModeBtn;
    private Button locationTrackingBtn;
    private LocationComponent locationComponent;
    private Location lastLocation;

    @CameraMode.Mode
    private int cameraMode = CameraMode.TRACKING;
    @RenderMode.Mode
    private int renderMode = RenderMode.NORMAL;
    private SymbolManager symbolManager;
    private List<Symbol> symbolsRoom = new ArrayList<>();
    private List<Symbol> symbolsBuilding = new ArrayList<>();
    private List<Symbol> symbolsItineraire = new ArrayList<>();


    //    private List<Symbol> symbolsRoom = new ArrayList<>();
    GraphController graphController = new GraphController(MainActivity.this);

    //---------------------------------Room Attributs-----------------------------------------------
    private AppDatabase mDb;
    //-----------------------------------Fin--------------------------------------------------------


    //-----------------------------------Intégrité de la BDD (mise à jour Salle)--------------------------
    private ProgressDialog pDialog;
    //-------------------------------------------fin------------------------------------------------

    List<Objet> listeSalleBatiments;

    private SearchView searchView;
    // List view
    private ListView lv;
    //Flag vérification récupération JSON
    private int success;
    Objet objetCourant;

    //Adapter qui enregistre les salle de la FST
    SearchAdapter adapter;
    //Adapter qui enregistre les salle de la FST après filtre
    SearchAdapter lstFoundAdapter;
    private PermissionsManager permissionsManager;
    // The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Récupération d'une instance de MapBox
        Mapbox.getInstance(this, "pk.eyJ1IjoidGhlbHVja3lkZXYiLCJhIjoiY2puZDZvZnUxMDR3ejN2bm1qdGFsM2N0eCJ9.bPvB11owmKkvLYYWraOUnA");

        //Mise en place de l'interface
        setContentView(R.layout.activity_main);
        
        //Définition de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Mise en place de la toolbar
        setSupportActionBar(toolbar);

        //Initialisatin de MapBox
        mapView = findViewById(R.id.mapView);
        if (savedInstanceState != null) {
            cameraMode = savedInstanceState.getInt(SAVED_STATE_CAMERA);
            renderMode = savedInstanceState.getInt(SAVED_STATE_RENDER);
            lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION);
        }
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //Fin intialisation de Maps

        //Definition du DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Definition du NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initNavigationViewItem(navigationView);

        //Initialisation de la listview pour la recherche
        lv = findViewById(R.id.listView);

        //Récupération de l'instnvce de la base de données interne
        mDb = AppDatabase.getInstance(this);

        //Verification maj + maj si besoin
        if(haveInternetConnection()) {
            if (!hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            } else {
                new MiseAJourDB(this).execute();
            }
        }else
            Toast.makeText(MainActivity.this, "Pas d'accès à internet", Toast.LENGTH_SHORT).show();

        initItineraireUI();

    }

    private void initItineraireUI(){
        AutoCompleteTextView autoCompleteTextViewStart = findViewById(R.id.autoCompleteTextViewStart);
        List<Objet> listObjets = new ArrayList<>();
        listObjets.addAll(mDb.roomModel().getAll());
//        listObjets.addA/
        ItineraireAdapter salleAdapterStart = new ItineraireAdapter(this, listObjets);
        autoCompleteTextViewStart.setAdapter(salleAdapterStart);
        autoCompleteTextViewStart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);
                salleAdapterStart.setCurrentItem(salleAdapterStart.getItem(position));
            }
        });


        AutoCompleteTextView autoCompleteTextViewEnd = findViewById(R.id.autoCompleteTextViewEnd);
        ItineraireAdapter salleAdapterStop = new ItineraireAdapter(this, listObjets);
        autoCompleteTextViewEnd.setAdapter(salleAdapterStop);
        autoCompleteTextViewEnd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);
                salleAdapterStop.setCurrentItem(salleAdapterStop.getItem(position));
            }
        });

        Button cancelItineraire = findViewById(R.id.cancelItineraire);
        cancelItineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                AppBarLayout itinearaireBar = findViewById(R.id.itineraireBar);
                toggle(itinearaireBar);
            }
        });

        Button startItineraire = findViewById(R.id.startItineraire);
        startItineraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                AppBarLayout itinearaireBar = findViewById(R.id.itineraireBar);
                if(salleAdapterStart.getCurrentItem() != null && salleAdapterStop.getCurrentItem() != null) {
                    toggle(itinearaireBar);
                    int start = -1;
                    int destination = -1;
                    List<Integer> nodeList = getIdNodeStartOrDestinationItineraire(salleAdapterStart.getCurrentItem());
                    if (nodeList.size() > 0)
                        start = getIdNodeStartOrDestinationItineraire(salleAdapterStart.getCurrentItem()).get(0);

                    nodeList = getIdNodeStartOrDestinationItineraire(salleAdapterStop.getCurrentItem());
                    if (nodeList.size() > 0)
                        destination = getIdNodeStartOrDestinationItineraire(salleAdapterStop.getCurrentItem()).get(0);
                    if (start != -1 && destination != -1) {
                        List<Vertex> listeItineraire = graphController.shortestWay(start, destination);
                        if (listeItineraire == null) {
                            Toast.makeText(MainActivity.this, "Aucun chemin n'a pu être trouvé", Toast.LENGTH_SHORT).show();
                        } else {
                            drawItineraire(listeItineraire);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Une erreur est survenue lors du calcul de l'itinéraire", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Sélectionnez un départ et une arrivée", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<Integer> getIdNodeStartOrDestinationItineraire(Objet objet){
        switch(objet.getType()){
            case "salle":
                Room room = (Room)objet;
                return mDb.entryRoomModel().getAllIdNodeByIdRoom(room.getIdRoom());
            case "batiment":
                Building building = (Building)objet;
                return mDb.entryBuildingModel().getAllIdNodeByIdRoom(building.getIdBuilding());
            default :
                return new ArrayList<>();
        }
    }

    /**
     * Function who draw the route beetween two points
     * @param listeItineraire, list of the vertex from the graph
     */
    private void drawItineraire(List<Vertex> listeItineraire) {
        //Reset list of symbol for route
        symbolsItineraire = new ArrayList<>();
        //Add Start point of the route in the list
        symbolsItineraire.add(symbolManager.create(new SymbolOptions()
                .withLatLng(new LatLng(listeItineraire.get(0).getLat(),
                        listeItineraire.get(0).getLng()))
                .withIconImage(MARKER_ICONE_PURPLE)
                .withIconSize(0.3f)
                .withIconOffset(new Float[]{0f, -1.5f})
                .withTextField("Start")
                .withTextHaloColor("rgba(255, 255, 255, 100)")
                .withTextHaloWidth(0.2f)
                .withTextOffset(new Float[] {0f, 1.5f})
                .withDraggable(false)
        ));

        //Add end point of the route to the list
        symbolsItineraire.add(symbolManager.create(new SymbolOptions()
                .withLatLng(new LatLng(listeItineraire.get(listeItineraire.size()-1).getLat(),
                        listeItineraire.get(listeItineraire.size()-1).getLng()))
                .withIconImage(MARKER_ICONE_PURPLE)
                .withIconSize(0.3f)
                .withIconOffset(new Float[]{0f, -1.5f})
                .withTextField("End")
                .withTextHaloColor("rgba(255, 255, 255, 100)")
                .withTextHaloWidth(0.2f)
                .withTextOffset(new Float[] {0f, 1.5f})
                .withDraggable(false)
        ));
        //Erase old style and layer to clean any old route
        mapboxMap.getStyle().removeLayer("linelayer");
        mapboxMap.getStyle().removeSource("line-source");
        //List of point used to create line
        List<Point> routeCoordinates = new ArrayList<>();
        for(Vertex v : listeItineraire){
            routeCoordinates.add(Point.fromLngLat(v.getLng(), v.getLat()));
        }
        //Add layer and source to draw the lines of the route
        mapboxMap.getStyle().addLayer(new LineLayer("linelayer", "line-source").withProperties(
                PropertyFactory.lineDasharray(new Float[] {0.01f, 2f}),
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                PropertyFactory.lineWidth(5f),
                PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
        ));
        mapboxMap.getStyle().addSource(new GeoJsonSource("line-source",
                FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(
                        LineString.fromLngLats(routeCoordinates)
                )})));

    }


    private void initNavigationViewItem(NavigationView navigationView) {
        //----Initialisation Checkbox Batiment (Drawer)
        MenuItem drawerCheckboxBuilding = navigationView.getMenu().findItem(R.id.drawer_checkbox_building);
        CompoundButton drawerCheckboxBuildingCompundButton = (CompoundButton) drawerCheckboxBuilding.getActionView();
        drawerCheckboxBuildingCompundButton.setChecked(true);

        drawerCheckboxBuildingCompundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableBuildingMarker(drawerCheckboxBuildingCompundButton.isChecked());
            }
        });

        //----Initialisation Checkbox Salle (Drawer)
        MenuItem drawerCheckboxRoom = navigationView.getMenu().findItem(R.id.drawer_checkbox_room);
        CompoundButton drawerCheckboxRoomCompundButton = (CompoundButton) drawerCheckboxRoom.getActionView();
        drawerCheckboxRoomCompundButton.setChecked(true);
        drawerCheckboxRoomCompundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableRoomMarker(drawerCheckboxRoomCompundButton.isChecked());
            }
        });
    }

    private void enableDisableBuildingMarker(boolean isChecked) {
        Log.i("CLEAN", ""+isChecked);
        if(isChecked) {
            try {
                symbolsBuilding.clear();
                updateBuildingMarker();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            for(Symbol smb: symbolsBuilding){
                symbolManager.delete(smb);
            }
        }
    }

    private void enableDisableRoomMarker(boolean isChecked) {
        if(isChecked) {
            try {
                symbolsRoom.clear();
                updateBuildingMarker();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            for(Symbol smb: symbolsRoom){
                symbolManager.delete(smb);
            }
        }
    }

    /**
     * @param l
     */
    public void setItemEvent(final List<Objet> l) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setIconified(true);
                int indiceItem = (int) parent.getItemIdAtPosition(position);
                //Génération du marker sur la carte
                mapboxMap.clear();
                if (l.get(indiceItem) instanceof Room) {
                    Room room = (Room) l.get(indiceItem);
                    //Affichage du marker du batiment
                    Marker marker = mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(room.getLatitude()), Double.parseDouble(room.getLongitude())))
                            .title(room.getNameRoom()));
                    marker.setTitle(room.getNameRoom());
                    marker.showInfoWindow(mapboxMap, mapView);
                } else if (l.get(indiceItem) instanceof Toilette) {
                    Toilette toilette = (Toilette) l.get(indiceItem);
                    //Affichage du marker du toilette
                    Marker marker = mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(toilette.getLatitude()), Double.parseDouble(toilette.getLongitude())))
                            .title(toilette.getStandardName()));
                    marker.setTitle(toilette.getStandardName());
                    marker.showInfoWindow(mapboxMap, mapView);
                } else {
                    Building building = (Building) l.get(indiceItem);
                    //Affichage du marker du batiment
                    Marker marker = mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(building.getLatitude()), Double.parseDouble(building.getLongitude())))
                            .title(building.getNameBuilding()));
                    marker.setTitle(building.getNameBuilding());
                    marker.showInfoWindow(mapboxMap, mapView);
                    List<SurfacePoint> surfacePointList = mDb.surfacePointModel().getSurfacePoint(building.getIdBuilding());
                    //Affichage de la surface du batiment
                    if (surfacePointList.size() != 0) {
                        PolygonOptions polygonOptions = new PolygonOptions()
                                .strokeColor(Color.RED)
                                .fillColor(Color.argb(100, 63, 127, 191));
                        for (SurfacePoint surface : surfacePointList) {
                            polygonOptions.add(new LatLng(Double.parseDouble(surface.getLatitude()), Double.parseDouble(surface.getLongitude())));
                        }
                        mapboxMap.addPolygon(polygonOptions);
                    }
                }
                objetCourant = l.get(indiceItem);
                lv.setVisibility(View.GONE);
                //Fermeture de la zone de recherche
                searchView.setIconified(true);
                searchView.clearFocus();
                Toolbar toolbar = findViewById(R.id.toolbar);
                toolbar.collapseActionView();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchBar:
                if (!searchView.isIconified()) {
                    lv.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.GONE);
                }
                break;
            case R.id.action_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.action_advanced_search:
                Intent advancedResearch = new Intent(this, RechercheAvanceeActivity.class);
                startActivityForResult(advancedResearch, 1);
                break;
            case R.id.clear_map:
                mapboxMap.clear();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.searchBar);
        searchView = (SearchView) myActionMenuItem.getActionView();
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                lv.setVisibility(View.GONE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("Query");
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                    lv.setVisibility(View.VISIBLE);
                } else {
                    lv.setVisibility(View.GONE);
                }
                searchView.setIconified(true);
                searchView.clearFocus();

                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty() && s.length() > 1) {
                    String newTextU = s.toLowerCase();
                    List<Objet> lstFound = new ArrayList<>();
                    for (Objet item : listeSalleBatiments) {
                        if (item.getStandardName().toLowerCase().contains(newTextU) ||
                                item.hasForAlias(newTextU))
                            lstFound.add(item);
                    }
                    lstFoundAdapter = new SearchAdapter(MainActivity.this, lstFound);
                    lv.setAdapter(lstFoundAdapter);
                    setItemEvent(lstFound);
                } else {
                    lv.setAdapter(adapter);
                    setItemEvent(listeSalleBatiments);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();
        switch(id) {
            case R.id.drawer_checkbox_building:
                Log.i("CLEAN", "Nique");
                break;
            case R.id.salle_libre:
                Intent intent = new Intent(this, SalleLibre.class);
                startActivity(intent);
                break;
            case R.id.gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Methode qui met à jour les listes de recherche
     */
    private void mettreAJourListeRecherche() {
        //Recuperation des salles
        listeSalleBatiments = new ArrayList<>();
        List<Building> buildings = mDb.buildingModel().getAll();
        for (Building b : buildings){
            b.setAliasList(mDb.aliasBuildingSearchModel().getAllAliasSearchByIdBuilding(b.getIdBuilding()));
        }
        listeSalleBatiments.addAll(buildings);

        List<Room> rooms = mDb.roomModel().getAll();
        for (Room r : rooms){
            r.setAliasList(mDb.aliasRoomSearchModel().getAllAliasSearchByIdRoom(r.getIdRoom()));
        }
        listeSalleBatiments.addAll(rooms);

        //Ajouter à la liste de recherche et mise en place des alias
        int indice = 0;
        for (Objet obj : listeSalleBatiments) {
            obj.indice = indice;
            indice++;
        }

        //Définition de l'Adaptater avec les salles de la FST (liste salles)
        adapter = new SearchAdapter(this, listeSalleBatiments);

        //Définition de l'Adaptater avec les nom des salles de la FST (liste salleNom)
        //adapterName = new ArrayAdapter<>(this, R.layout.list_item, R.id.product_name, salleBatimentNom);

        //Mise en place de la liste de nom dans la vue
        lv.setAdapter(adapter);
        //setEvent();
        //Définition des évènements des itels de la liste
        setItemEvent(listeSalleBatiments);
        //Cacher la liste de recherche
        lv.setVisibility(View.GONE);
    }

    private void updateAll() throws JSONException {
        updateMarker();
        graphController.initByBDD(mDb);
    }

    /**
     * Methode qui lit la BDD et ajoute les marker  à la carte
     */
    private void updateMarker() throws JSONException {
        updateBuildingMarker();
    }

    private void updateBuildingMarker() throws JSONException {
        List<Building> buildingList = mDb.buildingModel().getAll();
        for (Building b : buildingList) {
            JSONObject obj = new JSONObject();
            obj.put("type", "batiment");
            obj.put("id", b.getIdBuilding());
            symbolsBuilding.add(symbolManager.create(new SymbolOptions()
                    .withLatLng(new LatLng(Double.parseDouble(b.getLatitude()), Double.parseDouble(b.getLongitude())))
                    .withIconImage(MARKER_ICONE)
                    //set the below attributes according to your requirements
                    .withIconSize(0.3f)
                    .withIconOffset(new Float[]{0f, -1f})
                    .withTextField(b.getNameBuilding())
                    .withTextHaloColor("rgba(255, 255, 255, 100)")
                    .withTextHaloWidth(0.2f)
                    .withTextAnchor(b.getCodeBuilding())
                    .withTextOffset(new Float[] {0f, 1f})
                    .withDraggable(false)
                    .withData(JsonParser.parseString(obj.toString()))
            ));
            List<SurfacePoint> surfacePointList = mDb.surfacePointModel().getSurfacePoint(b.getIdBuilding());
            if (surfacePointList.size() != 0) {
                PolygonOptions polygonOptions = new PolygonOptions()
                        .strokeColor(Color.RED)
                        .fillColor(Color.argb(100, 63, 127, 191));
                for (SurfacePoint surface : surfacePointList) {
                    polygonOptions.add(new LatLng(Double.parseDouble(surface.getLatitude()), Double.parseDouble(surface.getLongitude())));
                }
                if (mapboxMap != null)
                    mapboxMap.addPolygon(polygonOptions);
            }
        }
    }

    private boolean haveInternetConnection() {
        // Fonction haveInternetConnection : return true si connecté, return false dans le cas contraire
        NetworkInfo network = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        // Le périphérique n'est pas connecté à Internet
        return network != null && network.isConnected();
        // Le périphérique est connecté à Internet
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Méthode qui génère la boite de dialogue pour choisir entre itinéraire ou plus d'information de la salle
     *
     * @param t,    nom de la salle
     * @param type, paramètre à changer
     */
    private void buildDialog(String t, String type) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        TextView title = popupView.findViewById(R.id.textPopup);
        title.setText(t);
        popupWindow.setAnimationStyle(R.style.DialogAnimation_2);//AsDropDown(btnOpenPopup, 50, -30);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

        //Récupération du bouton pour charger la page de visualisation des informations de la salle
        Button plus = popupView.findViewById(R.id.plus_bouton_popup);
        //Mise en place de l'évènement pour afficher la page de visualisation des évènements
        plus.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SalleDescriptionActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Log.i("INTENT", "" + objetCourant);
                if (objetCourant instanceof Building) {
                    Building objet = (Building) objetCourant;
                    i.putExtra("batimentDescription", objet);
                } else if (objetCourant instanceof Room) {
                    Room objet = (Room) objetCourant;
                    i.putExtra("salleDescription", objet);
                }
                startActivity(i);
            }
        });

        if (objetCourant instanceof Toilette || objetCourant == null) {
            plus.setEnabled(false);
        }

        //Récupération du bouton pour charger la page de visualisation des informations de la salle
        Button itineraire = popupView.findViewById(R.id.itineraire_bouton_popup);
        //Mise en place de l'évènement pour afficher la page de visualisation des évènements
        itineraire.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                AppBarLayout itinearaireBar = findViewById(R.id.itineraireBar);
                toggle(itinearaireBar);
                popupWindow.dismiss();
            }
        });
    }

    private void toggle(View view) {
        Transition transition = new Slide(Gravity.TOP);
        transition.setDuration(600);
        transition.addTarget(R.id.itineraireBar);
        TransitionManager.beginDelayedTransition((ViewGroup)view.getParent(), transition);

        boolean show = false;
        if(view.getVisibility()== View.GONE)
            show = true;
        view.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle salleExtra = data.getExtras();
            int salleId = salleExtra.getInt("salle");

            Room room = mDb.roomModel().getSalle(salleId);
            Marker m = mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(room.getLatitude()), Double.parseDouble(room.getLongitude())))
                    .title(room.getNameRoom())

            );
            m.showInfoWindow(mapboxMap, mapView);
            m.setTitle(room.getNameRoom());
            CameraUpdate cu = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(room.getLatitude()), Double.parseDouble(room.getLongitude())));
            mapboxMap.moveCamera(cu);
        }

    }

    ///------------------------------------------------------------------------------------------///
    ///------------------------------------------------------------------------------------------///
    ///---------------------------------Debut fonction MapBox------------------------------------///
    ///------------------------------------------------------------------------------------------///
    ///------------------------------------------------------------------------------------------///
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            BuildingPlugin buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);

            this.symbolManager = new SymbolManager(mapView, mapboxMap, style);
            symbolManager.setIconAllowOverlap(true);
            symbolManager.setTextAllowOverlap(false);
            symbolManager.setIconRotationAlignment(ICON_ROTATION_ALIGNMENT_VIEWPORT);

            symbolManager.addClickListener(new OnSymbolClickListener() {
                @Override
                public void onAnnotationClick(Symbol symbol) {
                    getObjectBySymbol(symbol);
                }
            });

            mapboxMap.getStyle().addImage(MARKER_ICONE, Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.red_marker))),
                    false);
            mapboxMap.getStyle().addImage(MARKER_ICONE_PURPLE, Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.purple_marker))),
                    false);
            mapboxMap.getStyle().addImage(NODE_ICONE_PURPLE, Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.red_marker))),
                    false);

            LatLng nancy = new LatLng(48.665465, 6.159111);

            LatLng one = new LatLng(48.663582, 6.153412);
            LatLng two = new LatLng(48.667300, 6.164254);
            LatLngBounds RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
                    .include(one)
                    .include(two)
                    .build();
            mapboxMap.setLatLngBoundsForCameraTarget(RESTRICTED_BOUNDS_AREA);

            enableLocationComponent(style);

            symbolManager.create(new SymbolOptions()
                            .withLatLng(new LatLng(48.667038, 6.159693))
                            .withIconImage(MARKER_ICONE_PURPLE)
                            //set the below attributes according to your requirements
                            .withIconSize(0.3f)
                            .withIconOffset(new Float[]{0f, -1.5f})
                            .withTextField("Faculté des Sciences et Technologies")
                            .withTextHaloColor("rgba(255, 255, 255, 100)")
                            .withTextHaloWidth(1.0f)
                            .withTextAnchor("top")
                            .withTextOffset(new Float[]{0f, 1f})
                            .withDraggable(false)
            );

            SymbolLayer roomLayer = new SymbolLayer("rooms", "rooms_source");
            roomLayer.setSourceLayer("rooms_source");
            roomLayer.setProperties(
                    visibility(VISIBLE)
            );
            style.addLayer(roomLayer);

            SymbolLayer buildingLayer = new SymbolLayer("buildings", "buildings_source");
            buildingLayer.setSourceLayer("buildings_source");
            buildingLayer.setProperties(
                    visibility(VISIBLE)
            );
            style.addLayer(buildingLayer);
//
//            LineLayer itineraireLayer = new LineLayer("itineraire", "itineraire_source");
//            itineraireLayer.setSourceLayer("itineraire_source");
//            itineraireLayer.setProperties(
//                    visibility(VISIBLE)
//            );
//            style.addLayer(itineraireLayer);

            // The layer properties for our line. This is where we make the line dotted, set the
            // color, etc.
            style.addLayer(new LineLayer("linelayer", "line-source").withProperties(
                    PropertyFactory.lineDasharray(new Float[] {0.01f, 2f}),
                    PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                    PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                    PropertyFactory.lineWidth(5f),
                    PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
            ));

            //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
            mapboxMap.setMinZoomPreference(15);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(nancy)
                    .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLng(nancy));
            try {
                updateAll();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void enableLocationComponent(@NonNull Style style) {
        if (!hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 2);
        } else {
            setModeButtonListeners();

            // Retrieve and customize the Maps SDK's LocationComponent
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions
                            .builder(this, style)
                            .useDefaultLocationEngine(true)
                            .locationEngineRequest(new LocationEngineRequest.Builder(750)
                                    .setFastestInterval(750)
                                    .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                                    .build())
                            .build());

            locationComponent.setLocationComponentEnabled(true);
            locationComponent.addOnLocationClickListener(this);
            locationComponent.addOnCameraTrackingChangedListener(this);
            locationComponent.setCameraMode(cameraMode);
            setRendererMode(renderMode);
            locationComponent.forceLocationUpdate(lastLocation);
        }
    }


    private void getObjectBySymbol(Symbol symbol) {
        String type = "object";
        JsonObject jsonObject = new JsonObject();

        if(!symbol.getData().isJsonNull()){
            jsonObject = symbol.getData().getAsJsonObject();
            type = jsonObject.get("type").getAsString();
        }
        switch(type){
            case "object":
                Log.i("TYPE", "Register object have no specific type");
                break;

            case "salle":
                objetCourant = mDb.roomModel().getSalle(jsonObject.get("id").getAsInt());
                buildDialog(symbol.getTextAnchor(), "Up - Down Animation!");
                break;

            case "batiment":
                objetCourant = mDb.buildingModel().getBatiment(jsonObject.get("id").getAsInt());
                buildDialog(symbol.getTextAnchor(), "Up - Down Animation!");
                break;
            default:
                Log.i("TYPE", "Unknown type" + type);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * Listen to and use a tap on the LocationComponent
     */
    @Override
    public void onLocationComponentClick() {
//        Toast.makeText(this, getString(R.string.clicked_on_location_component), Toast.LENGTH_LONG).show();
    }

    /**
     * Adjust the LocationComponent's image to one of the preset options.
     *
     * @param mode desired normal (small blue circle laid on top of larger white dot),
     *             compass (arrow point representing the phone's bearing), or
     *             GPS (blue arrow within a white circle).
     */
    private void setRendererMode(@RenderMode.Mode int mode) {
        renderMode = mode;
        locationComponent.setRenderMode(mode);
        if (mode == RenderMode.NORMAL) {
            locationModeBtn.setText(getString(R.string.normal));
        } else if (mode == RenderMode.COMPASS) {
            locationModeBtn.setText(getString(R.string.compass));
        } else if (mode == RenderMode.GPS) {
            locationModeBtn.setText(getString(R.string.gps));
        }
    }

    private void showModeListDialog() {
        List<String> modes = new ArrayList<>();
        modes.add(getString(R.string.normal));
        modes.add(getString(R.string.compass));
        modes.add(getString(R.string.gps));
        ArrayAdapter<String> profileAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, modes);
        ListPopupWindow listPopup = new ListPopupWindow(this);
        listPopup.setAdapter(profileAdapter);
        listPopup.setAnchorView(locationModeBtn);
        listPopup.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedMode = modes.get(position);
            locationModeBtn.setText(selectedMode);
            if (selectedMode.contentEquals(getString(R.string.normal))) {
                setRendererMode(RenderMode.NORMAL);
            } else if (selectedMode.contentEquals(getString(R.string.compass))) {
                setRendererMode(RenderMode.COMPASS);
            } else if (selectedMode.contentEquals(getString(R.string.gps))) {
                setRendererMode(RenderMode.GPS);
            }
            listPopup.dismiss();
        });
        listPopup.show();
    }

    /**
     * Instruct the map camera to disregard the LocationComponent or to
     * follow the device location in a certain way.
     * <p>
     * NONE = No camera tracking.
     * NONE_COMPASS = Camera does not track location, but does track compass bearing.
     * NONE_GPS = Camera does not track location, but does track GPS {@link Location} bearing.
     * TRACKING = Camera tracks the user location.
     * TRACKING_COMPASS = Camera tracks the user location, with bearing provided by a compass.
     * TRACKING_GPS = Camera tracks the user location, with bearing provided by a
     * normalized {@link Location#getBearing()}.
     * TRACKING_GPS_NORTH = Camera tracks the user location, with bearing always set to north (0).
     */
    private void showTrackingListDialog() {
        List<String> trackingTypes = new ArrayList<>();
        trackingTypes.add(getString(R.string.none));
        trackingTypes.add(getString(R.string.none_compass));
        trackingTypes.add(getString(R.string.none_gps));
        trackingTypes.add(getString(R.string.tracking));
        trackingTypes.add(getString(R.string.tracking_compass));
        trackingTypes.add(getString(R.string.tracking_gps));
        trackingTypes.add(getString(R.string.tracking_gps_north));
        ArrayAdapter<String> profileAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, trackingTypes);
        ListPopupWindow listPopup = new ListPopupWindow(this);
        listPopup.setAdapter(profileAdapter);
        listPopup.setAnchorView(locationTrackingBtn);
        listPopup.setOnItemClickListener((parent, itemView, position, id) -> {
            String selectedTrackingType = trackingTypes.get(position);
            locationTrackingBtn.setText(selectedTrackingType);
            if (selectedTrackingType.contentEquals(getString(R.string.none))) {
                setCameraTrackingMode(CameraMode.NONE);
            } else if (selectedTrackingType.contentEquals(getString(R.string.none_compass))) {
                setCameraTrackingMode(CameraMode.NONE_COMPASS);
            } else if (selectedTrackingType.contentEquals(getString(R.string.none_gps))) {
                setCameraTrackingMode(CameraMode.NONE_GPS);
            } else if (selectedTrackingType.contentEquals(getString(R.string.tracking))) {
                setCameraTrackingMode(CameraMode.TRACKING);
            } else if (selectedTrackingType.contentEquals(getString(R.string.tracking_compass))) {
                setCameraTrackingMode(CameraMode.TRACKING_COMPASS);
            } else if (selectedTrackingType.contentEquals(getString(R.string.tracking_gps))) {
                setCameraTrackingMode(CameraMode.TRACKING_GPS);
            } else if (selectedTrackingType.contentEquals(getString(R.string.tracking_gps_north))) {
                setCameraTrackingMode(CameraMode.TRACKING_GPS_NORTH);
            }
            listPopup.dismiss();
        });
        listPopup.show();
    }

    private void setCameraTrackingMode(@CameraMode.Mode int mode) {
        locationComponent.setCameraMode(mode, new OnLocationCameraTransitionListener() {
            @Override
            public void onLocationCameraTransitionFinished(@CameraMode.Mode int cameraMode) {
                if (mode != CameraMode.NONE) {
                    locationComponent.zoomWhileTracking(15, 750, new MapboxMap.CancelableCallback() {
                        @Override
                        public void onCancel() {
// No impl
                        }

                        @Override
                        public void onFinish() {
                            locationComponent.tiltWhileTracking(45);
                        }
                    });
                } else {
                    mapboxMap.easeCamera(CameraUpdateFactory.tiltTo(0));
                }
            }

            @Override
            public void onLocationCameraTransitionCanceled(@CameraMode.Mode int cameraMode) {
// No impl
            }
        });
    }

    @Override
    public void onCameraTrackingDismissed() {
        locationTrackingBtn.setText(getString(R.string.none));
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
        this.cameraMode = currentMode;
        if (currentMode == CameraMode.NONE) {
            locationTrackingBtn.setText(getString(R.string.none));
        } else if (currentMode == CameraMode.NONE_COMPASS) {
            locationTrackingBtn.setText(getString(R.string.none_compass));
        } else if (currentMode == CameraMode.NONE_GPS) {
            locationTrackingBtn.setText(getString(R.string.none_gps));
        } else if (currentMode == CameraMode.TRACKING) {
            locationTrackingBtn.setText(getString(R.string.tracking));
        } else if (currentMode == CameraMode.TRACKING_COMPASS) {
            locationTrackingBtn.setText(getString(R.string.tracking_compass));
        } else if (currentMode == CameraMode.TRACKING_GPS) {
            locationTrackingBtn.setText(getString(R.string.tracking_gps));
        } else if (currentMode == CameraMode.TRACKING_GPS_NORTH) {
            locationTrackingBtn.setText(getString(R.string.tracking_gps_north));
        }
    }

    private void setModeButtonListeners() {
        locationModeBtn = findViewById(R.id.button_location_mode);
        locationModeBtn.setOnClickListener(v -> {
            if (locationComponent == null) {
                return;
            }
            showModeListDialog();
        });

        locationTrackingBtn = findViewById(R.id.button_location_tracking);
        locationTrackingBtn.setOnClickListener(v -> {
            if (locationComponent == null) {
                return;
            }
            showTrackingListDialog();
        });
    }

    ///------------------------------------------------------------------------------------------///
    ///------------------------------------------------------------------------------------------///
    ///-----------------------------------Fin fonction MapBox------------------------------------///
    ///------------------------------------------------------------------------------------------///
    ///------------------------------------------------------------------------------------------///

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE_WRITE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new MiseAJourDB(this).execute();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission refusé", Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSION_CODE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            enableLocationComponent(style);
                        }
                    });
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission refusé", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();

                }
                break;
        }



    }

    /**
     * Thread qui vérifie si une mise à jour et nécéssaire et l'effectue si besoin
     */
    private static class MiseAJourDB extends AsyncTask<String, String, String> {
        JSONObject response;
        private WeakReference<MainActivity> activityReference;
        private String miseAJour;
        private String lastMiseAJour;
        private int erreur = 0;
        private String message = "Aucunes données trouvées";

        // only retain a weak reference to the activity
        MiseAJourDB(MainActivity context) {
            activityReference = new WeakReference<>(context);
            activityReference.get().pDialog = new ProgressDialog(activityReference.get());
            activityReference.get().pDialog.setMessage("Vérification de mise à jour...");
            activityReference.get().pDialog.setIndeterminate(false);
            activityReference.get().pDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            activityReference.get().pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser jsonParser = new HttpJsonParser();
            response = jsonParser.makeHttpRequest("http://10.0.2.2/fstdashboard/get", "GET", null);
            Log.i("MAJ", "" + response);
            try {
                if (response != null) {
                    activityReference.get().success = response.getInt(CleStatic.KEY_SUCCESS);
                    JSONArray miseAJourFromJSON = response.getJSONArray(CleStatic.KEY_MAJ);
                    JSONObject miseAJourJSONObject = miseAJourFromJSON.getJSONObject(0);
                    UpToDate miseAJourObjet = new UpToDate(miseAJourJSONObject.getInt(CleStatic.KEY_MAJ_ID), miseAJourJSONObject.getString(CleStatic.KEY_MAJ_DATE));
                    miseAJour = miseAJourObjet.getDate();
                    if (activityReference.get().mDb.upToDateModel().getAll() != null)
                        lastMiseAJour = activityReference.get().mDb.upToDateModel().getAll().getDate();
                    else
                        lastMiseAJour = null;
                    //Récupérer date mise à jour
                    //Si vide ou différent de actuelle mettre à jour
                    //Sinon rien faire
                    Log.i("MAJ", "success : " + activityReference.get().success);
                    if (activityReference.get().success == 1 && !miseAJour.equals(lastMiseAJour)) {
                        try {
                            activityReference.get().pDialog.setMessage("Mise à jour en cours...");
                            //Récupération de la partie "Data" du JSON
                            JSONObject dataFromJSONObject = response.getJSONObject(CleStatic.KEY_DATA);
                            //Mise sous forme d'un JSONArray
                            JSONArray dataFromJSON = dataFromJSONObject.toJSONArray(dataFromJSONObject.names());

                            //vider toutes les tables
                            activityReference.get().mDb.clearAllTables();

                            //Parcours des batiments
                            JSONArray dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(0));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject batimentObj = dataFromJSON_WithKey.getJSONObject(i);
                                    Building building = new Building(
                                            batimentObj.getInt(CleStatic.KEY_BATIMENT_ID),
                                            batimentObj.getString(CleStatic.KEY_CODE_BATIMENT),
                                            batimentObj.getString(CleStatic.KEY_NOM_BATIMENT),
                                            batimentObj.getString(CleStatic.KEY_NOM_COMPLET_BATIMENT),
                                            batimentObj.getString(CleStatic.KEY_LATITUDE_BATIMENT),
                                            batimentObj.getString(CleStatic.KEY_LONGITUDE_BATIMENT)
                                    );
                                    activityReference.get().mDb.buildingModel().insertBatiment(building);
                                }
                            }

                            //Parcours surface batiment
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(1));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject surfacePointObj = dataFromJSON_WithKey.getJSONObject(i);
                                    SurfacePoint surfacePoint = new SurfacePoint(
                                            surfacePointObj.getInt(CleStatic.KEY_SURFACEPOINT_ID),
                                            surfacePointObj.getString(CleStatic.KEY_SURFACEPOINT_LATITUDE),
                                            surfacePointObj.getString(CleStatic.KEY_SURFACEPOINT_LONGITUDE),
                                            surfacePointObj.getInt(CleStatic.KEY_SURFACEPOINT_ORDRE),
                                            surfacePointObj.getInt(CleStatic.KEY_SURFACEPOINT_IDBATIMENT)
                                    );
                                    activityReference.get().mDb.surfacePointModel().insertSurfacePoint(surfacePoint);
                                }
                            }

                            //Parcours des salles
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(2));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject salleObj = dataFromJSON_WithKey.getJSONObject(i);
                                    Room room = new Room(
                                            salleObj.getInt(CleStatic.KEY_SALLE_ID),
                                            salleObj.getString(CleStatic.KEY_CODE_SALLE),
                                            salleObj.getString(CleStatic.KEY_LATITUDE_SALLE),
                                            salleObj.getString(CleStatic.KEY_LONGITUDE_SALLE),
                                            salleObj.getString(CleStatic.KEY_NOM_SALLE),
                                            salleObj.getInt(CleStatic.KEY_CAPACITE_SALLE),
                                            salleObj.getString(CleStatic.KEY_ETAGE_SALLE),
                                            salleObj.getInt(CleStatic.KEY_TYPE_SALLE),
                                            salleObj.getInt(CleStatic.KEY_ID_BATIMENT_SALLE)
                                    );
                                    activityReference.get().mDb.roomModel().insertSalle(room);
                                }
                            }

                            //Parcours surface salles
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(3));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject surfaceSalleObj = dataFromJSON_WithKey.getJSONObject(i);

                                }
                            }

                            //Parcours des equipements
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(4));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject equipementObj = dataFromJSON_WithKey.getJSONObject(i);
                                    Equipment equipment = new Equipment(
                                            equipementObj.getInt(CleStatic.KEY_EQUIPEMENT_ID),
                                            equipementObj.getString(CleStatic.KEY_NOM_EQUIPEMENT)
                                    );
                                    activityReference.get().mDb.equipmentModel().insertEquipment(equipment);
                                }
                            }

                            //Parcours salle-equipement
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(5));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject logicielObj = dataFromJSON_WithKey.getJSONObject(i);
                                    RoomEquipment roomEquipment = new RoomEquipment(
                                            logicielObj.getInt(CleStatic.KEY_SALLEEQUIPEMENT_SALLE_ID),
                                            logicielObj.getInt(CleStatic.KEY_SALLEEQUIPEMENT_EQUIPEMENT_ID)
                                    );
                                    activityReference.get().mDb.roomEquipmentModel().insertSalleEquipement(roomEquipment);
                                }
                            }

                            //Parcours des logiciels
                            Log.i("JSON", dataFromJSON.getString(6));
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(6));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject logicielObj = dataFromJSON_WithKey.getJSONObject(i);
                                    Software logiciel = new Software(
                                            logicielObj.getInt(CleStatic.KEY_LOGICIEL_ID),
                                            logicielObj.getString(CleStatic.KEY_NOM_LOGICIEL),
                                            logicielObj.getString(CleStatic.KEY_VERSION_LOGICIEL),
                                            logicielObj.getString(CleStatic.KEY_ICONE_LOGICIEL)
                                    );
                                    activityReference.get().mDb.softwareModel().insertLogiciel(logiciel);
                                }
                            }

                            //Parcours salle-logiciel
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(7));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject salleLogicielObj = dataFromJSON_WithKey.getJSONObject(i);
                                    RoomSoftware salleLogiciel = new RoomSoftware(
                                            salleLogicielObj.getInt(CleStatic.KEY_SALLELOGICIEL_SALLE_ID),
                                            salleLogicielObj.getInt(CleStatic.KEY_SALLELOGICIEL_LOGICIEL_ID)
                                    );
                                    activityReference.get().mDb.roomSoftwareModel().insertRoomSoftware(salleLogiciel);
                                }
                            }

                            //Parcours des types
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(8));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject typeObj = dataFromJSON_WithKey.getJSONObject(i);
                                    Type type = new Type(
                                            typeObj.getInt(CleStatic.KEY_TYPE_ID),
                                            typeObj.getString(CleStatic.KEY_TYPE_SALLE)
                                    );
                                    activityReference.get().mDb.typeModel().insertType(type);
                                }
                            }

                            //Parcours des types-salles
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(9));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject typeObj = dataFromJSON_WithKey.getJSONObject(i);
                                    TypeRoom typeRoom = new TypeRoom(
                                            typeObj.getInt(CleStatic.KEY_TYPESALLE_TYPE_ID),
                                            typeObj.getInt(CleStatic.KEY_TYPESALLE_SALLE_ID)
                                    );
                                    activityReference.get().mDb.typeRoomModel().insertTypeRoom(typeRoom);
                                }
                            }

                            //Parcours AliasSalleRecherche
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(10));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject aliasRechercheObj = dataFromJSON_WithKey.getJSONObject(i);
                                    AliasRoomSearch aliasRoomSearch = new AliasRoomSearch(
                                            aliasRechercheObj.getInt(CleStatic.KEY_ALIASSALLERECHERCHE_ID),
                                            aliasRechercheObj.getString(CleStatic.KEY_ALIASSALLERECHERCHE_ALIAS),
                                            aliasRechercheObj.getInt(CleStatic.KEY_ALIASSALLERECHERCHE_IDSALLE)
                                    );
                                    Log.i("SEARCH", aliasRoomSearch.getStandardName());
                                    activityReference.get().mDb.aliasRoomSearchModel().insertAliasSearch(aliasRoomSearch);
                                }
                            }

                            //Parcours AliasBatimentRecherche
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(11));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject aliasRechercheObj = dataFromJSON_WithKey.getJSONObject(i);
                                    AliasBuildingSearch aliasBuildingSearch = new AliasBuildingSearch(
                                            aliasRechercheObj.getInt(CleStatic.KEY_ALIASBATIMENTRECHERCHE_ID),
                                            aliasRechercheObj.getString(CleStatic.KEY_ALIASBATIMENTRECHERCHE_ALIAS),
                                            aliasRechercheObj.getInt(CleStatic.KEY_ALIASBATIMENTRECHERCHE_IDBATIMENT)
                                    );
                                    Log.i("SEARCH", aliasBuildingSearch.getStandardName());
                                    activityReference.get().mDb.aliasBuildingSearchModel().insertAliasBuildingSearch(aliasBuildingSearch);
                                }
                            }

                            //Parcours nodetype
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(12));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject graphNodeTypeObj = dataFromJSON_WithKey.getJSONObject(i);
                                    GraphNodeType graphNodeType = new GraphNodeType(
                                            graphNodeTypeObj.getInt(CleStatic.KEY_NODETYPE_ID),
                                            graphNodeTypeObj.getString(CleStatic.KEY_NODETYPE_NAME)
                                    );
                                    activityReference.get().mDb.graphNodeTypeDAO().insertGraphNodeType(graphNodeType);
                                }
                            }

                            //Parcours node
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(13));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject nodeObj = dataFromJSON_WithKey.getJSONObject(i);
                                    GraphNode graphNode = new GraphNode(
                                            nodeObj.getInt(CleStatic.KEY_NODE_ID),
                                            nodeObj.getString(CleStatic.KEY_NODE_NAME),
                                            nodeObj.getString(CleStatic.KEY_NODE_LATITUDE),
                                            nodeObj.getString(CleStatic.KEY_NODE_LONGITUDE),
                                            nodeObj.getString(CleStatic.KEY_NODE_WEIGHT),
                                            nodeObj.getInt(CleStatic.KEY_NODE_IDGRAPHNODETYPEID)
                                    );
                                    activityReference.get().mDb.graphNodeDAO().insertGraphNode(graphNode);
                                }
                            }


                            //Parcours edge
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(14));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject graphEdgeObj = dataFromJSON_WithKey.getJSONObject(i);
                                    GraphEdge graphEdge = new GraphEdge(
                                            graphEdgeObj.getInt(CleStatic.KEY_EDGE_ID),
                                            graphEdgeObj.getInt(CleStatic.KEY_EDGE_NODE1),
                                            graphEdgeObj.getInt(CleStatic.KEY_EDGE_NODE2)
                                    );
                                    activityReference.get().mDb.graphEdgeDAO().insertGraphEdge(graphEdge);
                                }
                            }

                            //Parcours entreesalle
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(15));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject entreeSalleObj = dataFromJSON_WithKey.getJSONObject(i);
                                    EntreeSalle entreeSalle = new EntreeSalle(
                                            entreeSalleObj.getInt(CleStatic.KEY_ENTREESALLE_ID),
                                            entreeSalleObj.getInt(CleStatic.KEY_ENTREESALLE_IDSALLE),
                                            entreeSalleObj.getInt(CleStatic.KEY_ENTREESALLE_IDNODE)

                                    );
                                    activityReference.get().mDb.entryRoomModel().insertEntryRoom(entreeSalle);
                                }
                            }

                            //Parcours entreebatiment
                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(16));
                            if (dataFromJSON_WithKey.length() > 0) {
                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
                                    JSONObject entreeSalleObj = dataFromJSON_WithKey.getJSONObject(i);
                                    EntreeSalle entreeSalle = new EntreeSalle(
                                            entreeSalleObj.getInt(CleStatic.KEY_ENTREEBATIMENT_ID),
                                            entreeSalleObj.getInt(CleStatic.KEY_ENTREEBATIMENT_IDBATIMENT),
                                            entreeSalleObj.getInt(CleStatic.KEY_ENTREEBATIMENT_IDNODE)
                                    );
                                    activityReference.get().mDb.entryRoomModel().insertEntryRoom(entreeSalle);
                                }
                            }

                            //Parcours des toilettes
//                            dataFromJSON_WithKey = new JSONArray(dataFromJSON.getString(12));
//                            if(dataFromJSON_WithKey.length()>0) {
//                                for (int i = 0; i < dataFromJSON_WithKey.length(); i++) {
//                                    JSONObject toiletteObj = dataFromJSON_WithKey.getJSONObject(i);
//                                    Toilette toilette = new Toilette(
//                                            toiletteObj.getInt(CleStatic.KEY_TOILETTE_ID),
//                                            toiletteObj.getString(CleStatic.KEY_LATITUDE_TOILETTE),
//                                            toiletteObj.getString(CleStatic.KEY_LONGITUDE_TOILETTE)
//                                    );
//                                    activityReference.get().mDb.toiletteModel().insertToilette(toilette);
//                                }
//                            }

                            //Mise à jour de la date de dernière maj
                            activityReference.get().mDb.upToDateModel().insertUpToDate(new UpToDate(1, miseAJour));

                        } catch (JSONException e1) {
                            //Erreur de lecture des données du JSON
                            //Vériier le formattage du JSON
                            erreur = 3;
                            message = "\n Données erronée. \n ERREUR : INCOMPATIBLE_JSON";
                            e1.printStackTrace();
                        }
                    } else {
                        if (lastMiseAJour == null || lastMiseAJour.isEmpty()) {
                            //Sert uniquement au débugage
                            //Vérifie si une date d'une ancienne mise à jour existe
                            erreur = 4;
                        } else if (lastMiseAJour.equals(miseAJour)) {
                            //Aucune mise à jour nécéssaire
                            erreur = 5;
                        } else {
                            //Tout autres erreurs
                            erreur = 6;
                        }
                    }
                } else {
                    /*if(activityReference.get().pDialog != null)
                        activityReference.get().pDialog.hide();*/
                    erreur = 2;
                    //if(activityReference.get().haveInternetConnection())
                    message = "\n Aucune données trouvées. \n Vérifiez votre connexion internet. \n Erreur : NO_DATA_FOUND";
                }
            } catch (Exception e) {
                e.printStackTrace();
                erreur = 1;
                message = "\n Erreur serveur ou d'intégrité des données. \n Vérifiez votre connexion internet. \n Erreur : SERVEUR_HORS_LIGNE";
            }
            return null;
        }

        protected void onPostExecute(String result) {
            activityReference.get().pDialog.dismiss();
            switch (erreur) {
                case 1:
                case 2:
                    Toast.makeText(activityReference.get(),
                            "Pas de connexion internet",
                            Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    new AlertDialog.Builder(activityReference.get())
                            .setTitle("ATTENTION")
                            .setMessage(message)

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    System.out.println("Clique sur la boite de dialogue");
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    break;

                case 4:
                    Toast.makeText(activityReference.get(),
                            "Pas de date de mise à jour",
                            Toast.LENGTH_LONG).show();
                    break;

                case 5:
                    Toast.makeText(activityReference.get(),
                            "Déjà à jour",
                            Toast.LENGTH_LONG).show();
                    break;

                case 6:
                    Toast.makeText(activityReference.get(),
                            "Erreur de récupération des donnes",
                            Toast.LENGTH_LONG).show();
                    break;

            }
            activityReference.get().mettreAJourListeRecherche();
//            activityReference.get().graphController.initByBDD(activityReference.get().mDb);
//            activityReference.get().mettreAJourMarker();
            Log.i("MAJ", "mise a jour : FAITE");
        }

    }
}



