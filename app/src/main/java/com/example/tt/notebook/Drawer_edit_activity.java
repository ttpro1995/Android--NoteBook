package com.example.tt.notebook;

import android.app.Activity;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

        //reduce lag
        new LoadLayoutBackground().execute();

        noteManager = new NoteManager(this);
        //done: edit note
        NoteContent = (EditText) findViewById(R.id.NoteContentEditTextDrawer);
        SaveButton = (Button) findViewById(R.id.SaveNoteButtonDrawer);
        DeleteButton = (Button) findViewById(R.id.DeleteNoteButtonDrawer);
        Bundle data = getIntent().getExtras();
        NOTE_NAME = data.getString("name");
        this.setTitle(NOTE_NAME);
        FILE_NAME = NOTE_NAME + ".txt";
        ReadFile();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteFile();
                ReadFile();
               Toast toast = Toast.makeText(Drawer_edit_activity.this,"saved",Toast.LENGTH_SHORT);
            toast.show();

            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Drawer_edit_activity.this,DeleteConfirm.class);
                intent.putExtra("name",NOTE_NAME);
                startActivity(intent);
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
                String note_name = noteManager.getP_arr().get(position);
                NOTE_NAME = note_name;
                Drawer_edit_activity.this.setTitle(NOTE_NAME);
                FILE_NAME = NOTE_NAME+".txt";
                ReadFile();
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

    public void updateList()
    {
        ArrayList<String> p_arr ;
        noteManager.ReadNoteName();
        p_arr = noteManager.getP_arr();
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.item,R.id.itemTextView,p_arr);
        list.setAdapter(adapter);
    }

    public void ReadFile()
    {//TODO: use thread
        String tmp;
        String out;
        try {
            FileInputStream in = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            while ((tmp = bufferedReader.readLine())!=null)
            {
                builder.append(tmp);
                builder.append("\n");
            }
            out = builder.toString();
            NoteContent.setText(out);
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }

    }

    public void WriteFile()
    {//TODO: use thread
        String tmp;

        try{
            FileOutputStream out = openFileOutput(FILE_NAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(NoteContent.getText().toString());
            writer.close();
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){
            //do nothing
        }
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
            ResizeDrawable resizeTool = new ResizeDrawable(Drawer_edit_activity.this);
            Drawable image = resizeTool.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);
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
