package com.example.test.httpsession;

import android.graphics.Bitmap;

import io.realm.RealmObject;

/**
 * Created by okooo on 2018-12-21.
 */
//  MODEL
//  like DB의 table
//  Realm 상속받기
public class ImageVO extends RealmObject {
    private int number;
    private byte[] poster;  //  바이트 이미지로 받기

    //  default constructor
    public ImageVO(){

    }

    //  getter and setter

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
}