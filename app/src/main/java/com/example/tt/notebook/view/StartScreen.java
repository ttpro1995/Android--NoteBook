package com.example.tt.notebook.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tt.notebook.Navigator;
import com.example.tt.notebook.R;
import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartScreen extends AppCompatActivity {

   @Bind(R.id.button)  Button startBT;
   @Bind(R.id.textView) TextView quote_of_the_dayTextView;
   @Bind(R.id.imageView) ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        ButterKnife.bind(this);
        startBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.naviStartApp(StartScreen.this);
            }
        });
        getQuoteOfTheDay();
    }

    private void getQuoteOfTheDay(){
        ParseConfig.getInBackground(new ConfigCallback() {
            @Override
            public void done(ParseConfig config, ParseException e) {
                String quote_of_the_day = config.getString("quote_of_the_day");
                quote_of_the_dayTextView.setText(quote_of_the_day);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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
