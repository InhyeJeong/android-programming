package com.example.test.image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SmsTextView extends AppCompatActivity {
    EditText input;
    TextView tv;
    Button btnOk;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_text_view);
        setTitle("SMS 입력화면");

        input = (EditText)findViewById(R.id.input);
        tv = (TextView)findViewById(R.id.tv);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnClose = (Button)findViewById(R.id.btnClose);

        //  텍스트왓쳐 연결
        input.addTextChangedListener(new MyTExtWatcher());
    }

    void btn(View v) {
        Button btn = (Button) v;
        String name = btn.getText().toString(); // 적힌 이름을 스트링으로 받음
        if (name.equals("전송")) {
            Toast.makeText(this, "전송되었습니다.", Toast.LENGTH_SHORT).show();

        } else if(name.equals("닫기")){
            Toast.makeText(this, "닫습니다.", Toast.LENGTH_SHORT).show();

        }

    }



    //  TextWatcher Override
    class MyTExtWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
 //           tv.setText(input.getText().toString().length() + "/ 80 byte");
            tv.setText(editable.length() + "/ 80 byte");
        }
    }
}
