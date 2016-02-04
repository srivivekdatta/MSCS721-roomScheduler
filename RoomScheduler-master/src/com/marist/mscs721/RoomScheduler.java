package com.marist.mscs721;

/**
 * @author Sri vivek
 * CWID: 20062452
 * 
 * Program: This program is used to add and delete rooms into the system.
 * It can also be used to schedule meetings in a specific room.
 * It also offers a feature to list the rooms.
 */

/** Importing packages for the program */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.logging.Logger;

/**
 * 
 * 
 * 
 * This class is the main class of the program. It uses the Room and meeting
 * classes to provide services to the user.
 *
 */
public class RoomScheduler {
	protected static Scanner keyboard = new Scanner(System.in);

	/**
	 * Entry point to the program
	 */
	public static void main(String[] args) {
		ArrayList<Room> rooms = new ArrayList<Room>();

		while (true) {
			switch (mainMenu())     // this switch is used to make a 
			{						//selection from the available options
			case 1:
				System.out.println(addRoom(rooms));
				break;
			case 2:
				System.out.println(removeRoom(rooms));
				break;
			case 3:
				System.out.print(scheduleRoom(rooms));
				break;
			case 4:
				System.out.println(listSchedule(rooms));
				break;
			case 5:
				System.out.println(listRooms(rooms));
				break;
			case 6:
				System.out.println(exportIntoJson(rooms));
				break;
			case 7:
				System.out.println(importFromJson(rooms));
				break;
			}

		}

	}

	/** 
	 * Lists the schedule for a selected room
	 * 
	 */
	protected static String listSchedule(ArrayList<Room> roomList) {
		String roomName = getRoomName();
		System.out.println(roomName + " Schedule");
		System.out.println("---------------------");

		for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
			System.out.println(m.toString());
		}

		return "";
	}
	
	/** 
	 * Displaying the menu options available for user selection
	 * And take the users input
	 */
	protected static int mainMenu() {
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println("  6 - Export To JSON");
		System.out.println("  7 - Import From JSON");
		System.out.println("Enter your selection: ");

		return keyboard.nextInt();
	}
	
	/**
	 * Adds a room to the existing list of rooms
	 * 
	 */
		protected static String addRoom(ArrayList<Room> roomList) {
		System.out.println("Add a room:");
		String name = getRoomName();

		int capacity = getCapacity();

		for(int loop = 0 ;loop < roomList.size(); loop++)
		{
			if(roomList.get(loop).getName() == name)
				return "The selected room name "+ name +" already exists";
		}
		Room newRoom = new Room(name, capacity);
		roomList.add(newRoom);

		return "Room '" + newRoom.getName() + "' added successfully!";
	}
		
/**
 * Removes a room from the list of rooms 
 *
 */
	protected static String removeRoom(ArrayList<Room> roomList) {
		System.out.println("Remove a room:");
		roomList.remove(findRoomIndex(roomList, getRoomName()));

		return "Room removed successfully!";
	}

	/**
	 * Lists all the rooms added by the user
	 * 
	 * @param roomList : Contains the List of all the rooms added by the user
	 * @return roomList.size()  : Displays the number of rooms in the list
	 */
	protected static String listRooms(ArrayList<Room> roomList) {
		System.out.println("Room Name - Capacity");
		System.out.println("---------------------");

		for (Room room : roomList) {
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		System.out.println("---------------------");

		return roomList.size() + " Room(s)";
	}

	static String startDate;

	/**
	 * Adds a schedule for a selected room
	 * 
	 * @param roomList : Contains the list of all the rooms added by the user
	 * @return Success or error message to be displayed to the user.
	 */
	protected static String scheduleRoom(ArrayList<Room> roomList) {
		System.out.println("Schedule a room:");
		String name = getRoomName();

		System.out.println("Start Date? (yyyy-mm-dd):");
		startDate = keyboard.next();

		ValidateDate();

		System.out.println("Start Time?");
		String startTime = keyboard.next();
		startTime = startTime + ":00.0";

		System.out.println("End Date? (yyyy-mm-dd):");
		String endDate = keyboard.next();
		System.out.println("End Time?");
		String endTime = keyboard.next();
		endTime = endTime + ":00.0";

		Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);
		Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

		System.out.println("Subject?");
		String subject = keyboard.next();

		Room curRoom = getRoomFromName(roomList, name);

		Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

		curRoom.addMeeting(meeting);

		return "Successfully scheduled meeting!";
	}

	/**
	 * Retrieve the room details based on the given name
	 * 
	 * @param roomList : Contains the list of all the rooms and their details
	 * @param name : Contains the name of the room whose details are to be looked up.
	 * @return : Contains the Room details retrieved for a given room name.
	 */
	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
		return roomList.get(findRoomIndex(roomList, name));
	}

	/**
	 * Finds the index of the room based on its name
	 * 
	 * @param roomList : Contains the list of rooms an their details
	 * @param roomName : Contains the name of the room whose index is to be found.
	 * @return  Index of the given room name.
	 */
	protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
		int roomIndex = 0;

		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0) {
				break;
			}
			roomIndex++;
		}

		return roomIndex;
	}

	/**
	 * Fetches the room name from the user.
	 * 
	 * @return : the name fetched from the user
	 */
	protected static String getRoomName() {
		System.out.println("Room Name?");
		return keyboard.next();
	}

	/**
	 * Fetches the capacity of the room from the user.
	 * 
	 * @return  Valid capacity entere by the user
	 */
	protected static int getCapacity() {

		System.out.println("Room capacity?");
		// Take capacity input from the user
		String capvalue = keyboard.next();
		int capacity = 0; // Initialize variable to zero

		try {
			// Check if the value entered is an integer or not.
			if (capvalue.matches("[0-9]*"))
				capacity = Integer.parseInt(capvalue);
			else 
			{// If entered value is not an integer
				// display a message and get a valid input.
				System.out.println("Please enter a valid number for capacity..");
				capacity = getCapacity();
			}
		} catch (NumberFormatException nfex) {
			System.out.println("exception is " + nfex.getMessage());
		}
		return capacity;
	}

	/**
	 * Check for the validity of the entered date
	 * @return : true or false based on the validity checks
	 */
	protected static boolean ValidateDate() {
		// Check for date format in yyyy-mm-dd

		DateFormat df = new SimpleDateFormat(startDate);
		if (!startDate.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
			System.out.println("Please Enter valid date in the format yyyy-mm-dd");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			// get current date time with Date()
			Date date = new Date();
			System.out.println("Current date" + dateFormat.format(date));

			getDate();

		}
		return true;
	}

	/**
	 * Fetche a date from the user
	 */
	protected static void getDate() {
		try {
			startDate = keyboard.next();
			ValidateDate();
		} catch (Exception ex) {
			System.out.println("Exceptio in getDate " + ex.getMessage());
		}
	}

	/**
	 * Exports the rooms along with their details into a JSON file
	 * 
	 * @param roomList : List from which the rooms and their details are to be exported
	 * @return  :Success or Error message to be displayed to the user.
	 */
	protected static String exportIntoJson(ArrayList<Room> roomList) {
		if (roomList.isEmpty())
			return "There are no rooms to be saved";

		JSONObject Rooms = new JSONObject();
		JSONArray roomArr = new JSONArray();

		for (int loop = 0; loop < roomList.size(); loop++) {
			Room rm = roomList.get(loop);
			JSONObject roomObj = new JSONObject();

			roomObj.clear();
			roomObj.put("name", rm.getName());
			roomObj.put("capacity", rm.getCapacity());

			JSONArray meetings = new JSONArray();
			JSONObject meetingObj = new JSONObject();

			ArrayList<Meeting> localMeetings = roomList.get(loop).getMeetings();

			for (int innerloop = 0; innerloop < localMeetings.size(); innerloop++) {
				Meeting lmeet = localMeetings.get(innerloop);

				meetingObj.put("startTime", lmeet.getStartTime().toString());
				meetingObj.put("stopTime", lmeet.getStopTime().toString());
				meetingObj.put("subject", lmeet.getSubject().toString());
				meetings.add(meetingObj.clone());
			}

			roomObj.put("meetings", meetings);
			roomArr.add(roomObj.clone());
		}

		Rooms.put("rooms", roomArr);

		try {

			File file = new File("E:\\test.json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(Rooms.toJSONString());
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print(Rooms);

		return "";
	}
/**
 * Imports rooms and their details from the JSON files
 * 
 * @param roomList : Adds the imported rooms an their details into this array
 * @return  : Success or error message to be displayed to the user
 */
	protected static String importFromJson(ArrayList<Room> roomList) {
		String url = "E:\\test.json";
		if (validURL(url)) {
			JSONParser parser = new JSONParser();

			try {
				BufferedReader br = new BufferedReader(new FileReader(url));
				String currLine;

				while ((currLine = br.readLine()) != null) {

					Object obj = parser.parse(currLine);
					if (obj.toString().isEmpty())
						return "File is empty";

					Room room;
					Meeting meet;
					JSONObject Rooms = (JSONObject) obj;
					JSONArray roomArr = (JSONArray) Rooms.get("rooms");

					for (int loop = 0; loop < roomArr.size(); loop++) {

						JSONObject roomObj = (JSONObject) roomArr.get(loop);
						String name = (String) roomObj.get("name");
						long capacity = (long) roomObj.get("capacity");

						room = new Room(name, (int) capacity);

						JSONArray meetings = (JSONArray) roomObj.get("meetings");
						// ArrayList<Meeting> roomSchedule = new
						// ArrayList<Meeting>();
						for (int innerloop = 0; innerloop < meetings.size(); innerloop++) {
							JSONObject scheduleObj = (JSONObject) meetings.get(innerloop);
							meet = new Meeting(Timestamp.valueOf((String) scheduleObj.get("startTime")),
									Timestamp.valueOf((String) scheduleObj.get("stopTime")),
									(String) scheduleObj.get("subject"));
							room.addMeeting(meet);
						}
						roomList.add(room);
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else
			return importFromJson(roomList);

		return "Successfully imported!";
	}

	private static boolean validURL(String url) {
		if (url.isEmpty()) {
			System.out.println("Please enter a valid url");
			return false;
		}
		return true;
	}

}
