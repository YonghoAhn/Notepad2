package com.misaka.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    public static PrefManager instance = null;

    public static PrefManager getInstance(Context context) {
        return instance == null ? (instance = new PrefManager(context)) : instance;
    }

    // Shared preferences file name
    private static final String PREF_NAME = "pref";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String PASSWORD = "Password";


    public PrefManager(Context context) {
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setPassword(String password)
    {
        editor.putString(PASSWORD, password);
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
        String temp = pref.getString(Date,"");
        if(temp.equals(""))
        {
            return null;
        }
        else
        {
            Gson gson = new Gson();
            Log.d("MisakaMOE",temp);
            return gson.fromJson(temp,Note.class);
        }
    }

    public HashMap<String, Note> getAllNotes()
    {
        Map<String, ?> temp = pref.getAll();
        temp.remove(IS_FIRST_TIME_LAUNCH);
        temp.remove(PASSWORD);
        HashMap<String, Note> result = new HashMap<>();

        for(Map.Entry<String, ?> item : temp.entrySet())
        {
            Log.d("MisakaMOE", item.getKey());
            String date = item.getKey();
            result.put(date, getNote(date));
        }

        return result;
    }

    public void setNote(String date, Note note)
    {
        Gson gson = new Gson();
        editor.putString(date, gson.toJson(note));
        editor.commit();
    }

}