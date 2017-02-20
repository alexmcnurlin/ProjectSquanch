package com.example.alexmcnurlin.projectsquanch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the height and width of the screen in dp
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        // Just some variable declarations
        int nColumns = 4;
        int nRows = 4;

        GridLayout input = (GridLayout)findViewById(R.id.keyboard);

        // Let's create some views!
        input.setColumnCount(nColumns);
        input.setRowCount(nRows);
        char[] myString = [""];
        for (int i=0; i<(nColumns*nRows+1); i++) {
            TextView myView = new TextView(this);
            myString[0] = (char) i+48;
            myView.setText(Integer.toString(i), 0, 1);
        }

        setContentView(R.layout.activity_main);
    }
}
