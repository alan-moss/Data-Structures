/*
 *Alan Moss
 *CSCD 300
 *Program 3
 *This program constructs a circular doubly linked list from a txt file and performs round robin
 *processes on each node. Each cpu process is served for a predetermined amount of time.
 *Input: txt file containing a list of <process id, process's cpu time> tuples, and a 
 *       positive integer representing the length of each CPU service
 *Output: sequence of process ids that are in ascending order of their termination time, separated
 *          by the comma symbol
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class RoundRobin
{
   public static void main(String args[]) throws Exception
   {
      //User input
      String file = args[0];
      String cpuTime = args[1];
      //File handling
      File fileEntry = new File(file);
      String line = null;
      BufferedReader reader = null;

      try
      {
         reader = new BufferedReader(new FileReader(fileEntry));
	      // Create circular doubly linkedlist
	      CDLinkedList list = new CDLinkedList();
         int counter = 0;
         //Spliat line at comma
	      while((line = reader.readLine()) != null)
	      {
	         String[] parts = line.split(",");
            //First number is PID, second it CPU time required
            Integer id = Integer.parseInt(parts[0].trim());
            Integer totalTime = Integer.parseInt(parts[1].trim());

            //Add node to linkedlist
            DNode n = new DNode(id, totalTime);
            //Add first node in list
            if(counter < 1)
            {
               list.addFirst(n);
               counter++;
               //Reiterate while loop
               continue;
            }
            //After first node has been added, use addsorted method
            list.addSorted(list.getCursor(), n);
	      }

         //Do round robin cycles
         int cpuTIME = Integer.parseInt(cpuTime);
         ArrayList<Integer> arraylist = new ArrayList<Integer>();
         while(list.size() > 0)
         {
            DNode temp2 = list.getCursor();
            //DNode temp3 = temp2;
            for(int i = 0; i < list.size(); i++)
            {
               //Subtract given value from cputime
               temp2.setTime(temp2.time - cpuTIME);
               //Add id to arraylist if time is zero or less than 0
               if(temp2.time <= 0)
               {
                  arraylist.add(temp2.getID());
                  list.advance();
                  //Remove node from linked list
                  list.remove(temp2);
                  //Updtate temp
                  temp2 = list.getCursor();
                  continue;
               }
               list.advance();
               temp2 = list.getCursor();
            }
         }

         //Print list of IDs 
         for(int id : arraylist)
         {
            System.out.print(id + " ");
         }
	      
      }
      catch(FileNotFoundException e)
      {
    	  e.printStackTrace();
      }
      //Close reader
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
}


/*
* The node class for doubly linked list
*/
class DNode
{
   private int id;
   public int time;
   
   private DNode prev; //references previous node in list
   private DNode next; //references next node in the list
   
   //Constructor
   public DNode(int id, int time, DNode prev, DNode next)
   {
      this.id = id;
      this.time = time;
      this.prev = prev;
      this.next = next;
   }
   
   public DNode(int id, int time)
   {
      this.id = id;
      this.time = time;
      this.prev = null;
      this.next = null;
   }
   //Get methods
   public int getID() {return id;}
   public int getTime() {return time;}
   public DNode getNext() {return next;}
   public DNode getPrev() {return prev;}
   //Set methods
   public void setID(int id) {this.id = id;}
   public void setTime(int time) {this.time = time;}
   public void setNext(DNode next) {this.next = next;}
   public void setPrev(DNode prev) {this.prev = prev;}
}


/*
* The class for circular doubly linked list
* This class uses DNode class listed above
*/
class CDLinkedList
{
   protected DNode cursor;
   protected long size;
   
   //Constructor creates cursor node and sets size to 0
   public CDLinkedList()
   {
      cursor = null;
      //One node points to itself twice
      //cursor.setNext(cursor);
      //cursor.setPrev(cursor);
      size = 0;
   }
   
   //Move cursor to reference of next node in list
   public void advance()
   {
      if(size > 1)
         cursor = cursor.getNext();
   }
   
   //Move cursor to reference of previous node in list
   public void decrement()
   {
      if(size > 1)
         cursor = cursor.getPrev();
   }
   
   public void addFirst(DNode u)
   {
      this.cursor = u;
      cursor.setNext(u);
      cursor.setPrev(u);
      
      size++;
   }
   
   //Insert node v right after node u
   public void addAfter(DNode u, DNode v)
   {
      if(u == null)
         return;
      
      //Node u exists and is not the dummy tail
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
      
      //Node u exists and is not the dummy head
      v.setPrev(u.getPrev());
      v.setNext(u);
      u.getPrev().setNext(v);
      u.setPrev(v);
      size++;
   }
   
   //Removes the node that is right after v
   public void removeAfter(DNode v)
   {
      if(size == 0)
         return;
      if(v == null)
         return;
      
      DNode deleted = v.getNext();
      v.getNext().getNext().setPrev(v);
      v.setNext(v.getNext().getNext());
      deleted.setPrev(null);
      deleted.setNext(null);
      size--;
   }
   
   //Removes the node that is right before v
   public void removeBefore(DNode v)
   {
      if(size == 0)
         return;
      if(v == null)
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
      if(v == null)
         return;
         
      v.getPrev().setNext(v.getNext());
      v.getNext().setPrev(v.getPrev());
      v.setPrev(null);
      v.setNext(null);
      size--;
   }
   
   // add node to list, index is cursor
   public void addSorted(DNode index, DNode u)
   {   
      DNode prev = index.getPrev();
      DNode next = index.getNext();
      
      if(prev.getID() == next.getID() && prev.getID() == index.getID())
      {
         if(u.getID() > prev.getID())
         {
            addAfter(index, u);
            return;
         }
         else 
         {
            addBefore(index, u);
            return;
         }
      }
      
      if(index.getID() > u.getID())
      {
          addBefore(index, u);
          return;
      }
      
      while(index.getID() < u.getID() )
      {
         if(index.getID() > u.getID())
         {
            addBefore(index, u);
            return;
         }
         
         if(next.getID() > u.getID())
         {
            addAfter(index, u);
            return;
         }
         
         if(index.getID() > next.getID())
         {
            break;
         }
         
         index = next;
         next = next.getNext();
         prev = index;

      }
       addAfter(index, u);
       return;
   }
   
   
   //Return the cursor
   public DNode getCursor()
   {
      return cursor;
   }
   
   //Return the number ofnodes in the list
   public long size()
   {
      return size;
   }
   
   //Return whether the list is empty or not
   public boolean isEmpty()
   {
      if(size == 0)
         return true;
      return false;
   }
}