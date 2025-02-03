import java.lang.IllegalArgumentException;
/**
 A linked implemention of MinMaxListInterface. Adapted from Frank M. Carrano and Timothy M. Henry
 @author Stephen Ellis
 @version 5.0
 */
public class SortedLList<T extends Comparable<? super T>> implements MinMaxListInterface<T>
{
   private Node firstNode;            // Reference to first node of chain
   private int  numberOfEntries;
   
   public SortedLList()
   {
      initializeDataFields();
   } // end default constructor
   
   public void clear()
   {
      initializeDataFields();
   } // end clear

   // Lab 4: Update add() to keep all items in sorted ascending order
   
   public void add(T newEntry)          // OutOfMemoryError possible
   {
      Node newNode = new Node(newEntry);
      Node nodeBefore = null;
      Node nodeAfter = firstNode;
      while (newEntry.compareTo(nodeAfter.getData()) > 0 && nodeAfter != null){
         nodeBefore = nodeAfter;
         nodeAfter = nodeAfter.getNextNode();
      }

      if (nodeBefore == null){
         newNode.setNextNode(firstNode);
         firstNode = newNode;
      }else{
         nodeBefore.setNextNode(newNode);
         newNode.setNextNode(nodeAfter);
      }
      numberOfEntries++;
   } // end add

   public T remove(int givenPosition)
   {
      T result = null;                           // Return value
      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
         // Assertion: !isEmpty()
         if (givenPosition == 1)                 // Case 1: Remove first entry
         {
            result = firstNode.getData();        // Save entry to be removed
            firstNode = firstNode.getNextNode(); // Remove entry
         }
         else                                    // Case 2: Not first entry
         {
            Node nodeBefore = getNodeAt(givenPosition - 1);
            Node nodeToRemove = nodeBefore.getNextNode();
            result = nodeToRemove.getData();     // Save entry to be removed
            Node nodeAfter = nodeToRemove.getNextNode();
            nodeBefore.setNextNode(nodeAfter);   // Remove entry
        } // end if
         numberOfEntries--;                      // Update count
         return result;                          // Return removed entry
      }
      else
         throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
   } // end remove

   public T getEntry(int givenPosition)
   {
      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
         // Assertion: !isEmpty()
         return getNodeAt(givenPosition).getData();
      }
      else
         throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
   } // end getEntry
   
   public T[] toArray()
   {
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] result = (T[])new Object[numberOfEntries];
      
      int index = 0;
      Node currentNode = firstNode;
      while ((index < numberOfEntries) && (currentNode != null))
      {
         result[index] = currentNode.getData();
         currentNode = currentNode.getNextNode();
         index++;
      } // end while
      
      return result;
   } // end toArray
                                             
   public boolean contains(T anEntry)
   {
      boolean found = false;
      Node currentNode = firstNode;
      
      while (!found && (currentNode != null))
      {
         if (anEntry.equals(currentNode.getData()))
            found = true;
         else
            currentNode = currentNode.getNextNode();
      } // end while
      
      return found;
   } // end contains

   public int getLength()
   {
      return numberOfEntries;
   } // end getLength

   public boolean isEmpty()
   {
      boolean result;
      
      if (numberOfEntries == 0) // Or getLength() == 0
      {
         // Assertion: firstNode == null
         result = true;
      }
      else
      {
         // Assertion: firstNode != null
         result = false;
      } // end if
      
      return result;
   } // end isEmpty


   // Lab 4: Implement getMin() and getMax()

   public T getMin() {
      if(isEmpty()){
         return null;
      }else{
         return firstNode.getData();
      }
   } // end getMin

   public T getMax() {
      if(isEmpty()){
         return null;
      }else{
         Node currentNode = firstNode;
         T theMax = firstNode.getData();
         while(currentNode != null){
            if(currentNode.getData().compareTo(theMax) > 0){
               theMax = currentNode.getData();
            }
            currentNode = currentNode.getNextNode();
         }
         return theMax;
      }
   } // end getMax

	
   // Initializes the class's data fields to indicate an empty list.
   private void initializeDataFields()
   {
      firstNode = null;
      numberOfEntries = 0;
   } // end initializeDataFields
   
   // Returns a reference to the node at a given position.
   // Precondition: The chain is not empty;
   //               1 <= givenPosition <= numberOfEntries.
   private Node getNodeAt(int givenPosition)
   {
      // Assertion: (firstNode != null) &&
      //            (1 <= givenPosition) && (givenPosition <= numberOfEntries)
      Node currentNode = firstNode;
      
      // Traverse the chain to locate the desired node
      // (skipped if givenPosition is 1)
      for (int counter = 1; counter < givenPosition; counter++)
         currentNode = currentNode.getNextNode();
      // Assertion: currentNode != null
      return currentNode;
   } // end getNodeAt
   
   private class Node
   {
      private T    data; // Entry in list
      private Node next; // Link to next node
      
      private Node(T dataPortion)
      {
         data = dataPortion;
         next = null;
      } // end constructor
      
      private Node(T dataPortion, Node nextNode)
      {
         data = dataPortion;
         next = nextNode;
      } // end constructor
      
      private T getData()
      {
         return data;
      } // end getData
      
      private void setData(T newData)
      {
         data = newData;
      } // end setData
      
      private Node getNextNode()
      {
         return next;
      } // end getNextNode
      
      private void setNextNode(Node nextNode)
      {
         next = nextNode;
      } // end setNextNode
   } // end Node
} // end LList

