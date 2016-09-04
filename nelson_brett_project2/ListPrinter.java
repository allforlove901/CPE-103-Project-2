/*
 * Brett Nelson bnelso26@calpoly.edu
 * 2/5/16
 * Project 2
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ListPrinter {

	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		BinHeap<Student> bin = new BinHeap<Student>();
		
		System.out.println("Please enter the name of file to read");
		File file = new File(sc.nextLine());
		
		try{
			ArrayList<String[]> list = new ArrayList<String[]>();
			Scanner fileReader = new Scanner(file);
			while(fileReader.hasNextLine()){
				String store = fileReader.nextLine();
				list.add(store.split(" "));
			}
			for(int i = 0; i < list.size(); i++){
				if(list.get(i).length == 2 && Integer.parseInt(list.get(i)[0]) > 0){
					long id = Integer.parseInt(list.get(i)[0]);
					String name = list.get(i)[1];
					
					Student s = new Student(id, name);
					bin.insert(s);
				}
			}
		}catch(java.io.FileNotFoundException e){
			e.printStackTrace();//default throw
		}
		
		System.out.println("Student List:");
		int size = bin.size();
		for(int i = 0; i < size; i++){
			System.out.println(i+1 + ". { " + bin.deleteMin() + " }");
		}
	}
}