/*
 *  Java Program to Implement Red Black Tree
 */

import java.util.Scanner;

/* Class Node */
class RedBlackNode
{    
   RedBlackNode left, right;
   int element;
   int color;
   int total;

   /* Constructor */
   public RedBlackNode(int theElement)
   {
       this( theElement, null, null );
   } 
   /* Constructor */
   public RedBlackNode(int theElement, RedBlackNode lt, RedBlackNode rt)
   {
       left = lt;
       right = rt;
       element = theElement;
       color = 1;
       total = 0;
   }    
}

/* Class RBTree */
class RBTree
{
   private RedBlackNode current;
   private RedBlackNode parent;
   private RedBlackNode grand;
   private RedBlackNode great;
   public RedBlackNode header;    
   public static RedBlackNode nullNode;
   /* static initializer for nullNode */
   static 
   {
       nullNode = new RedBlackNode(0);
       nullNode.left = nullNode;
       nullNode.right = nullNode;
   }
   /* Black - 1  RED - 0 */
   static final int BLACK = 1;    
   static final int RED   = 0;

   /* Constructor */
   public RBTree(int negInf)
   {
       header = new RedBlackNode(negInf);
       header.left = nullNode;
       header.right = nullNode;
   }
   /* Function to check if tree is empty */
   public boolean isEmpty()
   {
       return header.right == nullNode;
   }
   /* Make the tree logically empty */
   public void makeEmpty()
   {
       header.right = nullNode;
   }
   /* Function to insert item */
   public void insert(int item )
   {
       current = parent = grand = header;
       nullNode.element = item;
       while (current.element != item)
       {            
           great = grand; 
           grand = parent; 
           parent = current;
           current = item < current.element ? current.left : current.right;
             // Check if two red children and fix if so            
           if (current.left.color == RED && current.right.color == RED)
               handleReorient( item );
       }
         // Insertion fails if already present
       if (current != nullNode)
           return;
       current = new RedBlackNode(item, nullNode, nullNode);
         // Attach to parent
       if (item < parent.element)
           parent.left = current;
       else
           parent.right = current;        
       handleReorient( item );
   }
   private void handleReorient(int item)
   {
         // Do the color flip
       current.color = RED;
       current.left.color = BLACK;
       current.right.color = BLACK;

       if (parent.color == RED)   
       {
             // Have to rotate
           grand.color = RED;
           if (item < grand.element != item < parent.element)
                 parent = rotate( item, grand );  // Start dbl rotate
             current = rotate(item, great );
             current.color = BLACK;
         }
         // Make root black
         header.right.color = BLACK; 
     }      
     private RedBlackNode rotate(int item, RedBlackNode parent)
     {
       if(item < parent.element)
           return parent.left = item < parent.left.element ? rotateWithLeftChild(parent.left) : rotateWithRightChild(parent.left) ;  
       else
           return parent.right = item < parent.right.element ? rotateWithLeftChild(parent.right) : rotateWithRightChild(parent.right);  
   }
   /* Rotate binary tree node with left child */
   private RedBlackNode rotateWithLeftChild(RedBlackNode k2)
   {
       RedBlackNode k1 = k2.left;
       k2.left = k1.right;
       k1.right = k2;
       return k1;
   }
   /* Rotate binary tree node with right child */
   private RedBlackNode rotateWithRightChild(RedBlackNode k1)
   {
       RedBlackNode k2 = k1.right;
       k1.right = k2.left;
       k2.left = k1;
       return k2;
   }
   /* Functions to count number of nodes */
   public int countNodes()
   {
       return countNodes(header.right);
   }
   private int countNodes(RedBlackNode r)
   {
       if (r == nullNode)
           return 0;
       else
       {
           int l = 1;
           l += countNodes(r.left);
           l += countNodes(r.right);
           return l;
       }
   }
   /* Functions to search for an element */
   public boolean search(int val)
   {
       return search(header.right, val);
   }
   private boolean search(RedBlackNode r, int val)
   {
       boolean found = false;
       while ((r != nullNode) && !found)
       {
           int rval = r.element;
           if (val < rval)
               r = r.left;
           else if (val > rval)
               r = r.right;
           else
           {
               found = true;
               break;
           }
           found = search(r, val);
       }
       return found;
   }
   /* Function for inorder traversal */ 
   public void inorder()
   {
       inorder(header.right);
   }
   private void inorder(RedBlackNode r)
   {
       if (r != nullNode)
       {
           inorder(r.left);
           char c = 'B';
           if (r.color == 0)
               c = 'R';
           System.out.print(r.element +""+c+" "+r.total+" ");
           inorder(r.right);
       }
   }
   /* Function for preorder traversal */
   public void preorder()
   {
       preorder(header.right);
   }
   private void preorder(RedBlackNode r)
   {
       if (r != nullNode)
       {
           char c = 'B';
           if (r.color == 0)
               c = 'R';
           System.out.print(r.element +""+c+" "+r.total+" ");
           preorder(r.left);             
           preorder(r.right);
       }
   }
   /* Function for postorder traversal */
   public void postorder()
   {
       postorder(header.right);
   }
   private void postorder(RedBlackNode r)
   {
       if (r != nullNode)
       {
           postorder(r.left);             
           postorder(r.right);
           char c = 'B';
           if (r.color == 0)
               c = 'R';
           System.out.print(r.element +""+c+" "+r.total+" ");
       }
   }

   /*Add total to the tree*/
   public void addPostOrderToTheTree(){
   		addPostOrderToTheTree(header.right);
   } 
   private void addPostOrderToTheTree(RedBlackNode r){
   		if (r != nullNode)
       {
           if(r.left == nullNode && r.right == nullNode){
           		r.total = 0;
           } else {
           		addPostOrderToTheTree(r.left);             
           		addPostOrderToTheTree(r.right);
           		if(r.left != nullNode) r.total += (r.left.total+r.left.element);
           		if(r.right != nullNode) r.total += (r.right.total+r.right.element);
           }
           
       }
   }

   
   RedBlackNode midNode = nullNode;
   int sum = 0;
   int count = 0;
   public void findSumInRange(int low,int high,RedBlackNode currNode){
      count ++;
   		if(currNode != nullNode){
   			if(currNode.element >= low && currNode.element <= high){
   				if(midNode == nullNode){
   					midNode = currNode;
   					sum = currNode.total + currNode.element;
   				}
          //System.out.println("\n"+currNode.element);
          
   				findSumInRange(low,high,currNode.left);
   				findSumInRange(low,high,currNode.right);
   			}else if(currNode.element < low){
   				if(midNode != nullNode){
   					sum -= (currNode.total + currNode.element);
   				}
   			}else if(currNode.element > high){
   				if(midNode != nullNode){
   					sum -= (currNode.total + currNode.element);
   				}
   			}
   		}
   }      
}

/* Class RedBlackTreeTest */
public class RedBlackTreeTest
{
   public static void main(String[] args)
   {            
    Scanner scan = new Scanner(System.in);
    /* Creating object of RedBlack Tree */
    RBTree rbt = new RBTree(Integer.MIN_VALUE); 
    System.out.println("Red Black Tree Test\n");          
    char ch;
    /*  Perform tree operations  */
        // do    
        // {
        //     System.out.println("\nRed Black Tree Operations\n");
        //     System.out.println("1. insert ");
        //     System.out.println("2. search");
        //     System.out.println("3. count nodes");
        //     System.out.println("4. check empty");
        //     System.out.println("5. clear tree");

        //     int choice = scan.nextInt();            
        //     switch (choice)
        //     {
        //     case 1 : 
        //         System.out.println("Enter integer element to insert");
        //         rbt.insert( scan.nextInt() );                     
        //         break;                          
        //     case 2 : 
        //         System.out.println("Enter integer element to search");
        //         System.out.println("Search result : "+ rbt.search( scan.nextInt() ));
        //         break;                                          
        //     case 3 : 
        //         System.out.println("Nodes = "+ rbt.countNodes());
        //         break;     
        //     case 4 : 
        //         System.out.println("Empty status = "+ rbt.isEmpty());
        //         break;     
        //     case 5 : 
        //         System.out.println("\nTree Cleared");
        //         rbt.makeEmpty();
        //         break;         
        //     default : 
        //         System.out.println("Wrong Entry \n ");
        //         break;    
        //     }
        //     /*  Display tree  */
        //     System.out.print("\nPost order : ");
        //     rbt.postorder();
        //     System.out.print("\nPre order : ");
        //     rbt.preorder();
        //     System.out.print("\nIn order : ");
        //     rbt.inorder(); 

        //     System.out.println("\nDo you want to continue (Type y or n) \n");
        //     ch = scan.next().charAt(0);                        
        // } while (ch == 'Y'|| ch == 'y');     
    /*  Display tree  */
    // rbt.insert(14);
    // rbt.insert(17);
    // rbt.insert(26);
    // rbt.insert(41);
    // rbt.insert(47);
    // rbt.insert(21);
    // rbt.insert(30);
    rbt.insert(7);
    rbt.insert(3);
    rbt.insert(18);
    rbt.insert(10);
    rbt.insert(8);
    rbt.insert(11);
    rbt.insert(22);
    rbt.insert(26);
    // for(int i = 30 ; i < 50 ; i++){
    //   rbt.insert(i);
    // }
    
    rbt.addPostOrderToTheTree();
    int low = 1;
    int high = 10;
    rbt.findSumInRange(low,high,rbt.header.right);
    System.out.print("\nSum for low "+low+" high "+high+" ,count: "+rbt.count+": "+rbt.sum);
    rbt.midNode = rbt.nullNode;
    rbt.sum = 0;
    rbt.count = 0;

    low = 7;
    high = 20;
    rbt.findSumInRange(low,high,rbt.header.right);
    System.out.print("\nSum for low "+low+" high "+high+" ,count: "+rbt.count+": "+rbt.sum);
    rbt.midNode = rbt.nullNode;
    rbt.sum = 0;
    rbt.count = 0;

    low = 5;
    high = 22;
    rbt.findSumInRange(low,high,rbt.header.right);
    System.out.print("\nSum for low "+low+" high "+high+" ,count: "+rbt.count+": "+rbt.sum);
    rbt.midNode = rbt.nullNode;
    rbt.sum = 0;
    rbt.count = 0;

    low = 1;
    high = 25;
    rbt.findSumInRange(low,high,rbt.header.right);
    System.out.print("\nSum for low "+low+" high "+high+" ,count: "+rbt.count+": "+rbt.sum);
    rbt.midNode = rbt.nullNode;
    rbt.sum = 0;
    rbt.count = 0;

    low = 1;
    high = 100;
    rbt.findSumInRange(low,high,rbt.header.right);
    System.out.print("\nSum for low "+low+" high "+high+" ,count: "+rbt.count+": "+rbt.sum);
    rbt.midNode = rbt.nullNode;
    rbt.sum = 0;
    rbt.count = 0;


    System.out.print("\nPost order : ");
    rbt.postorder();
    System.out.print("\nPre order : ");
    rbt.preorder();
    System.out.print("\nIn order : ");
    rbt.inorder();           
}
}
