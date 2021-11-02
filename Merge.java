/*
 * Alan Moss
 * CSCD 300
 * Prog 5
 * This program sorts and merges a number of txt files by using both
 * array based and linked list based fifo queues as well as dynamically
 * allocated arrays.
 * 
 * Input: k text files. Each contains a sequence of integers, one per
 * 		  line in ascending order
 * Output: The print of all the integers from the k input files in 
 * 		   ascending order
 */

package prog5;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Merge 
{

	public static void main(String[] args) throws Exception
	{
		//k is number of files, but also number of linked list and
		//array based queues
		int k = args.length;
		//Create an arraylist of files of size k
		ArrayList<File> fileArray = new ArrayList<File>(k); 
		//Create an arraylist of filereaders size k
		ArrayList<FileReader> FileReaders = new ArrayList<FileReader>(k);
		//Create an arraylist of araylists of numbers
		ArrayList<ArrayList<Float>> fileNumbers = new ArrayList<ArrayList<Float>>(k);
		
		//Command line args
		for(String fileName : args)
		{
			//Add files to file arraylist
			File file = new File(fileName);
			fileArray.add(file);
			//Create new file reader, add to arraylist of filereaders
			FileReader sc = new FileReader(file);
			FileReaders.add(sc);
		}
		
		//Create an arraylist (of size k) of fifo queues
		ArrayList<ArrayQueue> fifoQueues = new ArrayList<ArrayQueue>(k);
		for(int i = 0; i < k; i++)
		{
			//Fifo queues size 10
			ArrayQueue fifo = new ArrayQueue(10);
			//Add fifo queues to arraylist
			fifoQueues.add(fifo);
		}
		
		//Load top 10 numbers into array based fifo queues using enqueue
		int done_List = 0;
		
		//Create final linked list queue
		//To be added to after comparing
		ListQueue final_list = new ListQueue();
		
		//
		for(int index = 0; index < k; index++ )
		{
			//Create filereader and buffered reader and assign from arraylist
			FileReader sc = FileReaders.get(index);
			BufferedReader br = new BufferedReader(sc);
			ArrayList<Float> numberList = new ArrayList<Float>();
			String line = br.readLine();
			//add to number list
			while (line != null)
			{
				float num = Float.parseFloat(line);
				numberList.add(num);
				line = br.readLine();
			}
			fileNumbers.add(index, numberList);
		}
		
		for(int i=0; i<k; i++)
		{
			ArrayQueue q = fifoQueues.get(i);
			ArrayList<Float> list = fileNumbers.get(i);
			int size_to_remove = 0;
			for(int j=0;j<10;j++)
			{
				if(j >= list.size())
				{
					break;
				}
				q.enqueue(list.get(j));
				size_to_remove++;
				fifoQueues.set(i, q);
			}
			// remove numbers
			for(int j = size_to_remove-1; j >= 0; j--)
			{
				fileNumbers.get(i).remove(j);
			}
		}
		
		//Index of lowest float value
		int min_index = 0;
		
		while(done_List < k)
		{
			//Edge case handled
			if(fifoQueues.get(min_index).front() == null)
			{
				break;
			}
			//Assign head node of minimum fifo queue to min
			float min = fifoQueues.get(min_index).front();
			
				//Compare against other head (front) values
				for(int i = 0; i < k; i++)
				{
					//Edge case handled
					if(fifoQueues.get(i).front() == null)
					{
						break;
					}
					
					float next = fifoQueues.get(i).front();
					if(next < min)
					{
						min = next;
						min_index = i;
					}
				}
				
				//Add min value to final list using min index value
				final_list.enqueue(fifoQueues.get(min_index).dequeue());
				
				//Check if reached end
				if(fileNumbers.get(min_index).isEmpty())
				{
					done_List++;
				}
				
				//num 
				else
				{
					float num = fileNumbers.get(min_index).get(0);
					fileNumbers.get(min_index).remove(0);
					fifoQueues.get(min_index).enqueue(num);
				}
		}
		//total elements left
		int total_elements = 0;
		//
		for(int i = 0; i < k; i++)
		{
			//Find total number of elements
			total_elements += fifoQueues.get(i).size();
		}
		//Counter
		int starting_qu = 0;
		while(total_elements > 0)
		{
			//Check if reached end
			if (starting_qu == k)
			{
				break;
			}
			
			//If elements still in queue, assign front to min
			else if(fifoQueues.get(starting_qu).front() != null)
			{
				float min = fifoQueues.get(starting_qu).front();
				//Update min index
				min_index = starting_qu;
				for(int i = starting_qu; i < k; i++)
				{	
					//Edge case handled
					if(fifoQueues.get(i).front() == null)
					{
						break;
					}
					
					//Check head against other queue heads
					float next = fifoQueues.get(i).front();
					
					if(next < min)
					{
						min = next;
						min_index = i;
					}
				}
				
				//Add min value to final list using min index value
				final_list.enqueue(fifoQueues.get(min_index).dequeue());
				total_elements--;
			}
			//Increment counter
			else
				starting_qu++;
			
		}
		
		//Print final list
		float print_value = 0;
		while(final_list.size() > 0) 
		{
			print_value = final_list.dequeue();
			System.out.println(print_value);
		}
	}
	
	//Interface for fifo queue
	//Works for both array based and linked list based queues
	public interface Queue
	{
		public int size();
		public Float front();
		public void enqueue(Float item);
		public Float dequeue();
	}
	
	//Array based fifo queue implementation
	public static class ArrayQueue implements Queue
	{
		protected int capacity;
		public static final int CAPACITY = 1000;
		protected Float[] Q;
		protected int head;
		protected int tail;
		protected int size;
		
		public ArrayQueue()
		{
			capacity = CAPACITY;
			Q = new Float[capacity];
			head = -1;
			tail = -1;
			size = 0;
		}
		
		public ArrayQueue(int capacity)
		{
			if(capacity <= 0)
					return;
			this.capacity = capacity;
			Q = new Float[capacity];
			head = -1;
			tail = -1;
			size = 0;
		}
		
		public int size()
		{
			return size;
		}
		
		public Float front()
		{
			if(size == 0)
				return null;
			return Q[head];
		}
		
		public void enqueue(Float item)
		{
			if(size == capacity)
				return;
			if(size == 0)
			{
				Q[0] = item;
				head = 0;
				tail = 0;
			}
			else
			{
				tail = (tail + 1) % capacity;
				Q[tail] = item;
			}
			size++;
		}
		
		public Float dequeue()
		{
			if(size == 0)
				return null;
			Float ret = Q[head];
			Q[head] = null;
			
			if(size == 1)
			{
				head = -1;
				tail = -1;
			}
			else
				head = (head + 1) % capacity;
			
			size--;
			return ret;
		}
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
	
	//Singly linked list based implementation of fifo queue
	public static class ListQueue implements Queue
	{
		protected Node head;
		protected Node tail;
		protected int size;
		
		public ListQueue()
		{
			head = null;
			tail = null;
			size = 0;
		}
		
		public int size()
		{
			return size;
		}
		
		public Float front()
		{
			if(size == 0)
				return null;
			return head.getElement();
		}
		
		public void enqueue(Float item)
		{
			if(item == null)
				return;
			
			Node new_node = new Node(item, null);
			
			if(size == 0)
			{
				head = new_node;
				tail = new_node;
			}
			
			else
			{
				tail.setNext(new_node);
				tail = new_node;
			}
			size++;
		}
		
		public Float dequeue()
		{
			if(size == 0)
				return null;
			
			Float ret = head.getElement();
			
			if(size == 1)
			{
				head = null;
				tail = null;
			}
			
			else
			{
				Node old_head = head;
				head = head.getNext();
				old_head.setNext(null);
			}
			
			size--;
			return ret;
		}
	}
}