package com.theluckydev.fst.objet.adapter.search;

/**
 * Created by Alexis on 12/04/2018.
 */
import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import com.theluckydev.fst.R;
import com.theluckydev.fst.objet.Objet;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Objet> listStorage;

    public SearchAdapter(Context context, List<Objet> customizedListView) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item, null, false);

            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.product_name);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        listViewHolder.textInListView.setText(listStorage.get(position).getStandardName());
        return convertView;
    }

    static class ViewHolder{

        TextView textInListView;
        ImageView imageInListView;
    }
}