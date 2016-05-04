package main.java.com.marist.mscs721;

/**
 * @author Sri vivek CWID: 20062452
 *
 * Program: This program is used to add and delete rooms into the system. It can
 * also be used to schedule meetings in a specific room. It also offers a
 * feature to list the rooms.
 */
/**
 * Importing packages for the program
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
// import sun.util.logging.PlatformLogger;

/**
 * This class is the main class of the program. It uses the Room and meeting
 * classes to provide services to the user.
 *
 */
public class RoomScheduler {

  protected static Scanner keyboard = new Scanner(System.in);

  private static String startDate;

  private static String endDate;

  private static String startTime;

  private static String endTime;

  private static final Logger LOGGER = Logger.getLogger(RoomScheduler.class.getName());

  private enum StartOrEnd {
    START, END
  };

  /**
   * Entry point to the program
   *
   * @param args
   */
  public static void main(String[] args) {
    ArrayList<Room> rooms = new ArrayList<Room>();

    while (true) {
      // this switch is used to make a
      // selection from the available options
      switch (mainMenu()) {
        case 1:
          LOGGER.entering("RoomSchedular", "addRoom", rooms);
          addRoom(rooms);
          LOGGER.exiting("RoomSchedular", "addRoom");
          break;
        case 2:
          LOGGER.entering("RoomSchedular", "removeRoom", rooms);
          removeRoom(rooms);
          LOGGER.exiting("RoomSchedular", "removeRoom");
          break;
        case 3:
          LOGGER.entering("RoomSchedular", "scheduleRoom", rooms);
          scheduleRoom(rooms);
          LOGGER.exiting("RoomSchedular", "scheduleRoom");
          break;
        case 4:
          LOGGER.entering("RoomSchedular", "listSchedule", rooms);
          listSchedule(rooms);
          LOGGER.exiting("RoomSchedular", "listSchedule");
          break;
        case 5:
          LOGGER.entering("RoomSchedular", "listRooms", rooms);
          listRooms(rooms);
          LOGGER.exiting("RoomSchedular", "listRooms");
          break;
        case 6:
          LOGGER.entering("RoomSchedular", "exportIntoJson", rooms);
          exportIntoJson(rooms);
          LOGGER.exiting("RoomSchedular", "exportIntoJson");
          break;
        case 7:
          LOGGER.entering("RoomSchedular", "importFromJson", rooms);
          LOGGER.log(Level.INFO, importFromJson(rooms));
          LOGGER.exiting("RoomSchedular", "importFromJson");
          break;
        default:
          LOGGER.log(Level.WARNING, "Invalid selection.Please select from the above menu.");
          break;
      }
    }
  }

  /**
   * Lists the schedule for a selected room
   *
   * @param roomList:
   */
  protected static void listSchedule(ArrayList<Room> roomList) {
    String roomName = getRoomName();
    System.out.println(roomName + " Schedule");
    System.out.println("---------------------");

    Room room = getRoomFromName(roomList, roomName);
    if (room != null) {
      ArrayList<Meeting> ArrayOfMeeting = room.getMeetings();
      if (ArrayOfMeeting != null && ArrayOfMeeting.isEmpty()) {
        System.out.println("No Meetings for room " + roomName);
      } else {
        for (Meeting m : ArrayOfMeeting) {
          System.out.println(m.toString());
        }
      }
    } else {
      LOGGER.log(Level.WARNING, "Room {0} not found in the List", roomName);
    }
  }

  /**
   * Displaying the menu options available for user selection And take the users
   * input
   *
   * @return selection : contains the list of options to be selected from
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

    String input = keyboard.next();
    int selection = 0;
    try {
      // Check if the value entered is an integer or not.
      if (input.matches("[1-7]")) {
        selection = Integer.parseInt(input);
      } else {// If entered value is not an integer
        // display a message and get a valid input.
        System.out.println("Please enter a valid number from the menu");
        selection = mainMenu();
      }
    } catch (NumberFormatException nfex) {
      LOGGER.throwing("RoomScheduler", "mainMenu", nfex);
      System.out.println("Exception in mainMenu is " + nfex.getMessage());
    }
    return selection;
  }

  /**
   * @param roomList : Adds a room to the existing list of rooms
   */
  protected static void addRoom(ArrayList<Room> roomList) {
    System.out.println("Add a room:");
    String name = getRoomName();
    int capacity = getCapacity();
    int roomListSize = roomList.size();

    for (int loop = 0; loop < roomListSize; loop++) {
      String roomName = roomList.get(loop).getName();
      if (roomName.equals(name)) {
        LOGGER.log(Level.WARNING, "The selected room name {0} already exists", name);
        System.out.println("Room not added");
      }
    }
    Room newRoom = new Room(name, capacity);
    roomList.add(newRoom);
    LOGGER.log(Level.INFO, "Room ''{0}'' added successfully!", newRoom.getName());
  }

  /**f
   * @param roomList  :Removes a room from the list of rooms
   */
  protected static void removeRoom(ArrayList<Room> roomList) {
    System.out.println("Remove a room:");
   
	   int roomindex = findRoomIndex(roomList, getRoomName());
    if (roomindex != -1) {
      roomList.remove(roomindex);
      LOGGER.log(Level.INFO, "Room removed successfully!");
    } else {
      LOGGER.log(Level.WARNING, "Entered room does not exist. Please enter a valid name");
    }
  }
  /**
   * Lists all the rooms added by the user
   *
   * @param roomList : Contains the List of all the rooms added by the user
   */
  protected static void listRooms(ArrayList<Room> roomList) {
    System.out.println("Room Name - Capacity");
    System.out.println("---------------------");

    for (Room room : roomList) {
      System.out.println(room.getName() + " - " + room.getCapacity());
    }

    System.out.println("---------------------");
    int roomSize = roomList.size();
    System.out.println(roomSize + " Room(s)");
    LOGGER.log(Level.INFO, "{0} Room(s)", roomSize);
  }

  /**
   * Adds a schedule for a selected room
   *
   * @param roomList : Contains the list of all the rooms added by the user
   */
  protected static void scheduleRoom(ArrayList<Room> roomList) {
    System.out.println("Schedule a room:");
    String name = getRoomName();

    if (getValidDate(StartOrEnd.START)) {
      LOGGER.log(Level.INFO, "Valid Start Date entered as :{0}", startDate);
    }

    if (getValidTime(StartOrEnd.START)) {
      LOGGER.log(Level.INFO, "Valid Start Time entered as : {0}", startTime);
    }

    if (getValidDate(StartOrEnd.END)) {
      LOGGER.log(Level.INFO, "Valid End Date entered as :{0}", endDate);
    }

    if (getValidTime(StartOrEnd.END)) {
      LOGGER.log(Level.INFO, "Valid End Time entered as : {0}", endTime);
    }

    Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);
    Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

    System.out.println("Subject?");
    String subject = keyboard.next();
    if (!subject.equals("")) {
      LOGGER.log(Level.INFO, "Subject entered as : {0}", subject);
    } else {
      LOGGER.log(Level.WARNING, "Subject not entered!!");
    }

    Room curRoom = getRoomFromName(roomList, name);
    if (curRoom != null) {
      Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
      curRoom.addMeeting(meeting);
      LOGGER.log(Level.INFO, "Successfully scheduled meeting!");
    } else {
      LOGGER.log(Level.WARNING, "Room {0} not found!!", name);
    }
  }

  /**
   * Retrieve the room details based on the given name
   *
   * @param roomList : Contains the list of all the rooms and their details
   * @param name : Contains the name of the room whose details are to be looked
   * up.
   * @return : Contains the Room details retrieved for a given room name.
   */
  protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
    LOGGER.entering("RoomScheduler", "getRoomFromName");
    int index = findRoomIndex(roomList, name);
    if (index != -1) {
      System.out.println("Room not found");
      LOGGER.log(Level.WARNING, "Room not found");
      LOGGER.exiting("RoomScheduler", "getRoomFromName");
      return null;
    } else {
      Room roomFromName = roomList.get(index);
      LOGGER.exiting("RoomScheduler", "getRoomFromName");
      return roomFromName;
    }
  }

  /**
   * Finds the index of the room based on its name
   *
   * @param roomList : Contains the list of rooms an their details
   * @param roomName : Contains the name of the room whose index is to be found.
   * @return Index of the given room name.
   */
  protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
    LOGGER.entering("RoomScheduler", "findRoomIndex");
    int roomIndex = 0;
    for (Room room : roomList) {
      if ((room.getName()).equals(roomName)) {
        break;
      }
      roomIndex++;
    }

    if (roomIndex == roomList.size()) {
      LOGGER.log(Level.WARNING, "Room index for room {0} not found !!", roomName);
      LOGGER.exiting("RoomScheduler", "findRoomIndex");
      return -1;
    } else {
      LOGGER.log(Level.INFO, "Room index for room {0} is : {1}", new Object[]{roomName, roomIndex});
      LOGGER.exiting("RoomScheduler", "findRoomIndex");
      return roomIndex;
    }
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
   * @return Valid capacity entered by the user
   */
  protected static int getCapacity() {
    LOGGER.entering("RoomScheduler", "getCapacity");
    System.out.println("Room capacity?");
    // Take capacity input from the user
    String capvalue = keyboard.next();
    int capacity = 0; // Initialize variable to zero

    try {
      // Check if the value entered is an integer or not.
      if (capvalue.matches("[0-9]*")) {
        capacity = Integer.parseInt(capvalue);
      } else {// If entered value is not an integer
        // display a message and get a valid input.
        LOGGER.log(Level.WARNING, "Invalid value entered for capacity as : {0}", capvalue);
        System.out.println("Please enter a valid number for capacity..");
        capacity = getCapacity();
      }
    } catch (NumberFormatException nfex) {
      LOGGER.logp(Level.SEVERE, "RoomScheduler", "findRoomIndex", "Exception caught", nfex);
      System.out.println("Exception in getCapacity is " + nfex.getMessage());
    }
    LOGGER.exiting("RoomScheduler", "getCapacity");
    return capacity;
  }

  /**
   * Check for the validity of the entered date
   *
   * @param pStartEnd
   *
   * @return : true or false based on the validity checks
   */
  protected static boolean getValidDate(StartOrEnd pStartEnd) {
    LOGGER.entering("RoomScheduler", "getValidDate");
    // Check for date format in yyyy-mm-dd
    DateFormat df;
    switch (pStartEnd) {
      case START:
        System.out.println("Start Date? (yyyy-mm-dd):");
        startDate = keyboard.next();
        df = new SimpleDateFormat(startDate);
        break;
      case END:
        System.out.println("End Date? (yyyy-mm-dd):");
        endDate = keyboard.next();
        df = new SimpleDateFormat(startDate);
        break;
      default:
        df = new SimpleDateFormat();
        break;
    }

    if (!df.toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
      System.out.println("Please Enter valid date in the format yyyy-mm-dd");
      LOGGER.log(Level.WARNING, "Invalid value entered for date as : {0}", df.toString());

      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      // get current date time with Date()
      Date date = new Date();
      System.out.println("Current date" + dateFormat.format(date));

      getDate(pStartEnd);

    }
    LOGGER.exiting("RoomScheduler", "getValidDate");
    return true;
  }

  protected static boolean getValidTime(StartOrEnd pStartEnd) {
    LOGGER.entering("RoomScheduler", "getValidTime");
    // Check for date format in yyyy-mm-dd
    DateFormat df;
    switch (pStartEnd) {
      case START:
        System.out.println("Start Time?");
        startTime = keyboard.next();
        startTime = startTime + ":00.0";
        df = new SimpleDateFormat(startTime);
        break;
      case END:
        System.out.println("End Time?");
        endTime = keyboard.next();
        endTime = endTime + ":00.0";
        df = new SimpleDateFormat(endTime);
        break;
      default:
        df = new SimpleDateFormat();
        break;
    }

    if (!df.toString().matches("([0-2][0-9]):([0-5][0-9]):([0-5][0-9])")) {
      System.out.println("Please Enter valid time in the format hh:mm:ss");
      LOGGER.log(Level.WARNING, "Invalid value entered for time as : {0}", df.toString());
      DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

      // get current date time with Date()
      Date date = new Date();
      System.out.println("Current time" + dateFormat.format(date));

      getDate(pStartEnd);

    }
    LOGGER.exiting("RoomScheduler", "getValidTime");
    return true;
  }

  /**
   * Fetches a date from the user
   *
   * @param pStartEnd
   */
  protected static void getDate(StartOrEnd pStartEnd) {
    try {
      switch (pStartEnd) {
        case START:
          startDate = keyboard.next();
          break;
        case END:
          endDate = keyboard.next();
          break;
        default:
          break;
      }

      getValidDate(pStartEnd);
    } catch (Exception ex) {
      System.out.println("Exception in getDate " + ex.getMessage());
    }
  }

  /**
   * Exports the rooms along with their details into a JSON file
   *
   * @param roomList : List from which the rooms and their details are to be
   * exported
   */
  protected static void exportIntoJson(ArrayList<Room> roomList) {
    if (roomList.isEmpty()) {
      LOGGER.log(Level.WARNING, "There are no rooms to be saved");
      System.out.println("There are no rooms to be saved");
      return;
    }
    // Outer most Object containing an array of Rooms
    JSONObject Rooms = new JSONObject();
    // This is the array of Room Objects
    JSONArray roomArr = new JSONArray();

    for (int loop = 0; loop < roomList.size(); loop++) {
      Room rm = roomList.get(loop);
      // Contains the name, capacity, meetings of every room
      // This is added to the Array of Rooms
      JSONObject roomObj = new JSONObject();

      roomObj.put("name", rm.getName());
      roomObj.put("capacity", rm.getCapacity());

      // This is the array containing the meetings for every room.
      JSONArray meetings = new JSONArray();

      // This contains details of one meeting : Start Time, Stop Time and
      // Subject
      JSONObject meetingObj = new JSONObject();

      ArrayList<Meeting> localMeetings = roomList.get(loop).getMeetings();

      for (int innerloop = 0; innerloop < localMeetings.size(); innerloop++) {
        Meeting lmeet = localMeetings.get(innerloop);

        meetingObj.put("startTime", lmeet.getStartTime().toString());
        meetingObj.put("stopTime", lmeet.getStopTime().toString());
        meetingObj.put("subject", lmeet.getSubject());
        meetings.add(meetingObj.clone());
      }

      roomObj.put("meetings", meetings);
      roomArr.add(roomObj.clone());
    }

    Rooms.put("rooms", roomArr);

    try {
      String filePath = getFilePath();
      File file = new File(filePath);

      // Creating a JSON file at the specified location
      file.createNewFile();

      // Writes the contents into the file
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(Rooms.toJSONString());
      fileWriter.flush();
      fileWriter.close();

    } catch (IOException ioex) {
      LOGGER.logp(Level.SEVERE, "RoomScheduler", "exportIntoJson", "There are no rooms to be saved", ioex);
      System.out.println("Error while exporting JSON");
    }
    LOGGER.log(Level.INFO, "Successfully exported into JSON");
  }

  /**
   * Imports rooms and their details from the JSON files
   *
   * @param roomList : Adds the imported rooms and their details into this array
   * @return : Success or error message to be displayed to the user
   */
  protected static String importFromJson(ArrayList<Room> roomList) {
    String url = getFilePath();

    JSONParser parser = new JSONParser();

    try {
      BufferedReader br = new BufferedReader(new FileReader(url));
      String currLine;

      // Read the file till the end
      while ((currLine = br.readLine()) != null) {

        // Convert the read stream into object.
        Object obj = parser.parse(currLine);

        // Check if the file is empty
        if (obj.toString().isEmpty()) {
          return "File is empty";
        }

        Room room;
        Meeting meet;
        JSONObject Rooms = (JSONObject) obj;

        JSONArray roomArr = (JSONArray) Rooms.get("rooms");

        for (int loop = 0; loop < roomArr.size(); loop++) {

          JSONObject roomObj = (JSONObject) roomArr.get(loop);
          String name = (String) roomObj.get("name");
          Integer capacity = (Integer) roomObj.get("capacity");

          room = new Room(name, capacity);

          JSONArray meetings = (JSONArray) roomObj.get("meetings");

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
      System.out.println("Your export failed. File Couldnt be found");
    } catch (IOException ioEx) {
      System.out.println("Exception caught : "+ ioEx.getMessage());
    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return "Successfully imported!";
  }

  /**
   *
   * @return
   */
  private static String getFilePath() {
    System.out.println("Please specify a path to save the file:");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String filePath = "";
    try {
      filePath = br.readLine();
      if (filePath.equals("") || !filePath.matches("^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\$")) {
        System.out.println("Please enter a valid path");
        LOGGER.log(Level.WARNING, "Invalid value for file path entered as : {0}", filePath);
        filePath = getFilePath();
      }
    } catch (IOException ioEx) {
      LOGGER.logp(Level.SEVERE, "RoomScheduler", "getFilePath", "Invalid value for filepath", ioEx);
    }
    return filePath;
  }

}
