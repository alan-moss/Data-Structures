
/*
 *Alan Moss
 *CSCD 300
 *Program 2 
 *This program recursively finds the best days to buy and sell stocks to maximize profit
 *Input: txt file
 *Output: array of 3 integers which gives the day to buy, day to sell, and profit
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class BestTrading
{
   public static void main(String args[]) throws Exception
   {
      // Store data from command line
      String file = args[0];
      
      File fileEntry = new File(file);
      // Scanner for reading txt file to arraylist 
      // I first created an arraylist then converted to an array so that I could figure out the number of
      // integers in the text file without having to read the file twice in the scanner. I'm sure there is a better way to do this.
      try(Scanner sc = new Scanner(fileEntry))
      {
	      // Create arraylist
	      ArrayList<Integer> arraylist = new ArrayList<Integer>();
	      while(sc.hasNext())
	      {
	         if(sc.hasNextInt())
	         {
	            arraylist.add(sc.nextInt());
	         }
	      }
	      
	      // Convert ArrayList arraylist to array A of size arraylist.size()
	      int[] A = new int[arraylist.size()];
	      // Store n value
	      int n = A.length;
	      for(int i=0; i < n; i++)
	      {
	         A[i] = arraylist.get(i).intValue();
	      }
	      
	      int [] maxprofit = new int[3];
	      maxprofit = bestTrading(A, 0, A.length-1);
	      
	      // Print results
	      System.out.println("Buy on day " + (maxprofit[0] + 1));
	      System.out.println("Sell on day " + (maxprofit[1] + 1));
	      System.out.println("For a profit of " + maxprofit[2]);
      }
      catch(FileNotFoundException e)
      {
    	  e.printStackTrace();
      }
      
      
   }
   
   public static int[] bestTrading(int [] array, int low_index, int high_index) throws Exception
   {
	   // Return array
	   // [0] = buy 
	   // [1] = sell 
	   // [2] = profit
	   int [] return_array = new int[3];
	   
	   if(low_index > high_index)
	   {
		   throw new Exception();
	   }
	   
	   if(low_index == high_index)
	   {
		   //Assign values to array
		   return_array[0] = low_index;
		   return_array[1] = high_index;
		   return_array[2] = 0;
		   // Can return low or high, single element array
		   return return_array; // buy day = sell day = low = high; profit = 0;
	   }
	   
	   int mid = (low_index+high_index)/2;
	   
	   // Case 1: both values from first half of array
	   int [] leftarray = bestTrading(array, low_index, mid);
	   // Case 2: both values from second half of array
	   int [] rightarray = bestTrading(array, mid+1, high_index);
	   // Case 3: buy day from left array, sell day from right array
	   int [] across_array = new int[3];
	   across_array[0] = leftarray[0];
	   across_array[1] = rightarray[1];
	   //across_array[2] = array[across_array[1]]-array[across_array[0]];
	   across_array = bestTradingAcross(array, low_index, high_index);
	   
	   
	   if(leftarray[2] >= rightarray[2] && leftarray[2] >= across_array[2])
	   {
		   return leftarray;
	   }
	   
	   else if(rightarray[2] >= leftarray[2] && rightarray[2] >= across_array[2])
	   {
		   return rightarray;
	   }
	   
	   else
	   {
		   return across_array;
	   }
   }
   
   // Method for finding case 3, where low value is in left side of array and high value is in right side of array
   public static int[] bestTradingAcross(int [] p, int low, int high)
   {
	   int mid = (low+high)/2;
	   int buy = p[0];
	   int sell = p[mid];
	   int buyindex = 0;
	   int sellindex = mid;
	   
	   for(int i = 0; i < mid; i++)
	   {
		   int temp = p[i];
		   if(temp < buy)
		   {
			   buy = temp;
			   buyindex = i;
		   }
	   }
	   
	   for(int j = mid; j < high; j++)
	   {
		   int temp = p[j];
		   if(temp > sell)
		   {
			   sell = temp;
			   sellindex = j;
		   }
	   }
	   
	   int profit = sell - buy;
	   
	   int [] return_array = new int[3];
	   return_array[0] = buyindex;
	   return_array[1] = sellindex;
	   return_array[2] = profit;
	   
	   return return_array;
   }
}