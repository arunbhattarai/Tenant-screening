package com.example.urgenism.tenantscreening;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PropertyDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String country = "Nepal";

    private TextView _propertyNameET, _propertyNo, _addressET, _wardNoET,
            _municipalityET, _districtET, _zoneET, _note, buildingSpinner, mapView,
            _amount, _tax, _totalAmount;
    private Button _editbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _propertyNameET = (TextView) findViewById(R.id.editPropertyName);
        _propertyNo = (TextView) findViewById(R.id.editPropertyNo);
        _addressET = (TextView) findViewById(R.id.editAddress);
        _wardNoET = (TextView) findViewById(R.id.editWardNo);
        _municipalityET = (TextView) findViewById(R.id.editMunicipality);
        _districtET = (TextView) findViewById(R.id.editDistrict);
        _zoneET = (TextView) findViewById(R.id.editZone);
        _amount = (TextView) findViewById(R.id.amount);
        _tax = (TextView) findViewById(R.id.tax);
        _totalAmount = (TextView) findViewById(R.id.totalAmount);
        _note = (TextView) findViewById(R.id.editNote);
        mapView = (TextView) findViewById(R.id.mapView);


        buildingSpinner = (TextView) findViewById(R.id.spinnerProperty);

        _editbtn = (Button) findViewById(R.id.btn_edit);


        final Intent intent = getIntent();
        buildingSpinner.setText(intent.getStringExtra(PropertyListViewActivity.BUILDING_TYPE));
        _propertyNameET.setText(intent.getStringExtra(PropertyListViewActivity.PROPERTY_NAME));
        _propertyNo.setText(intent.getStringExtra(PropertyListViewActivity.PROPERTY_NUMBER));
        _addressET.setText(intent.getStringExtra(PropertyListViewActivity.ADDRESS));
        _wardNoET.setText(intent.getStringExtra(PropertyListViewActivity.WARD_NO));
        _municipalityET.setText(intent.getStringExtra(PropertyListViewActivity.MUNICIPALITY));
        _districtET.setText(intent.getStringExtra(PropertyListViewActivity.DISTRICT));
        _zoneET.setText(intent.getStringExtra(PropertyListViewActivity.ZONE));
        _amount.setText(intent.getStringExtra(PropertyListViewActivity.AMOUNT));
        _tax.setText(intent.getStringExtra(PropertyListViewActivity.TAX));
        _totalAmount.setText(intent.getStringExtra(PropertyListViewActivity.TOTAL_AMOUNT));
        _note.setText(intent.getStringExtra(PropertyListViewActivity.NOTE));

        final String address = (intent.getStringExtra(PropertyListViewActivity.MUNICIPALITY)+","
                +intent.getStringExtra(PropertyListViewActivity.DISTRICT)+ ","
        +intent.getStringExtra(PropertyListViewActivity.ZONE) +","+country);

        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        _editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditPropertyDetailActivity.class);
                startActivity(intent);
                finish();

    }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(this, MyInformationActivity.class);
                this.startActivity(intent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                this.startActivity(new Intent(this, LoginActivity.class));
                this.finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                // another startActivity, this is for item with id "menu_item2"
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_property) {
            startActivity(new Intent(getApplicationContext(), PropertyListViewActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }

        else if (id == R.id.nav_addProperty) {
            startActivity(new Intent(getApplicationContext(), AddPropertyActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        } else if (id == R.id.nav_tenant) {
            startActivity(new Intent(getApplicationContext(), SelectPropertyForDisplayingTenants_Activity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        } else if (id == R.id.nav_addTenant) {
            startActivity(new Intent(getApplicationContext(), SelectPropertyForAddingTenant_Activity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        } else if (id == R.id.profile) {
            startActivity(new Intent(getApplicationContext(),MyInformationActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            this.startActivity(new Intent(this, LoginActivity.class));
            this.finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
