package com.example.tt.notebook.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.tt.notebook.R;
import com.example.tt.notebook.ResizeDrawable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


// with no drawer
//This activity is now USELESS
//Which is replace by Drawer_edit_activity

public class Edit_Note_Activity extends ActionBarActivity {

    //variable/////////

    private EditText NoteContent;
    private Button SaveButton;
    private Button DeleteButton;
    private String NOTE_NAME ;// no .txt
                               //only name
    private String FILE_NAME;//FILENAME =NOTE_NAME+".txt"

    //////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__note_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        NoteContent = (EditText) findViewById(R.id.NoteContentEditText);
        DeleteButton = (Button) findViewById(R.id.DeleteNoteButton);
        SaveButton = (Button) findViewById(R.id.SaveNoteButton);
        Bundle data = getIntent().getExtras();
        NOTE_NAME = data.get("name").toString();

        this.setTitle(NOTE_NAME);
        FILE_NAME= NOTE_NAME+".txt";
        ReadFile();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteFile();//save a note
                ReadFile();//update it on screen
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Note_Activity.this,DeleteConfirm.class);
                intent.putExtra("name",NOTE_NAME);
                startActivity(intent);
            }
        });

    }

    protected void onResume()
    {
        super.onResume();
        new LoadLayoutBackground().execute();
        ReadFile();
    }


    private void UnloadBackground()
    {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.EditNote_activity_layout_id);
        linearLayout.setBackground(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UnloadBackground();
    }





    public void ReadFile()
    {

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
    {

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
        getMenuInflater().inflate(R.menu.menu_edit__note_, menu);
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
            ResizeDrawable resizeTool = new ResizeDrawable(Edit_Note_Activity.this);
            Drawable image = resizeTool.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);
            return image;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.EditNote_activity_layout_id);
            linearLayout.setBackground(drawable);
        }
    }
}
