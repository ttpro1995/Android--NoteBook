package com.example.tt.notebook;

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
import android.widget.TextView;


public class Add_Note_Activity extends ActionBarActivity {

    private EditText nameEdit;
    private Button addButton;
    private NoteManager noteManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new LoadLayoutBackground().execute();

        nameEdit = (EditText) findViewById(R.id.NoteNameEditText);
        addButton = (Button) findViewById(R.id.AddNoteButton);
        noteManager = new NoteManager(this);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_name = nameEdit.getText().toString();
                noteManager.AddFile(note_name);
                noteManager.SaveNoteName();
                Intent intent = new Intent(Add_Note_Activity.this,Drawer_edit_activity.class);
                intent.putExtra("name",note_name);
                startActivity(intent);
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
            ResizeDrawable resizeTool = new ResizeDrawable(Add_Note_Activity.this);
            Drawable image = resizeTool.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);
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
