package com.example.test.httpsession;

import android.graphics.Bitmap;

import io.realm.RealmObject;

/**
 * Created by okooo on 2018-12-21.
 */
//  MODEL
//  like DB의 table
//  Realm 상속받기
public class MovieVO extends RealmObject {
    private int number;
    private String title;
    private String actor;
    private String director;
    private String category;
    private String runningTime;
    private String openDate;
   //  비트맵 이미지는 따로 table 구성

    //  default constructor
    public MovieVO(){

    }
    //  constructor
    public MovieVO(int number, String title, String actor, String director, String category, String runningTime, String openDate) {
        this.number = number;
        this.title = title;
        this.actor = actor;
        this.director = director;
        this.category = category;
        this.runningTime = runningTime;
        this.openDate = openDate;

    }

    //  getter and setter
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

}