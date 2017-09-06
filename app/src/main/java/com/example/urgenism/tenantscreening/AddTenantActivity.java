package com.example.urgenism.tenantscreening;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urgenism.tenantscreening.List.TenantInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddTenantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private EditText _tenantET, _emailET, _mobilenoET, _sex, _datOfBirth,_citizennoET, _zone, _district,
            _municipality, _wardNo, _fatherName, _maritalStatus, _moveindateET, _rentamountET,
            _note;
    private Button _donebtn;

    DatabaseReference databaseTenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtenant);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


     //   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseTenant = FirebaseDatabase.getInstance().getReference("tenants");
        _tenantET = (EditText) findViewById(R.id.input_tenantName);
        _emailET = (EditText) findViewById(R.id.input_email);
        _mobilenoET = (EditText) findViewById(R.id.input_mobile);
        _sex = (EditText) findViewById(R.id.input_sex);
        _datOfBirth = (EditText) findViewById(R.id.input_dateOfBirth);
        _citizennoET = (EditText) findViewById(R.id.input_citizenNo);
        _zone = (EditText) findViewById(R.id.input_zone);
        _district = (EditText) findViewById(R.id.input_district);
        _municipality = (EditText) findViewById(R.id.input_Municipality);
        _wardNo = (EditText) findViewById(R.id.input_wardNo);
        _fatherName = (EditText) findViewById(R.id.input_fatherName);
        _maritalStatus = (EditText) findViewById(R.id.maritalStatus);
        _moveindateET = (EditText) findViewById(R.id.input_movein);
        _rentamountET = (EditText) findViewById(R.id.input_rentamount);
        _note = (EditText) findViewById(R.id.input_note);


        _donebtn = (Button) findViewById(R.id.done_btn);

        _datOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                _datOfBirth.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        _moveindateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTenantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                _moveindateET.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

            _donebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == _donebtn) {
                        register();
                    }
                }
            });


    }

    public void register() {

        if (validate()) {

            String tenantName = _tenantET.getText().toString().trim();
            String email = _emailET.getText().toString().trim();
            String mobile = _mobilenoET.getText().toString().trim();
            String citizenNo = _citizennoET.getText().toString().trim();
            String sex = _sex.getText().toString().trim();
            String DOB = _datOfBirth.getText().toString().trim();
            String zone = _zone.getText().toString().trim();
            String district = _district.getText().toString().trim();
            String municipality = _municipality.getText().toString().trim();
            String wardNo = _wardNo.getText().toString().trim();
            String fatherName = _fatherName.getText().toString().trim();
            String maritalStatus =_maritalStatus.getText().toString().trim();
            String moveInDate = _moveindateET.getText().toString().trim();
            String rentAmount = _rentamountET.getText().toString().trim();
            String note = _note.getText().toString().trim();

            Intent intent = getIntent();
            String p_ID = intent.getStringExtra(SelectPropertyForAddingTenant_Activity.PROPERTY_ID);

            String tenantID = databaseTenant.push().getKey();
            TenantInformation tenant = new TenantInformation(tenantID, tenantName, email, mobile, sex,
                    DOB, citizenNo, zone, district, municipality, wardNo, fatherName, maritalStatus,
                    moveInDate, rentAmount, note);
            databaseTenant.child(p_ID).child(tenantID).setValue(tenant);

            Toast.makeText(this, "Successfully created new tenants!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddTenantActivity.this, HomeActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        }
    }


    public boolean validate() {
        boolean valid = true;

        String tenantName = _tenantET.getText().toString().trim();
        String email = _emailET.getText().toString().trim();
        String mobile = _mobilenoET.getText().toString().trim();
        String citizenNo = _citizennoET.getText().toString().trim();
        String sex = _sex.getText().toString().trim();
        String DOB = _datOfBirth.getText().toString().trim();
        String zone = _zone.getText().toString().trim();
        String district = _district.getText().toString().trim();
        String municipality = _municipality.getText().toString().trim();
        String wardNo = _wardNo.getText().toString().trim();
        String fatherName = _fatherName.getText().toString().trim();
        String maritalStatus =_maritalStatus.getText().toString().trim();
        String moveInDate = _moveindateET.getText().toString().trim();
        String rentAmount = _rentamountET.getText().toString().trim();
        String note = _note.getText().toString().trim();


        if (tenantName.isEmpty() || tenantName.length() < 3) {
            _tenantET.setError("at least 3 characters");
            valid = false;
        } else {
            _tenantET.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailET.setError("enter a valid email address");
            valid = false;
        } else {
            _emailET.setError(null);
        }


        if (mobile.isEmpty() || mobile.length() != 10) {
            _mobilenoET.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobilenoET.setError(null);
        }

        if (citizenNo.isEmpty()) {
            _citizennoET.setError("Enter Valid citizenship Number");
            valid = false;
        } else {
            _citizennoET.setError(null);
        }

        if (sex.isEmpty() || sex.length() < 4) {
            _sex.setError("Enter male or female");
            valid = false;
        } else {
            _sex.setError(null);
        }

        if (DOB.isEmpty()) {
            _datOfBirth.setError("Enter the valid birth date");
            valid = false;
        } else {
            _datOfBirth.setError(null);
        }

        if (zone.isEmpty() || zone.length() < 3 ) {
            _zone.setError("at least 3 characters");
            valid = false;
        } else {
            _zone.setError(null);
        }

        if (district.isEmpty() || district.length() < 3) {
            _district.setError("at least 3 characters");
            valid = false;
        } else {
            _district.setError(null);
        }

        if (municipality.isEmpty() || municipality.length() < 3) {
            _municipality.setError("at least 3 characters");
            valid = false;
        } else {
            _municipality.setError(null);
        }

        if (wardNo.isEmpty()) {
            _wardNo.setError("Enter the ward number");
            valid = false;
        } else {
            _wardNo.setError(null);
        }

        if (fatherName.isEmpty() || fatherName.length() < 3) {
            _fatherName.setError("at least 3 characters");
            valid = false;
        } else {
            _fatherName.setError(null);
        }

        if (maritalStatus.isEmpty() || maritalStatus.length() < 3) {
            _maritalStatus.setError("Enter single or married");
            valid = false;
        } else {
            _maritalStatus.setError(null);
        }

        if (moveInDate.isEmpty()) {
            _moveindateET.setError("Enter Valid Date");
            valid = false;
        } else {
            _moveindateET.setError(null);
        }

        if (rentAmount.isEmpty() || rentAmount.length() < 3 || rentAmount.length() > 7) {
            _rentamountET.setError("Enter the valid rent amount");
            valid = false;
        } else {
            _rentamountET.setError(null);
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
