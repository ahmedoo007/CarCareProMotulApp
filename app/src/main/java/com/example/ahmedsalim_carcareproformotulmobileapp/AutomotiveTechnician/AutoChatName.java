package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.Client.Chat;
import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserInformation;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AutoChatName extends AppCompatActivity {

    DatabaseReference reference;

    ArrayList<String> arrayList;

    EditText e1;
    ListView l1;
    ArrayAdapter<String> adapter;
    String name;
    EditText ee;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    String getUser_name;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_chat_name);

        l1 = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        float textSize = 20;

        adapter = new ArrayAdapter<String>(this,R.layout.list_text_view, R.id.listText,arrayList);
        l1.setAdapter(adapter);

        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Chat");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Profile");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation= dataSnapshot.getValue(UserInformation.class);
                getUser_name= userInformation.getUserName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AutoChatName.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.exists()){

                        Iterator i = postSnapshot.getChildren().iterator();
                        key= postSnapshot.getKey();
                        while (i.hasNext()) {
                            set.add(((DataSnapshot) i.next()).getKey());

                        }
                    }
                    if (!postSnapshot.exists()){
                        Toast.makeText(AutoChatName.this, "There is no Live chat request yet", Toast.LENGTH_SHORT).show();
                    }
                }

                arrayList.clear();
                arrayList.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AutoChatName.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                TextView textView = view.findViewById(R.id.listText);
                String getTextValue = textView.getText().toString();

                Intent intent = new Intent(AutoChatName.this, AutoChat.class);
                intent.putExtra("room_name", getTextValue);
                intent.putExtra("user_name", getUser_name);
                intent.putExtra("key", key);
                startActivity(intent);
            }

        });


    }


    public void onBackPressed() {
        //Display alert message when back button has been pressed
        //return;
        startActivity(new Intent(AutoChatName.this, AutoHome.class));
        finish();
    }
}