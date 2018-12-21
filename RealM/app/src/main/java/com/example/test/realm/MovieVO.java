package com.example.test.realm;

import io.realm.RealmObject;

/**
 * Created by okooo on 2018-12-21.
 */
//  MODEL
//  like DB의 table
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by student on 2018-12-21.
 */


public class MovieVO extends RealmObject {
    private int number;
    private String title;

    public RealmList<ActorVO> actorList= new RealmList<ActorVO>();


    public RealmList<ActorVO> getActorList() {
        return actorList;
    }

    public void setActorList(RealmList<ActorVO> actorList) {
        this.actorList = actorList;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}