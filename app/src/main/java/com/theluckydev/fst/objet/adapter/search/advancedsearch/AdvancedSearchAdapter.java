package com.theluckydev.fst.objet.adapter.search.advancedsearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.theluckydev.fst.R;
import com.theluckydev.fst.RechercheAvanceeActivity;
import com.theluckydev.fst.SalleDescriptionActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AdvancedSearchAdapter extends ArrayAdapter<RoomDetails> {


    private List<RoomDetails> dataSet;
    private Context mContext;

    public AdvancedSearchAdapter(List<RoomDetails> dataSet, Context mContext) {
        super(mContext, R.layout.article_row, dataSet);
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(dataSet.get(0).getIdRoom() > 0) {
                v = inflater.inflate(R.layout.article_row, null);
                final RoomDetails salle = dataSet.get(position);
                if (salle != null) {
                    //Text View references
                    TextView titre = (TextView) v.findViewById(R.id.titreArticle);
                    TextView description = (TextView) v.findViewById(R.id.descriptionArticle);
                    TextView dateDebut = (TextView) v.findViewById(R.id.dateDebutArticle);
                    TextView dateFin = (TextView) v.findViewById(R.id.dateFinArticle);
                    Button seeDetailButton = (Button) v.findViewById(R.id.seeDetailsButton);
                    Button seeOnMapButton = (Button) v.findViewById(R.id.seeOnMapButton);

                    seeOnMapButton.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent();
                            i.putExtra("salle", dataSet.get(position).getIdRoom());
                            ((RechercheAvanceeActivity)mContext).setResult(RESULT_OK,i);
                            ((RechercheAvanceeActivity)mContext).finish();
                        }
                    });

                    seeDetailButton.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(((RechercheAvanceeActivity)mContext), SalleDescriptionActivity.class);
                            i.putExtra("salleDetails", dataSet.get(position).getIdRoom());
                            mContext.startActivity(i);

                        }
                    });
                    //TextView auteur = (TextView) v.findViewById(R.id.idAuteurArticle);

                    //Updating the text views
                    titre.setText(salle.getNameRoom());
                    description.setText(salle.getCodeRoom());
                }
            } else {
                v = inflater.inflate(R.layout.article_row_vide, null);
                    //Text View references
                    TextView titre = (TextView) v.findViewById(R.id.titreArticle);
                    //Updating the text views
                    titre.setText("Pas de résultat");
            }

        }



        return v;
    }

}
