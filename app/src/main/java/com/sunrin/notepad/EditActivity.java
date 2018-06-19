package com.sunrin.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if(getIntent().getIntExtra("_id",-1) == -1)
        {

        }
        else
        {

        }
    }
}
