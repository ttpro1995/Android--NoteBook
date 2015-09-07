package com.example.tt.notebook;

import android.content.Context;
import android.content.Intent;

import com.example.tt.notebook.model.Note;
import com.example.tt.notebook.view.Add_Note_Activity;
import com.example.tt.notebook.view.DeleteConfirm;
import com.example.tt.notebook.view.Drawer_edit_activity;

import java.util.ArrayList;

/**
 * Created by Thien on 9/7/2015.
 */
public class Navigator {
    public static void naviNewNote(Context context){
        Intent intent = new Intent(context,Add_Note_Activity.class);
        context.startActivity(intent);
    }

    public static void naviEdit(Context context,int position){
        ImproveNoteManager improveNoteManager = new ImproveNoteManager(context);
        Intent intent = new Intent(context, Drawer_edit_activity.class);
        ArrayList<Note> notes = improveNoteManager.loadNote();
        String note_name = notes.get(position).getName();
        intent.putExtra(context.getResources().getString(R.string.extra_name),note_name);
        intent.putExtra(context.getResources().getString(R.string.extra_id),notes.get(position).getId());
        context.startActivity(intent);
    }

    public static void naviEditNewNote(Context context,int id){
        Intent intent = new Intent(context,Drawer_edit_activity.class );
        intent.putExtra(context.getResources().getString(R.string.extra_id),id);
        context.startActivity(intent);
    }

    public static void naviDelete(Context context, int id){
        Intent intent = new Intent(context,DeleteConfirm.class);
        intent.putExtra(context.getResources().getString(R.string.extra_id),id);
        context.startActivity(intent);
    }
}
