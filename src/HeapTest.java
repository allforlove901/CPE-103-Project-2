/*
 * Brett Nelson bnelso26@calpoly.edu
 * 2/5/16
 * Project 2
 */

import java.util.Scanner;

//class to test BinHeap objects
public class HeapTest {

	//main method
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Give the size of the heap.");
		boolean quit = false;
		
		BinHeap<String> bin = new BinHeap<String>(Integer.parseInt(sc.nextLine()));
		//print menu
		System.out.println("Choose one of the following operations:");
		System.out.println("   - add an element (enter the letter a)");
		System.out.println("   - delete the smallest element (enter the letter d)");
		System.out.println("   - is the heap empty (enter the letter e)");
		System.out.println("   - size of the collection (enter the letter s)");
		System.out.println("   - print the collection (enter the letter p)");
		System.out.println("   - Quit (enter the letter q)");
		
		while(!quit){
			System.out.println("Please enter a menu choice.");
			switch(sc.nextLine()){
			case("a"):
				System.out.println("What value would you like to insert?");
				String store = sc.nextLine();
				bin.insert(store);
				System.out.println(store + " inserted");
				break;
				
			case("d"):
				try{
					String store2 = bin.deleteMin();
					System.out.println(store2 + " deleted");
				}catch(Exception e){
					System.out.println("Invalid operation on an empty heap");
				}
				break;
				
			case("e"):
				if(bin.isEmpty())
					System.out.println("heap is empty");
				else
					System.out.println("heap is not empty");
				break;
			
			case("s"):
				System.out.println("the size is " + bin.size());
				break;
			
			case("p"):
				System.out.println(bin);
				break;
			
			case("q"):
				quit = true;
				System.out.println("quitting");
				while(!bin.isEmpty()){
					System.out.print(bin.deleteMin() + " ");
				}
				break;
			
			default:
				System.out.println("Invalid choice");
				break;
			}
			
		}
		
	}
}
