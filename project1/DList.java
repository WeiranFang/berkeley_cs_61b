/* DList.java */

public class DList {

  protected DListNode head;
  protected DListNode tail;
  protected long size;

  public DList() {
    head = null;
    tail = null;
    size = 0;
  }

  public DList(int[] a) {
    head = new DListNode();
    tail = head;
    head.item = a;
    size = 1;
  }

  public void insertFront(int[] i) {
    if (size == 0) {
      head = new DListNode(i);
      tail = head;
    }
    else {
      DListNode front = new DListNode(i);
      head.prev = front;
      front.next = head;
      head = front;
    }
    size++;
  }
  
  public void insertEnd(int[] i) {
    if (size == 0) {
      tail = new DListNode(i);
      head = tail;
    }
    else {
      DListNode end = new DListNode(i);
      tail.next = end;
      end.prev = tail;
      tail = end;
    }
    size++;
  }
  
  public void insertAfter(DListNode insertPoint, int[] i) {
    if (insertPoint == tail) {
      insertEnd(i);
    }
    else {
      DListNode insert = new DListNode(i);
      insertPoint.next.prev = insert;
      insert.next = insertPoint.next;
      insertPoint.next = insert;
      insert.prev = insertPoint;
    }
    size++;
  }
  
  public void removeNode(DListNode remove) {
    if (remove == head) {
      removeFront();
    }
    else if (remove == tail) {
      removeEnd();
    }
    else {
      DListNode left = remove.prev;
      DListNode right = remove.next;
      left.next = right;
      right.prev = left;
      remove.next = null;
      remove.prev = null;
    }
    size--;
  }

  /**
   *  removeFront() removes the first item (and first non-sentinel node) from
   *  a DList.  If the list is empty, do nothing.
   */
  public void removeFront() {
    if (size == 0) {
      return;
    }
    else if (size == 1) {
      head = null;
      tail = null;
    }
    else {
      head = head.next;
      head.prev.next = null;
      head.prev = null;
    }
    size--;
  }
  
  public void removeEnd() {
    if (size == 0) {
      return;
    }
    else if (size == 1) {
      head = null;
      tail = null;
    }
    else {
      tail = tail.prev;
      tail.next.prev = null;
      tail.next = null;
    }
    size--;
  }
  
  public DListNode firstNode() {
    return head;
  }
  
  public DListNode tailNode() {
    return tail;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + "{" + current.item[0] + ", " + current.item[1] + ", " + current.item[2] + ", " + current.item[3] + "}  ";
      current = current.next;
    }
    return result + "]";
  }


}
