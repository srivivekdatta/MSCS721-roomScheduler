package main.java.com.marist.mscs721;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class RoomTest {
	public Room newRoom;

	/*
	 * removing a new room
	 * 
	 * @param name and capacity
	 */

	@Test
	public void removeRoom() {
		Room newRoom = new Room("vivek", 15);
	}

	/*
	 * Getting the added room and capacity using the Assert
	 */
	@Test
	public void getRoomName() {
		Room newRoom = new Room("sri", 5);
		String name = newRoom.getName();
		Assert.assertEquals("sri", name);
		int capacity = newRoom.getCapacity();
		Assert.assertEquals(20, capacity);

	}

}