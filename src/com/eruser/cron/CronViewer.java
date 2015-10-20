package com.eruser.cron;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.*;

/*
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
*/

import com.eruser.cron.model.CronHolder;
import com.eruser.cron.view.ControlPanel;

public class CronViewer implements Runnable {
	
	private GridBagConstraints controlPanelConstraint;
	private GridBagConstraints displayViewConstraint;

	public CronViewer(){
		controlPanelConstraint = new GridBagConstraints();
		controlPanelConstraint.gridx = 0;
		controlPanelConstraint.gridy = 0;
		controlPanelConstraint.gridheight = 20;
		displayViewConstraint = new GridBagConstraints();
		displayViewConstraint.gridx = 0;
		displayViewConstraint.gridy = 1;
	}

	//@Override
	public void run() {
        // Create the window
        JFrame mainFrame = new JFrame("Cron Viewer");
        // Sets the behavior for when the window is closed
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(5,5));
        
        JPanel cronViewPanel = new JPanel();
        cronViewPanel.setLayout(new GridLayout(0,1));
        
        JScrollPane displayViewPane = new JScrollPane(cronViewPanel);
        
        

        CronHolder holder = new CronHolder(cronViewPanel);
        
        ControlPanel controlPanel = new ControlPanel(holder);
        controlPanel.setLayout(new GridLayout(1,3));
        
        
        mainFrame.add(controlPanel, BorderLayout.PAGE_START);
        mainFrame.add(displayViewPane, BorderLayout.CENTER);

        mainFrame.setPreferredSize(new Dimension(800,600));
        
        // Arrange the components inside the window
        mainFrame.pack();
        // By default, the window is not visible. Make it visible.
        mainFrame.setVisible(true);
	}
	
	
	public static void main(String[] args){
		
		CronViewer v = new CronViewer();
        SwingUtilities.invokeLater(v);		
	}
	
	
	
	public static JPanel buildDatePanel(String label, Date value) {
		JPanel datePanel = new JPanel();

		SpinnerModel model = new SpinnerDateModel();
		JSpinner timeSpinner = new JSpinner(model);
		JComponent editor = new JSpinner.DateEditor(timeSpinner, "MM/dd/yyyy HH:mm:ss");
		timeSpinner.setEditor(editor);
		if(value != null) {
		    timeSpinner.setValue(value);
		}

		datePanel.add(timeSpinner);

		return datePanel;
		}
	

}
