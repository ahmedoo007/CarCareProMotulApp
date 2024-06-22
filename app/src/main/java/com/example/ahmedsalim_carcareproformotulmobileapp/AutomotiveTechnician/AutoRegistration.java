package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientLogin;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientRegistration;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AutoRegistration extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    NetworkInfo nInfo;
    private EditText email, password, cPassword, name, phone, address;
    private Button signup;
    String user_email, user_password, user_cPassword, user_name, user_phone, user_gender, user_address;
    private TextView log;

    String[] gend= {"Select Gender","Male","Female"};
    String s, value;
    Spinner spinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_registration);

        firebaseAuth= FirebaseAuth.getInstance();
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        nInfo = cManager.getActiveNetworkInfo();

        email= (EditText) findViewById(R.id.email1);
        password= (EditText) findViewById(R.id.password1);
        cPassword= (EditText) findViewById(R.id.c_password1) ;
        name= (EditText) findViewById(R.id.name1);
        phone= (EditText) findViewById(R.id.phone1);
        spinner= (Spinner) findViewById(R.id.spinner1);
        address= (EditText) findViewById(R.id.address1);
        signup= (Button) findViewById(R.id.signup1);
        log= (TextView) findViewById(R.id.log1);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(AutoRegistration.this, android.R.layout.simple_spinner_item, gend);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value= adapterView.getItemAtPosition(i).toString();
                s= value;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    if (paswordlength()) {
                        if (paswordmatch()) {
                            if (valid()){
                            if (nInfo != null && nInfo.isConnected()) {

                                String email_user = email.getText().toString().trim();
                                String pass_user = password.getText().toString().trim();
                                final String phone_user = phone.getText().toString().trim();
                                final String name_user = name.getText().toString().trim();
                                final String address_user = address.getText().toString().trim();

                                firebaseAuth.createUserWithEmailAndPassword(email_user, pass_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AutoRegistration.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(AutoRegistration.this, AutoLogin.class));

                                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                            if (firebaseUser != null) {

                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                                UserInformation userInformation = new UserInformation(name_user, phone_user, address_user, user_gender);
                                                ref.child(firebaseUser.getUid()).child("Profile").setValue(userInformation);
                                            }

                                            finish();

                                        } else {
                                            Toast.makeText(AutoRegistration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(AutoRegistration.this, "Network is not available", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    }
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoRegistration.this, AutoLogin.class));
                finish();
            }
        });
    }

    private Boolean validate(){
        boolean result= false;

        user_email = email.getText().toString();
        user_password = password.getText().toString();
        user_cPassword= cPassword.getText().toString();
        user_name = name.getText().toString();
        user_phone = phone.getText().toString();
        user_address= address.getText().toString();
        if(user_name.isEmpty() || user_password.isEmpty() || user_email.isEmpty() ||
                user_phone.isEmpty() || user_cPassword.isEmpty() || user_address.isEmpty()){
            Toast.makeText(this, "Fill every required information", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private Boolean paswordlength(){
        boolean result1= false;
        if (user_password.length() < 6 ){
            Toast.makeText(this, "Password should be of at-least 6 characters", Toast.LENGTH_SHORT).show();
        }else{
            result1= true;
        }
        return result1;
    }

    private Boolean paswordmatch(){
        boolean result2= false;
        if (!user_password.equals(user_cPassword)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }else{
            result2= true;
        }
        return result2;
    }

    private Boolean valid(){
        boolean result= false;
        user_gender= s;

        if(user_gender.equals("Select Gender")){
            Toast.makeText(this, "Select any Gender First", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        finish();
    }
}