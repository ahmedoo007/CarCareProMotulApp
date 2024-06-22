package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class ViewAppointment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapterViewAppointment mAdapter;
    private DatabaseReference mDatabaseRef, ref1;
    private FirebaseAuth firebaseAuth;
    private ValueEventListener mDBListener;
    private FirebaseUser user;
    private List<UserAppointment> mUploads;
    private ProgressBar mProgressCircle;
    TextView txt_toast;

    String selectedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle3);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapterViewAppointment(ViewAppointment.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Appointment").child(user.getUid()).child("all");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mUploads.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        UserAppointment userupload= postSnapshot.getValue(UserAppointment.class);
                        userupload.setKey(postSnapshot.getKey());
                        mUploads.add(userupload);
                    }
                    mAdapter.notifyDataSetChanged();
                    mProgressCircle.setVisibility(View.INVISIBLE);
                }
                if (!snapshot.exists()){
                    Toast.makeText(ViewAppointment.this, "There is no Appointment yet!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAppointment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(ViewAppointment.this, ClientHome.class));
        finish();
    }
}