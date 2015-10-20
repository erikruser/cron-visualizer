package com.eruser.cron.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.eruser.cron.model.CronModel;

public class CronDisplayPanel extends JPanel{
	
	CronModel cronObj;
	Date endDate;
	Integer width = 600;
	
	public CronDisplayPanel(Date endDate){
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.cronObj = new CronModel("");
		this.endDate = endDate;
	}
	
	
	public void setViewDate(Date endDate){
		this.endDate = endDate;
		this.repaint();
	}
	
	public void setCronExpr(String cronExpression){
		cronObj = new CronModel(cronExpression);
		this.repaint();
	}

	
	public Dimension getPreferredSize() {
        return new Dimension(width,50);
    }
    
	
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		List<Float> points = cronObj.getCronExecPoints(endDate);
		
		g.setColor(Color.RED);
		
		for(Float point : points){
			int x = (int) ((float)width * point);
			g.drawLine(x, 0, x, 50);
		}
		
		
	}
	

}
