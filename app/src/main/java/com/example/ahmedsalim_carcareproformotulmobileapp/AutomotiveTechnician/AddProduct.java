package com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ahmedsalim_carcareproformotulmobileapp.R;
import com.example.ahmedsalim_carcareproformotulmobileapp.UserProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProduct extends AppCompatActivity {
    private Button upload, select;
    private int Request_Code= 234;
    private Uri filepath;
    private ImageView img;
    private EditText price, name, quantity, detail;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String u_price, u_name, u_detail, u_quantity;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        upload=(Button) findViewById(R.id.upload);
        select= (Button) findViewById(R.id.select);

        price= (EditText) findViewById(R.id.price);
        name= (EditText) findViewById(R.id.name);
        quantity= (EditText) findViewById(R.id.quantity);
        detail= (EditText) findViewById(R.id.detail);
        img= (ImageView)findViewById(R.id.img);

        firebaseAuth= FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Products").child(user.getUid()).child("image");

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filepath!=null){
                    if (validate()){
                        upload();
                    }
                }
                else{
                    Toast.makeText(AddProduct.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private Boolean validate(){
        boolean result= false;

        u_price= price.getText().toString();
        u_detail= detail.getText().toString();
        u_name= name.getText().toString();
        u_quantity= quantity.getText().toString();
        if(u_price.isEmpty() || u_detail.isEmpty() || u_name.isEmpty() || u_quantity.isEmpty() ){
            Toast.makeText(AddProduct.this, "First add all details of Product", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private void showImageChooser(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1 && resultCode== RESULT_OK && data != null && data.getData() != null){
            filepath= data.getData();
            img.setImageURI(filepath);

        }
    }

    private String getExtension(Uri uri){
        ContentResolver cr= getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void upload() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference= storageReference.child("uploads/"+System.currentTimeMillis()+ "."+getExtension(filepath));

        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url= uri.getResult();

                        UserProduct userProduct = new UserProduct(u_name, u_price, u_detail, u_quantity, url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(userProduct);

                        progressDialog.dismiss();
                        Toast.makeText(AddProduct.this, "Product Successfully added", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddProduct.this, AutoHome.class));
                        finish();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress= (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialog.setMessage("uploaded: " +(int)progress+" %");
                    }
                });

    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        startActivity(new Intent(AddProduct.this, AutoHome.class));
        finish();

    }
}