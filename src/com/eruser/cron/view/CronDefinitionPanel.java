package com.eruser.cron.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.eruser.cron.model.CronHolder;

/**
 * Definition for a single Cron expression
 * @author eruser
 *
 */
public class CronDefinitionPanel extends JPanel implements ActionListener, ChangeListener{
	
	private final Long MILLIS_IN_DAY = 86400000l;
	private final Long MILLIS_IN_HOUR = 3600000l;
	private final Long MILLIS_IN_MINUTE = 60000l;
	private final Long MILLIS_IN_SECOND = 1000l;
	private int durationMultiplier = 0;
	private Long durationMillis = MILLIS_IN_SECOND;
	
	JTextField cronExpressionInput;
	JSpinner cronExecDuration;
	
	GridBagConstraints expressionLabelConstraint = new GridBagConstraints();
	GridBagConstraints textFieldConstraint = new GridBagConstraints();
	GridBagConstraints actionButtonConstraint = new GridBagConstraints();
	GridBagConstraints deleteButtonConstraint = new GridBagConstraints();
	GridBagConstraints durationLabelConstraint = new GridBagConstraints();
	GridBagConstraints durationEntryConstraint = new GridBagConstraints();
	
	//Associated display panel for this cron definition
	CronDisplayPanel display;

	private CronHolder cronHolder;
	private int cronId;

	private String[] dateFields = {"Second(s)", "Minute(s)", "Hour(s)", "Day(s)"};
	private int selectedDateField = Calendar.SECOND;
	
	
	public CronDefinitionPanel(CronDisplayPanel display, CronHolder cronHolder, int cronId){
		
		this.cronHolder = cronHolder;
		this.cronId = cronId;
		
		this.display = display;
		Color panelBackground = this.getBackground();
		
		Insets insets = new Insets(5,5,5,5);

		expressionLabelConstraint.gridx = 0;
		expressionLabelConstraint.gridy = 0;
		expressionLabelConstraint.insets = insets;
		
		textFieldConstraint.gridx = 1;
		textFieldConstraint.gridy = 0;
		textFieldConstraint.insets = insets;
		
		durationLabelConstraint.gridx = 0;
		durationLabelConstraint.gridy = 1;
		durationLabelConstraint.insets = insets;
		
		durationEntryConstraint.gridx = 1;
		durationEntryConstraint.gridy = 1;
		durationEntryConstraint.insets = insets;
		
		actionButtonConstraint.gridx = 2;
		actionButtonConstraint.gridy = 0;
		actionButtonConstraint.ipadx = 10;
		actionButtonConstraint.insets = insets;
		
		deleteButtonConstraint.gridx = 2;
		deleteButtonConstraint.gridy = 1;
		deleteButtonConstraint.ipadx = 10;
		deleteButtonConstraint.insets = insets;
		
		
		this.setLayout(new GridBagLayout());
		
		
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		this.setBorder(border);
		this.setSize(new Dimension(250, 60));
		
		JLabel label = new JLabel("Expression");
		label.setFont(label.getFont().deriveFont(10f));
		add(label, expressionLabelConstraint);
		
		JLabel durationLabel = new JLabel("Est. Duration");
		durationLabel.setFont(label.getFont().deriveFont(10f));
		add(durationLabel, durationLabelConstraint);
		
		cronExpressionInput = new JTextField(10);
		add(cronExpressionInput, textFieldConstraint);
		
		JPanel durationPanel = new JPanel();
		durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.X_AXIS));

		SpinnerModel spinnerModel = new SpinnerNumberModel(0,0, 100, 1);
		cronExecDuration = new JSpinner(spinnerModel);
		cronExecDuration.addChangeListener(this);
		durationPanel.add(cronExecDuration);
		
		JComboBox dateFieldBox = new JComboBox(dateFields);
		dateFieldBox.setSelectedIndex(0);
		dateFieldBox.setActionCommand("updateDuration");
		dateFieldBox.addActionListener(this);
		durationPanel.add(dateFieldBox);
		
		add(durationPanel, durationEntryConstraint);
		
		JButton updateButton = new JButton();
		updateButton.setBackground(panelBackground);
		updateButton.addActionListener(this);
		updateButton.setText("Update");
		updateButton.setBorder(border);
		updateButton.setActionCommand("Update");
		add(updateButton, actionButtonConstraint);
		
		
		JButton deleteButton = new JButton();
		deleteButton.setBackground(UIManager.getColor("Panel.background"));
		//deleteButton.setSize(100,25);
		deleteButton.addActionListener(this);
		deleteButton.setText("Delete");
		deleteButton.setActionCommand("Delete");
		deleteButton.setBorder(border);
		add(deleteButton, deleteButtonConstraint);
		
	}
	
	public void actionPerformed(ActionEvent evt){
		if("Update".equals(evt.getActionCommand())){
			update();
		}else if("Delete".equals(evt.getActionCommand())){
			delete();
		}else if("updateDuration".equals(evt.getActionCommand())){
			
			JComboBox cb = (JComboBox) evt.getSource();
			String selection = (String) cb.getSelectedItem();
			if("Second(s)".equals(selection)){
				durationMillis = MILLIS_IN_SECOND;
			}else if("Minute(s)".equals(selection)){
				durationMillis = MILLIS_IN_MINUTE;
			}else if("Hour(s)".equals(selection)){
				durationMillis = MILLIS_IN_HOUR;
			}else if("Day(s)".equals(selection)){
				durationMillis = MILLIS_IN_DAY;
			}
			
			updateCronDuration();
		}
	}

	
	private void update(){
		String cronExpression = cronExpressionInput.getText();
		display.setCronExpr(cronExpression);
	}
	
	private void delete(){
		cronHolder.deleteCron(cronId);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		SpinnerModel spinnerModel = cronExecDuration.getModel();
		if(spinnerModel instanceof SpinnerNumberModel){
			durationMultiplier = ((SpinnerNumberModel)spinnerModel).getNumber().intValue();
		}
		
		updateCronDuration();
	}

	private void updateCronDuration() {
		Long duration = durationMillis * durationMultiplier;
		display.setCronDuration(duration);
	}

}
