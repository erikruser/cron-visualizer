package com.eruser.cron.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import com.eruser.cron.model.CronModel;

public class CronDisplayPanel extends JPanel{
	
	CronModel cronObj;
	Integer width = 1000;
	Integer durationWidth = 0;
	
	public CronDisplayPanel(Date endDate){
		Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		
		this.setBorder(border);
		//this.setBackground(Color.LIGHT_GRAY);
		this.cronObj = new CronModel(endDate);
		this.setSize(new Dimension(1000, 60));
	}
	
	
	public void setViewDate(Date endDate){
		cronObj.setEndDate(endDate);
		this.repaint();
	}
	
	public void setCronExpr(String cronExpression){
		cronObj.setCronExpr(cronExpression);
		this.repaint();
	}

	public void setCronDuration(Long duration) {
		cronObj.setDuration(duration);
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.RED);
		
		Float durationPercent = cronObj.getDurationPercent();

		List<Float> points = cronObj.getCronExecPoints();
		if(points.size() > width){
			//optomization so it doesn't try to draw more lines than there are pixels
			g.fillRect(0, 0, width, 70);
		}else{
			for(Float point : points){
				int x = (int) ((float)width * point);
				int xDelta = (int) ((float)width * durationPercent);
				//int x2 = x + xDelta;
				g.setColor(new Color(0, 255, 255, 100));
				g.fillRect(x, 0, xDelta, 70);
				g.setColor(Color.RED);
				g.drawLine(x, 0, x, 70);
			}
		}
		
		
	}


	

}
