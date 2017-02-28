package com.example.alexmcnurlin.projectsquanch;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by alexmcnurlin on 2/23/17.
 */

public class CustomButton extends RelativeLayout {
    String mainButtonText;
    String altButtonText;

    CustomButton(Context context) {
        super(context);
    }

    public String getAltButtonText() {
        return altButtonText;
    }

    public void setAltButtonText(String altButtonText) {
        this.altButtonText = altButtonText;
    }

    public String getMainButtonText() {
        return mainButtonText;
    }

    public void setMainButtonText(String mainButtonText) {
        this.mainButtonText = mainButtonText;
    }
}
