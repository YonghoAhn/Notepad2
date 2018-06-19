package com.sunrin.notepad;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PrefManager prefManager = new PrefManager(getApplicationContext());
        if(prefManager.isFirstTimeLaunch())
        {
            startActivityForResult(new Intent(this,SettingActivity.class),200);
        }

        findViewById(R.id.btnCheckPass).setOnClickListener(v -> {
            EditText text = (findViewById(R.id.txtpassword));
            if(prefManager.getPassword().toString().equals(text.getText().toString()))
            {
                //Password confirmed
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(this,"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                text.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
        {
            Toast.makeText(this,"비밀번호를 설정해야 이용하실 수 있습니다",Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(this,SettingActivity.class),200);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
