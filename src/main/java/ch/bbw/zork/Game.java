package ch.bbw.zork;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author: Michael Kolling Version: 1.1 Date: March 2000
 * 
 * This class is the main class of the "Zork" application. Zork is a very
 * simple, text based adventure game. Users can walk around some scenery. That's
 * all. It should really be extended to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * routine.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates the commands that
 * the parser returns.
 */

public class Game {

	private Parser parser;
	private Room currentRoom;
	private Room outside, lab, tavern, gblock, office, garden;
	private Item hammer, key, coat;
	private ArrayList<Room> map;
	private Stack<Room> previousRooms;
	Random r;

	private Backpack bag = new Backpack();

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {

		r = new Random();
		parser = new Parser(System.in);

		// Create all the rooms and link their exits together.
		outside = new Room("outside G block on Peninsula campus");
		lab = new Room("lab, a lecture theatre in A block");
		tavern = new Room("the Seahorse Tavern (the campus pub)");
		gblock = new Room("the G Block");
		office = new Room("the computing admin office");
		garden = new Room("beautiful garden on the Campus - nice smelling roses");

		// initialise room exits
		outside.setExits(garden, lab, gblock, tavern);
		lab.setExits(null, null, null, outside);
		tavern.setExits(null, outside, null, null);
		gblock.setExits(outside, office, null, garden);
		office.setExits(null, null, null, gblock);
		garden.setExits(null, gblock, outside, null);

		//Add ghosts
		lab.addGhost(new Ghost(true));

		currentRoom = outside; // start game outside
		previousRooms = new Stack<>();

		map = new ArrayList<>();
		map.add(outside);
		map.add(lab);
		map.add(tavern);
		map.add(gblock);
		map.add(office);
		map.add(garden);

		hammer = new Item("Hammer", "Crafting Tool", 2.0);

		key = new Item("Key", "Used for opening doors", 0.5);

		coat = new Item("Coat", "This cot protects you against angry ghosts.", 5.0);

		lab.addItem(hammer);
		outside.addItem(key);
		garden.addItem(coat);

	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Zork!");
		System.out.println("Zork is a simple adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(currentRoom.longDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command ends
	 * the game, true is returned, otherwise false is returned.
	 */
	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("map")) {
			System.out.println("Currentroom: " + currentRoom.shortDescription());
			System.out.println("All Rooms: ");
			for (Room room : map) {
				System.out.println(room.shortDescription());
			}
		} else if(commandWord.equals("say")) {
			if(command.hasSecondWord()){
				ArrayList<Ghost> ghosts = currentRoom.getGhosts();
				if(!ghosts.isEmpty()){
					for (Ghost ghost : ghosts) {
						if (ghost.isFriendly()){
							System.out.println(ghost.responseToPlyer(command.getSecondWord()));
						} else {
							System.out.println("RUN!!");
						}
					}
				} else {
					System.out.println("No one is here.");
				}
			} else {
				System.out.println("No one is here.");
			}
		}else if (commandWord.equals("go")) {
			goRoom(command);

			if(currentRoom == gblock){
				int time = r.nextInt(10) + 5;
				System.out.println("Something is wrong....");
				if(bag.isInInventorybyStr("Coat")){
					System.out.println("Oh, there is a Ghost. But he won't harm you since you have the magical coat.");
				} else {
					try {
						TimeUnit.SECONDS.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					gblock.addGhost(new Ghost(false));
					System.out.println("OH NO! A GHOST IS COMING FOR YOU!!!! RUN!!!!");
				}
			}
			// Gewonnen?
			if (currentRoom == tavern) {
				System.out.println("Sie sind in der Taverne und haben gewonnen!");
				return true;
			}

		} else if (commandWord.equals("get")) {
			if (command.hasSecondWord()) {
				ArrayList<Item> itemeqs = currentRoom.getItems();

				Boolean bol = false;
				String secound = command.getSecondWord();
				System.out.println(secound);

				for (int i = 0; i < itemeqs.size();) {
					if (itemeqs.get(i).getName().equals(secound)) {
						Item itemfin = currentRoom.getItem(secound);
						bag.addItem(itemfin);
						System.out.println("Item now in your Bag:" + itemfin.getName());
						bol = true;
					}
					i++;
				}

				if (bol == false) {
					System.out.println("This Item don't exist in Room:" + currentRoom.shortDescription());
				}
				// TODO: Gegenstand im Rucksack aufnehmen //backpack.add(item);
			}
		} else if (commandWord.equals("put")) {
			// TODO: Gegenstand aus dem Rucksack nehmen und ablegen

			if (command.hasSecondWord()) {
				if (bag.isInInventorybyStr(command.getSecondWord())) {
					bag.removeItembyStr(command.getSecondWord());
				}
			}

		} else if (commandWord.equals("backpack")) {

			bag.printInventory();

		} else if (commandWord.equals("back")) {
			if (!previousRooms.isEmpty()) {
				currentRoom = previousRooms.pop();
				System.out.println(currentRoom.longDescription());
			} else {
				System.out.println("Don't know where to go :(");
			}

		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Quit what?");
			} else {
				return true; // signal that we want to quit
			}
		}
		return false;
	}

	/*
	 * implementations of user commands:
	 */

	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at Monash Uni, Peninsula Campus.");
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println(parser.showCommands());
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		// if there is no second word, we don't know where to go...
		if (!command.hasSecondWord()) {
			System.out.println("Go where?");
		} else {

			String direction = command.getSecondWord();

			// Try to leave current room.
			Room nextRoom = currentRoom.nextRoom(direction);

			if (nextRoom == null)
				System.out.println("There is no door!");
			else {
				previousRooms.push(currentRoom);
				currentRoom = nextRoom;
				System.out.println(currentRoom.longDescription());

			}
		}
	}
}
