package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MakePayment extends AppCompatActivity {
    TextView bo_name, bo_price, bo_detail;
    EditText credit, us_address, us_name, us_phone;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button confirm;
    String user_credit, text, u_address, u_name, u_phone;
    String getKey, getUid;
    String img_price, img_name, img_url;
    ImageView imgGet;
    TextView here, cal_price;

    private FirebaseStorage mStrorage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    DatabaseReference ref, ref1, ref2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        radioGroup= (RadioGroup) findViewById(R.id.radioGroup1);
        confirm= (Button) findViewById(R.id.confirm1);
        bo_name= (TextView) findViewById(R.id.bo_name1);
        bo_price= (TextView) findViewById(R.id.bo_price1);
        credit= (EditText) findViewById(R.id.credit1);
        us_address= (EditText) findViewById(R.id.buyer_Address1);
        us_name= (EditText) findViewById(R.id.buyer_name1);
        us_phone= (EditText) findViewById(R.id.buyer_phone1);
        here= (TextView) findViewById(R.id.here1);

        imgGet= (ImageView) findViewById(R.id.imgGet1);

        credit.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (getIntent().hasExtra("key") || getIntent().hasExtra("uid")) {
            getKey = getIntent().getStringExtra("key");
            getUid= getIntent().getStringExtra("uid");
        }

        mStrorage = FirebaseStorage.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Products").child(getUid).child("image").child(getKey);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserProduct userProduct = snapshot.getValue(UserProduct.class);
                    bo_name.setText("Product Name: "+userProduct.getProName());
                    bo_price.setText("Price: "+userProduct.getProPrice());
                    Picasso.get()
                            .load(userProduct.getProUrl())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(imgGet);

                    img_name= userProduct.getProName();
                    img_price= userProduct.getProPrice();
                    img_url= userProduct.getProUrl();

                }
                if (!snapshot.exists()){
                    Toast.makeText(MakePayment.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MakePayment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                text= radioButton.getText().toString();
                if (text.equals("Cash on delivery")) {
                    if (validate1()) {
                        successMessager();
                    }
                } else {
                    credit.setVisibility(View.VISIBLE);
                    if (validate1()){
                        if (validate()) {
                           successMessager();
                        }//validate end
                    }
                }
            }
        });
    }

    private Boolean validate(){
        boolean result= false;

        user_credit = credit.getText().toString();

        if(user_credit.isEmpty()){
            Toast.makeText(this, "Enter credit card number", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private Boolean validate1(){
        boolean result= false;

        u_address= us_address.getText().toString();
        u_name = us_name.getText().toString();
        u_phone= us_phone.getText().toString();

        if(u_address.isEmpty() || u_name.isEmpty() || u_phone.isEmpty() ){
            Toast.makeText(this, "Enter all required details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    public void successMessager() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MakePayment.this);
        alertDialog.setTitle("Product Order Confirm");
        alertDialog.setMessage("Product is purchased, and it will be at this address "+u_address +" within 2 days");
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MakePayment.this, ClientHome.class));
                        finish();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MakePayment.this, BuyProduct.class));
        finish();
    }
}