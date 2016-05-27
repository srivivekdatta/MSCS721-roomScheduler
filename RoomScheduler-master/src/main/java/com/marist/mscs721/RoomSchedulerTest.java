package main.java.com.marist.mscs721;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class RoomSchedulerTest {

	ArrayList<Room> roomList = new ArrayList<Room>();

	/*
	 * removig the room using
	 * 
	 * @method removeRoomTest()
	 * 
	 */

	@Test
	public void removeRoomTest() {
		Room room1 = new Room("r_101", 10, "Donnelly", "Marist Campus");
		roomList.add(room1);
		Assert.assertEquals(room1.getName(), "r_101");

		roomList.remove(RoomScheduler.findRoomIndex(roomList, "remove room"));
		Assert.assertEquals(roomList.size(), 0);
		System.out.println("Room has been succesfully removed");

	}

	/*
	 * Removing the added room based on
	 * 
	 * @method findRoomIndex()
	 */

	@Test
	public void FindRoomIndexTest() {
		int Index = RoomScheduler.findRoomIndex(roomList, "r_102");
		Assert.assertEquals(Index, -1);
	}
	
	/*
     * Exporting the room details to the JSON file using
     * 
     * @method size() to check whether any room added(existed) or not
     */
    @Test
    public void exportRoomTest() {
        addRoomTest();
        RoomScheduler.saveRoomJSON(roomList);
        Assert.assertEquals(roomList.size(), 1);
    }

	/*
	 * fetch the roomname using
	 * 
	 * @method listRoomTest() and comparing using asserEquals()
	 * 
	 * @Parameters getRoomName and "r_103"
	 */
	@Test
	public void listRoomsTest() {

		// addRoomTest();
		Room room1 = new Room("r_103", 40, "Hancock Center", "Marist Campus");
		roomList.add(room1);

		Assert.assertEquals(room1.getName(), "r_103");

		RoomScheduler.listRooms(roomList);
		Assert.assertEquals(roomList.size(), 1);
	}
	
	
	/*
     * Schedule export details to the JSON file
     * 
     * @method size() to check whether any room added(existed) or not
     */
    @Test
    public void exportScheduleTest() {

        addRoomTest();
        RoomScheduler.saveScheduleJSON(roomList);
        Assert.assertEquals(roomList.size(), 1);
    }
    
    
	/*
	 * Finding the room index which will help us to remove the room and to
	 * getFromRoomName() method
	 */
	@Test
	public void getRoomFromNameTest() {
		listRoomsTest();
		String getRoomName = RoomScheduler.getRoomFromName(roomList, "getRoomName").getName();
		Assert.assertEquals(getRoomName, "r_103");

	}

	/*
	 * Add a new room to the array list and validate it using assertEquals
	 * method
	 */
	/*
	 * Generating the list of rooms which we have added
	 * 
	 * @method addRoomTest() and comparing the size of the room with 1.
	 */
	@Test
	public void addRoomTest() {
		Room room1 = new Room("r_101", 10, "Lowell Thomas", "Marist Campus");
		roomList.add(room1);
		Assert.assertEquals(room1.getName(), "r_101");
	}

	/*
	 * Schedule a room by validating the date and time using TimeStamp and
	 * assertEquals() method.
	 */

	@Test
	public void scheduleRoom() {
		addRoomTest();
		Room curRoom = RoomScheduler.getRoomFromName(roomList, "r_101");
		Timestamp startTimestamp = Timestamp.valueOf("2015-08-08 10:10:00.0");
		Timestamp endTimestamp = Timestamp.valueOf("2012-02-04 10:10:00.0");
		Assert.assertNotEquals(startTimestamp, "2015-08-08 10:10:00.0");
		Assert.assertNotEquals(endTimestamp, "2015-08-08 10:10:00.0");
		String subject = "Test";
		Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
		Assert.assertEquals(subject, "test case");
		curRoom.addMeeting(meeting);
	}

}