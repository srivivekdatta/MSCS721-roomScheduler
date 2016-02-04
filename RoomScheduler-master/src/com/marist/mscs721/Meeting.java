package com.marist.mscs721;

import java.sql.Timestamp;

public class Meeting {

	private Timestamp startTime = null;
	private Timestamp stopTime = null;
	private String subject = null;

	/**
	 * Constructor to add a schedule for the selected Room.
	 * 
	 * @param newStartTime
	 *            : Start Time for the meeting
	 * @param newEndTime
	 *            : End time for the meeting
	 * @param newSubject
	 *            : Reason for the meeting
	 */
	public Meeting(Timestamp newStartTime, Timestamp newEndTime, String newSubject) {
		setStartTime(newStartTime);
		setStopTime(newEndTime);
		if (newSubject.isEmpty()) {
			setSubject("N/A");
		} else {
			setSubject(newSubject);
		}
	}

	/**
	 * 
	 */
	public String toString() {
		return this.getStartTime().toString() + " - " + this.getStopTime() + ": " + getSubject();
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getStopTime() {
		return stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
