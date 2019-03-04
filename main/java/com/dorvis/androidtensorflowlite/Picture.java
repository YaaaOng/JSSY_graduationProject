package com.dorvis.androidtensorflowlite;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Picture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);

        setTitle("단어장"); //액티비티 라벨

        //이미지 출력을 위한 텍스트뷰와 이미지뷰 인식
        TextView tv_result = (TextView)findViewById(R.id.textViewResult);
        ImageView iv_result = (ImageView)findViewById(R.id.imageViewResult);

        //이 액티비티를 호출한 인턴트를 반환하고, 인텐트의 it_tag변수에 저장된 값 추출
        Intent it = getIntent();
        String tag = it.getStringExtra("it_tag");

        Resources res = getResources();

        //제목 출력
        int id_title = res.getIdentifier("textViewResult" +tag, "string", getPackageName());//명화제목출력

        String title = res.getString(id_title);
        tv_result.setText(title);

        //이미지 출력
        int id_picture = res.getIdentifier("imageViewResult" + tag, "String", getPackageName());

        String picture = res.getString(id_picture);
        int id_img = res.getIdentifier(picture, "drawable", getPackageName());

        Drawable drawable = res.getDrawable(id_img);
        iv_result.setBackground(drawable);

    }

    public void closePicture(View v){
        finish();   //현재 액티비티 종료
    }
}
