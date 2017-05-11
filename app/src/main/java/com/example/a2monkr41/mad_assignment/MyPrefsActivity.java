package com.example.a2monkr41.mad_assignment;


import android.preference.PreferenceActivity;
import android.os.Bundle;

public class MyPrefsActivity extends PreferenceActivity{

    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
