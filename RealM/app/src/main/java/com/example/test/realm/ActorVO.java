package com.example.test.realm;

import io.realm.RealmObject;

/**
 * Created by okooo on 2018-12-21.
 */

public class ActorVO extends RealmObject {

    String actor;

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
