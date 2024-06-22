package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician.AutoHome;
import com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician.DeleteProduct;
import com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician.ImageAdapterDeleteProduct;
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

public class BuyProduct extends AppCompatActivity implements ImageAdapterBuyProduct.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageAdapterBuyProduct mAdapter;
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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        mStrorage = FirebaseStorage.getInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle2);

        mUploads = new ArrayList<>();
        mUploads1 = new ArrayList<>();
        mAdapter = new ImageAdapterBuyProduct(BuyProduct.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(BuyProduct.this);


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
                Toast.makeText(BuyProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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


        Intent intent = new Intent(BuyProduct.this, MakePayment.class);
        intent.putExtra("key", selectedKey);
        intent.putExtra("uid", selectedUID);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BuyProduct.this, ClientHome.class));
        finish();
    }
}