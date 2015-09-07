package com.example.tt.notebook.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tt.notebook.BackGroundSingleton;
import com.example.tt.notebook.ImproveNoteManager;
import com.example.tt.notebook.Navigator;
import com.example.tt.notebook.NoteManager;
import com.example.tt.notebook.R;
import com.example.tt.notebook.Tool.RealmTool;
import com.example.tt.notebook.model.Note;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private NoteManager noteManager;
    private ImproveNoteManager improveNoteManager;
    private ListView list;
    private Button NewNote;

    private Button exportButton;
    private Button importButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("conMeo", "OnCreate");

        //with LoadLayoutBackground , main thread is not lag any more
        //resize job is doing in LoadLayoutBackground extend AsyncTask


        list = (ListView) findViewById(R.id.listView);
        NewNote = (Button) findViewById(R.id.NewNoteButton);
        exportButton = (Button) findViewById(R.id.exportButton);
        importButton = (Button) findViewById(R.id.importButton);



        improveNoteManager = new ImproveNoteManager(this);
        //noteManager = new NoteManager(this);
        //noteManager.ReadNoteName();
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmTool.exportDatabase(MainActivity.this,improveNoteManager.getRealmConfiguration());
            }
        });
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmTool.importDatabase(MainActivity.this);

            }
        });
        updateList();
        NewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.naviNewNote(MainActivity.this);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, Drawer_edit_activity.class);

                Navigator.naviEdit(MainActivity.this,position);
            }
        });
    }

    private void UnloadBackground()
    {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.Main_activity_layout_id);
        linearLayout.setBackground(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UnloadBackground();

    }

    protected void onResume()
    {
        super.onResume();
        new LoadLayoutBackground().execute();
        updateList();
    }

    public void updateList()
    {
        ArrayList<String> p_arr ;
       // noteManager.ReadNoteName();
       // p_arr = noteManager.getP_arr();
        p_arr = improveNoteManager.getNameArray();
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

    private class LoadLayoutBackground extends AsyncTask<Void,Void,Drawable>
    {
        @Override
        protected Drawable doInBackground(Void... params) {
            BackGroundSingleton backgroundSingleton = BackGroundSingleton.getInstance(MainActivity.this);
            Drawable image = backgroundSingleton.getBackground();
            return image;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.Main_activity_layout_id);
            linearLayout.setBackground(drawable);
        }
    }

}
