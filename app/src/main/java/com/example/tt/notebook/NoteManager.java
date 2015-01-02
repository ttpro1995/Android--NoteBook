package com.example.tt.notebook;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by TT on 1/2/2015.
 * This object will manage the list of note.
 */

public class NoteManager {


    private  String ALL_NOTE_NAME ="Con_meo_Note_name-12345.txt";//a file that will contain name of all note
    private ArrayList<String> p_arr;
    private Context context;

    public NoteManager(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        p_arr = new ArrayList<String>();
    }

    public void ReadNoteName(){
        // read notename from file --> array
        try {
            p_arr.clear();
            FileInputStream in = context.openFileInput(ALL_NOTE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String tmp;//save single file name
            while ((tmp = bufferedReader.readLine())!=null){
                p_arr.add(tmp);
                Log.i("conMeo", "Read");
            }

        }
        catch (FileNotFoundException e){

            Log.i("conMeo", "read filenotfound");
        }
        catch (IOException e){
            //i dont know what to do
            Log.i("conMeo", "read io");
        }
    }

    public void SaveNoteName(){
        //write note name into file
        try{
            FileOutputStream out = context.openFileOutput(ALL_NOTE_NAME,0);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
           for (int i=0;i<p_arr.size();i++)
           {
               outputStreamWriter.write(p_arr.get(i));
               outputStreamWriter.write("\n");//new line

               Log.i("conMeo", "Save");
           }
            outputStreamWriter.close();

        }
        catch (FileNotFoundException e){

            Log.i("conMeo", "save filenotfound");
        }
        catch (IOException e){
            //what ?
            Log.i("conMeo", "save io");
        }
    }

    public ArrayList<String> getP_arr() {
        return p_arr;
    }

    public boolean AddFile(String NOTE_NAME)
    {
        //check dublicate here
try {
    Throwable a = new Throwable();
    if (NOTE_NAME.equals(ALL_NOTE_NAME)) throw a;
    for (int i = 0; i < p_arr.size(); i++) {
        if (NOTE_NAME.equals(p_arr.get(i)))
            throw  a;
    }
}
catch (Throwable a)
{
    Log.i("conMeo","Dublicate");
    return false;
}


        // NOTE_NAME shouldn't have .txt or whatever
        p_arr.add(NOTE_NAME);
        SaveNoteName();
        return true;
    }
}
