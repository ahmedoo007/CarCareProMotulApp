package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

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
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.Client.CancelAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class DeleteProduct extends AppCompatActivity  implements ImageAdapterDeleteProduct.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageAdapterDeleteProduct mAdapter;
    private DatabaseReference mDatabaseRef, ref1;
    private FirebaseStorage mStrorage;
    private FirebaseAuth firebaseAuth;

    private ValueEventListener mDBListener;
    private FirebaseUser user;
    private List<UserProduct> mUploads, mUploads1;
    private ProgressBar mProgressCircle;
    private FirebaseDatabase firebaseDatabase;
    String userName, prodName;
    String im_name, im_price, im_time, im_detail, im_pic, key;

    String selectedUID,selectedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        mStrorage = FirebaseStorage.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle1);

        mUploads = new ArrayList<>();
        mUploads1 = new ArrayList<>();
        mAdapter = new ImageAdapterDeleteProduct(DeleteProduct.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DeleteProduct.this);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot ds: postSnapshot.child("image").getChildren()){
                        UserProduct userProduct= ds.getValue(UserProduct.class);
                        userProduct.setKey(ds.getKey());
                        mUploads.add(userProduct);

                        UserProduct userProduct1= postSnapshot.getValue(UserProduct.class);
                        userProduct1.setKey(postSnapshot.getKey());
                        mUploads1.add(userProduct1);
                    }

                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DeleteProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        UserProduct selectUId= mUploads1.get(position);
        selectedUID= selectUId.getKey();

        UserProduct selectedItem = mUploads.get(position);
        selectedKey = selectedItem.getKey();

        deleteProductFunction();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteProduct.this, AutoHome.class));
        finish();
    }

    public void deleteProductFunction() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                DeleteProduct.this);
        alertDialog.setTitle("Delete Product");
        alertDialog.setMessage("Are you sure you want to delete this product?");
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ref1= FirebaseDatabase.getInstance().getReference().child("Products").child(selectedUID).child("image").child(selectedKey);
                        ref1.getRef().removeValue();
                        Toast.makeText(DeleteProduct.this, "Product with details deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DeleteProduct.this, DeleteProduct.class));
                        finish();
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}