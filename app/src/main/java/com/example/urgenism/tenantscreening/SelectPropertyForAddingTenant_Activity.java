package com.example.urgenism.tenantscreening;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.urgenism.tenantscreening.List.PropertyInformation;
import com.example.urgenism.tenantscreening.List.PropertyList;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectPropertyForAddingTenant_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String PROPERTY_ID = "propertyID";
    public static final String PROPERTY_NAME = "propertyName";
    public static final String BUILDING_TYPE = "buildingType";
    public static final String PROPERTY_NUMBER = "propertyNumber";
    public static final String ADDRESS = "address";
    public static final String WARD_NO = "wardNo";
    public static final String MUNICIPALITY = "municipality";
    public static final String DISTRICT = "district";
    public static final String ZONE = "zone";
    public static final String NOTE = "note";


    DatabaseReference databaseProperty;

    ListView propertyListView;

    List<PropertyInformation> propertyList;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseProperty = FirebaseDatabase.getInstance().getReference("properties").child(user.getUid());

        propertyListView = (ListView) findViewById(R.id.propertyListView);

        propertyList = new ArrayList<>();

        propertyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                PropertyInformation propertyInfo = propertyList.get(i);

           /*     SharedPreferences sharedPreferences = getSharedPreferences("selectProperty", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("property_ID",propertyInfo.getProperty_ID());
                editor.putString("property_Name",propertyInfo.getPropertyName());
                editor.putString("Building_Type",propertyInfo.getBuildingType());
                editor.putString("address",propertyInfo.getAddress());
                editor.putString("city",propertyInfo.getCity());
                editor.putString("postal_code",propertyInfo.getPostalCode());
                editor.putString("state",propertyInfo.getState());
                editor.putString("country",propertyInfo.getCountry());
                editor.putString("note",propertyInfo.getNote());
                editor.apply(); */

                Intent intent = new Intent(getApplicationContext(), AddTenantActivity.class);

                intent.putExtra(PROPERTY_ID,propertyInfo.getProperty_ID() );
                intent.putExtra(PROPERTY_NAME,propertyInfo.getPropertyName() );
                intent.putExtra(BUILDING_TYPE,propertyInfo.getPropertyType() );
                intent.putExtra(PROPERTY_NUMBER,propertyInfo.getPropertyNumber() );
                intent.putExtra(ADDRESS,propertyInfo.getAddress() );
                intent.putExtra(WARD_NO,propertyInfo.getWard_no() );
                intent.putExtra(MUNICIPALITY,propertyInfo.getMunicipality() );
                intent.putExtra(DISTRICT,propertyInfo.getDistrict() );
                intent.putExtra(ZONE,propertyInfo.getZone() );
                intent.putExtra(NOTE,propertyInfo.getNote() );
                startActivity(intent);
            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();

        databaseProperty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                propertyList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    PropertyInformation property = postSnapshot.getValue(PropertyInformation.class);
                    //adding artist to the list
                    propertyList.add(property);
                }

                //creating adapter
                PropertyList artistAdapter = new PropertyList(SelectPropertyForAddingTenant_Activity.this, propertyList);
                //attaching adapter to the listview
                propertyListView.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
