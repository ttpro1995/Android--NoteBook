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

import com.example.tt.notebook.BackGroundSingleton;
import com.example.tt.notebook.ImproveNoteManager;
import com.example.tt.notebook.Navigator;
import com.example.tt.notebook.NoteManager;
import com.example.tt.notebook.R;


public class Add_Note_Activity extends ActionBarActivity {

    private EditText nameEdit;
    private Button addButton;
    //private NoteManager noteManager;
    private ImproveNoteManager improveNoteManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nameEdit = (EditText) findViewById(R.id.NoteNameEditText);
        addButton = (Button) findViewById(R.id.AddNoteButton);
        //noteManager = new NoteManager(this);
        improveNoteManager = new ImproveNoteManager(this);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_name = nameEdit.getText().toString();
                //noteManager.AddFile(note_name);
                //noteManager.SaveNoteName();
               int id = improveNoteManager.createNewNote(note_name);
                Navigator.naviEditNewNote(Add_Note_Activity.this,id);
            }
        });
    }

    private void UnloadBackground()
    {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.AddNote_activity_layout_id);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__note_, menu);

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
           // ResizeDrawable resizeTool = new ResizeDrawable(Add_Note_Activity.this);
          //  Drawable image = resizeTool.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);

            BackGroundSingleton backgroundSingleton = BackGroundSingleton.getInstance(Add_Note_Activity.this);
            Drawable image = backgroundSingleton.getBackground();

            return image;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.AddNote_activity_layout_id);
            linearLayout.setBackground(drawable);
        }
    }
}
