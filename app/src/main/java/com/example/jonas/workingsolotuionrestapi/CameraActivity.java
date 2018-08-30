package com.example.jonas.workingsolotuionrestapi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private final static String LOGTAG = "Camera05";
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    File mFile;

    ImageView mImage;
    TextView mFilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mImage = (ImageView) findViewById(R.id.imgView);
        mFilename = (TextView) findViewById(R.id.txtFileName);
        mFilename.setBackgroundColor(Color.LTGRAY);
        findViewById(R.id.btnTakePics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTakePics();

            }
        });
    }

    private void onClickTakePics()
    {

        mFile = getOutputMediaFile(); // create a file to save the image
        if (mFile == null)
        {
            Toast.makeText(this, "Could not create file...", Toast.LENGTH_LONG).show();
            return;
        }
        // create Intent to take a picture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));

        log("file uri = " + Uri.fromFile(mFile).toString());

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showPictureTaken(mFile);

            } else
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show();
                return;

            } else
                Toast.makeText(this, "Picture NOT taken - unknown error...", Toast.LENGTH_LONG).show();
        }
    }

    private void showPictureTaken(File f) {

        mImage.setImageURI(Uri.fromFile(f));
        mImage.setBackgroundColor(Color.RED);
        mImage.setRotation(90);
        mFilename.setText(f.getAbsolutePath());
    }


    void log(String s)
    { Log.d(LOGTAG, s); }

    /** Create a File for saving an image */
    private File getOutputMediaFile(){


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera05");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                log("failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String postfix = "jpg";
        String prefix = "IMG";

        File mediaFile = new File(mediaStorageDir.getPath() +
                File.separator + prefix +
                "_"+ timeStamp + "." + postfix);

        return mediaFile;
    }

    private void scaleImage()
    {
        final Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        final float screenWidth = p.x;
        final float screenHeight = p.y-100;
        mImage.setMaxHeight((int)screenHeight);
        mImage.setMaxWidth((int)screenWidth);
    }
}
