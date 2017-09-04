package com.mattmccomb.recipepup.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattmccomb.recipepup.R;
import com.mattmccomb.recipepup.models.RecipePreview;

/**
 * Created by matt on 04/09/2017.
 */
public class RecipeSearchResultAdapter extends ArrayAdapter<RecipePreview> {

    public RecipeSearchResultAdapter(@NonNull Context context) {
        super(context, R.layout.search_result);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result, parent, false);
        }
        TextView itemTitle = convertView.findViewById(R.id.searchResultTitle);
        RecipePreview recipe = getItem(position);
        itemTitle.setText(recipe.getTitle());
        return convertView;
    }

}
