package com.example.alexmcnurlin.projectsquanch;

import android.content.Context;
import android.content.res.Resources;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexmcnurlin on 2/21/17.
 */

public class ButtonLayout {
    private Context context;
    private String jString;
    private JSONObject jObject;
    private JSONArray jButtons;

    /*UserData(Context myContext) {
        context = myContext;
    }*/

    ButtonLayout(Context myContext, int layout) throws IOException {
        InputStream is = myContext.getResources().openRawResource(layout);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        this.jString = jsonString;
        try {
            this.jObject = new JSONObject(this.jString);
            this.jButtons = jObject.getJSONArray("buttons");
        } catch (Exception e) {
            this.jObject = null;
        }
    }

    public JSONObject getjObject() {
        return this.jObject;
    }

    public String getjString() {
        return this.jString;
    }

    public String getButtonText(int i) throws JSONException {
        String text = this.jButtons.getString(i);
        return text;
    }

    public int getNButtons() {
        return this.jButtons.length();
    }

    public int getNColumns() throws JSONException {
        return this.jObject.getInt("nColumns");
    }
}
