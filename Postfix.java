/*
 * Alan Moss
 * CSCD 300
 * Prog 4
 * This program, when given a postfix expression, calculates the result
 * of the expression using a singly linked list implementation of a stack
 * data structure.
 * 
 * INPUT: txt file
 * OUTPUT: value of postfix expression
 */

package prog4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Postfix {
	
	//Main function
	public static void main(String[] args) throws Exception
	{
		String file = args[0]; 
	    File fileEntry = new File(file);
	    
	    try(Scanner sc = new Scanner(fileEntry))
	    {
	    	Stack s = new ListStack();
	    	//Handles * or x for multiplication
	    	String mult = "*";
	    	String mult2 = "x";
	    	
	    	String div = "/";
	    	String plus = "+";
	    	String minus = "-";
	    	
	    	while(sc.hasNextLine())
	    	{
	    		String str = sc.nextLine();
	    		float floatVal;
	    		
	    		//Check against multiplication operator
	    		if(str.equals(mult) || str.equals(mult2))
	    		{
	    			if(s.size() >= 2)
	    			{
		    			float val1 = s.pop();
			    		float val2 = s.pop();
		    			s.push(val2 * val1);
	    			}
	    			
	    			else
	    			{
	    				System.out.println("Invalid order of numbers/operations");
	    				return;
	    			}
	    		}
	    		
	    		//Check against division operator
	    		else if(str.equals(div))
	    		{
	    			if(s.size() >= 2)
	    			{
		    			float val1 = s.pop();
			    		float val2 = s.pop();
		    			s.push(val2 / val1);
	    			}
	    			
	    			else
	    			{
	    				System.out.println("Invalid order of numbers/operations");
	    				return;
	    			}
	    		}
	    		
	    		//Check against addition operator
	    		else if(str.equals(plus))
	    		{
	    			if(s.size() >= 2)
	    			{
		    			float val1 = s.pop();
			    		float val2 = s.pop();
		    			s.push(val2 + val1);
	    			}
	    			
	    			else
	    			{
	    				System.out.println("Invalid order of numbers/operations");
	    				return;
	    			}
	    		}
	    		
	    		//Check against subtraction operator
	    		else if(str.equals(minus))
	    		{
	    			if(s.size() >= 2)
	    			{
		    			float val1 = s.pop();
			    		float val2 = s.pop();
		    			s.push(val2 - val1);
	    			}
	    			
	    			else
	    			{
	    				System.out.println("Invalid input");
	    				return;
	    			}
	    		}
	    		
	    		//If not operator, add to stack
	    		else
	    		{
	    			try
	    			{
	    				floatVal = Float.parseFloat(str);
	    				s.push(floatVal);
	    			}
	    			
	    			catch (NumberFormatException nfe) 
	    			{
	    				System.out.println("File must only contain numbers or operators (+, -, *, /) on separate lines");
	    				return;
	    			}
	    		}
	    	}
	    	
	    	//Print final value
	    	PrintStack(s);
	    	
	    }
	    //Print filenotfound
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
	    
	}
	
	
	//Stack interface
	public static interface Stack
	{
		public int size(); //Return number of items in stack
		public Float top(); //Return top item
		public void push(Float item); //Push new item
		public Float pop(); //Return and remove the top
	}
	
	//Node class for singly linked list
	public static class Node
	{
		private Float element;
		private Node next; //References the next node in linked list
		
		//Constructor takes string and Node
		public Node(Float element, Node next)
		{
			this.element = element;
			this.next = next;
		}
		
		public Float getElement() {return element;}
		public Node getNext() {return next;}
		public void setElement(Float element) {this.element = element;}
		public void setNext(Node next) {this.next = next;}
	}
	
	public static class ListStack implements Stack
	{
		protected int size;
		protected Node top;
		
		//No-arg constructor sets size to 0 and top item to null
		public ListStack()
		{
			size = 0;
			top = null;
		}
		
		//Returns number of items in stack
		public int size()
		{
			return size;
		}
		
		//Returns top item in stack
		public Float top()
		{
			//Check if list is empty
			if(size == 0)
					return null;
			return top.getElement();
		}
		
		//Adds item to top of stack, sets next node, updates size
		public void push(Float item)
		{
			//Check item has value
			if(item == null)
				return;
			//Create new node if item has value, set next node to top
			Node new_node = new Node(item, top);
			//Set top to new node and update size
			top = new_node;
			size++;
		}
		
		//Returns item from top of stack, removes it from stack, updates size
		public Float pop()
		{
			//Check if there are items in stack
			if(size == 0)
				return null;
			
			//Assign current top to old top
			Node old_top = top;
			//Update top to second to last item in stack
			top = top.getNext();
			//Reduce top size by one
			size--;
			//Delete link from old top to top
			old_top.setNext(null);
			//Return content of old top node
			return old_top.getElement();
		}
	}
	
	//This method prints the top item in the stack
	//To be called after all calculations are completed
	//Handles if stack size zero or greater than one
	public static void PrintStack(Stack s)
	{
		//Check stack size
    	if(s.size() == 0)
    	{
    		System.out.println("Empty stack");
    		return;
    	}
    	
    	//If stack size greater than one, not enough operations, print error message
    	if(s.size() > 1)
    	{
    		System.out.println("Invalid post-fix expression passed.");
    		return;
    	}
    	
    	//Pop value and print
    	float f = s.pop();
    	System.out.println("Value of expression is: " + f);
    	
    	//Put value back into stack
    	s.push(f);
	}
}
