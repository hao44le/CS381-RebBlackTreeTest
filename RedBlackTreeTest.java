/*
 *  Java Program to Implement Red Black Tree
 */

import java.util.Scanner;

/* Class Node */
class RedBlackNode
{
    RedBlackNode left, right;
    int key;
    int color;
    int total;
    
    /* Constructor */
    public RedBlackNode(int theKey)
    {
        this( theKey, null, null );
    }
    /* Constructor */
    public RedBlackNode(int theKey, RedBlackNode lt, RedBlackNode rt)
    {
        left = lt;
        right = rt;
        key = theKey;
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
        nullNode.key = item;
        while (current.key != item)
        {
            great = grand;
            grand = parent;
            parent = current;
            current = item < current.key ? current.left : current.right;
            // Check if two red children and fix if so
            if (current.left.color == RED && current.right.color == RED)
                handleReorient( item );
        }
        // Insertion fails if already present
        if (current != nullNode)
            return;
        current = new RedBlackNode(item, nullNode, nullNode);
        // Attach to parent
        if (item < parent.key)
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
            if (item < grand.key != item < parent.key)
                parent = rotate( item, grand );  // Start dbl rotate
            current = rotate(item, great );
            current.color = BLACK;
        }
        // Make root black
        header.right.color = BLACK;
    }
    private RedBlackNode rotate(int item, RedBlackNode parent)
    {
        if(item < parent.key)
            return parent.left = item < parent.left.key ? rotateWithLeftChild(parent.left) : rotateWithRightChild(parent.left) ;
        else
            return parent.right = item < parent.right.key ? rotateWithLeftChild(parent.right) : rotateWithRightChild(parent.right);
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
    /* Functions to search for an key */
    public boolean search(int val)
    {
        return search(header.right, val);
    }
    private boolean search(RedBlackNode r, int val)
    {
        boolean found = false;
        while ((r != nullNode) && !found)
        {
            int rval = r.key;
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
            System.out.print(r.key +""+c+" "+r.total+" ");
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
            System.out.print(r.key +""+c+" "+r.total+" ");
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
            System.out.print(r.key +""+c+" "+r.total+" ");
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
                r.total = r.key;
            } else {
                addPostOrderToTheTree(r.left);
                addPostOrderToTheTree(r.right);
                if(r.left != nullNode) r.total += (r.left.total);
                if(r.right != nullNode) r.total += (r.right.total);
                r.total += r.key;
            }
            
        }
    }
    
    
    // int greaterThanOrEqualToHigh = 0;
    // int lowerThanOrEqualToLow = 0;
    // int count = 0;
    // public int findSumInRange(int low,int high,RedBlackNode currNode){
    //    int sum = currNode.total;
    //    greaterThanOrEqualToHigh = 0;
    //    lowerThanOrEqualToLow = 0;
    //    count = 0;
    //    if(currNode.key >= high){
    //       greaterThanOrEqualToHigh = currNode.total;
    //       if(currNode.left != nullNode) greaterThanOrEqualToHigh -= currNode.left.total;
    //       findSumOfValueGreaterThanHighOnLeftBranch(high,currNode.left,currNode,nullNode);
    //    } else {
    //       findSumOfValueGreaterThanHighOnRightBranch(high,currNode.right,currNode,nullNode);
    //    }
    //    System.out.println("\ngreaterThanOrEqualToHigh: "+greaterThanOrEqualToHigh);
    
    
    //    if(currNode.key <= low){
    //       lowerThanOrEqualToLow = currNode.total;
    //       if(currNode.right != nullNode) lowerThanOrEqualToLow -= currNode.right.total;
    //       findSumInRangeLower(low,currNode.right,true);
    //    } else {
    //       findSumInRangeLower(low,currNode.left,false);
    //    }
    
    //    System.out.println("\nlowerThanOrEqualToLow: "+lowerThanOrEqualToLow);
    
    
    //    return sum - greaterThanOrEqualToHigh - lowerThanOrEqualToLow;
    // }
    // /*for this method, currNode starts on left branch,
    // i need to go there and add all the values that are greather than high back to greaterThanOrEqualToHigh*/
    // public void findSumOfValueGreaterThanHighOnLeftBranch(int high, RedBlackNode currNode,boolean fromLeft,RedBlackNode pivotNode){
    //    count++;
    //    if(currNode != nullNode){
    //        if(currNode.key >= high){
    //            if(pivotNode == nullNode){
    
    //            }else{
    
    //            }
    //            greaterThanOrEqualToHigh += currNode.total;
    //            findSumOfValueGreaterThanHighOnLeftBranch(high,currNode.left,fromLeft);
    
    
    //        } else {
    //            if(fromLeft) greaterThanOrEqualToHigh -= currNode.total;
    //            findSumOfValueGreaterThanHighOnLeftBranch(high,currNode.right,true);
    //        }
    
    //    }
    // }
    
    // /*for this method, currNode starts on right branch,
    // i need to go there and subtract all the values that are smaller than high to greaterThanOrEqualToHigh*/
    // public void findSumOfValueGreaterThanHighOnRightBranch(int high, RedBlackNode currNode){
    //    count++;
    //    if(currNode != nullNode){
    
    //    }
    // }
    
    
    // public void findSumInRangeGreater(int high,RedBlackNode currNode,boolean fromLeft,boolean previousFromLeft,boolean startFromLeft){
    //  count++;
    //    if(currNode != nullNode){
    //      System.out.println("\nhigher currNode.key: "+currNode.key);
    //      if(currNode.key < high){
    //        if((!fromLeft && previousFromLeft && startFromLeft)||(fromLeft && !previousFromLeft && !startFromLeft)) {
    //          System.out.println(greaterThanOrEqualToHigh+" -= "+currNode.key);
    //          greaterThanOrEqualToHigh -= currNode.key;
    //        }
    
    //        findSumInRangeGreater(high,currNode.right,true,fromLeft,startFromLeft);
    //      }else {
    //        if((fromLeft && !previousFromLeft)||fromLeft){
    //          System.out.println(greaterThanOrEqualToHigh+" += "+currNode.total);
    //           greaterThanOrEqualToHigh += currNode.total;
    //        }
    //        findSumInRangeGreater(high,currNode.left,false,fromLeft,startFromLeft);
    //      }
    //    }
    // }
    // public void findSumInRangeLower(int low,RedBlackNode currNode,boolean fromLeft){
    //  count++;
    //    if(currNode != nullNode){
    //      System.out.println("\nlower currNode.key: "+currNode.key);
    //      if(currNode.key <= low) {
    //        if(!fromLeft) lowerThanOrEqualToLow += currNode.total;
    //        findSumInRangeLower(low,currNode.right,true);
    //      } else {
    //        if(fromLeft) lowerThanOrEqualToLow -= currNode.key;
    //        findSumInRangeLower(low,currNode.left,false);
    //      }
    
    
    
    //    }
    // }
    //
    //
    int higherSum = 0;
    public void getSumlargeThanHigh(int high,RedBlackNode treeNode){
        if(treeNode != nullNode){
            if(treeNode.key >= high){
                higherSum += treeNode.key;
                if(treeNode.right != nullNode) higherSum += treeNode.right.total;
                getSumlargeThanHigh(high,treeNode.left);
            }
            else getSumlargeThanHigh(high,treeNode.right);
        }
    }
    
    int lowerSum = 0;
    public void getSumlowerThanLow(int low,RedBlackNode treeNode){
        if(treeNode != nullNode){
            if(treeNode.key <= low) {
                lowerSum += treeNode.key;
                if(treeNode.left != nullNode) lowerSum += treeNode.left.total;
                getSumlowerThanLow(low,treeNode.right);
            }
            else getSumlowerThanLow(low,treeNode.left);
        }
    }
    public int findSumInRange(int low, int high,RedBlackNode treeNode){
        higherSum = 0;
        getSumlargeThanHigh(high,treeNode);
        lowerSum = 0;
        getSumlowerThanLow(low,treeNode);
        return treeNode.total - higherSum - lowerSum;
    }
    public int findSumInRange2(int low, int high,RedBlackNode node){
        if(node==nullNode){
            return 0;
        }else if(low==Integer.MIN_VALUE && high == Integer.MAX_VALUE ){
            return node.total;
        }else if(high <= node.key){
            return findSumInRange2(low,high,node.left);
        }else if(low>=node.key){
            return findSumInRange2(low,high,node.right);
        }else{
            return findSumInRange2(low,Integer.MAX_VALUE,node.left) + node.key + findSumInRange2(Integer.MIN_VALUE,high,node.right);
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
        //         System.out.println("Enter integer key to insert");
        //         rbt.insert( scan.nextInt() );
        //         break;
        //     case 2 :
        //         System.out.println("Enter integer " to search");
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
        rbt.insert(9);
        rbt.insert(11);
        rbt.insert(22);
        rbt.insert(26);
        rbt.insert(8);
        rbt.insert(12);
        rbt.insert(24);
        rbt.insert(4);
        rbt.insert(5);
        rbt.insert(13);
        rbt.insert(20);
        // for(int i = 30 ; i < 50 ; i++){
        //   rbt.insert(i);
        // }
        
        rbt.addPostOrderToTheTree();
        int low = 2;
        int high = 8;
        int sum = rbt.findSumInRange(low,high,rbt.header.right);
        System.out.print("\nSum for low "+low+" high "+high+", "+sum);
        sum = rbt.findSumInRange2(low,high,rbt.header.right);
        System.out.print("\nSum2 for low "+low+" high "+high+", "+sum);
        
        low = 7;
        high = 10;
        sum = rbt.findSumInRange(low,high,rbt.header.right);
        System.out.print("\nSum for low "+low+" high "+high+", "+sum);
        sum = rbt.findSumInRange2(low,high,rbt.header.right);
        System.out.print("\nSum2 for low "+low+" high "+high+", "+sum);
        
        low = 8;
        high = 18;
        sum = rbt.findSumInRange(low,high,rbt.header.right);
        System.out.print("\nSum for low "+low+" high "+high+", "+sum);
        sum = rbt.findSumInRange2(low,high,rbt.header.right);
        System.out.print("\nSum2 for low "+low+" high "+high+", "+sum);
        
        low = 5;
        high = 22;
        sum = rbt.findSumInRange(low,high,rbt.header.right);
        System.out.print("\nSum for low "+low+" high "+high+", "+sum);
        sum = rbt.findSumInRange2(low,high,rbt.header.right);
        System.out.print("\nSum2 for low "+low+" high "+high+", "+sum);
        
        low = 1;
        high = 25;
        sum = rbt.findSumInRange(low,high,rbt.header.right);
        System.out.print("\nSum for low "+low+" high "+high+", "+sum);
        sum = rbt.findSumInRange2(low,high,rbt.header.right);
        System.out.print("\nSum2 for low "+low+" high "+high+", "+sum);
        
        
        System.out.print("\nPost order : ");
        rbt.postorder();
        System.out.print("\nPre order : ");
        rbt.preorder();
        System.out.print("\nIn order : ");
        rbt.inorder();           
    }
}
