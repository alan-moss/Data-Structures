package prog7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class HashChain 
{

	public static void main(String[] args) 
	{
		//Bring in file from command line
		String file = args[0];
		File fileEntry = new File(file);
		String line = null;
		BufferedReader reader = null;
		
		//Using buffered reader
		try
		{
			reader = new BufferedReader(new FileReader(fileEntry));
			//Create hashchain, with hash table size 5
			hashChain hashMap = new hashChain(5);
			
			//Splits line when comma is reached
			//First part is the key, second part is the Name
			while((line = reader.readLine()) != null)
			{
				String[] parts = line.split(",");
				
				Integer id = Integer.parseInt(parts[0].trim());
				String name = parts[1].trim();
				
				//Create node and add to hashmap
				Node n = new Node(id, name, null);
				hashMap.put(n);
			}
			
			//Menu
			Scanner sc = new Scanner(System.in);
			boolean loop = true;
			
			int input;
			
			while(true)
			{
				System.out.println("Choose one of the following options.");
				System.out.println("====================================");
				System.out.println("1) insert/update a new student record");
				System.out.println("2) delete a student record");
				System.out.println("3) search for a student record");
				System.out.println("4) print all the student records");
				System.out.println("5) quit");
				System.out.println("Your choice: ");
				
				input = sc.nextInt();
				
				switch(input)
				{
					case 1:
						System.out.println("Input the student id: ");
						int id = sc.nextInt();
						System.out.println("Input the student name: ");
						String name = sc.next();
						
						Node n = new Node(id, name, null);
						if(hashMap.put(n) == null)
						{
							hashMap.put(n);
							System.out.println("The new student has been added successfully.");
							break;
						}
						
						else
						{
							System.out.println("The student was exiting and the record has been updated.");
							break;
						}
					
					case 2:
						System.out.println("Input the student id: ");
						int key = sc.nextInt();
						
						//Student not found
						if(hashMap.remove(key) == null)
						{
							hashMap.remove(key);
							System.out.println("No such student.");
							break;
						}
						
						//Student id does not exist
						else
						{
							hashMap.remove(key);
							System.out.println("The student has been deleted successfully.");
							break;
						}
						
					case 3:
						System.out.println("Input the student id: ");
						int search = sc.nextInt();
						
						//student not found
						if(hashMap.get(search) == null)
						{
							System.out.println("No such student.");
							break;
						}
						
						//Student record found, display student
						else
						{
							String element = hashMap.get(search);
							System.out.println("Student id: " + search + "\nStudent name: " + element);
							break;
						}
						
					case 4:
						System.out.println("All student records: ");
						System.out.println("=====================");
						hashMap.printList();
						break;
						
					case 5:
						System.out.println("Goodbye.");
						System.exit(0);
						break;
						
					default:
						System.out.println("Invalid menu option. Try again.");
						break;
				}
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//Guarantee reader is always closed
		finally
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				}
				catch(Exception e){};
			}
		}
		
	}
	



	//Map interface, only contains method declarations
	public static interface map
	{
		//Returns number of elements in hash table
		int size();
		
		//Returns element whose id=key, if it exists
		//If does not exist, return null
		String get(int key);
		
		//If element.id exists, update that element's value to be e.v and return
		//that element's old value
		//Otherwise, insert e and return null
		String put(Node e);
		
		//Delete and return the element whose id=key if it exists
		//If does not exist, return null
		String remove(int key);
	}
	
	//hashchain implements map interface
	//Defines methods
	static class hashChain implements map
	{
		int size;
		SLinkedList[] HashTable;
		
		hashChain(int table_size)
		{
			size = 0;
			HashTable = new SLinkedList[table_size];
			for(int i = 0; i < table_size; i++)
				HashTable[i] = new SLinkedList();
		}

		//Print method for hash table
	    public void printList()
	    {
	    	for(int i = 0; i < HashTable.length; i++)
			{
	    		SLinkedList L = HashTable[i];
				Node temp = L.head;
		    	
		    	while(temp != null)
		    	{
		    		System.out.print("(" + temp.getId() + ", " + temp.getElement() + ") ");
		    		temp = temp.next;
		    	}
		    	System.out.println();
			}
	    }
		
		//Hash function
		int hash(int key)
		{
			return (7 * key + 29) % 5;
		}
		
		//Returns size, size is updated in both put and remove methods
		public int size() 
		{
			return size;
		}

		//Returns element whose id=key, if it exists
		//If does not exist, return null
		public String get(int key) 
		{
			Node temp = HashTable[hash(key)].search(key);
			
			if(temp == null)
			{
				return null;
			}
			
			else
			{
				return temp.element;
			}
		}

		//Either inserts or updates depending on if value can be found
		public String put(Node e) 
		{
			Node temp = HashTable[hash(e.id)].search(e.id);
			
			//Insert operation
			if(temp == null)
			{
				HashTable[hash(e.id)].addFirst(e);
				size++;
				return null;
			}
			
			//Update operation
			else
			{
				String old_value = temp.getElement();
				temp.setElement(e.getElement());
				return old_value;
			}
		}

		//Delete and return the element whose id=key if it exists
		//If does not exist, return null
		public String remove(int key) 
		{
			Node temp = HashTable[hash(key)].delete(key);
			//Was not found
			if(temp == null)
			{
				return null;
			}
			
			//Was found, returns element of node that was deleted
			else
			{
				size--;
				return temp.element;
			}
		}
	}
	
	
	//Node class for singly linked list
	static public class Node{
		private int id;
	    private String element; 
	    
	    private Node next;  //the link referencing to the next node in the link list.
	    
	    public Node(int id, String element, Node next){
	    this.id = id;
		this.element = element; 
		this.next = next; 
	    }
	    
	    public int getId() { return id; }
	    public String getElement() { return element; }
	    public Node getNext() { return next; }
	    public void setElement(String element) { this.element = element; }
	    public void setNext(Node next) { this.next = next; }
	}
	
	//Singly linked list class
	static public class SLinkedList{
	    protected Node head; 
	    protected long size; 
	    
	    public SLinkedList(){
		head = null; 
		size = 0;
	    }
	     
	    
	    //Deletes first instance of given key from linked list
	    public Node delete(int key)
	    {
	    	//Keep track of head and previous, maintain two pointers
	    	//Do not need "next node" for singly linked list
	    	Node temp = head;
	    	Node previous = null;
	    	
	    	//Found match, first item in linked list matches id
	    	if(temp != null && temp.id == key)
	    	{
	    		//Change head node
	    		head = temp.next;
	    		return temp;
	    	}
	    	
	    	//Continue until id and key values match, or move on if null
	    	while(temp != null && temp.id != key)
	    	{
	    		previous = temp;
	    		temp = temp.next;
	    	}
	    	
	    	//Returns if no instance of given key was found in list
	    	if(temp == null)
	    	{
	    		return null;
	    	}
	    	
	    	//Remove node from list and return it
	    	previous.next = temp.next;
	    	return temp;
	    }
	    
	    //Searches through singly linked list for given key
	    //Returns the node if key found, otherwise returns null
	    public Node search(int key) 
	    {
	    	Node current = head;
	    	while(current != null)
	    	{
	    		if(current.id == key)
	    		{
	    			return current;
	    		}
	    		current = current.next;
	    	}
			return null;
		}

		/* add node v at the beginning of the list */
	    public void addFirst(Node v){
		if(v == null)
		    return; 

		v.setNext(head);
		head = v; 
		size ++;
	    }
	    
	    /* add node v at the end of the list */
	    public void addLast(Node v){
		if(size == 0)
		    addFirst(v);
		else{
		    Node cur; 
		    for(cur = head; cur.getNext() != null; cur = cur.getNext())
			;
		    v.setNext(null);
		    cur.setNext(v); 
		    size ++; 
		}
	    }

	    /* remove the first node of the list */
	    public void removeFirst(){
		if(size == 0)
		    return; 
		
		Node old_head = head; 
		head = head.getNext(); 
		size --; 
		old_head.setNext(null);
		
		//If using C or C++, the memoery for the deleted node needs to be freed. 
	    }

	    /* remove the last node of the list */
	    public void removeLast(){
		if(size == 0)
		    return; 
		else if(size == 1)
		    removeFirst();
		else
		{
		    Node cur; 
		    for(cur = head; cur.getNext().getNext() != null; cur = cur.getNext())
			;
		    cur.setNext(null);
		    size --;
		}
		//If using C or C++, the memoery for deleted node needs to be freed. 
	    }

	    /* Insert node v right after node u*/
	    public void addAfter(Node u, Node v){
		if(u == null)
		    return; 
		
		v.setNext(u.getNext()); 
		u.setNext(v);
		size ++;
	    }

	    /* Insert node v right before node u*/
	    public void addBefore(Node u, Node v){
		if(u == null)
		    return;
		else if(u == head)
		    addFirst(v);
		else{
		    Node cur; 
		    for(cur = head; cur.getNext() != u; cur = cur.getNext())
			;
		    v.setNext(u);
		    cur.setNext(v);
		    size ++;
		}
	    }
	    
	    /* remove the node that is right after v */
	    public void removeAfter(Node v){
		if(v == null)
		    return;

		if(v.getNext() == null)
		    return; 
		
		Node deleted = v.getNext(); 
		v.setNext(v.getNext().getNext());
		deleted.setNext(null); 
		size --;
	    }

	    /* remove the node that is right before v */
	    public void removeBefore(Node v){
		if(v == null)
		    return;
		if(v == head)
		    return; 

		if(head.getNext() == v){
		    Node old_head = head; 
		    head = v;
		    old_head.setNext(null);
		}
		else{
		    Node cur; 
		    for(cur = head; cur.getNext().getNext() != v; cur = cur.getNext())
			;
		    Node deleted = cur.getNext(); 
		    cur.setNext(v);
		    deleted.setNext(null);
		}
		size --; 
	    }
	    
	    /* The concatenation of all the elements in the list */
	    public String toString(){
		StringBuilder all = new StringBuilder();
		for(Node cur = head; cur != null; cur = cur.getNext())
		    all.append(cur.getElement() + "   ");
		return all.toString();
	    }
	}
}