package ch.bbw.zork;

import java.util.ArrayList;

public class Backpack {

	  private ArrayList<Item> bag = new ArrayList<>(); 
	     
	     public Backpack() {
    
	     }
	     
	     public boolean isInInventory(Item anItem) {
	       for (int i = 0; i < bag.size();) {
	             if(bag.get(i).equals(anItem)) { 
	              return true;
	             }
	             i++;
	       }
	       return false;
	     }
	     
	     public boolean isInInventorybyStr(String anItem) {
		       for (int i = 0; i < bag.size();) {
		             if(bag.get(i).getName().equals(anItem)) { 
		              return true;
		             }
		             i++;
		       }
		       return false;
		     }
	     
	     public void addItem(Item item) {
	           bag.add(item); 
	     }
	     
	     public void removeItem(Item item) {
	           for (int i = 0; i < bag.size(); i++) {
	                if (item.getName().equals(bag.get(i).getName()) && item.getWeight() == (bag.get(i).getWeight())) {
	                     bag.remove(i); 
	                }
	           }
	     }
	     
	     public void removeItembyStr(String item) {
	           for (int i = 0; i < bag.size(); i++) {
	                if (bag.get(i).getName().equals(item)) {
	                     bag.remove(i); 
	                     System.out.println("Succesfully out of the Bag!");
	                }
	           }
	     }
	     
	     public void printInventory() {
	           if (bag.size() == 0) {
	                System.out.println("There is nothing in your Bag! :)"); 
	           } else if(bag.size()>0){
	           
	                for (int i = 0; i < bag.size(); i++) { 
	                System.out.println(bag.get(i).getName());
	                }
	           }else {
	                System.out.println("There is nothing in your Bag! :)");
	           }
	     }
	     
	     public int numberOfItems() {
	           return bag.size(); 
	     }
}
