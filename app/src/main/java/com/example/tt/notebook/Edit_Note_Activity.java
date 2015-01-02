package com.example.tt.notebook;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class Edit_Note_Activity extends ActionBarActivity {

    //variable/////////
    private EditText NoteContent;
    private Button SaveButton;
    private String NOTE_NAME ;// no .txt
                               //only name


    //////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__note_);
        NoteContent = (EditText) findViewById(R.id.NoteContentEditText);
        SaveButton = (Button) findViewById(R.id.SaveNoteButton);
        Bundle data = getIntent().getExtras();
        NOTE_NAME = data.get("name").toString();
        

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
}
