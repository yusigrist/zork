package ch.bbw.zork;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestBackpack {
	private Backpack bag;
	private Backpack bag2;
	private Backpack bag3;
	private Backpack bag4;
	private Item item1;
	private Item item2;
	private Item item3;

	@Before
	public void before() {
		bag = new Backpack();
		item1 = new Item("Havy", "Havyitem", 5.0);
		item2 = new Item("Light", "Lightitem", 2.0);
		item3 = new Item("Some", "Something", 0.5);
		bag2 = new Backpack();
		bag2.addItem(item2);
		bag3 = new Backpack();
		bag3.addItem(item2);
		bag4 = new Backpack();
		bag4.addItem(item2);
		bag4.addItem(item3);
	}

	@Test
	public void testCheckweight() {
		assertEquals(bag.checkweight(item1), false);
		assertEquals(bag.checkweight(item2), true);
	}

	@Test
	public void testIsInventory() {
		assertEquals(bag2.isInInventorybyStr("Light"), true);
		assertEquals(bag2.isInInventorybyStr("Havy"), false);
	}

	@Test
	public void testRemoveItem() {
		assertEquals(bag3.isInInventorybyStr("Light"), true);
		bag3.removeItembyStr("Light");
		assertEquals(bag3.isInInventorybyStr("Light"), false);
	}

	@Test
	public void testNumberOfItems() {
		assertEquals(bag4.numberOfItems(), 2);
	}

}
