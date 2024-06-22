package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AutoEditProfile extends AppCompatActivity {
    private EditText u_gender, u_fname, u_phone, u_add;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button update;
    String user_gander, user_fname, user_uphone, user_add;
    NetworkInfo nInfo;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_edit_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        update=(Button) findViewById(R.id.update1);
        u_gender= (EditText)findViewById(R.id.u_gender1);
        u_fname= (EditText) findViewById(R.id.u_fname1);
        u_phone= (EditText) findViewById(R.id.u_phone1);
        u_add= (EditText) findViewById(R.id.u_add1);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo = cManager.getActiveNetworkInfo();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        //get firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation= dataSnapshot.getValue(UserInformation.class);
                u_gender.setText(userInformation.getUserGender());
                u_fname.setText(userInformation.getUserName());
                u_phone.setText(userInformation.getUserPhone());
                u_add.setText(userInformation.getUserAddress());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AutoEditProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user!=null){
                    if (validate()) {
                        if (nInfo != null && nInfo.isConnected()) {
                            UserInformation userInformation = new UserInformation(user_fname, user_uphone, user_add,user_gander);
                            ref.setValue(userInformation);
                            Toast.makeText(AutoEditProfile.this, "Profile is Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AutoEditProfile.this, AutoHome.class));
                            finish();

                        }
                    } else {
                        Toast.makeText(AutoEditProfile.this, "Network is not available", Toast.LENGTH_LONG).show();
                    }
                }//user null
            }
        });
    }

    private Boolean validate(){
        boolean result= false;

        user_gander = u_gender.getText().toString();
        user_fname = u_fname.getText().toString();
        user_uphone=u_phone.getText().toString();
        user_add= u_add.getText().toString();
        if(user_gander.isEmpty() ||  user_fname.isEmpty() || user_uphone.isEmpty() || user_add.isEmpty()){
            Toast.makeText(this, "Fill every required information", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AutoEditProfile.this, AutoHome.class));
        finish();
    }
}