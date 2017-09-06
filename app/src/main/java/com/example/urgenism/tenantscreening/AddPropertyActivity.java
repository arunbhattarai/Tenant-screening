package com.example.urgenism.tenantscreening;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urgenism.tenantscreening.List.PropertyInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPropertyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText _propertyNameET, _propertyNo, _addressET, _wardNoET,
            _municipalityET, _districtET, _zoneET,_amountET, _taxET, _note;
    private TextView _totalAmount;
    private Spinner buildingSpinner;
    private Button _calculate, _donebtn;

    private FirebaseUser user;

    DatabaseReference databaseProperty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproperty);

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

        _propertyNameET = (EditText) findViewById(R.id.editPropertyName);
        _propertyNo = (EditText) findViewById(R.id.editPropertyNo);
        _addressET = (EditText) findViewById(R.id.editAddress);
        _wardNoET = (EditText) findViewById(R.id.editWardNo);
        _municipalityET = (EditText) findViewById(R.id.editMunicipality);
        _districtET = (EditText) findViewById(R.id.editDistrict);
        _zoneET = (EditText) findViewById(R.id.editZone);
        _amountET = (EditText) findViewById(R.id.editamount);
        _taxET = (EditText) findViewById(R.id.editTax);
        _note = (EditText) findViewById(R.id.editNote);

        _totalAmount = (TextView) findViewById(R.id.totalAmount);
        buildingSpinner = (Spinner) findViewById(R.id.spinnerProperty);

        _donebtn = (Button) findViewById(R.id.btn_done);
        _calculate = (Button) findViewById(R.id.btnCalculate);

        _calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()){

                    int amount = Integer.parseInt(_amountET.getText().toString());
                    int tax = Integer.parseInt(_taxET.getText().toString());

                    int total = amount+(tax*amount)/100;
                    _totalAmount.setText(String.valueOf(total));
                }
            }
        });

        _donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == _donebtn){
                    register();
                }
            }
        });

    }

    public boolean valid(){

        boolean valid = true;
        String amount = _amountET.getText().toString().trim();
        String tax = _taxET.getText().toString().trim();

        if (amount.isEmpty()) {
            _amountET.setError("Enter valid amount");
            valid = false;
        } else {
            _amountET.setError(null);
        }

        if (tax.isEmpty() || tax.length() > 2) {
            _taxET.setError("Enter valid tax rate");
            valid = false;
        } else {
            _taxET.setError(null);
        }
        return valid;

    }

    public void register() {

        if (validate()) {


            String spinner = buildingSpinner.getSelectedItem().toString().trim();
            String propertyName = _propertyNameET.getText().toString().trim();
            String propertyNumber = _propertyNo.getText().toString().trim();
            String address = _addressET.getText().toString().trim();
            String wardNo = _wardNoET.getText().toString().trim();
            String municipality = _municipalityET.getText().toString().trim();
            String district = _districtET.getText().toString().trim();
            String zone = _zoneET.getText().toString().trim();
            String amount = _amountET.getText().toString().trim();
            String tax = _taxET.getText().toString().trim();
            String totalAmount = _totalAmount.getText().toString().trim();
            String note = _note.getText().toString().trim();

            String propertyID = databaseProperty.push().getKey();
            PropertyInformation property = new PropertyInformation(propertyID, spinner, propertyName, propertyNumber,
                    address, wardNo, municipality, district, zone, amount, tax, totalAmount, note);
            databaseProperty.child(propertyID).setValue(property);

            Toast.makeText(this, "Successfully created new Property!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddPropertyActivity.this, HomeActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


        }

        }


    public boolean validate() {
        boolean valid = true;

        String spinner = buildingSpinner.getSelectedItem().toString().trim();
        String propertyName = _propertyNameET.getText().toString().trim();
        String propertyNumber = _propertyNo.getText().toString().trim();
        String address = _addressET.getText().toString().trim();
        String wardNo = _wardNoET.getText().toString().trim();
        String municipality = _municipalityET.getText().toString().trim();
        String district = _districtET.getText().toString().trim();
        String zone = _zoneET.getText().toString().trim();
        String amount = _amountET.getText().toString().trim();
        String tax = _taxET.getText().toString().trim();
        String totalAmount = _totalAmount.getText().toString().trim();
        String note = _note.getText().toString().trim();

        if (spinner.equals("Select Building type")) {
            ((TextView) buildingSpinner.getSelectedView()).setError("Select Building type");
            valid = false;
        } else {
            ((TextView) buildingSpinner.getSelectedView()).setError(null);
        }

        if (propertyName.isEmpty() || propertyName.length() < 3) {
            _propertyNameET.setError("at least 3 characters");
            valid = false;
        } else {
            _propertyNameET.setError(null);
        }

        if (propertyNumber.isEmpty() || propertyNumber.length() < 2) {
            _propertyNo.setError("at least 2 characters");
            valid = false;
        } else {
            _propertyNo.setError(null);
        }

        if (address.isEmpty() || address.length() < 3) {
            _addressET.setError("enter a valid address");
            valid = false;
        } else {
            _addressET.setError(null);
        }


        if (wardNo.isEmpty()) {
            _wardNoET.setError("Enter the wardNo");
            valid = false;
        } else {
            _wardNoET.setError(null);
        }

        if (municipality.isEmpty() || municipality.length() < 3) {
            _municipalityET.setError("at least 3 characters");
            valid = false;
        } else {
            _municipalityET.setError(null);
        }

        if (district.isEmpty() || district.length() < 3) {
            _districtET.setError("at least 3 characters");
            valid = false;
        } else {
            _districtET.setError(null);
        }

        if (zone.isEmpty() || zone.length() < 3) {
            _zoneET.setError("at least 3 characters");
            valid = false;
        } else {
            _zoneET.setError(null);
        }

        if (amount.isEmpty()) {
            _amountET.setError("Enter valid amount");
            valid = false;
        } else {
            _amountET.setError(null);
        }

        if (tax.isEmpty() || tax.length() > 2) {
            _taxET.setError("Enter valid tax rate");
            valid = false;
        } else {
            _taxET.setError(null);
        }

        if (totalAmount.isEmpty()) {
            _totalAmount.setError("calculate the total income");
            valid = false;
        } else {
            _totalAmount.setError(null);
        }

        if (note.isEmpty() || note.length() < 5) {
            _note.setError("at least 5 characters");
            valid = false;
        } else {
            _note.setError(null);
        }

        return valid;
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
                finish();
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

