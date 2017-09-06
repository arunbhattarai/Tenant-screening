package com.example.urgenism.tenantscreening;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.urgenism.tenantscreening.List.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUserInformationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    final static String TAG = "check";

    private EditText _userName,  _address, _mobileNo, _sex, _datOfBirth, _citizenNo;
    private Button save;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _userName = (EditText) findViewById(R.id.userName);
        _address = (EditText) findViewById(R.id.address);
        _mobileNo = (EditText) findViewById(R.id.mobileNo);
        _sex = (EditText) findViewById(R.id.sex);
        _datOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        _citizenNo = (EditText) findViewById(R.id.citizenshipNo);

        save = (Button) findViewById(R.id.save);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = user.getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                Log.d(TAG, "User name: " + user.getName());
                Log.d(TAG, "mobile No: " + user.getMobile_no());


                _userName.setText(user.getName());
                _mobileNo.setText(user.getMobile_no());
                _address.setText(user.getAddress());
                _sex.setText(user.getSex());
                _datOfBirth.setText(user.getDate_of_birth());
                _citizenNo.setText(user.getCitizenship_no());



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        databaseUser = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    updateUserInfo();



                    startActivity(new Intent(EditUserInformationActivity.this, MyInformationActivity.class));
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                }
            }
        });
    }

    public boolean validate()
    {
        boolean valid = true;

        String name = _userName.getText().toString().trim();
        String address = _address.getText().toString().trim();
        String mobile = _mobileNo.getText().toString().trim();
        String sex = _sex.getText().toString().trim();
        String DOB = _datOfBirth.getText().toString().trim();
        String citizenshipNo= _citizenNo.getText().toString().trim();


        if (name.isEmpty() || name.length() < 3) {
            _userName.setError("at least 3 characters");
            valid = false;
        } else {
            _userName.setError(null);
        }

        if (address.isEmpty() || address.length() < 3) {
            _address.setError("enter a valid address");
            valid = false;
        } else {
            _address.setError(null);
        }
        if (mobile.isEmpty() || mobile.length() != 10) {
            _mobileNo.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileNo.setError(null);
        }

        if (sex.isEmpty() || name.length() < 4) {
            _sex.setError("Enter male or female");
            valid = false;
        } else {
            _sex.setError(null);
        }

        if (DOB.isEmpty() || DOB.length() < 5) {
            _datOfBirth.setError("enter a valid date of birth");
            valid = false;
        } else {
            _datOfBirth.setError(null);
        }
        if (citizenshipNo.isEmpty() || citizenshipNo.length() < 2) {
            _citizenNo.setError("Enter Valid Citizenship Number");
            valid = false;
        } else {
            _citizenNo.setError(null);
        }

        return valid;
    }

    public void updateUserInfo(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userID = user.getUid();

        String name = _userName.getText().toString().trim();
        String address = _address.getText().toString().trim();
        String mobile = _mobileNo.getText().toString().trim();
        String sex = _sex.getText().toString().trim();
        String DOB = _datOfBirth.getText().toString().trim();
        String citizenshipNo= _citizenNo.getText().toString().trim();

        UserInformation userInfo = new UserInformation(userID, name, address,sex, DOB,
                citizenshipNo, mobile);
        databaseUser.setValue(userInfo);


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
