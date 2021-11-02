/*
 *Alan Moss
 *CSCD 300
 *Program 6
 *This program takes as an input an ASCII text file whose each line is an integer value.
 *It stores these numbers into a doubly linked list and performs the quick sort algorithm.
 *Both the unsorted and sorted lists are printed to the screen.
 *Input inserted at tail side of linked list, then sorted.
*/

package prog6;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ListQuickSort 
{
	public static void main(String[] args) throws Exception
	{
		String file = args[0];
		File fileEntry = new File(file);
		
		try(Scanner sc = new Scanner(fileEntry))
		{
			//Create arraylist for integers from file
			ArrayList<Integer> arraylist = new ArrayList<Integer>();
			while(sc.hasNext())
			{
				if(sc.hasNextInt())
				{
					arraylist.add(sc.nextInt());
				}
				
				//Check for characters that are not integers
				else if(sc.hasNext() && !sc.hasNextInt())
				{
					System.out.println("Invalid input in file");
					return;
				}
			}
			
			//Check if array list is empty
			if(arraylist.isEmpty())
			{
				System.out.println("List is empty");
				return;
			}
			
			//Create doubly linked list
			DLinkedList list = new DLinkedList();
			
			//Fill doubly linked list from arraylist
			for(int i = 0; i < arraylist.size(); i++)
			{
				list.addLast(new DNode(arraylist.get(i), null, null));
			}
			
			//Print unsorted list
			System.out.println("Unsorted List: ");
			list.printList();
			System.out.println();
			
			//Sort list ignoring dummy nodes
			quickSort(list.getFirst(), list.getLast());
			
			//Print sorted list
			System.out.println("Sorted List: ");
			list.printList();
			
		}
		
		//Print stack trace in case of filenotfound exception
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	//Rather than taking a doubly linked list and two integers, take left and right node arguments
	static DNode partition(DNode leftNode, DNode rightNode)
	{
		//If partition same two nodes, can return either left or right
		if(leftNode == rightNode)
			return rightNode;
		//Pivot is number contained in right node
		int pivot = rightNode.getElement();
		
		//Assign i and index left node
		DNode i = leftNode;
		DNode index = leftNode;
		
		//Bounds
		while(leftNode != rightNode)
		{
			if(leftNode.getElement() <= pivot)
			{
				i = index;
				//Temp holds number contained in left node
				int temp = index.getElement();
				
				index.element = leftNode.getElement();
				leftNode.element = temp;
				//Only increment index if element less than or equal to pivot
				index = index.next;
			}
			//Increment left node to next
			leftNode = leftNode.next;
		}
		//Swap position of next index and pivot
		int temp = index.getElement();
		index.element = pivot;
		rightNode.element = temp;
		return i;
	}
	
	//Left and right node arguments
	//Use recursion to complete quickSort, update bounds each time
	static void quickSort(DNode leftNode, DNode rightNode)
	{
		//Invalid if arguments null or equal to each other or if 
		if(leftNode == rightNode || leftNode == null || rightNode == null || leftNode == rightNode.next)
		{
			return;
		}
		//First partition
		DNode pivot = partition(leftNode, rightNode);
		//First sort using left and pivot
		quickSort(leftNode, pivot);
		
		//If pivot has been placed at start after partition, move to next node
		if(pivot == leftNode && pivot != null)
		{
			quickSort(pivot.next, rightNode);
		}
		
		//If pivot not at beginning
		else if(pivot.next != null && pivot != null)
		{
			//Move forward two nodes
			quickSort(pivot.next.next, rightNode);
		}
	}
	
	//Method for swapping nodes in doubly linked list
	public void swap(DLinkedList A, int left, int right) 
	{
        if (left == right)
        {
        	return;
        }
        //Assign node values
        DNode left_prev = null;
        DNode left_current = A.getFirst(); 
        
        while (left_current != null && left_current.element != left) 
        { 
        	//Update left nodes, move forward
            left_prev = left_current; 
            left_current = left_current.next; 
        }
        //Assign right node values
        DNode right_prev = null;
        DNode right_current = A.getFirst(); 
        
        while (right_current != null && right_current.element != right) 
        { 
        	//Update right nodes, move forward
            right_prev = right_current; 
            right_current = right_current.next; 
        } 
        
        //Do nothing if same nodes
        if (left_current == null || right_current == null) 
            return; 
        
        //Assign current right node to current left node
        if (left_prev != null)
        {
        	left_prev.next = right_current;
        }
        
        //If previous null
        else
        {
        	A.head.next = right_current;
        }
        
        //Assign current left node to current right node
        if (right_prev != null)
        {
        	right_prev.next = left_current;
        }
        
        //If previous null
        else
        {
        	A.head.next = left_current;
        }
        
        //Reassign current nodes using temp node
        DNode temp = left_current.next; 
        left_current.next = right_current.next; 
        right_current.next = temp;
	}
	
	//Couldn't get this version to work properly, kept running indefinitely
	//Found it much easier to use left and right nodes for partition and quickSort
//	public static void QuickSort(DLinkedList A, int left, int right)
//	{
//		if(left >= right)
//			return;
//		
//		int pivot_index = partition(A, left, right);
//		QuickSort(A, left, pivot_index - 1);
//		QuickSort(A, pivot_index + 1, right);
//	}
	
//	public static int partition(DLinkedList A, int left, int right)
//	{
//		int pivot = A.getElement(right);
//		int index = left;
//		
//		for(int i = left; i < right; i++)
//		{
//			if(A.getElement(i) <= pivot)
//			{
//				//swap(A, index, i);
//				A.addBefore(getNode(A, i), getNode(A, index));
//				A.remove(getNode(A, i));
//				index++;
//			}
//		}
//		//swap(A, index, right)
//		//A.addBefore(getNode(), null);
//		return index;
//	}
	
	//Node class for doubly linked list
	public static class DNode
	{
		private int element;
		
		private DNode prev;
		private DNode next;
		
		public DNode(int element, DNode prev, DNode next)
		{
			this.element = element;
			this.prev = prev;
			this.next = next;
		}
		
		public int getElement() {return element;}
		public DNode getNext() {return next;}
		public DNode getPrev() {return prev;}
		public void setElement(int element) {this.element = element;}
		public void setNext(DNode next) {this.next = next;}
		public void setPrev(DNode prev) {this.prev = prev;}
	}

	//Doubly linked list class
	public static class DLinkedList
	{
		protected DNode head;
		protected DNode tail;
		protected long size;
		
		//Sets up doubly linked list with dummy nodes
		public DLinkedList()
		{
			//Initialize with number 0, null and null for prev and next nodes
			head = new DNode(0, null, null);
			tail = new DNode(0, null, null);
			head.setNext(tail);
			tail.setPrev(head);
			size = 0;
		}
		
//		//Swap two nodes in DLinkedList
//		public static void swap(DLinkedList A, int pos1, int pos2)
//		{
//			//Check if list is empty or pos1 and pos2 are equal
//			if(pos1 == pos2 || A.isEmpty())
//				return;
//			
//			A.addBefore(getNode(pos1), getNode(pos2));
//		}
		
		
		//Get element at specified index from doubly linked list A
		public int getElement(int index)
		{
			//Start at head node
			DNode current = head;
			int counter = 0;
			//Iterate through list until reach correct index
			//Return the element
			while(current != null)
			{
				if(counter == index)
					return current.element;
				counter++;
				current = current.next;
			}
			//Element does not exist in DLinkedList
			assert(false);
			return 0;
		}
		
		//Add node v after dummy head node
		public void addFirst(DNode v)
		{
			v.setPrev(head);
			v.setNext(head.getNext());
			head.getNext().setPrev(v);
			head.setNext(v);
			size++;
		}
		
		//Add node v before dummy tail node
		public void addLast(DNode v)
		{
			v.setPrev(tail.getPrev());
			v.setNext(tail);
			tail.getPrev().setNext(v);
			tail.setPrev(v);
			size++;
		}
		
		//Remove node right after dummy head
		public void removeFirst()
		{
			if(size == 0)
				return;
			
			DNode deleted = head.getNext();
			
			head.getNext().getNext().setPrev(head);;
			head.setNext(head.getNext().getNext());
			deleted.setPrev(null);
			deleted.setPrev(null);
			size--;
		}
		
		//Remove node right before dummy tail
		public void removeLast()
		{
			if(size == 0)
				return;
			
			DNode deleted = tail.getPrev();
			tail.getPrev().getPrev().setNext(tail);;
			tail.setPrev(tail.getPrev().getPrev());
			deleted.setPrev(null);
			deleted.setNext(null);
			size--;
		}
		
		//Insert node v right after node u
		public void addAfter(DNode u, DNode v)
		{
			if(u == null)
				return;
			if(u == tail)
				return;
			
			v.setPrev(u);
			v.setNext(u.getNext());
			u.getNext().setPrev(v);
			u.setNext(v);
			size++;
		}
		
		//Insert node v right before node u
		public void addBefore(DNode u, DNode v)
		{
			if(u == null)
				return;
			if(u == head)
				return;
			
			v.setPrev(u.getPrev());
			v.setNext(u);
			u.getPrev().setNext(v);
			u.setPrev(v);
			size++;
		}
		
		//Remove the node that is right after v
		public void removeAfter(DNode v)
		{
			if(size == 0)
				return;
			if(v == null)
				return;
			if(v == tail)
				return;
			if(v == tail.getPrev())
				return;
			
			DNode deleted = v.getNext();
			v.getNext().getNext().setPrev(v);
			v.setNext(v.getNext().getNext());
			deleted.setPrev(null);
			deleted.setNext(null);
			size--;
		}
		
		//Remove the node that is right before v
		public void removeBefore(DNode v)
		{
			if(size == 0)
				return;
			if(v == null)
				return;
			if(v == head)
				return;
			if(v == head.getNext())
				return;
			
			DNode deleted = v.getPrev();
			v.getPrev().getPrev().setNext(v);
			v.setPrev(v.getPrev().getPrev());
			deleted.setPrev(null);
			deleted.setNext(null);
			size--;
		}
		
		//Remove the node v
		public void remove(DNode v)
		{
			if(v == null || v == head || v == tail)
				return;
			
			v.getPrev().setNext(v.getNext());
			v.getNext().setPrev(v.getPrev());
			v.setPrev(null);
			v.setNext(null);
			size--;
		}
		
		//Return the reference of the first non-dummy node
		public DNode getFirst()
		{
			if(size == 0)
				return null;
			return head.getNext();
		}
		
		//Return the reference of the last non-dummy node
		public DNode getLast()
		{
			if(size == 0)
				return null;
			return tail.getPrev();
		}
		
		//Return the number of non-dummy nodes in the list
		public long size()
		{
			return size;
		}
		
		//Return whether list is empty
		public boolean isEmpty()
		{
			if(size == 0)
				return true;
			return false;
		}
		
		//Concatenation of all elements in the list
		public String toString()
		{
			StringBuilder all = new StringBuilder();
			for(DNode cur = head.getNext(); cur != tail; cur = cur.getNext())
				all.append(cur.getElement() + " ");
			return all.toString();
		}
		
		//Prints each node from doubly linked list using .next 
		//Does not print dummy head or dummy tail nodes
		public void printList()
		{
			DNode current = head.next;
			if(head == null || head.next == null)
			{
				System.out.println("No elements in list");
				return;
			}
			
			while(current != null && current.next != null)
			{
				System.out.print(current.element + " ");
				current = current.next;
			}
		}
	}
}