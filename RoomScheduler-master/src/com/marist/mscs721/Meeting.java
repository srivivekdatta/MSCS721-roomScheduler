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
	 * Formatting the date and time
	 */
	public String toString() {
		return this.getStartTime().toString() + " - " + this.getStopTime() + ": " + getSubject();
	}

	/**
	 * Fetches the start time for a selected room
	 * 
	 * @return start time for a selected room
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time for a selected room
	 * 
	 * @param startTime
	 *            : Contains the start time for a selected room
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * Fetches the end time for a selected room
	 * 
	 * @return end time for a selected room
	 */
	public Timestamp getStopTime() {
		return stopTime;
	}

	/**
	 * Sets the end time for a selected room
	 * 
	 * @param stopTime
	 *            : Contains the end time for a selected room
	 */
	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * Fetches the subject for a selected room
	 * 
	 * @return subject for a selected room
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject for a selected room
	 * 
	 * @param subject
	 *            : Contains the subject for a selected room
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
