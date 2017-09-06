package com.example.urgenism.tenantscreening;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.urgenism.tenantscreening.List.TenantInformation;
import com.example.urgenism.tenantscreening.List.TenantList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TenantListViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TENANT_ID = "tenantID";
    public static final String TENANT_NAME = "tenantName";
    public static final String EMAIL = "email";
    public static final String MOBILE_NO = "mobileNo";
    public static final String SEX = "sex";
    public static final String DATEOFBIRTH = "DOB";
    public static final String CITIZENSHIP_NO = "citizenshipNO";
    public static final String ZONE = "zone";
    public static final String DISTRICT = "district";
    public static final String MUNICIPALITY = "municipality";
    public static final String WARD_NO = "wardNo";
    public static final String FATHER_NAME = "fatherName";
    public static final String MARITAL_STATUS = "maritalStatus";
    public static final String MOVE_IN_DATE = "moveInDate";
    public static final String RENT_AMOUNT = "rentAmount";
    public static final String NOTE = "note";

    DatabaseReference databaseProperty;

    ListView tenantListView;

    List<TenantInformation> tenantList;


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

        Intent intent = getIntent();
        String p_ID = intent.getStringExtra(SelectPropertyForDisplayingTenants_Activity.PROPERTY_ID);

        databaseProperty = FirebaseDatabase.getInstance().getReference("tenants").child(p_ID);

        tenantListView = (ListView) findViewById(R.id.propertyListView);

        tenantList = new ArrayList<>();

        tenantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TenantInformation tenantInfo = tenantList.get(i);

                SharedPreferences sharedPreferences = getSharedPreferences("tenantInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tenant_ID",tenantInfo.getTenantID());
                editor.putString("tenant_Name",tenantInfo.getTenantName());
                editor.putString("email",tenantInfo.getEmail());
                editor.putString("mobileNo",tenantInfo.getMobileNo());
                editor.putString("sex",tenantInfo.getSex());
                editor.putString("dateOfBirth",tenantInfo.getDOB());
                editor.putString("citizenshipNo",tenantInfo.getCitizenshipNo());
                editor.putString("zone",tenantInfo.getZone());
                editor.putString("district",tenantInfo.getDistrict());
                editor.putString("municipality",tenantInfo.getMunicipality());
                editor.putString("wardNo",tenantInfo.getWardNo());
                editor.putString("fatherName",tenantInfo.getFatherName());
                editor.putString("maritalStatus",tenantInfo.getMaritalStatus());
                editor.putString("moveInDate",tenantInfo.getMoveInDate());
                editor.putString("rentAmount",tenantInfo.getRentAmount());
                editor.putString("note",tenantInfo.getNote());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), TenantDetailActivity.class);

                intent.putExtra(TENANT_ID,tenantInfo.getTenantID() );
                intent.putExtra(TENANT_NAME,tenantInfo.getTenantName() );
                intent.putExtra(EMAIL,tenantInfo.getEmail() );
                intent.putExtra(MOBILE_NO,tenantInfo.getMobileNo() );
                intent.putExtra(SEX,tenantInfo.getSex() );
                intent.putExtra(DATEOFBIRTH,tenantInfo.getDOB() );
                intent.putExtra(CITIZENSHIP_NO,tenantInfo.getCitizenshipNo() );
                intent.putExtra(ZONE,tenantInfo.getZone() );
                intent.putExtra(DISTRICT,tenantInfo.getDistrict() );
                intent.putExtra(MUNICIPALITY,tenantInfo.getMunicipality() );
                intent.putExtra(WARD_NO,tenantInfo.getWardNo() );
                intent.putExtra(FATHER_NAME,tenantInfo.getFatherName() );
                intent.putExtra(MARITAL_STATUS,tenantInfo.getMaritalStatus() );
                intent.putExtra(MOVE_IN_DATE,tenantInfo.getMoveInDate() );
                intent.putExtra(RENT_AMOUNT,tenantInfo.getRentAmount() );
                intent.putExtra(NOTE,tenantInfo.getNote() );
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        tenantListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TenantInformation tenantInfo = tenantList.get(i);
                showDeleteDialog(tenantInfo.getTenantID());

                return true;
            }
        });



    }

    public void showDeleteDialog(final String t_ID){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete =dialogView.findViewById(R.id.delete_btn);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProperty(t_ID);
                b.dismiss();
            }
            });

    }

    public boolean deleteProperty(String t_id){

        Intent intent = getIntent();
        String p_ID = intent.getStringExtra(SelectPropertyForDisplayingTenants_Activity.PROPERTY_ID);
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("tenants").child(p_ID).child(t_id);
        dr.removeValue();

        Toast.makeText(getApplicationContext(),"successfully deleted tenant!!...",Toast.LENGTH_SHORT).show();
        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseProperty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                tenantList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    TenantInformation tenant = postSnapshot.getValue(TenantInformation.class);
                    //adding artist to the list
                    tenantList.add(tenant);
                }

                //creating adapter
                TenantList tenantAdapter = new TenantList(TenantListViewActivity.this, tenantList);
                //attaching adapter to the listview
                tenantListView.setAdapter(tenantAdapter);
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
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
