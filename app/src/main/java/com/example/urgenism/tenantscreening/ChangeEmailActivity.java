package com.example.urgenism.tenantscreening;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private EditText oldEmail, oldPassword, newEmail;
    private Button save;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        oldEmail = (EditText) findViewById(R.id.editOldEmail);
        oldPassword = (EditText) findViewById(R.id.editOldPass);
        newEmail = (EditText) findViewById(R.id.editNewEmail);
        save = (Button) findViewById(R.id.save);

        user = FirebaseAuth.getInstance().getCurrentUser();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                        final String _oldEmail = oldEmail.getText().toString().trim();
                        final String _oldPass = oldPassword.getText().toString().trim();
                        final String _newEmail = newEmail.getText().toString().trim();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(_oldEmail, _oldPass);

                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            if(!_oldEmail.equals(_newEmail))
                                            {
                                                user.updateEmail(_newEmail)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(), "User email address updated!!", Toast.LENGTH_SHORT).show();
                                                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                // email sent

                                                                                Toast.makeText(getApplicationContext(),"Check your email for confirmation!!..",Toast.LENGTH_LONG).show();
                                                                                FirebaseAuth.getInstance().signOut();
                                                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                                                finish();
                                                                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                                                                // after email is sent just logout the user and finish this activity

                                                                            } else {
                                                                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                                                                //restart this activity
                                                                                overridePendingTransition(0, 0);
                                                                                finish();
                                                                                overridePendingTransition(0, 0);
                                                                                startActivity(getIntent());

                                                                            }
                                                                        }
                                                                    });
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "User email address  not updated!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "User email address  not updated!!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MyInformationActivity.class));
                                                finish();
                                                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Authentication failed!Check your old email and Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                    }

                }
        });
    }
    public boolean validate() {
        boolean valid = true;

        String _oldEmail = oldEmail.getText().toString().trim();
        String _oldPass = oldPassword.getText().toString().trim();
        String _newEmail = newEmail.getText().toString().trim();

        if (_oldEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_oldEmail).matches()) {
            oldEmail.setError("enter a valid email address");
            valid = false;
        } else {
            oldEmail.setError(null);
        }

        if (_oldPass.isEmpty() || _oldPass.length() < 6) {
            oldPassword.setError("Password too short, enter minimum 6 characters!");
            valid = false;
        } else {
            oldPassword.setError(null);
        }

        if (_newEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_newEmail).matches()) {
            newEmail.setError("enter a valid email address");
            valid = false;
        } else {
            newEmail.setError(null);
        }

        return valid;


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
