package com.sunrin.notepad;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences note_pref;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor note_editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "NoteApp";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String PASSWORD = "Password";
    private static final String NOTE_PREF = "notes";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        note_pref = context.getSharedPreferences(NOTE_PREF,PRIVATE_MODE);
        editor = pref.edit();
        note_editor = note_pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setPassword(String password)
    {
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public String getPassword() {
        return pref.getString(PASSWORD,"");
    }

    public Note getNote(String Date)
    {
        String temp = note_pref.getString(Date,"");
        if(temp.equals(""))
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();
            return gson.fromJson(temp,Note.class);
        }
    }

    public HashMap<String, Note> getAllNotes()
    {
        Map<String, ?> temp = note_pref.getAll();
        HashMap<String, Note> result = new HashMap<>();


        for(Map.Entry<String, ?> item : temp.entrySet())
        {
            String date = item.getKey();
            result.put(date, getNote(date));
        }

        return result;
    }

    public void setNote(String date, Note note)
    {
        Gson gson = new Gson();
        note_editor.putString(date, gson.toJson(note)).commit();
    }

}