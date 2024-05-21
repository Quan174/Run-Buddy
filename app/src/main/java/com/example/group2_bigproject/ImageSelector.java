package com.example.group2_bigproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImageSelector {
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int PICK_IMAGE_REQUEST = 2;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static void selectImage(Activity activity) {
        Toast.makeText(activity.getApplicationContext(),"SI runned", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_EXTERNAL_STORAGE);
        } else {
            openImagePicker(activity);
        }
    }

    public static void openImagePicker(Activity activity) {
        Toast.makeText(activity.getApplicationContext(),"OIP runned", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}
