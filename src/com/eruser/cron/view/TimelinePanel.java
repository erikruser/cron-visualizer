package com.eruser.cron.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimelinePanel extends JPanel{
	
	Date endDate;
	JLabel endDateLabel;
	
	public TimelinePanel(){
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		endDateLabel = new JLabel();
		this.add(endDateLabel);
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(600,50);
    }
	
	
	public void setViewDate(Date endDate){
		this.endDate = endDate;
		
		endDateLabel.setText(endDate.toString());
		
	}
	
}
