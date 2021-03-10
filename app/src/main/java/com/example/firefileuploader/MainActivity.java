package com.example.firefileuploader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout;

    StorageReference storageReference;
    ImageButton imageButton;
    ImageView imageView;

    private static final int gallary = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout =findViewById(R.id.relLay);

        imageButton = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        storageReference = FirebaseStorage.getInstance().getReference();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent,gallary);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        ProgressBar progressBar = new ProgressBar(MainActivity.this,null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( 100,100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(progressBar,layoutParams);

        if(requestCode == gallary){
          progressBar.setVisibility(View.VISIBLE);
            Uri uri = data.getData();

            imageView.setImageURI(uri);

            StorageReference fileName = storageReference.child("Photos/"+uri.getLastPathSegment()+".png");

            fileName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Upload Done!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }



    }
}




































