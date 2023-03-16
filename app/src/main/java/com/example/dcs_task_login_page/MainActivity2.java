package com.example.dcs_task_login_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private int STORAGE_PERMISSION_CODE = 1;
    private Button upload, logout;
    private ImageButton back;
    private final int GALLERY_REQ_CODE = 1;
    private ImageView pic;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        upload = (Button) findViewById(R.id.upload_photos);
        upload.setOnClickListener(this);
        logout = (Button) findViewById(R.id.log_out);
        logout.setOnClickListener(this);
        pic=(ImageView) findViewById(R.id.img);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.back:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.upload_photos:
                upload_pho();
                break;
        }
    }

    private void upload_pho() {
        if (ContextCompat.checkSelfPermission(MainActivity2.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            upload_photos();
        } else {
            requestStoragePermission();
        }
    }

    private void upload_photos() {
        Intent intent = new Intent(Intent.ACTION_PICK, (MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        startActivityForResult(intent, GALLERY_REQ_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Do something with the selected image
            // For example, you can set it to an ImageView
            pic.setImageURI(selectedImageUri);
        }
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Required Permission: ")
                    .setMessage("This is required to access photos to upload.")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity2.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
