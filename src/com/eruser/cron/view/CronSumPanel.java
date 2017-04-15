package com.eruser.cron.view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class CronSumPanel extends JPanel {

	public CronSumPanel(){
		Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		this.setBorder(border);
		this.setSize(new Dimension(1000, 60));
	}
	
}
