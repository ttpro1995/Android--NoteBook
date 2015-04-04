package com.example.tt.notebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

        //with LoadLayoutBackground , main thread is not lag any more
        //resize job is doing in LoadLayoutBackground extend AsyncTask
       new LoadLayoutBackground().execute();

        list = (ListView) findViewById(R.id.listView);
        NewNote = (Button) findViewById(R.id.NewNoteButton);
        noteManager = new NoteManager(this);
        noteManager.ReadNoteName();
        updateList();
        NewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add_Note_Activity.class);
                startActivity(intent);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //old code
               // Intent intent = new Intent(MainActivity.this, Edit_Note_Activity.class);

                //debug code
                Intent intent = new Intent(MainActivity.this, Drawer_edit_activity.class);
                String note_name = noteManager.getP_arr().get(position);
                intent.putExtra("name",note_name);
                startActivity(intent);
            }
        });
    }


    protected void onResume()
    {
        super.onResume();
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

    private class LoadLayoutBackground extends AsyncTask<Void,Void,Drawable>
    {
        @Override
        protected Drawable doInBackground(Void... params) {
            ResizeDrawable resizeTool = new ResizeDrawable(MainActivity.this);
            Drawable image = resizeTool.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);
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
