package com.example.tt.notebook;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by haha on 4/12/2015.
 *
 * Contain background drawable after resize
 */
public class BackGroundSingleton {

   static Context context;
    Drawable background;

    private static BackGroundSingleton ourInstance = null; // must be init null here
    //if we call constructor here, it will pass null context into ResizeDrawable class

    public static BackGroundSingleton getInstance(Context the_context){
        context = the_context;// contest must be set before call private constructor

        if (ourInstance==null)
        {
            //call constructor
            ourInstance = new BackGroundSingleton();
        }
        return ourInstance;
    }

    private BackGroundSingleton() {

        ResizeDrawable resizeDrawable = new ResizeDrawable(context);
        background = resizeDrawable.FitScreen(R.drawable.old_paper_texture_by_caminopalmero_720x1080);
    }

    public Drawable getBackground()
    {
        return background;
    }

}
