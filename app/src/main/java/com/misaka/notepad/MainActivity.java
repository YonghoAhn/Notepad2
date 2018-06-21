package com.misaka.notepad;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list_items;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Note> notes = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        prefManager = PrefManager.getInstance(getApplicationContext());
        HashMap<String, Note> items = prefManager.getAllNotes();
        list_items = new ArrayList<>();
        for(Map.Entry<String, Note> item : items.entrySet()) {
            list_items.add(item.getValue().getTitle());
            dates.add(item.getKey());
            notes.add(item.getValue());
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list_items);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("Date",dates.get(position));
            intent.putExtra("Title",notes.get(position).getTitle());
            intent.putExtra("Content",notes.get(position).getContent());
            startActivityForResult(intent,200);
        });

        FloatingActionButton fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

    }

    private DatePickerDialog.OnDateSetListener listener =
            (view, year, monthOfYear, dayOfMonth) -> {
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("Date", "" + year + "|" + (monthOfYear + 1) + "|" + dayOfMonth);
                startActivityForResult(intent, 200);
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list_items.clear();
        dates.clear();
        notes.clear();
        HashMap<String, Note> items = prefManager.getAllNotes();
        for(Map.Entry<String, Note> item : items.entrySet()) {
            list_items.add(item.getValue().getTitle());
            dates.add(item.getKey());
            notes.add(item.getValue());
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting,menu);
        menu.getItem(0).setIcon(R.drawable.baseline_settings_white_48dp);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btnConfirm)
        {
            startActivity(new Intent(this, SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
