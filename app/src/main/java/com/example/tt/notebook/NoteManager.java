package com.example.tt.notebook;

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

    public String ALL_NOTE_NAME ="haha_ttpro_note_name_001927003.txt";//a file that will contain name of all note
    public ArrayList<String> p_arr;


    public void init(){
        p_arr = new ArrayList<String>();
    }

    public void ReadNoteName(){
        // read notename from file --> array
        try {
            FileInputStream in = new FileInputStream(ALL_NOTE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String tmp;//save single file name
            while ((tmp = bufferedReader.readLine())!=null){
                p_arr.add(tmp);
            }
        }
        catch (FileNotFoundException e){
            File f = new File(ALL_NOTE_NAME);
        }
        catch (IOException e){
            //i dont know what to do
        }
    }

    public void SaveNoteName(){
        //write note name into file
        try{
            FileOutputStream out = new FileOutputStream(ALL_NOTE_NAME);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
           for (int i=0;i<p_arr.size();i++)
           {
               outputStreamWriter.write(p_arr.get(i));
               outputStreamWriter.write("\n");//new line
           }
        }
        catch (FileNotFoundException e){
            File f = new File(ALL_NOTE_NAME);
        }
        catch (IOException e){
            //what ?
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
            if (NOTE_NAME == ALL_NOTE_NAME) throw  a;
            for (int i = 0; i < p_arr.size(); i++) {
                if (NOTE_NAME == p_arr.get(i))
                    throw a ;
            }
        }
        catch (Throwable a){
            return false;
        }

        // NOTE_NAME shouldn't have .txt or whatever
        p_arr.add(NOTE_NAME);
        SaveNoteName();
        return true;
    }
}
