package com.eruser.cron.view;

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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.eruser.cron.model.CronHolder;

/**
 * Definition for a single Cron expression
 * @author eruser
 *
 */
public class CronDefinitionPanel extends UIPanel implements ActionListener, ChangeListener, DocumentListener {
	
	private final Long MILLIS_IN_DAY = 86400000l;
	private final Long MILLIS_IN_HOUR = 3600000l;
	private final Long MILLIS_IN_MINUTE = 60000l;
	private final Long MILLIS_IN_SECOND = 1000l;
	private int durationMultiplier = 0;
	private Long durationMillis = MILLIS_IN_SECOND;
	
	JTextField cronExpressionInput;
	JSpinner cronExecDuration;

	
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
		
		Insets insets = new Insets(1,1,1,1);

		
		this.setLayout(new GridBagLayout());

		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		this.setBorder(border);
		//this.setSize(new Dimension(250, 100));


        UIPanel expressionPanel = new UIPanel();

        cronExpressionInput = new JTextField(10);
        cronExpressionInput.getDocument().addDocumentListener(this);
        //cronExpressionInput.addActionListener(this);
        expressionPanel.add(cronExpressionInput);

        GridBagConstraints expressionPanelConstraint = new GridBagConstraints();
        expressionPanelConstraint.gridx = 1;
        expressionPanelConstraint.gridy = 0;
        expressionPanelConstraint.weightx = 1;
        expressionPanelConstraint.weighty = 1;
        //expressionPanelConstraint.insets = insets;
        add(cronExpressionInput, expressionPanelConstraint);

		
		UIPanel durationPanel = new UIPanel();
		durationPanel.setLayout(new BoxLayout(durationPanel, BoxLayout.X_AXIS));
        //durationPanel.setBorder(BorderFactory.createTitledBorder("Est. Duration"));

		SpinnerModel spinnerModel = new SpinnerNumberModel(0,0, 100, 1);
		cronExecDuration = new JSpinner(spinnerModel);
		cronExecDuration.addChangeListener(this);
		durationPanel.add(cronExecDuration);
		
		JComboBox dateFieldBox = new JComboBox(dateFields);
		dateFieldBox.setSelectedIndex(0);
		dateFieldBox.setActionCommand("updateDuration");
		dateFieldBox.addActionListener(this);
		durationPanel.add(dateFieldBox);

        GridBagConstraints durationEntryConstraint = new GridBagConstraints();
        durationEntryConstraint.gridx = 1;
        durationEntryConstraint.gridy = 1;
        durationEntryConstraint.insets = insets;
		add(durationPanel, durationEntryConstraint);


		JButton deleteButton = new JButton();
		deleteButton.setBackground(UIManager.getColor("Panel.background"));
		//deleteButton.setSize(100,25);
		deleteButton.addActionListener(this);
		deleteButton.setText("x");
		deleteButton.setActionCommand("Delete");
		deleteButton.setBorder(border);
        GridBagConstraints deleteButtonConstraint = new GridBagConstraints();
        deleteButtonConstraint.anchor = GridBagConstraints.NORTHEAST;
        deleteButtonConstraint.gridx = 0;
        deleteButtonConstraint.gridy = 0;
        //deleteButtonConstraint.ipadx = 10;
        deleteButtonConstraint.insets = insets;
		add(deleteButton, deleteButtonConstraint);

		
	}

	public void actionPerformed(ActionEvent evt){
        System.out.println(evt.toString());
        System.out.println(evt.getActionCommand());

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
        System.out.println(e.toString());
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

    @Override
    public void insertUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }
}
