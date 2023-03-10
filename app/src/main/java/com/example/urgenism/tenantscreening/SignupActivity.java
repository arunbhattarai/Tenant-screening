package com.example.urgenism.tenantscreening;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.urgenism.tenantscreening.List.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPassword, inputAddress, inputDOB,
            inputCitizenshipNo, inputMobile, inputSex;
    private Button btnSignUp;
    private TextView textSignIn;
    private FirebaseAuth auth;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        databaseUser = FirebaseDatabase.getInstance().getReference("Users");

        textSignIn = (TextView) findViewById(R.id.sign_in_text);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputAddress = (EditText) findViewById(R.id.address);
        inputMobile = (EditText) findViewById(R.id.mobile);
        inputSex = (EditText) findViewById(R.id.sex);
        inputDOB = (EditText) findViewById(R.id.DOB);
        inputCitizenshipNo = (EditText) findViewById(R.id.citizenshipNo);

        inputDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                inputDOB.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String mobile = inputMobile.getText().toString().trim();
                String address = inputAddress.getText().toString().trim();
                String DOB = inputDOB.getText().toString().trim();
                String sex = inputSex.getText().toString().trim();
                String citizenNo = inputCitizenshipNo.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (name.length() < 3) {
                    Toast.makeText(getApplicationContext(), "Name should contain atleast 3 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Enter valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter mobile number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mobile.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Enter valid mobile number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (address.length() < 3) {
                    Toast.makeText(getApplicationContext(), "Address should contain atleast 3 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(DOB)) {
                    Toast.makeText(getApplicationContext(), "Enter your birthDate!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(sex)) {
                    Toast.makeText(getApplicationContext(), "Enter sex", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sex.length() < 4) {
                    Toast.makeText(getApplicationContext(), "Enter male or female", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(citizenNo)) {
                    Toast.makeText(getApplicationContext(), "Enter your citizenship no.", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Registration failed!....",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    registerUser(task.getResult().getUser());
                                    sendVerificationEmail();

                                    }
                                }

                        });


            }
        });
    }

    public void registerUser(FirebaseUser user){


        String name = inputName.getText().toString().trim();
        String mobile = inputMobile.getText().toString().trim();
        String address = inputAddress.getText().toString().trim();
        String DOB = inputDOB.getText().toString().trim();
        String sex = inputSex.getText().toString().trim();
        String citizenNo = inputCitizenshipNo.getText().toString().trim();

        String userID = user.getUid();
        UserInformation newUser = new UserInformation(userID, name, address, sex, DOB,
                citizenNo, mobile);
        databaseUser.child(userID).setValue(newUser);
        Toast.makeText(this,"Successfully registered!....",Toast.LENGTH_SHORT).show();

    }

        private void sendVerificationEmail()
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            Toast.makeText(getApplicationContext(),"Check your email for confirmation!!..",Toast.LENGTH_LONG).show();
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
            }
        }



}

