/*
 * Brett Nelson bnelso26@calpoly.edu
 * 2/5/15
 * Project 2
 */

//class to implement BinHeap objects
//includes generic class T which extends Comparable
public class BinHeap<T extends Comparable<? super T>> {

	//nested MyException class to implement custom exceptions
	public static class MyException extends RuntimeException{
		
		//no argument constructor
		public MyException(){
			super();
		}
		
		//MyException with string argument
		public MyException(String s){
			super(s);
		}
	}//close class
	
	private T[] arr; //array of generic variables T
	private int size; //size of heap
	
	//empty constructor with array size set to 100
	public BinHeap(){
		arr = (T[]) new Comparable[100];
		size = 0;
	}
	
	//constructor that takes in array size as integer
	public BinHeap(int x){
		arr = (T[]) new Comparable[x];
		size = 0;
	}
	
	//method to insert new element into heap
	public void insert(T element){
		if(size == arr.length){ //checks if the array is full
			T[] store = arr.clone();
			arr = (T[]) new Comparable[size*2];
			
			for(int i = 0; i<size; i++){
				arr[i] = store[i];
			}
		}
			
		arr[size] = element; //inserts element to next open spot
		size++;
		int parentIndex = (int)Math.floor((size-2)/2);
		int childIndex = size-1;
		
		while(parentIndex != childIndex){ //checking to see if at root
			if(element.compareTo(arr[parentIndex]) < 0){ //is new element > it's parent?
				arr[childIndex] = arr[parentIndex];
				arr[parentIndex] = element;
				childIndex = parentIndex;
				parentIndex = (int)Math.floor((parentIndex-1)/2);
			}else{
				break;
			}//close else
		}//close while
	}//close method
	
	//method to delete lowest value in the binary heap
	public T deleteMin(){
		T output = null;
		if(isEmpty()){ //is it empty?
			throw new MyException("Invalid: the heap is empty");
		}else{
			output = arr[0];
			size--;
			arr[0] = arr[size];
			arr[size] = null;
			int parentIndex = 0;
			while(true){
				//is the left child outisde array size or null?
				if(arr.length < parentIndex*2+1 || arr[parentIndex*2+1] == null){
					break;
				//is the parent > left child?
				}else if(arr[parentIndex].compareTo(arr[parentIndex*2+1]) > 0){
					//is right child null or > left child?
					if(arr[parentIndex*2+2] == null || arr[parentIndex*2+1].compareTo(arr[parentIndex*2+2]) < 0){
						T store = arr[parentIndex];
						arr[parentIndex] = arr[parentIndex*2+1];
						arr[parentIndex*2+1] = store;
						parentIndex = parentIndex*2+1;
					}else{
						T store = arr[parentIndex];
						arr[parentIndex] = arr[parentIndex*2+2];
						arr[parentIndex*2+2] = store;
						parentIndex = parentIndex*2+2;
					}
				//is right child null?
				}else if(arr[parentIndex*2+2] == null){
					break;
				//is parent > right child?
				}else if(arr[parentIndex].compareTo(arr[parentIndex*2+2]) > 0){
					T store = arr[parentIndex];
					arr[parentIndex] = arr[parentIndex*2+2];
					arr[parentIndex*2+2] = store;
					parentIndex = parentIndex*2+2;
				}else{
					break;
				}//close else
			}//close while
		}//close else
		return output;
	}//close method
	
	//method to check if heap is empty
	public boolean isEmpty(){
		return arr[0] == null;
	}
	
	//method returns size of heap
	public int size(){
		return size;
	}
	
	//method to format toString of BinHeap object
	public String toString(){
		String output = "";
		for(int i = 0; i<size; i++){
			output += arr[i] + " ";
		}
		return output;
	}
	
	//private support method to determine if a string is an integer
	private boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}