package com.example.urgenism.tenantscreening.List;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.urgenism.tenantscreening.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by urgenism on 7/28/17.
 */

public class TenantList extends ArrayAdapter<TenantInformation> {
    private Activity context;
    List<TenantInformation> tenants;

    public TenantList(Activity context, List<TenantInformation> tenants) {
        super(context, R.layout.list_view, tenants);
        this.context = context;
        this.tenants = tenants;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view, null, true);

        TextView tenantName = listViewItem.findViewById(R.id.bigText);
        TextView email = listViewItem.findViewById(R.id.bigText2);
        TextView citizenshipNO = listViewItem.findViewById(R.id.smallText);

        TenantInformation tenant = tenants.get(position);

        tenantName.setText(tenant.getTenantName());
        email.setText(tenant.getEmail());
        citizenshipNO.setText(tenant.getCitizenshipNo());

        return listViewItem;
    }
}

