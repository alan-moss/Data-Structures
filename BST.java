/*
Alan Moss
CSCD 300
Program 8
This program constructs a binary search tree from a text file
and performs different operations depending on user input.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BST
{
    public static void main(String[] args) throws Exception 
    {
        //Bring in file from command line
        String file = args[0];
        File fileEntry = new File(file);

        try(Scanner sc = new Scanner(fileEntry))
        {
            //Arraylist for integers in text file
            ArrayList<Integer> arraylist = new ArrayList<Integer>();
            //Add numbers to arraylist
            while(sc.hasNext())
            {
                if(sc.hasNextInt())
                {
                    arraylist.add(sc.nextInt());
                }
            }

            if(arraylist.isEmpty())
            {
                System.out.println("List is empty");
                return;
            }

            //Create BST
            BST_Class tree = new BST_Class();

            for(int i=0; i < arraylist.size(); i++)
            {
                tree.insert(arraylist.get(i));
            }
            sc.close();

            Scanner sc2 = new Scanner(System.in);
            String input;
            //Menu
            while(true)
            {
                System.out.println();
                System.out.println("Choose one of the following options.");
				System.out.println("====================================");
				System.out.println("1) Search for a key");
				System.out.println("2) Insert a new key");
				System.out.println("3) Delete an existing key");
				System.out.println("4) Inorder traversal of the BST");
				System.out.println("5) Preorder traversal of the BST");
                System.out.println("6) Postorder traversal of the BST");
                System.out.println("7) Level-order traversal of the BST");
                System.out.println("8) Find the smallest key");
                System.out.println("9) Find the largest key");
                System.out.println("a) Find the successor of a given key");
                System.out.println("b) Find the predecessor of a given key");
                System.out.println("x) Quit");
				System.out.println("Your choice: ");
				
				input = sc2.next();

                switch(input)
				{
					case "1":
						System.out.println("Input the key: ");
                        //Insist user enters int
                        while(!sc2.hasNextInt())
                        {
                            sc2.next();
                        }
						int key = sc2.nextInt();
						
						if(tree.search(key) == null)
						{
							System.out.println("The given key does not exist.");
							break;
						}
						
						else
						{
							System.out.println("The given key exists.");
							break;
						}
					
					case "2":
						System.out.println("Input the key: ");
                        //Insist user enters int
                        while(!sc2.hasNextInt())
                        {
                            sc2.next();
                        }
						key = sc2.nextInt();
						
						if(tree.insert(key) == null)
						{
							System.out.println("The given key exists.");
							break;
						}
						
						//Key does not exist
						else
						{
							tree.insert(key);
							System.out.println("The given key has been inserted successfully.");
							break;
						}
						
					case "3":
						System.out.println("Input the key: ");
                        //Insist user enters int
                        while(!sc2.hasNextInt())
                        {
                            sc2.next();
                        }
						key = sc2.nextInt();
						
						//Not found
						if(tree.delete(key) == null)
						{
							System.out.println("No such key.");
							break;
						}
						
						//Key found, delete node
						else
						{
							tree.delete(key);
							System.out.println("The given key has been successfully deleted.");
							break;
						}
						
					case "4":
                        System.out.println("=====================");
						System.out.println("Inorder traversal of BST: ");
						System.out.println("=====================");
						tree.InOrder_Traversal(tree.root);
						break;
                    
                    case "5":
                        System.out.println("=====================");
                        System.out.println("Preorder traversal of BST: ");
                        System.out.println("=====================");
                        tree.PreOrder_Traversal(tree.root);
                        break;

                    case "6":
                        System.out.println("=====================");
                        System.out.println("Postorder traversal of BST: ");
                        System.out.println("=====================");
                        tree.PostOrder_Traversal(tree.root);
                        break;

                    case "7":
                        System.out.println("=====================");
                        System.out.println("Level-order traversal of BST: ");
                        System.out.println("=====================");
                        tree.LevelOrder_Traversal(tree.root);
                        break;

                    case "8":
                        if(tree.Min(tree.root) == null)
                        {
                            System.out.println("The tree is empty.");
                        }
                        else
                        {
                            System.out.println("Smallest key: \n" + tree.Min(tree.root).key);
                        }
                        break;

                    case "9":
                        if(tree.Max(tree.root) == null)
                        {
                            System.out.println("The tree is empty.");
                        }
                        else
                        {
                            System.out.println("Largest key: \n" + tree.Max(tree.root).key);
                        }
                        break;

                    case "a":
                        System.out.println("Input the key: ");
                        //Insist user enters int
                        while(!sc2.hasNextInt())
                        {
                            sc2.next();
                        }
                        key = sc2.nextInt();

                        //Key does not exist
                        if(tree.search(key) == null)
                        {
                            System.out.println("No such key.");
                            break;
                        }

                        //Key and successor both exist
                        else if(tree.search(key) != null && tree.Successor(tree.search(key)) != null)
                        {
                            System.out.println("Successor's key value: " + tree.Successor(tree.search(key)).key);
                            break;
                        }
                        
                        //Key exists but successor does not
                        else
                        {
                            System.out.println("The given key exists but does not have successor.");
                            break;
                        }

                    case "b":
                        System.out.println("Input the key: ");
                        //Insist user enters int
                        while(!sc2.hasNextInt())
                        {
                            sc2.next();
                        }
                        key = sc2.nextInt();

                        //Key does not exist
                        if(tree.search(key) == null)
                        {
                            System.out.println("No such key.");
                            break;
                        }

                        //Key and successor both exist
                        else if(tree.search(key) != null && tree.Predecessor(tree.search(key)) != null)
                        {
                            System.out.println("Predecessor's key value: " + tree.Predecessor(tree.search(key)).key);
                            break;
                        }
                        
                        //Key exists but Predecessor does not
                        else
                        {
                            System.out.println("The given key exists but does not have a Predecessor.");
                            break;
                        }
						
					case "x":
						System.out.println("Goodbye.");
						System.exit(0);
						break;
						
					default:
						System.out.println("Invalid menu option. Try again.");
						break;
				}
            }
        }
        //File not found
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }



    //BST node class
    static class BST_Node
    {
        int key;
        BST_Node left;
        BST_Node right;
        BST_Node parent;

        //Constructor
        BST_Node(int k)
        {
            key = k;
            parent = left = right = null;
        }
    }

    //BST class contains methods for binary search tree
    public static class BST_Class
    {
        BST_Node root;

        //Constructor
        public void BST_Class()
        {
            root = null;
        }

        //Insert function
        BST_Node insert(int k)
        {
            BST_Node new_node = new BST_Node(k);

            BST_Node y = null;
            BST_Node x = root;

            //Find the leaf node where new_node is attached
            while(x != null)
            {
                y = x;
                //Key value is already in tree
                if(k == x.key)
                {
                    return null;
                }

                //Key value less than current node value
                else if(k < x.key)
                {
                    x = x.left;
                }

                //Key value greater than current node value
                else
                {
                    x = x.right;
                }
            }

            //Attach new node
            new_node.parent = y;
            //Tree is empty
            if(y == null)
            {
                root = new_node;
            }
            //Create link
            else if(k < y.key)
            {
                y.left = new_node;
            }
            //Create link
            else
            {
                y.right = new_node;
            }

            return new_node;
        }

        //Delete node containing given key k, returns null if not existing
        BST_Node delete(int k)
        {
            BST_Node z = search(k);
            if(z != null)
            {
                delete(z);
            }

            return z;
        }

        void delete(BST_Node z)
        {
            //z has no child
            if(z.left == null && z.right == null)
            {
                transplant(z, null);
            }
            //z has only a right child
            else if(z.left == null)
            {
                transplant(z, z.right);
            }
            //z has only a left child
            else if(z.right == null)
            {
                transplant(z, z.left);
            }
            //z has two children, replace z with its successor
            else
            {
                BST_Node y = Min(z.right);
                if(y.parent != z)
                {
                    transplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                transplant(z, y);
                y.left = z.left;
                y.left.parent = y;
            }
        }

        //Transplant function
        void transplant(BST_Node old_subtree, BST_Node new_subtree)
        {
            if(old_subtree.parent == null)
            {
                root = new_subtree;
            }
            else if(old_subtree == old_subtree.parent.left)
            {
                old_subtree.parent.left = new_subtree;
            }
            else
            {
                old_subtree.parent.right = new_subtree;
            }

            if(new_subtree != null)
            {
                new_subtree.parent = old_subtree.parent;
            }
        }

        //Search for a given key
        BST_Node search(int k)
        {
            BST_Node temp = root;

            //Continue searching until end of list reached or key found
            while(temp != null && k != temp.key)
            {
                if(k < temp.key)
                {
                    temp = temp.left;
                }
            
                else 
                {
                    temp = temp.right;
                }
            }
            return temp;
        }

        //Finds minimum in a given subtree
        BST_Node Min(BST_Node subtree_root)
        {
            BST_Node temp = subtree_root;
            while(temp.left != null)
            {
                temp = temp.left;
            }
            
            return temp;
        }

        //Finds maximum in a given subtree
        BST_Node Max(BST_Node subtree_root)
        {
            BST_Node temp = subtree_root;
            while(temp.right != null)
            {
                temp = temp.right;
            }
            
            return temp;
        }

        //Find successor of given node
        BST_Node Successor(BST_Node x)
        {
            if(x.right != null)
            {
                return Min(x.right);
            }

            //Find x's lowest ancestor such that x is in its left subtree
            BST_Node y = x.parent;

            while(y != null && x == y.right)
            {
                x = y;
                y = y.parent;
            }

            return y;
        }

        //Find the predecessor of given node
        BST_Node Predecessor(BST_Node x)
        {
            if(x.left != null)
            {
                return Max(x.left);
            }

            //Find X's lowest ancestore such that x is in its right subtree
            BST_Node y = x.parent;

            while(y != null && x == y.left)
            {
                x = y;
                y = y.parent;
            }

            return y;
        }

        //In-order traversal prints the BST in ascending order
        void InOrder_Traversal(BST_Node subtree_root)
        {
            if(subtree_root != null)
            {
                InOrder_Traversal(subtree_root.left);
                System.out.print(subtree_root.key + " ");
                InOrder_Traversal(subtree_root.right);
            }
        }

        //Pre-order traversal prints root first, then left and right
        void PreOrder_Traversal(BST_Node subtree_root)
        {
            if(subtree_root != null)
            {
                System.out.print(subtree_root.key + " ");
                PreOrder_Traversal(subtree_root.left);
                PreOrder_Traversal(subtree_root.right);
            }
        }

        //Post-order traversal prints left first, then right, then root
        void PostOrder_Traversal(BST_Node subtree_root)
        {
            if(subtree_root != null)
            {
                PostOrder_Traversal(subtree_root.left);
                PostOrder_Traversal(subtree_root.right);
                System.out.print(subtree_root.key + " ");
            }
        }

        //Level-order traversal prints top level to bottom level, left to right
        //Iteration based
        void LevelOrder_Traversal(BST_Node subtree_root)
        {
            //Create new fifo queue of tree node type
            ListQueue Q = new ListQueue();

            Q.enqueue(subtree_root);

            while(Q.size > 0)
            {
                BST_Node node = Q.dequeue();
                System.out.print(node.key + " ");
                
                if(node.left != null)
                {
                    Q.enqueue(node.left);
                }

                if(node.right != null)
                {
                    Q.enqueue(node.right);
                }
            }
        }
    }


    //Classes for FIFO queue
    //______________________________________________________________________
    //Node class for singly linked list
    public static class Node
    {
        private BST_Node element;
        private Node next; //References the next node in linked list
        
        //Constructor takes string and Node
        public Node(BST_Node element, Node next)
        {
            this.element = element;
            this.next = next;
        }
        
        public BST_Node getElement() {return element;}
        public Node getNext() {return next;}
        public void setElement(BST_Node element) {this.element = element;}
        public void setNext(Node next) {this.next = next;}
    }

    //Interface for fifo queue
	//Works for both array based and linked list based queues
	public interface Queue
	{
		public int size();
		public BST_Node front();
		public void enqueue(BST_Node item);
		public BST_Node dequeue();
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
		
		public BST_Node front()
		{
			if(size == 0)
				return null;
			return head.getElement();
		}
		
		public void enqueue(BST_Node item)
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
		
		public BST_Node dequeue()
		{
			if(size == 0)
				return null;
			
			BST_Node ret = head.getElement();
			
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