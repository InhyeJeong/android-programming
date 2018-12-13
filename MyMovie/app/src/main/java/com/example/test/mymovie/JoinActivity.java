package com.example.test.mymovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    EditText ed_id;
    EditText ed_pw;
    EditText ed_pw2;

    Button btn_join;
    TextView tv_pw;

    // Join us 클릭시 조건 만족 확인하는 전역변수 설정
    boolean possible_id;
    boolean possible_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //  비밀번호 첫째칸을 입력해야 두번째 확인 입력칸이 활성화됨
        ed_pw2.setEnabled(false);

        //  초기화
        boolean possible_id=false;
        boolean possible_pw=false;

        //  객체 찾기
        ed_id = (EditText)findViewById(R.id.ed_id);
        ed_pw = (EditText)findViewById(R.id.ed_pw);
        ed_pw2 = (EditText)findViewById(R.id.ed_pw2);

        btn_join = (Button)findViewById(R.id.btn_join);
        tv_pw = (TextView)findViewById(R.id.tv_pw);

        //  텍스트왓쳐 연결
        ed_id.addTextChangedListener(new IdWatcher());
        ed_pw.addTextChangedListener(new PwWatcher());
        ed_pw2.addTextChangedListener(new pw2Watcher());

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }




    //  join us 클릭시 토스트 출력
    void joinCheck(View v) {
        Button btn = (Button) v;
        String name = btn.getText().toString(); // 적힌 이름을 스트링으로 받음
        if(name.equals("JOIN US")) {
            if(possible_id==true && possible_pw==true) {
                // 조건 모두 만족하면 "반갑습니다."토스트 출력
                Toast.makeText(JoinActivity.this, "반갑습니다 :).", Toast.LENGTH_SHORT).show();
            } else {
                // 조건 모두 만족하지 않으면 "정보 확인해주세요"
                Toast.makeText(JoinActivity.this, "정보를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //  id watcher
    class IdWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {

            if(editable.length() >= 5 && editable.length() <=12){
                //  만족
                tv_pw.setText("정상적인 아이디입니다.");
                possible_id = true;
            } else {
                //  불만족
                tv_pw.setText("아이디는 5이상 12글자이하입니다.");
                possible_id = false;
            }

        }
    }



    //  PW watcher
    class PwWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            if(ed_pw.getText().toString().length() >=4 && ed_pw.getText().toString().length() <=12) {
                //  비번이 4~12글자 사이일 때
                tv_pw.setText("사용가능한 비밀번호 입니다.");
                ed_pw2.setEnabled(true);
            } else {
                //  아닐때
                tv_pw.setText("비밀번호는 4~12글자여야 합니다.");
            }



        }
    }



    //  PW2 watcher
    class pw2Watcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            // 비밀번호 일치시
            if(ed_pw.getText().toString().equals(ed_pw2.getText().toString()) ){
                tv_pw.setText("비밀번호가 일치합니다.");
                possible_pw = true;
            } else {
                //  비밀번호 불 일치시
                tv_pw.setText("비밀번호가 일치하지 않습니다.");
            }
        }
    }
}
