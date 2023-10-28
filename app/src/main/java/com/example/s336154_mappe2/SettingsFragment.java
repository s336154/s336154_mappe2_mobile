package com.example.s336154_mappe2;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;



public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }

}