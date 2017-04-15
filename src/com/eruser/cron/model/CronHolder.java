package com.eruser.cron.model;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import com.eruser.cron.view.TimelinePanel;
import com.eruser.cron.view.CronDefinitionPanel;
import com.eruser.cron.view.CronDisplayPanel;
import com.eruser.cron.view.CronSumPanel;

public class CronHolder {
	
	private TimelinePanel timelinePanel;
	private List<CronDisplayPanel> cronDisplays;
	private CronSumPanel sumDisplay;
	private HashMap<Integer, JPanel> defAndDispPanels = new HashMap<Integer, JPanel>();
	private JPanel cronViewPanel;
	
	private Date endDate;
	
	private GridBagConstraints defConstraint;
	private GridBagConstraints dispConstraint;
	
	int numCrons = 0;
	
	public CronHolder(JPanel cronViewPanel){
		
		cronDisplays = new ArrayList<CronDisplayPanel>();
		this.cronViewPanel = cronViewPanel;
		
		this.timelinePanel = new TimelinePanel();
		Dimension dispDim = new Dimension(1000,30);
		timelinePanel.setMinimumSize(dispDim);
		timelinePanel.setMaximumSize(dispDim);
		timelinePanel.setPreferredSize(dispDim);
		JPanel defAndDispPanel = new JPanel(new GridBagLayout());
		defAndDispPanel.setMaximumSize(new Dimension(1400,30));
		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(300,30));
		emptyPanel.setMinimumSize(new Dimension(300,30));
		defAndDispPanel.add(emptyPanel, defConstraint);
		defAndDispPanel.add(timelinePanel, dispConstraint);
		cronViewPanel.add(defAndDispPanel);
		
		defConstraint = new GridBagConstraints();
		defConstraint.gridx = 0;
		defConstraint.gridy = 0;
		dispConstraint = new GridBagConstraints();
		dispConstraint.gridx = 1;
		dispConstraint.gridy = 0;
		
	}
	
	
	public void addCron(){
		numCrons ++;
		CronDisplayPanel cronDisplay = new CronDisplayPanel(endDate);
		Dimension dispDim = new Dimension(1000,70);
		cronDisplay.setMaximumSize(dispDim);
		cronDisplay.setMinimumSize(dispDim);
		cronDisplay.setPreferredSize(dispDim);
		CronDefinitionPanel cronDefinition = new CronDefinitionPanel(cronDisplay, this, numCrons);
		Dimension defDim = new Dimension(300, 70);
		cronDefinition.setMaximumSize(defDim);
		cronDefinition.setMinimumSize(defDim);
		cronDefinition.setPreferredSize(defDim);
		
		cronDisplays.add(cronDisplay);
		
		JPanel defAndDispPanel = new JPanel(new GridBagLayout());
		defAndDispPanel.add(cronDefinition, defConstraint);
		defAndDispPanel.add(cronDisplay, dispConstraint);
		defAndDispPanel.setMaximumSize(new Dimension(1400,70));
		
		defAndDispPanels.put(numCrons, defAndDispPanel);
		
		cronViewPanel.add(defAndDispPanel);
		
		cronViewPanel.revalidate();
	}
	
	public void setEndDate(Date endDate){
		this.endDate = endDate;
		for(CronDisplayPanel displayPanel : cronDisplays){
			displayPanel.setViewDate(endDate);
		}
		timelinePanel.setViewDate(endDate);
	}


	public void setDateField(int selectedDateField) {
		timelinePanel.setDateField(selectedDateField);
	}
	
	
	public void deleteCron(Integer cronId){
		JPanel panelToDelete = defAndDispPanels.get(cronId);
		cronViewPanel.remove(panelToDelete);
		cronViewPanel.invalidate();
		cronViewPanel.revalidate();
		cronViewPanel.repaint();
		defAndDispPanels.remove(cronId);
	}
	

}
