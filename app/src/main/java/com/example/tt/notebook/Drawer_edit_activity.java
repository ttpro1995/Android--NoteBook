package com.example.tt.notebook;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Drawer_edit_activity extends ActionBarActivity {
    private NoteManager noteManager;
    private ListView list;
    private EditText NoteContent;
    private Button SaveButton;
    private Button DeleteButton;
    private String NOTE_NAME ;// no .txt
    //only name
    private String FILE_NAME;//FILENAME =NOTE_NAME+".txt"

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_edit_activity);

        list = (ListView) findViewById(R.id.DrawerListView);
        noteManager = new NoteManager(this);
        updateList();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

       drawerLayout.openDrawer(list);//open drawer
        //TODO: complete drawer
        //TODO: edit note
        

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                String note_name = noteManager.getP_arr().get(position);
                NOTE_NAME = note_name;
                Drawer_edit_activity.this.setTitle(NOTE_NAME);
            }
        });




    }

    public void updateList()
    {
        ArrayList<String> p_arr ;
        noteManager.ReadNoteName();
        p_arr = noteManager.getP_arr();
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.item,R.id.itemTextView,p_arr);
        list.setAdapter(adapter);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer_edit_activity, menu);
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
    */
}
