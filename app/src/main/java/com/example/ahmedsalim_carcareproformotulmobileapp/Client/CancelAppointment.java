package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class CancelAppointment extends AppCompatActivity implements ImageAdapterCancelAppointment.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageAdapterCancelAppointment mAdapter;
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
        setContentView(R.layout.activity_cancel_appointment);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view5);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle5);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapterCancelAppointment(CancelAppointment.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(CancelAppointment.this);

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
                    Toast.makeText(CancelAppointment.this, "There is no appointment yet!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CancelAppointment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

        });
    }

    @Override
    public void onItemClick(int position) {
        UserAppointment selectedItem = mUploads.get(position);
        selectedKey = selectedItem.getKey();
        deleteBookingFunction();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    public void deleteBookingFunction() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                CancelAppointment.this);
        // Setting Dialog Title
        alertDialog.setTitle("Delete Booking");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to delete this booking?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ref1 = FirebaseDatabase.getInstance().getReference("Appointment").child(user.getUid()).child("all").child(selectedKey);
                        ref1.getRef().removeValue();
                        Toast.makeText(CancelAppointment.this, "Appointment canceled", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CancelAppointment.this, CancelAppointment.class));
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CancelAppointment.this, ClientHome.class));
        finish();
    }
}