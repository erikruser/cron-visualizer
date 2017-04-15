package com.eruser.cron;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import com.eruser.cron.view.ControlPanel;
import com.eruser.cron.model.CronHolder;

public class CronViewer implements Runnable {
	

	public CronViewer(){

	}

	@Override
	public void run() {
        // Create the window
        JFrame mainFrame = new JFrame("Cron Visualizer");
        // Sets the behavior for when the window is closed
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10,10));
        
        JPanel cronViewPanel = new JPanel();
        cronViewPanel.setLayout(new BoxLayout(cronViewPanel, BoxLayout.Y_AXIS));
        
        JScrollPane displayViewPane = new JScrollPane(cronViewPanel);

        CronHolder holder = new CronHolder(cronViewPanel);
        ControlPanel controlPanel = new ControlPanel(holder);
        
        mainFrame.add(controlPanel, BorderLayout.PAGE_START);
        mainFrame.add(displayViewPane, BorderLayout.CENTER);

        mainFrame.setPreferredSize(new Dimension(1350,600));
        
        // Arrange the components inside the window
        mainFrame.pack();
        // By default, the window is not visible. Make it visible.
        mainFrame.setVisible(true);
	}
	
	
	public static void main(String[] args){
		
		CronViewer v = new CronViewer();
        SwingUtilities.invokeLater(v);		
	}
	

	

}
