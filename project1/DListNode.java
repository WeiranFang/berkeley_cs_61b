/* DListNode.java */

/**
 *  A DListNode is a node in a DList2 (doubly-linked list).
 */

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public int[] item;
  public DListNode prev;
  public DListNode next;

  /**
   *  DListNode() constructor.
   */
  DListNode() {
    item = new int[4];
    prev = null;
    next = null;
  }

  DListNode(int[] i) {
    item = i;
    prev = null;
    next = null;
  }

}
