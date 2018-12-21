package com.example.test.realm;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText et_title, et_number;
    Button btn_add, btn_select, btn_update, btn_del, btn_delAll;
    TextView tv_select;

    // db 변수생성
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_title = (EditText) findViewById(R.id.et_title);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_delAll = (Button) findViewById(R.id.btn_delAll);
        tv_select = (TextView) findViewById(R.id.tv_select);

        //  변수 초기화
        Realm.init(MainActivity.this);
        mRealm = Realm.getDefaultInstance();    //  like singletone

        MyBtnListener btnListener = new MyBtnListener();

        btn_add.setOnClickListener(btnListener);
        btn_select.setOnClickListener(btnListener);
        btn_update.setOnClickListener(btnListener);
        btn_del.setOnClickListener(btnListener);
        btn_delAll.setOnClickListener(btnListener);
    }

    class MyBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final String title = et_title.getText().toString();
            final String number = et_number.getText().toString();

            //  C R U D
            //  insert
            switch (view.getId()) {
                case R.id.btn_add:
                    if (!title.equals("") && !number.equals("")) {
                        //  트랜잭션 data추가할때 반드시 필요
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                //  객체 생성
                                //  new X, realm에서 생성해서 주소를 알려주고 만듦
                                MovieVO movieVO = realm.createObject(MovieVO.class);
                                //  realm에 있는 data가 수정됨
                                movieVO.setTitle(title);
                                movieVO.setNumber(Integer.parseInt(number));


                                ActorVO actorVO = new ActorVO();
                                actorVO.setActor("hihi");
                                movieVO.getActorList().add(actorVO);
                            }
                        });
                    }
                    break;


                //  select
                case R.id.btn_select:
                    //  where (클래스넣어주고).findall() 하면 다 가져옴
                    RealmResults<MovieVO> results = mRealm.where(MovieVO.class).findAll();
                    String str = "";

                    if (results.size() > 0) {
                        for (int i = 0; i < results.size(); i++) {
                            str += ("number : " + results.get(i).getNumber() + " title : "
                                    + results.get(i).getTitle() + "\n");
                        }
                    } else {
                        str += "no data";
                    }
                    tv_select.setText(str);
                    break;


                //  upddate
                case R.id.btn_update:
                    if (!title.equals("") && !number.equals("")) {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                MovieVO target = mRealm.where(MovieVO.class)
                                        .equalTo("number", Integer.valueOf(number)).findFirst();
                                target.setTitle(title);
                            }
                        });
                    }
                    break;


                //  target찾아서 delete
                case R.id.btn_del:
                    if (!number.equals("")) {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                MovieVO target = mRealm.where(MovieVO.class)
                                        .equalTo("number", Integer.valueOf(number)).findFirst();
                                target.deleteFromRealm();
                            }
                        });
                    }
                    break;


                //  delete All
                case R.id.btn_delAll:
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            mRealm.delete(MovieVO.class);
                        }
                    });
                    break;
            }
        }
    }
}