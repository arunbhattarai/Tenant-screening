package com.example.urgenism.tenantscreening.List;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.urgenism.tenantscreening.R;

import java.util.List;

/**
 * Created by urgenism on 7/28/17.
 */

public class PropertyList extends ArrayAdapter<PropertyInformation> {
        private Activity context;
        List<PropertyInformation> properties;

        public PropertyList(Activity context, List<PropertyInformation> properties) {
            super(context, R.layout.list_view, properties);
            this.context = context;
            this.properties = properties;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.list_view, null, true);

            TextView propertyName = listViewItem.findViewById(R.id.bigText);
            TextView address = listViewItem.findViewById(R.id.bigText2);
            TextView propertyNumber = listViewItem.findViewById(R.id.smallText);

            PropertyInformation property = properties.get(position);

            propertyName.setText(property.getPropertyName());
            address.setText(property.getAddress());
            propertyNumber.setText(property.getPropertyNumber());

            return listViewItem;
        }
    }

