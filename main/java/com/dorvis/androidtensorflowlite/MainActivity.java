package com.dorvis.androidtensorflowlite;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 *  Created by sainathhiwale on 1/5/18
 */
public class MainActivity extends AppCompatActivity {

    private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite";
    private static final String LABEL_PATH = "labels.txt";
    private static final int INPUT_SIZE = 224;

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    //private TextView textViewResult;
    private Button btnDetectObject, btnToggleCamera;
    //private ImageView imageViewResult;
    private CameraView cameraView;
    private String sourceText;
    private String resultText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);


        //final ImageView imageViewResult = view.findViewById(R.id.imageViewResult);
        //final TextView textViewResult = view.findViewById(R.id.textViewResult);
        //textViewResult.setMovementMethod(new ScrollingMovementMethod());

        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                //imageViewResult.setImageBitmap(bitmap);
                //activity_object_detect_result 에 넣기, 다른 화면에 넣는거 구글링



                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

                //textViewResult.setText(results.toString());
                Translator translator = new Translator();
                sourceText = getText(results.toString());
                translator.execute(sourceText);
                resultText = translator.resultText;
                //textViewResult.setText(resultText);


                //activity_object_detect_result 에 넣기

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
//toggle camera 기능 사용할건지 말건지 정하기
        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraView.captureImage();
                Intent intent = new Intent(MainActivity.this,ObjectDetectResult.class);
                intent.putExtra("resultText_KEY",resultText);
                startActivity(intent);
            }
        });

        initTensorFlowAndLoadModel();
    }

    protected String getText(String s) {
        return s;
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
}
