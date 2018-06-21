package com.misaka.notepad;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText txtTitle;
    EditText txtContent;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        date = intent.getExtras().getString("Date");
        if(intent.getExtras().getString("Content") == null) {}
        else
        {
            txtTitle.setText(intent.getExtras().getString("Title"));
            txtContent.setText(intent.getExtras().getString("Content"));
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.getItem(0).setIcon(R.drawable.baseline_note_add_white_48dp);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btnConfirm)
        {
            //Save Note
            PrefManager prefManager = new PrefManager(this);
            Note note = new Note();
            note.setTitle(txtTitle.getText().toString());
            note.setContent(txtContent.getText().toString());
            prefManager.setNote(date,note);
            setResult(Activity.RESULT_OK);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
