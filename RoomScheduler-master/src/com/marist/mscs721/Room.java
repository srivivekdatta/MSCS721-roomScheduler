package com.marist.mscs721;

import java.util.ArrayList;

public class Room {

	private String name;
	private int capacity;
	private ArrayList<Meeting> meetings;

	/**
	 * Constructor to add a room.
	 * 
	 * @param newName
	 *            : Name of the room
	 * @param newCapacity
	 *            : Capacity of the room
	 */
	public Room(String newName, int newCapacity) {
		setName(newName);
		setCapacity(newCapacity);
		setMeetings(new ArrayList<Meeting>());
	}

	/**
	 * Add a meeting for the room
	 * 
	 * @param newMeeting
	 *            : Meeting scheduled for the room
	 */
	public void addMeeting(Meeting newMeeting) {
		this.getMeetings().add(newMeeting);
	}

	/**
	 * Get the name of the room
	 * 
	 * @return name : Name of the given room
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the room
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the capacity of the room
	 * 
	 * @return capacity : Capacity of the given room
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Set the capacity of the room
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Get the meetings of the room
	 * 
	 * @return meetings : Meetings of the given room
	 */
	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}

	/**
	 * Set the meetings of the room
	 */
	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}

}
