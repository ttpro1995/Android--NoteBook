package com.example.tt.notebook.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tt.notebook.BackGroundSingleton;
import com.example.tt.notebook.ImproveNoteManager;
import com.example.tt.notebook.Navigator;
import com.example.tt.notebook.NoteManager;
import com.example.tt.notebook.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class Drawer_edit_activity extends ActionBarActivity {
   // private NoteManager noteManager;
    private ImproveNoteManager improveNoteManager;
    private ListView list;
    private EditText noteContent;
    private Button SaveButton;
    private Button DeleteButton;
    private String NOTE_NAME ;// no .txt
    private int NOTE_ID;


    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_edit_activity);

        //reduce lag
        new LoadLayoutBackground().execute();

        //noteManager = new NoteManager(this);
        improveNoteManager = new ImproveNoteManager(this);

        //done: edit note
        noteContent = (EditText) findViewById(R.id.NoteContentEditTextDrawer);
        SaveButton = (Button) findViewById(R.id.SaveNoteButtonDrawer);
        DeleteButton = (Button) findViewById(R.id.DeleteNoteButtonDrawer);
        Bundle data = getIntent().getExtras();

        NOTE_ID = data.getInt(getResources().getString(R.string.extra_id));
        NOTE_NAME = improveNoteManager.queryID(NOTE_ID).getName();
        this.setTitle(NOTE_NAME);
        readNote();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNote();
                readNote();
               Toast toast = Toast.makeText(Drawer_edit_activity.this,"saved",Toast.LENGTH_SHORT);
            toast.show();

            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.naviDelete(Drawer_edit_activity.this,NOTE_ID);
            }
        });



        //done complete drawer
        //done: move to right and add action bar button to open list
        list = (ListView) findViewById(R.id.DrawerListView);
        updateList();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

      // drawerLayout.openDrawer(list);//open drawer







        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                String note_name = improveNoteManager.getNameArray().get(position);
                NOTE_NAME = note_name;
                Drawer_edit_activity.this.setTitle(NOTE_NAME);

                readNote();
            }
        });




    }

    private void UnloadBackground()
    {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.Drawer_activity_layout_id);
        linearLayout.setBackground(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UnloadBackground();
    }


    protected void  onResume()
    {
        super.onResume();
        new LoadLayoutBackground().execute();
    }

    public void updateList()
    {
        ArrayList<String> p_arr ;
        //noteManager.ReadNoteName();
       // p_arr = noteManager.getP_arr();
        p_arr = improveNoteManager.getNameArray();
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.item,R.id.itemTextView,p_arr);
        list.setAdapter(adapter);
    }

    public void readNote()
    {//TODO: use thread
        String data;
        data = improveNoteManager.readNote(NOTE_ID);
        noteContent.setText(data);
    }

    public void writeNote()
    {//TODO: use thread
        String data = noteContent.getText().toString();
        improveNoteManager.editNote(NOTE_ID,data);
    }



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
        if (id == R.id.open_drawer) {
            if (!drawerLayout.isDrawerOpen(list))
            drawerLayout.openDrawer(list);
            else
           drawerLayout.closeDrawers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LoadLayoutBackground extends AsyncTask<Void,Void,Drawable>
    {
        @Override
        protected Drawable doInBackground(Void... params) {
            BackGroundSingleton backgroundSingleton = BackGroundSingleton.getInstance(Drawer_edit_activity.this);
            Drawable image = backgroundSingleton.getBackground();
            return image;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.Drawer_activity_layout_id);
            linearLayout.setBackground(drawable);
        }
    }

}
