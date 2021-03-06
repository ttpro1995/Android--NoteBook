package com.example.tt.notebook;

import android.content.Context;
import android.util.Log;

import com.example.tt.notebook.model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Thien on 8/31/2015.
 */
public class ImproveNoteManager {
    private Realm realm;
    private Context context;
    private String TAG = ImproveNoteManager.class.getSimpleName();
    private RealmConfiguration realmConfiguration;

    public ImproveNoteManager(Context context) {
        this.context = context;
        realmConfiguration = new RealmConfiguration.Builder(context).build();
        realm = Realm.getInstance(realmConfiguration);
    }


    public RealmConfiguration getRealmConfiguration() {
        return realmConfiguration;
    }

    public ArrayList<Note> loadNote(){
        ArrayList<Note> notes = new ArrayList<>();
        List<Note> noteList = realm.where(Note.class).findAll();
        for (int i = 0;i<noteList.size();i++){
            notes.add(noteList.get(i));
        }
        return notes;
    }

    public ArrayList<String> getNameArray(){
        ArrayList<String> noteName = new ArrayList<>();
        List<Note> noteList = realm.where(Note.class).findAll();
        for (int i = 0;i<noteList.size();i++){
            noteName.add(noteList.get(i).getName());
        }
        return noteName;
    }

    public int createNewNote(String note_name){
        int id = 1;
        RealmResults<Note> realmResults = realm.where(Note.class).findAll();
        if (!realmResults.isEmpty()) {
            realmResults.sort("id");
            id = realmResults.get(realmResults.size()-1).getId()+1;
        }
        Note note = new Note();
        note.setId(id);
        note.setName(note_name);
        note.setDate(new Date());

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        realm.beginTransaction();
        realm.copyToRealm(note);
        Log.i(TAG,"id = "+note.getId());
        Log.i(TAG,"name = " +note.getName());
        Log.i(TAG,"date = "+ df.format(note.getDate()));
        realm.commitTransaction();
        return id;
    }

    public void editNote(String note_name,String data){
        realm.beginTransaction();
        Note note = realm.where(Note.class).equalTo("name",note_name).findFirst();
        note.setData(data);
        realm.commitTransaction();
    }

    public void editNote(int id,String data){
        realm.beginTransaction();
        Note note = realm.where(Note.class).equalTo("id",id).findFirst();
        note.setData(data);
        realm.commitTransaction();
    }

    public String readNote(String note_name){
        String data= null;
        Note note = realm.where(Note.class).equalTo("name",note_name).findFirst();
        if (note!=null)
            data = note.getData();
        return  data;
    }

    public String readNote(int id){
        String data= null;
        Note note = realm.where(Note.class).equalTo("id",id).findFirst();
        if (note!=null)
            data = note.getData();
        return  data;
    }

    public void removeNote(String note_name){
        //TODO:
        RealmResults<Note> results = realm.where(Note.class).equalTo("name",note_name).findAll();
        realm.beginTransaction();
        results.removeLast();
        realm.commitTransaction();
    }

    public void removeNote(int id){

        RealmResults<Note> results = realm.where(Note.class).equalTo("id",id).findAll();
        realm.beginTransaction();
        results.removeLast();
        realm.commitTransaction();
    }

    public void close(){
        realm.close();
    }

    public Note queryID(int id){
        Note mNote = null;
        mNote = realm.where(Note.class).equalTo("id",id).findFirst();
        return mNote;
    }
}

