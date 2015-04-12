package com.example.tt.notebook;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;

/**
 * Created by haha on 4/4/2015.
 */
 public final class ResizeDrawable {



    Context context;
    private String LOG_TAG;
    public ResizeDrawable(Context context) {
        LOG_TAG = this.getClass().getSimpleName();

        this.context = context;
    }



    public Drawable FitScreen (int imageID) {

        // ResizeImage and getResizedBitmap: http://stackoverflow.com/questions/13684947/resize-image-in-android-drawable
        // pokerface

        //Get device dimensions
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        double deviceWidth = display.getWidth();
        double deviceHeight = display.getHeight();
        BitmapDrawable bd=(BitmapDrawable) ((Activity)context).getResources().getDrawable(imageID);
        double imageHeight = bd.getBitmap().getHeight();
        double imageWidth = bd.getBitmap().getWidth();
        double ratio = deviceWidth / imageWidth;
        int newImageHeight = (int) (imageHeight * ratio);
        Bitmap bMap = BitmapFactory.decodeResource (((Activity)context).getResources(), imageID);

        //Error when rotate screen
       // Drawable drawable = new BitmapDrawable(((Activity)context).getResources(),getResizedBitmap(bMap,newImageHeight,(int) deviceWidth));

        Bitmap resizedBitmap =      getResizedBitmap(bMap,(int) deviceHeight,(int) deviceWidth);
        Drawable drawable = new BitmapDrawable(((Activity)context).getResources(),resizedBitmap);

        bMap.recycle(); //it is no longer need
        //dont recycle resizedBitmap


        return drawable;
    }

    /************************ Resize Bitmap *********************************/
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        // ResizeImage and getResizedBitmap: http://stackoverflow.com/questions/13684947/resize-image-in-android-drawable
        // pokerface
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = null;
        try {
            resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        }
        catch (OutOfMemoryError e)
        {
            Log.e(LOG_TAG, "Out Of Memory Error");
        }
        return resizedBitmap;
    }
}
