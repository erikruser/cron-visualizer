package com.eruser.cron.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Definition for a single Cron expression
 * @author eruser
 *
 */
public class CronDefinitionPanel extends JPanel implements ActionListener{
	
	JTextField cronExpressionInput;
	
	//Associated display panel for this cron definition
	CronDisplayPanel display;
	
	public CronDefinitionPanel(CronDisplayPanel display){
		
		this.display = display;
		
		cronExpressionInput = new JTextField(10);
		cronExpressionInput.addActionListener(this);
		this.add(cronExpressionInput);
		
		
	}
	
	public void actionPerformed(ActionEvent evt){
		String cronExpression = cronExpressionInput.getText();
		display.setCronExpr(cronExpression);
	}

}
