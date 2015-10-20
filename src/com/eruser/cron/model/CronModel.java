package com.eruser.cron.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

public class CronModel {

	private String cron;

	public CronModel(String cronExpr) {
		this.cron = cronExpr;
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
	public List<Float> getCronExecPoints(Date endDate) {

		Date now = new Date();

		if(!endDate.after(now)){
			throw new IllegalArgumentException("Specified date must be in the future.");
		}
		
		Long nowMillis = now.getTime();
		Long endMillis = endDate.getTime();
		Long duration = endMillis - nowMillis;

		ArrayList<Float> execPoints = new ArrayList<Float>();

		CronTrigger trigger;
		try{
			//If cron expression is invalid, return an empty list of points.
			trigger = new CronTrigger(cron);
		}catch(Exception e){
			return execPoints;
		}
		
		
		SimpleTriggerContext context = new SimpleTriggerContext();
		context.update(now, now, now);
		
		Long execMillis;
		Long execDuration;
		Float execPercent;
		
		Date execution = trigger.nextExecutionTime(context);
		while (execution.before(endDate)) {
			
			execMillis = execution.getTime();
			execDuration = execMillis - nowMillis;
			execPercent = execDuration.floatValue() / duration.floatValue();
			
			execPoints.add(execPercent);
			
			context.update(execution, execution, execution);
			
			execution = trigger.nextExecutionTime(context);
			
		}
		
		
		return execPoints;

	}

}
