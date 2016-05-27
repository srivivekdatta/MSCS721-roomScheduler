package main.java.com.marist.mscs721;

import java.util.ArrayList;
import java.sql.Timestamp;


public class Room {

	private String name;
	private int capacity;
	   private String location;
    private String building;
	private ArrayList<Meeting> meetings;

	/**
	 * Constructor to add a room.
	 * 
	 * @param newName
	 *            : Name of the room
	 * @param newCapacity
	 *            : Capacity of the room
	 */
	public Room(String newName, int newCapacity, String building, String location) {
		setName(newName);
		setCapacity(newCapacity);
		setMeetings(new ArrayList<Meeting>());
		setbuilding(building);
        setlocation(location);

	}

	/**
	 * Add a meeting for the room
	 * 
	 * @param newMeeting
	 *            : Meeting scheduled for the room
	 */
	public String addMeeting(Meeting newMeeting) {
		if(this.verifySchedule(newMeeting))
		{
			this.getMeetings().add(newMeeting);
			return "Successfully scheduled meeting!";
		}
		else
		{
			return "There is a meeting or meetings that overlap with this time, please change the meeting time. Check the schedule for more information";
		}
		
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
	
	/**
	 * Get the building name of the room
	 * 
	 * @return building : building name of the given room
	 */
	public String getbuilding() {
        return building;
    }
	
	/**
	 * Set the building of the room
	 */

    public void setbuilding(String building) {
        this.building = building;

    }
    
    /**
	 * Get the location of the room
	 * 
	 * @return location : location of the given room
	 */

    public String getlocation() {
        return location;
    }
    
    /**
	 * Set the location of the room
	 */

    public void setlocation(String location) {
        this.location = location;
    }
    public boolean verifySchedule(Meeting meeting)
	{
		ArrayList<Meeting> meetingsL = this.meetings;
		for(Meeting m: meetingsL)
		{
			//overlap check
			if(!(checkMeeting(meeting.getStartTime(), meeting.getStopTime(), m.getStartTime(), m.getStopTime())))
			{
				return false;
			}
			
		}
		return true;
	}
    public boolean checkMeeting(Timestamp startA, Timestamp endA, Timestamp startB, Timestamp endB)
	{
		return startA.after(endB) && endA.before(startB);
		
	}
}
