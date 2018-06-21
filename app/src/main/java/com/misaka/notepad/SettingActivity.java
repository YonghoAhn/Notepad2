package com.misaka.notepad;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        EditText text = findViewById(R.id.editText);
        PrefManager prefManager = PrefManager.getInstance(getApplicationContext());

        findViewById(R.id.btnSavePass).setOnClickListener(v ->
        {
            prefManager.setPassword(text.getText().toString());
            Toast.makeText(this, "비밀번호가 설정되었습니다.", Toast.LENGTH_SHORT).show();
            if (getIntent().getIntExtra("first", -1) == 1) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
            {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
