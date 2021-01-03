package com.theluckydev.fst.objet.adapter.itineraire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.theluckydev.fst.BuildConfig;
import com.theluckydev.fst.R;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Building;
import com.theluckydev.fst.SalleBatimentDatabase.Entity.Room;
import com.theluckydev.fst.objet.Objet;

import java.util.List;

public class ItineraireAdapter extends ArrayAdapter<Objet> {

    private List<Objet> objets;
    private Objet currentItem;

    public ItineraireAdapter(Context context, List<Objet> objets) {
        super(context, 0, objets);
        this.objets = objets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_itineraire, parent, false);
        }
        TextView roomName = (TextView) convertView.findViewById(R.id.roomName);
        // Get the data item for this position
        Objet objet = getItem(position);
        if(objet != null) {
            switch (objet.getType()) {
                case "salle":
                    Room r = (Room) objet;
                    roomName.setText(r.getCodeRoom());
                    break;
                case "batiment":
                    Building b = (Building) objet;
                    roomName.setText(b.getCodeBuilding());
                    break;
            }
        }else{
            roomName.setText("NaN");
        }

        // Check if an existing view is being reused, otherwise inflate the view

        // Lookup view for data population
        // Populate the data into the template view using the data object

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public Objet getItem(int position)
    {
        return objets.get(position);
    }

    public Objet getCurrentItem(){
        return currentItem;
    }

    public void setCurrentItem(Objet objet){
        this.currentItem = objet;
    }
}