package com.example.dart.flyaway;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dart.flyaway.entities.FlyParameter;

import java.util.ArrayList;

/**
 * Created by dart on 07.05.17.
 */

public class FlyParameterAdapter extends ArrayAdapter<FlyParameter> {

    public FlyParameterAdapter(@NonNull Context context, ArrayList<FlyParameter> items) {
        super(context,0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_fly_parameter,parent,false
            );
        }

        final FlyParameter currentItem = getItem(position);

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.item_parameter_description_text);
        descriptionTextView.setText(currentItem.getDescription());

        ImageView image = (ImageView) listItemView.findViewById(R.id.item_parameter_icon_image);

        if(currentItem.hasImage()) {
            image.setImageResource(currentItem.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }
        else {
            image.setVisibility(View.GONE);
        }

        if (currentItem.hasIntent()){
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(currentItem.getIntent());
                }
            });
        }

        return listItemView;
    }
}
