package com.example.test.image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextTest extends AppCompatActivity {
    EditText input;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_test);

        input = (EditText)findViewById(R.id.input);
        tv = (TextView)findViewById(R.id.tv);

        //  텍스트왓쳐 연결
        input.addTextChangedListener(new MyTExtWatcher());
    }

    //  TextWatcher Override
    class MyTExtWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            tv.setText(tv.getText().toString() + "\n ----------------" +"\n수정되기 전 : " + charSequence.toString());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            tv.setText(tv.getText().toString() + "\n수정되는 중 : " + charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            tv.setText(tv.getText().toString() + "\n수정 후 : " + editable.toString());
        }
    }
}
