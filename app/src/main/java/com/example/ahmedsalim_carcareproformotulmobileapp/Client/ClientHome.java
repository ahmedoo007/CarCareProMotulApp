package com.example.ahmedsalim_carcareproformotulmobileapp.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class ClientHome extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button book_app, cancel_app, view_app, chat, buy, view_profile, edit_profile, delete_account, logout, add_feed;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    public static final String SHARED_PREFS= "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        drawerLayout= findViewById(R.id.drawerlayout);
        navigationView= findViewById(R.id.navigationview);
        toolbar= findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        book_app= findViewById(R.id.book);
        cancel_app= findViewById(R.id.delete_app);
        view_app= findViewById(R.id.view_app);
        chat= findViewById(R.id.chat);
        buy= findViewById(R.id.buy);
        view_profile= findViewById(R.id.view_profile);
        edit_profile= findViewById(R.id.edit_profile);
        delete_account= findViewById(R.id.delete_account);
        logout= findViewById(R.id.logout);
        add_feed= findViewById(R.id.add_feed);

        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //to access header to display user name
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nameHeaderUSer);
        //dislay name end of header


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation= dataSnapshot.getValue(UserInformation.class);
                navUsername.setText(userInformation.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ClientHome.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open, R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();

                if (id == R.id.home_menu) {
                    startActivity(new Intent(ClientHome.this, ClientHome.class));
                    finish();

                } else if (id == R.id.add_menu) {
                    startActivity(new Intent(ClientHome.this, BookAppointment.class));
                    finish();

                } else if (id == R.id.buy_menu) {
                    startActivity(new Intent(ClientHome.this, BuyProduct.class));
                    finish();

                } else if (id == R.id.chat_menu) {
                    startActivity(new Intent(ClientHome.this, Chat.class));
                    finish();

                } else if (id == R.id.add_feedback_menu) {
                    startActivity(new Intent(ClientHome.this, AddFeedback.class));
                    finish();

                } else if (id == R.id.view_ap_menu) {
                    startActivity(new Intent(ClientHome.this, ViewAppointment.class));
                    finish();

                } else if (id == R.id.delete_ap_menu) {
                    startActivity(new Intent(ClientHome.this, CancelAppointment.class));
                    finish();


                } else if (id == R.id.delete_menu) {
                    startActivity(new Intent(ClientHome.this, ClientDeleteAccount.class));
                    finish();

                } else if (id == R.id.view_menu) {
                    startActivity(new Intent(ClientHome.this, ClientViewProfile.class));
                    finish();

                } else if (id == R.id.edit_menu) {
                    startActivity(new Intent(ClientHome.this, ClientEditProfile.class));
                    finish();

                }  else if (id == R.id.logout_menu) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    firebaseAuth.signOut();
                    finish();
                    Toast.makeText(ClientHome.this, "Account Logout", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ClientHome.this, SelectUserType.class));
                }
                return false;
            }
        });



        book_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, BookAppointment.class));
                finish();
            }
        });

        cancel_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, CancelAppointment.class));
                finish();
            }
        });

        view_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, ViewAppointment.class));
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, Chat.class));
                finish();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, BuyProduct.class));
                finish();
            }
        });

        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, ClientViewProfile.class));
                finish();
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, ClientEditProfile.class));
                finish();
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, ClientDeleteAccount.class));
                finish();
            }
        });

        add_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHome.this, AddFeedback.class));
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
                Toast.makeText(ClientHome.this, "Account Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ClientHome.this, SelectUserType.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ClientHome.this);
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