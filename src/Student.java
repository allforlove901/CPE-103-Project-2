/*
 * Brett Nelson bnelso26@calpoly.edu
 * 2/5/16
 * Project 2
 */

//class to implement student object
public class Student implements Comparable<Student>{
	
	private String lastName; //holds last name of student object
	private long ID; //holds ID number of student
	
	//constructor with 2 parameters
	public Student(long id, String name){
		lastName = name;
		ID = id;
	}
	
	//Comparable's required method to compare student ID numbers
	public int compareTo(Student s){
		if(s.ID > this.ID){
			return -1;
		}else if(s.ID == this.ID){
			return 0;
		}else{
			return 1;
		}
	}
	
	//Student class's toString method
	public String toString(){
		return "id: " + ID + ", name: " + lastName; 
	}
}