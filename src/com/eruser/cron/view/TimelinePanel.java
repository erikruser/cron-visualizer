package com.eruser.cron.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.javatuples.Triplet;

public class TimelinePanel extends JPanel{
	
	private final Long MILLIS_IN_DAY = 86400000l;
	private final Long MILLIS_IN_HOUR = 3600000l;
	private final Long MILLIS_IN_MINUTE = 60000l;
	Date endDate;
	
	private SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
	private SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss a");
	
	private int selectedDateField = Calendar.HOUR;
	
	public TimelinePanel(){
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.setSize(new Dimension(1000,30));
	}
	
	public void setViewDate(Date endDate){
		this.endDate = endDate;
		this.repaint();
	}
	
	public void setDateField(int dateField){
		this.repaint();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setFont(g.getFont().deriveFont(10f));
		
		Float tickPercent;
		Integer tickHeight;
		String tickLabel;
		
		ArrayList<Triplet<Float, Integer, String>> tickPoints = getTickPoints();
		for(Triplet<Float, Integer, String> point : tickPoints){
			tickPercent = point.getValue0();
			tickHeight = point.getValue1();
			tickLabel = point.getValue2();
			int x = (int) (1000f * tickPercent);
			g.drawLine(x, (30 - tickHeight), x, 30);
			g.drawChars(tickLabel.toCharArray(), 0, tickLabel.length(), x, 12);
		}
		
	}

	private ArrayList<Triplet<Float, Integer, String>> getTickPoints() {
		
		Date now = new Date();
		Long nowMillis = now.getTime();
		Long endMillis = endDate.getTime();
		Long duration = endMillis - nowMillis;		
		
		ArrayList<Triplet<Float, Integer, String>> tickPoints = new ArrayList<Triplet<Float, Integer, String>>();
		
		Long tickMillis;
		Long tickDuration;
		Float tickPercent;
		Integer tickHeight;
		String tickLabel;
		
		
		if(duration > (MILLIS_IN_DAY * 5)){
			selectedDateField = Calendar.DATE;
		}else if(duration > (MILLIS_IN_HOUR * 5)){
			selectedDateField = Calendar.HOUR;
		}else if(duration > (MILLIS_IN_MINUTE * 4)){
			selectedDateField = Calendar.MINUTE;
		}else{
			selectedDateField = Calendar.SECOND;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		if(this.selectedDateField == Calendar.MINUTE){
			cal.set(Calendar.SECOND, 0);
		}else if(this.selectedDateField == Calendar.HOUR){
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
		}else if(this.selectedDateField == Calendar.DATE){
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
		}
		cal.add(this.selectedDateField, 1);
		
		while(cal.getTime().before(endDate)){
			
			
			tickMillis = cal.getTime().getTime();
			tickDuration = tickMillis - nowMillis;
			tickPercent = tickDuration.floatValue() / duration.floatValue();
			
			tickHeight = 5;
			tickLabel = "";
			boolean majorTick = false;
			boolean useDateLabel = false;
			
			if(selectedDateField == Calendar.SECOND || selectedDateField == Calendar.MINUTE){
				if(cal.get(selectedDateField) == 0 || cal.get(selectedDateField) == 15 || cal.get(selectedDateField) == 30 || cal.get(selectedDateField) == 45){
					majorTick = true;
				}
			}else if(selectedDateField == Calendar.DATE){
				if(cal.get(Calendar.HOUR_OF_DAY) == 0){
					majorTick = true;
					useDateLabel = true;
				}
			}else{
				if(cal.get(selectedDateField) == 0){
					majorTick = true;
				}
			}
			
			if(majorTick){
				tickHeight = 10;
				if(useDateLabel){
					tickLabel = dayFormat.format(cal.getTime());
				}else{
					tickLabel = hourFormat.format(cal.getTime());
				}
			}
			
			Triplet<Float, Integer, String> tickPoint = Triplet.with(tickPercent, tickHeight, tickLabel);
			
			tickPoints.add(tickPoint);
			
			cal.add(this.selectedDateField, 1);
			
			if(tickPoints.size() > 1000){
				break;
			}
			
		}
		
		return tickPoints;
	}
	
	
}
