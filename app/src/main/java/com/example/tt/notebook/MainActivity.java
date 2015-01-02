package com.example.tt.notebook;

import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private NoteManager noteManager;
    private ListView list;
    private Button NewNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("conMeo", "OnCreate");
        list = (ListView) findViewById(R.id.listView);
        noteManager = new NoteManager(this);

        noteManager.ReadNoteName();
        noteManager.AddFile("testing3");

        updateList();
    }

    public void updateList()
    {
        ArrayList<String> p_arr ;
        noteManager.ReadNoteName();
        p_arr = noteManager.getP_arr();
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.item,R.id.itemTextView,p_arr);
        list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
