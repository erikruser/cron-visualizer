package com.eruser.cron;

import javax.swing.*;
import java.awt.*;

/**
 * Created by erikruser on 4/17/17.
 */
public class UIPanel extends JPanel {

    private Boolean debug = false;

    public UIPanel(){

        if(debug) {

            float r = (float) Math.random();
            float g = (float) Math.random();
            float b = (float) Math.random();

            Color randColor = new Color(r, g, b);

            this.setBackground(randColor);

        }
    }

}
