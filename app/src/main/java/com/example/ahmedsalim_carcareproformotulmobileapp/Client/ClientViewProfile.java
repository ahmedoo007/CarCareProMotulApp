package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class ClientViewProfile extends AppCompatActivity {
    private TextView u_gender, u_fname, u_phone, u_add;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button update;
    NetworkInfo nInfo;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        update=(Button) findViewById(R.id.v_update);
        u_gender= (TextView) findViewById(R.id.uv_gender);
        u_fname= (TextView) findViewById(R.id.uv_fname);
        u_phone= (TextView) findViewById(R.id.uv_phone);
        u_add= (TextView) findViewById(R.id.uv_add);

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
                Toast.makeText(ClientViewProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientViewProfile.this, ClientEditProfile.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ClientViewProfile.this, ClientHome.class));
        finish();
    }
}