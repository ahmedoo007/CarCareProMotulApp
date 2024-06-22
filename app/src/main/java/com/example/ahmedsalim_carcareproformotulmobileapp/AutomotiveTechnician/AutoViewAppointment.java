package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientHome;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ViewAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.ImageAdapterViewAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserAppointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AutoViewAppointment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapterViewAppointment mAdapter;
    private DatabaseReference mDatabaseRef, ref1;
    private FirebaseAuth firebaseAuth;
    private ValueEventListener mDBListener;
    private FirebaseUser user;
    private List<UserAppointment> mUploads;
    private ProgressBar mProgressCircle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_view_appointment);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view4);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle4);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapterViewAppointment(AutoViewAppointment.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Appointment");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mUploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot ds : postSnapshot.child("all").getChildren()) {
                            UserAppointment userupload= ds.getValue(UserAppointment.class);
                            userupload.setKey(ds.getKey());
                            mUploads.add(userupload);
                        }
                        mAdapter.notifyDataSetChanged();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                }
                if (!snapshot.exists()){
                    Toast.makeText(AutoViewAppointment.this, "There is no Appointment yet!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AutoViewAppointment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AutoViewAppointment.this, AutoHome.class));
        finish();
    }
}