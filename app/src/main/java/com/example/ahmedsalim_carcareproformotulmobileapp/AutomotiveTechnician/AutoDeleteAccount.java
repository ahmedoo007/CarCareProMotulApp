package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.SelectUserType;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AutoDeleteAccount extends AppCompatActivity {
    private TextView u_lname, u_fname, u_phone, u_add;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button del_acc;
    NetworkInfo nInfo;
    private EditText d_email, d_password;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    public static final String SHARED_PREFS= "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_delete_account);

        firebaseAuth = FirebaseAuth.getInstance();
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo = cManager.getActiveNetworkInfo();

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");

        del_acc = (Button) findViewById(R.id.del_acc1);
        u_lname = (TextView) findViewById(R.id.u_lname1);
        u_fname = (TextView) findViewById(R.id.u_fname1);
        u_phone = (TextView) findViewById(R.id.u_phone1);
        u_add = (TextView) findViewById(R.id.u_add1);

        d_email = findViewById(R.id.d_email1);
        d_password = findViewById(R.id.d_password1);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInfo = dataSnapshot.getValue(UserInformation.class);
                u_lname.setText("Gender: "+userInfo.getUserGender());
                u_fname.setText("UserName: " +userInfo.getUserName());
                u_phone.setText("Phone no.: "+userInfo.getUserPhone());
                u_add.setText("Address: "+userInfo.getUserAddress());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AutoDeleteAccount.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        del_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()){
                    //ref.getRef().removeValue();
                    delete_user();
                }
            }
        });
    }

    private void delete_user() {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

        String e= d_email.getText().toString().trim();
        String p= d_password.getText().toString().trim();
        AuthCredential credential = EmailAuthProvider.getCredential(e, p);
        if(nInfo!=null && nInfo.isConnected()) {
            if (user1 != null) {
                user1.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user1.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    ref.getRef().removeValue();
                                                    Log.d("TAG", "User account deleted.");
                                                    Toast.makeText(AutoDeleteAccount.this, "Account Deleted,", Toast.LENGTH_LONG).show();
                                                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                                    SharedPreferences.Editor editor= sharedPreferences.edit();
                                                    editor.clear();
                                                    editor.commit();
                                                    firebaseAuth.signOut();
                                                    startActivity(new Intent(AutoDeleteAccount.this, SelectUserType.class));
                                                    finish();

                                                }
                                            }
                                        });
                            }
                        });
            }
        }
    }

    private Boolean valid(){
        boolean result= false;
        String password1= d_password.getText().toString();
        String email1= d_email.getText().toString();

        if(password1.isEmpty() || email1.isEmpty()){
            Toast.makeText(this, "Please enter email & password", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AutoDeleteAccount.this, AutoHome.class));
        finish();
    }
}