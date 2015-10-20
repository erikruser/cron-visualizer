package com.eruser.cron.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.eruser.cron.model.CronHolder;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener {

	private Date endDate;
	private CronHolder holder;
	private JButton addButton;
	private Timer secondTimer;
	private String[] dateFields = {"Second", "Minute", "Hour", "Day"};
	private int selectedDateField = Calendar.DATE;
	private int endDateIncrement = 1;
	private JSpinner dateIncrementSpinner;

	public ControlPanel(CronHolder holder){
		
		
		this.holder = holder;
		
		Calendar cal = Calendar.getInstance();
		cal.add(selectedDateField, endDateIncrement);
		endDate = cal.getTime();
		
		holder.setEndDate(endDate);

		JComboBox dateFieldBox = new JComboBox(dateFields);
		dateFieldBox.setSelectedIndex(3);
		dateFieldBox.setActionCommand("updateEndDateField");
		dateFieldBox.addActionListener(this);
		add(dateFieldBox);
		
		
		dateIncrementSpinner = new JSpinner();
		SpinnerModel spinnerModel = new SpinnerNumberModel(1,1, 100, 1);
		dateIncrementSpinner.setModel(spinnerModel);
		dateIncrementSpinner.addChangeListener(this);
		add(dateIncrementSpinner);
		
		
		addButton = new JButton();
		addButton.setText("Add Cron");
		addButton.setActionCommand("addCron");
		addButton.addActionListener(this);
		add(addButton);
		
		secondTimer = new Timer(1000, this);
		secondTimer.setActionCommand("updateTime");
		secondTimer.start();
		
	}



	//@Override
	public void actionPerformed(ActionEvent e) {
		if("addCron".equals(e.getActionCommand())){
			holder.addCron();
		}
		else if("updateTime".equals(e.getActionCommand())){
			endDate.setTime(endDate.getTime() + 1000);
			holder.setEndDate(endDate);
		}
		else if("updateTime".equals(e.getActionCommand())){
			JComboBox cb = (JComboBox) e.getSource();
			String selection = (String) cb.getSelectedItem();
			if("Second".equals(selection)){
				selectedDateField = Calendar.SECOND;
			}else if("Minute".equals(selection)){
				selectedDateField = Calendar.MINUTE;
			}else if("Hour".equals(selection)){
				selectedDateField = Calendar.HOUR;
			}else if("Day".equals(selection)){
				selectedDateField = Calendar.DATE;
			}
			Calendar cal = Calendar.getInstance();
			cal.add(selectedDateField, endDateIncrement);
			endDate = cal.getTime();
			holder.setEndDate(endDate);
		}
	}



	//@Override
	public void stateChanged(ChangeEvent e) {
		SpinnerModel spinnerModel = dateIncrementSpinner.getModel();
		if(spinnerModel instanceof SpinnerNumberModel){
			endDateIncrement = ((SpinnerNumberModel)spinnerModel).getNumber().intValue();
			Calendar cal = Calendar.getInstance();
			cal.add(selectedDateField, endDateIncrement);
			endDate = cal.getTime();
			holder.setEndDate(endDate);
		}
	}


	
	
	
	
	
}
