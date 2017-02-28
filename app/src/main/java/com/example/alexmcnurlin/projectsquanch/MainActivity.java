package com.example.alexmcnurlin.projectsquanch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Just some variable declarations
        int nRows = 4;

        int match_parent = LinearLayout.LayoutParams.MATCH_PARENT;
        int wrap_content = LinearLayout.LayoutParams.WRAP_CONTENT;

        // Get the height and width of the screen in px
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpToPx = ((float)displayMetrics.densityDpi)/((float)DisplayMetrics.DENSITY_DEFAULT);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;
        int margin = (int)(dpToPx*getResources().getDimension(R.dimen.button_margin));

        // Add a statement for debugging purposes
        TextView test = (TextView)findViewById(R.id.test);

        try {
            // Read data from file
            ButtonLayout myLayout = new ButtonLayout(this, R.raw.default_layout);
            JSONObject myData = myLayout.getjObject();

            EqParser myEq = new EqParser("3+5*7");
            String words = myEq.printEquation();

            // #debugging
            //String words = myData.getString("name");
            test.setText(" " + words + " ");

            // Get the array of text for the buttons
            //JSONArray buttonText = myData.getJSONArray("buttons");
            int nColumns = myLayout.getNColumns();
            int nButtons = myLayout.getNButtons();

            // Get and modify the GridLayout
            GridLayout input = (GridLayout)findViewById(R.id.keyboard);
            input.removeAllViews();

            // Let's create some views!
            input.setColumnCount(nColumns);
            input.setRowCount(nRows);

            for (int i=0; i<nButtons; i++) {
                try {
                    CustomButton button = new CustomButton(this);

                    // Set up the main text within the button
                    TextView mainText = new TextView(this);
                    mainText.setText(myLayout.getButtonText(i));
                    button.setMainButtonText(myLayout.getButtonText(i));
                    mainText.setTextColor(getResources().getColor(R.color.colorAltText));
                    RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(wrap_content, wrap_content);
                    mainParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    mainText.setLayoutParams(mainParams);
                    int mainId = View.generateViewId();
                    mainText.setId(mainId);

                    // Setup the alt text within the button
                    TextView altText = new TextView(this);
                    altText.setText("sin^-1");
                    altText.setGravity(Gravity.RIGHT);
                    altText.setTextSize((float) 10);
                    altText.setTextColor(getResources().getColor(R.color.colorAltText));
                    altText.setPadding(0, 0, 10, 10);
                    RelativeLayout.LayoutParams altParams = new RelativeLayout.LayoutParams(match_parent, wrap_content);
                    altParams.addRule(RelativeLayout.BELOW, mainId);
                    altText.setLayoutParams(altParams);

                    // Add main text and alt text to the buttons, as well as configure the button
                    button.addView(mainText);
                    button.addView(altText);
                    button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(match_parent, wrap_content);
                    params.height = (int) getResources().getDimension(R.dimen.button_height);
                    params.width = (int) width / nColumns - 2 * margin;
                    params.setMargins(margin, margin, margin, margin);
                    button.setLayoutParams(params);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomButton v_0 = (CustomButton)v;
                            String text = v_0.getMainButtonText();
                            TextView test = (TextView)findViewById(R.id.test);
                            test.setText(text);
                        }
                    });
                    input.addView(button);
                } catch (JSONException e) {
                    test.setText("Error in button loop");
                }
            }
        } catch (IOException e) {
            ;
        } catch (JSONException e) {
            test.setText("oops");
        }
    }
}
