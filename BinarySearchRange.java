/*
Alan Moss
CSCD 300
Program 1

This program is a modified binary search that searches a sorted array A[0...n-1] for all member elements whose values
fall into a given value range [x...y]. This program takes three inputs: a text file whose every line is a number
in sorted order, and two integer values representing x and y.
*/

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class BinarySearchRange
{
   public static void main(String args[]) throws FileNotFoundException
   {
      // Store data from command line
      String file = args[0];
      // Convert strings to ints
      int x = Integer.parseInt(args[1]);
      int y = Integer.parseInt(args[2]);
      // Was getting filenotfoundexception
      // user.dir directory where java is run from, get absolute path of txt file
      String path = System.getProperty("user.dir") + "\\test.txt";
      File fileEntry = new File(path);
      // Scanner for reading txt file to arraylist 
      // I first created an arraylist then converted to an array so that I could figure out the number of
      // integers in the text file without having to read the file twice in the scanner. I'm sure there is a better way to do this.
      Scanner sc = new Scanner(fileEntry);
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
      
      // Print null
      if(A.length == 0 || x > y)
      {
         System.out.println("Null");
      }
      
      // Print null
      else if(y < A[0])
      {
         System.out.println("Null");
      }
      
      // Print null
      else if(x > A[n-1])
      {
         System.out.println("Null");
      }
      
      // Print whole array
      else if(x <= A[0] && y >= A[n-1])
      {
         System.out.println("A[" + x + ", " + y + "]");
      }
      
      // Call binary search range
      else
      {
         binaryRangeSearch(A, x, y);
      }
   }
   
   // Modifies the binary search to search for index of leftmost array element(>=x) called index s.
   // Modifies the binary search to search for index of rightmost array element(<=y) called index t.
   // If s <= t, answer is A[s...t], otherwise answer is null
   
   
   // we have an array, we have lower bound x, find x 
   //[1,2,3,5,6,7,8]
   // y = 4
   // output = index(5)
   
   public static void binaryRangeSearch(int array[], int x, int y)
   {
      // Variables for range
      int s = 0, t = 0;
      int low = 0, high = array.length-1, mid = 0;
      // While loop for finding low bounds
      while(low <= high)
      {
         mid = (low + high) / 2;
         if(x == array[mid])
         {
            // If mid at beginning of array
            if(mid >= 1 && array[mid-1] == x)
            {
               high = mid -1;
            }
            else //(array[mid - 1] < x)
            {
               s = mid;
               break;
            }
         }
         
         if(x > array[mid])
         {
            low = mid + 1;
         }
         
         if(x < array[mid])
         {  
            if(array[mid -1] < x)
            {
               s = mid;
               break;
            }
            else //(array[mid - 1] >= x
            {
               high = mid-1;
            }
         }
      }
      int i=0;
      // While loop for finding upper bounds
      // [1,3,4,4,8,9,9,27,31,40]
      // Update low and high
      low = 0;
      high = array.length-1;
      while(low <= high)
      {
         i++;
         mid = (low + high) / 2;
         if(y == array[mid])
         {
            if(array[mid+1] == y)
            {
               low = mid + 1;
            }
            else
            {
               t = mid;
               break;
            }
         }
         
         if(y > array[mid])
         {
            // If mid at end of array
            if(mid < array.length-1 && array[mid + 1] > y)
            {
               t = mid;
               break;
            }
            
            else
            {
               low = mid + 1; // array[mid + 1] <= y
            }
         }
         
         if(y < array[mid])
         { 
            high = mid-1;
         }
      }
      
      // Verification
      if (t < s)
      {
         System.out.println("Null");
      }
      else
      {
         System.out.println("A[" + s + ", " + t + "]");
      }
   }
}