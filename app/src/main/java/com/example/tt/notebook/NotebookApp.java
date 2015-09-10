package com.example.tt.notebook;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Thien on 9/9/2015.
 */
public class NotebookApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "iJdwYLFlFVwv5zyovfDI5IO8W9YTZrLnOnxSMeij", "AUvDadR4mYOrm558aGGOw2F8rg1kqxrPE4YEBe3f");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Log.i("Application","init parse");
    }
}
