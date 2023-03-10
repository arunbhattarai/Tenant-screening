package com.example.urgenism.tenantscreening;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class TenantDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView _tenantET, _emailET, _mobilenoET, _sex, _datOfBirth,_citizennoET, _zone, _district,
            _municipality, _wardNo, _fatherName, _maritalStatus, _moveindateET, _rentamountET,
            _note;
    private Button _editbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _tenantET = (TextView) findViewById(R.id.input_tenantName);
        _emailET = (TextView) findViewById(R.id.input_email);
        _mobilenoET = (TextView) findViewById(R.id.input_mobile);
        _sex = (TextView) findViewById(R.id.input_sex);
        _datOfBirth = (TextView) findViewById(R.id.input_dateOfBirth);
        _citizennoET = (TextView) findViewById(R.id.input_citizenNo);
        _zone = (TextView) findViewById(R.id.input_zone);
        _district = (TextView) findViewById(R.id.input_district);
        _municipality = (TextView) findViewById(R.id.input_Municipality);
        _wardNo = (TextView) findViewById(R.id.input_wardNo);
        _fatherName = (TextView) findViewById(R.id.input_fatherName);
        _maritalStatus = (TextView) findViewById(R.id.maritalStatus);
        _moveindateET = (TextView) findViewById(R.id.input_movein);
        _rentamountET = (TextView) findViewById(R.id.input_rentamount);
        _note = (TextView) findViewById(R.id.input_note);

        _editbtn = (Button) findViewById(R.id.btn_edit);

        Intent intent = getIntent();
        _tenantET.setText(intent.getStringExtra(TenantListViewActivity.TENANT_NAME));
        _emailET.setText(intent.getStringExtra(TenantListViewActivity.EMAIL));
        _mobilenoET.setText(intent.getStringExtra(TenantListViewActivity.MOBILE_NO));
        _sex.setText(intent.getStringExtra(TenantListViewActivity.SEX));
        _datOfBirth.setText(intent.getStringExtra(TenantListViewActivity.DATEOFBIRTH));
        _citizennoET.setText(intent.getStringExtra(TenantListViewActivity.CITIZENSHIP_NO));
        _zone.setText(intent.getStringExtra(TenantListViewActivity.ZONE));
        _district.setText(intent.getStringExtra(TenantListViewActivity.DISTRICT));
        _municipality.setText(intent.getStringExtra(TenantListViewActivity.MUNICIPALITY));
        _wardNo.setText(intent.getStringExtra(TenantListViewActivity.WARD_NO));
        _fatherName.setText(intent.getStringExtra(TenantListViewActivity.FATHER_NAME));
        _maritalStatus.setText(intent.getStringExtra(TenantListViewActivity.MARITAL_STATUS));
        _moveindateET.setText(intent.getStringExtra(TenantListViewActivity.MOVE_IN_DATE));
        _rentamountET.setText(intent.getStringExtra(TenantListViewActivity.RENT_AMOUNT));
        _note.setText(intent.getStringExtra(TenantListViewActivity.NOTE));

        _editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EditTenantDetailActivity.class));
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
