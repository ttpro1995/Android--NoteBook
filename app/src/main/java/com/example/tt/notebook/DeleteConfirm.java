package com.example.tt.notebook;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class DeleteConfirm extends ActionBarActivity {
    private String LOG_TAG;
    private TextView noteNameTextView;
    private Button Yes;
    private Button No;
    private NoteManager noteManager;
    String NOTE_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_confirm);
        LOG_TAG=DeleteConfirm.class.getSimpleName();
        new LoadLayoutBackground().execute();

        noteNameTextView = (TextView) findViewById(R.id.deleteNoteNameTextView);
        Yes=    (Button) findViewById(R.id.YesDeleteButton);
        No = (Button)findViewById(R.id.NoDeleteButton);
        noteManager = new NoteManager(this);
        Bundle data;
        data = getIntent().getExtras();
       NOTE_NAME = data.get("name").toString();
        this.setTitle(NOTE_NAME);
        noteNameTextView.setText(NOTE_NAME);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteManager.RemoveNote(NOTE_NAME);
                NavUtils.navigateUpFromSameTask(DeleteConfirm.this);
            }
        });

        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// back to main menu
                NavUtils.navigateUpFromSameTask(DeleteConfirm.this);
            }
        });

    }

    private void UnloadBackground()
    {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.delete_confirm_activity_layout_id);
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
        getMenuInflater().inflate(R.menu.menu_delete_confirm, menu);
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
            ResizeDrawable resizeTool = new ResizeDrawable(DeleteConfirm.this);
            Drawable image=null;

                image = resizeTool.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);


            return image;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.delete_confirm_activity_layout_id);
            linearLayout.setBackground(drawable);

        }
    }
}
