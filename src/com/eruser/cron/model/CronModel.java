package com.eruser.cron.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

public class CronModel {

	private String cron;
	private Date endDate;
	private Date now;
	private Long runDuration;
	private Long totalDuration;

	public CronModel(Date endDate) {
		this.cron = "";
		this.runDuration = 0l;
		setEndDate(endDate);
	}

	/**
	 * Computes all of the dates that the given cron expression would execute
	 * between now and the given date. Returns a list of points for each
	 * computed date normalized as a percentage of the elapsed time where now is
	 * 0% and the given date is 100%.
	 * 
	 * @param endDate
	 *            The final Date we are viewing cron data for
	 * @return A list of timeline points
	 */
	public List<Float> getCronExecPoints() {

		ArrayList<Float> execPoints = new ArrayList<Float>();

		CronTrigger trigger;
		try{
			//See if expression is valid six field expression with seconds as spring expects
			trigger = new CronTrigger(cron);
		}catch(Exception e){
			try{
				//Try adding seconds field, if input is a five field crontab expression
				trigger = new CronTrigger("0 " + cron);
			}catch(Exception ex){
				//If cron expression is invalid, return an empty list of points.
				return execPoints;
			}
		}
		
		//Set cron trigger start time to runDuration in the past, to capture past events that may still be executing
		Date earliestDate = new Date(now.getTime() - runDuration);
		SimpleTriggerContext context = new SimpleTriggerContext();
		context.update(earliestDate, earliestDate, earliestDate);
		
		Long execMillis;
		Long execDuration;
		Float execPercent;
		
		Date execution = trigger.nextExecutionTime(context);
		while (execution.before(endDate)) {
			
			execMillis = execution.getTime();
			Long nowMillis = now.getTime();
			execDuration = execMillis - nowMillis;
			execPercent = execDuration.floatValue() / totalDuration.floatValue();
			execPoints.add(execPercent);
			
			context.update(execution, execution, execution);
			execution = trigger.nextExecutionTime(context);
			
			if(execPoints.size() > 1000){
				break;
			}
			
		}
		
		return execPoints;

	}

	public void setCronExpr(String cronExpression) {
		this.cron = cronExpression;
	}

	public void setDuration(Long duration) {
		this.runDuration = duration;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		now = new Date();
		Long nowMillis = now.getTime();
		Long endMillis = endDate.getTime();
		totalDuration = endMillis - nowMillis;
	}

	public Float getDurationPercent() {
		return runDuration.floatValue() / totalDuration.floatValue();
	}

}
