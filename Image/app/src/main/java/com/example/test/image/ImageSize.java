package com.example.test.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageSize extends AppCompatActivity {

    ImageView iv_city;
    ImageView iv_kiwi;
    ImageView iv_money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_size);


        iv_city = (ImageView)findViewById(R.id.iv_city);
        iv_kiwi = (ImageView)findViewById(R.id.iv_kiwi);
        iv_money = (ImageView)findViewById(R.id.iv_money);

        //  out of memory

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // 1/inSampleSize resize 2, 4, 8, 16 -> 4분의1로 줄임

        //  1/inSampleSize 비율로 리사이징
        //  4분의 1로 줄어든 비트맵을 만듬
        Bitmap bitmap_city = BitmapFactory.decodeResource(getResources(), R.drawable.city, options);
        Bitmap bitmap_kiwi = BitmapFactory.decodeResource(getResources(), R.drawable.kiwi, options);
        Bitmap bitmap_money = BitmapFactory.decodeResource(getResources(), R.drawable.money, options);

        //  200. 200 으로 리사이징
        //  높낮이를 변경하여 비트맵 만듬
        Bitmap bitmap_city_resize = Bitmap.createScaledBitmap(bitmap_city, 350, 200, true);
        Bitmap bitmap_kiwi_resize = Bitmap.createScaledBitmap(bitmap_kiwi, 350, 200, true);
        Bitmap bitmap_money_resize = Bitmap.createScaledBitmap(bitmap_money, 350, 200, true);

        //  이미지 뷰에 적용됨
        iv_city.setImageBitmap(bitmap_city_resize);
        iv_kiwi.setImageBitmap(bitmap_kiwi_resize);
        iv_money.setImageBitmap(bitmap_money_resize);

    }

}
