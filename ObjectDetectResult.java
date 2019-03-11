package com.dorvis.androidtensorflowlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.content.Intent;


public class ObjectDetectResult extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detect_result);

        TextView textViewResult = findViewById(R.id.textViewResult);
        Intent intent = getIntent();
        String detectResultWord = intent.getStringExtra("resultText_KEY");
        textViewResult.setText(detectResultWord);


    }
}
