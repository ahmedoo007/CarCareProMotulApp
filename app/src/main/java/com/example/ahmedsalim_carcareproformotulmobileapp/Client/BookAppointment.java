package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserAppointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner_services;

    Button r_date, t_time, r_complete;
    EditText e_r_date, e_t_time, username, phone;

    String selectedService;
    private FirebaseAuth firebaseAuth;
    NetworkInfo nInfo;

    DatabaseReference ref, ref1, ref2;

    String u_e_r_date, u_e_t_time, u_username, u_phone;
    FirebaseUser user;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Spinner spinnerservice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        spinner_services= findViewById(R.id.spinner_services);
        String[] comps = getResources().getStringArray(R.array.services_list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comps);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_services.setAdapter(arrayAdapter);
        spinner_services.setOnItemSelectedListener(this);

        e_r_date= findViewById(R.id.e_r_date);
        r_date= (Button) findViewById(R.id.r_date);
        e_t_time= findViewById(R.id.e_t_time);
        t_time= findViewById(R.id.t_time);
        username= findViewById(R.id.with_user);
        phone= findViewById(R.id.with_phone);
        t_time= findViewById(R.id.t_time);
        r_complete= (Button) findViewById(R.id.r_complete);


        firebaseAuth= FirebaseAuth.getInstance();
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);


        r_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(BookAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                e_r_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        t_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                e_t_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        r_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validService()){
                    if (validate()){
                        ref = FirebaseDatabase.getInstance().getReference("Appointment").child(user.getUid()).child("all");
                        UserAppointment userAppointment= new UserAppointment(selectedService, u_e_t_time, u_e_r_date, u_username, u_phone);
                        ref.child(ref.push().getKey()).setValue(userAppointment);
                        Toast.makeText(BookAppointment.this, "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BookAppointment.this, ClientHome.class));
                        finish();
                    }
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedService = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), selectedService, Toast.LENGTH_SHORT);
    }

    private Boolean validService(){
        boolean result= false;

        if (selectedService.equals("Select any Service")){
            Toast.makeText(this, "Select any service first", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;

    }

    private Boolean validate(){
        boolean result= false;

        u_e_r_date = e_r_date.getText().toString();
        u_e_t_time= e_t_time.getText().toString();
        u_username= username.getText().toString();
        u_phone= phone.getText().toString();

        if(u_e_t_time.isEmpty() || (u_e_r_date.isEmpty()) || u_username.isEmpty() || u_phone.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        startActivity(new Intent(BookAppointment.this, ClientHome.class));
        finish();
    }
}