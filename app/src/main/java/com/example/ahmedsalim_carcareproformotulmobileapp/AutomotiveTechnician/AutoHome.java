package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.Client.AddFeedback;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.BookAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.BuyProduct;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.CancelAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.Chat;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientDeleteAccount;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientEditProfile;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientHome;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientViewProfile;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ViewAppointment;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.SelectUserType;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserInformation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AutoHome extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button add_product, delete_product, view_app, chat, view_product, view_profile, edit_profile, delete_account, logout, view_feed;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public static final String SHARED_PREFS= "sharedPrefs";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_home);

        drawerLayout= findViewById(R.id.drawerlayout1);
        navigationView= findViewById(R.id.navigationview1);
        toolbar= findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);

        add_product= findViewById(R.id.add_product);
        delete_product= findViewById(R.id.delete_product);
        view_app= findViewById(R.id.view_app1);
        chat= findViewById(R.id.chat1);
        view_product= findViewById(R.id.view_product);
        view_profile= findViewById(R.id.view_profile1);
        edit_profile= findViewById(R.id.edit_profile1);
        delete_account= findViewById(R.id.delete_account1);
        logout= findViewById(R.id.logout1);
        view_feed= findViewById(R.id.view_feed);

        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        //to access header to display user name
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.m_nameHeaderuser);
        //dislay name end of header


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                navUsername.setText(userInformation.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AutoHome.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();

                if (id == R.id.home_menu1) {
                    startActivity(new Intent(AutoHome.this, AutoHome.class));
                    finish();

                } else if (id == R.id.m_view_ap_menu) {
                    startActivity(new Intent(AutoHome.this, AutoViewAppointment.class));
                    finish();

                } else if (id == R.id.m_chat_menu) {
                    startActivity(new Intent(AutoHome.this, AutoChatName.class));
                    finish();


                } else if (id == R.id.m_view_feed_menu) {
                    startActivity(new Intent(AutoHome.this, ViewFeedback.class));
                    finish();

                } else if (id == R.id.m_addProduct_menu) {
                    startActivity(new Intent(AutoHome.this, AddProduct.class));
                    finish();

                } else if (id == R.id.m_viewPro_menu) {
                    startActivity(new Intent(AutoHome.this, ViewProduct.class));
                    finish();

                } else if (id == R.id.m_delPro_menu) {
                    startActivity(new Intent(AutoHome.this, DeleteProduct.class));
                    finish();

                } else if (id == R.id.m_delete_menu) {
                    startActivity(new Intent(AutoHome.this, AutoDeleteAccount.class));
                    finish();

                } else if (id == R.id.m_view_menu) {
                    startActivity(new Intent(AutoHome.this, AutoViewProfile.class));
                    finish();

                } else if (id == R.id.m_edit_menu) {
                    startActivity(new Intent(AutoHome.this, AutoEditProfile.class));
                    finish();

                }  else if (id == R.id.m_logout_menu) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    firebaseAuth.signOut();
                    finish();
                    Toast.makeText(AutoHome.this, "Account Logout", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AutoHome.this, SelectUserType.class));
                }
                return false;
            }
        });



        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, AddProduct.class));
                finish();
            }
        });

        delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, DeleteProduct.class));
                finish();
            }
        });

        view_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, AutoViewAppointment.class));
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, AutoChatName.class));
                finish();
            }
        });

        view_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, ViewProduct.class));
                finish();
            }
        });

        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, AutoViewProfile.class));
                finish();
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, AutoEditProfile.class));
                finish();
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, AutoDeleteAccount.class));
                finish();
            }
        });

        view_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutoHome.this, ViewFeedback.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                firebaseAuth.signOut();
                finish();
                Toast.makeText(AutoHome.this, "Account Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AutoHome.this, SelectUserType.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                AutoHome.this);
        alertDialog.setTitle("Leave application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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