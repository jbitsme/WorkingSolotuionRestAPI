package com.example.jonas.workingsolotuionrestapi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Button makeSkypeCall = (Button) findViewById(R.id.makeSkypeCall);
        final EditText SIPAddress = (EditText) findViewById(R.id.SIPAddress);
        final CheckBox videoCall = (CheckBox) findViewById(R.id.videoCheck);
        makeSkypeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uriString = "ms-sfb://call?id=" + SIPAddress.getText().toString();
                if (videoCall.isChecked()) {
                    uriString += "&video=true";
                }
                Uri uri = Uri.parse(uriString);
                Intent callIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(callIntent);
            }
        });

        Button skypeMessageButton = (Button) findViewById(R.id.makeSkypeMessage);
        skypeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriString = "ms-sfb://chat?id=" + SIPAddress.getText().toString();
                Uri uri = Uri.parse(uriString);
                Intent callIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(callIntent);
            }
        });

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });
    }
}
