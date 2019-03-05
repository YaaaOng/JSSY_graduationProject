package com.dorvis.androidtensorflowlite;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VocaActivity extends AppCompatActivity {

    ArrayList<String> vocaList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca);

        StringBuffer buffer= new StringBuffer();

        View imageViewResult = findViewById(R.id.imageViewResult);

        try{
            FileInputStream fi = openFileInput("data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fi));

            String str = reader.readLine();
            String str2 = reader.readLine();
            String english;
            String korean;
            String temp;
            String filename;

            int i=0;
            int englishindex;
            int koreanindex;

            while(str!=null){
                vocaList.add(str+"\n"+str2);
                buffer.append(str+"\n");

                str = reader.readLine();
                str2 = reader.readLine();
            }
            while(vocaList.get(i) != null){
                temp = vocaList.get(i);
                englishindex = temp.indexOf("\n");
                koreanindex = temp.indexOf("\n", englishindex + 1);
                english = temp.substring(0, englishindex);
                korean = temp.substring(englishindex + 1, koreanindex);
                filename = temp.substring(koreanindex + 1);

                File imgFile = new File(filename);
                Drawable d;

                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    d = new BitmapDrawable(getResources(), myBitmap);
                }
                else {
                    d = null;
                }
                adapter.addItem(d, english, korean);
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}