package com.eruser.cron.model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import com.eruser.cron.view.CronDefinitionPanel;
import com.eruser.cron.view.CronDisplayPanel;
import com.eruser.cron.view.TimelinePanel;

public class CronHolder {
	
	private TimelinePanel timelinePanel;
	private List<CronDisplayPanel> cronDisplays;
	private JPanel cronViewPanel;
	
	private Date endDate;
	
	private GridBagConstraints defConstraint;
	private GridBagConstraints dispConstraint;
	
	public CronHolder(JPanel cronViewPanel){
		
		this.timelinePanel = new TimelinePanel();
		JPanel defAndDispPanel = new JPanel(new GridBagLayout());
		defAndDispPanel.add(timelinePanel, dispConstraint);
		
		cronDisplays = new ArrayList<CronDisplayPanel>();
		this.cronViewPanel = cronViewPanel;
		
		defConstraint = new GridBagConstraints();
		defConstraint.gridx = 0;
		defConstraint.gridy = 0;
		dispConstraint = new GridBagConstraints();
		dispConstraint.gridx = 1;
		dispConstraint.gridy = 0;
		
	}
	
	
	public void addCron(){
		CronDisplayPanel cronDisplay = new CronDisplayPanel(endDate);
		CronDefinitionPanel cronDefinition = new CronDefinitionPanel(cronDisplay);
		cronDisplays.add(cronDisplay);
		
		JPanel defAndDispPanel = new JPanel(new GridBagLayout());
		defAndDispPanel.add(cronDefinition, defConstraint);
		defAndDispPanel.add(cronDisplay, dispConstraint);
		
		//cronViewPanel.add(cronDefinition);
		//cronViewPanel.add(cronDisplay);
		cronViewPanel.add(defAndDispPanel);
		cronViewPanel.revalidate();
	}
	
	public void setEndDate(Date endDate){
		this.endDate = endDate;
		for(CronDisplayPanel displayPanel : cronDisplays){
			displayPanel.setViewDate(endDate);
		}
	}
	

}
