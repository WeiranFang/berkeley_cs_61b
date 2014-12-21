/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  
  protected List[] buckets;
  protected int N;
  protected int n = 0; 
  protected int prime = 109345121;
  protected long scale, shift;
  
  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    // Your solution here.
    N = findPrime(sizeEstimate);
    buckets = new List[N];
    for (int i = 0; i < N; i++) {
      buckets[i] = new DList();
    }
    java.util.Random rand = new java.util.Random();
    scale = rand.nextInt(prime - 1) + 1;
    shift = rand.nextInt(prime);
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    this(101);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    return (int) ((Math.abs(code*scale + shift) % prime) % N);
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return n;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return n == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    Entry newEntry = new Entry();
    newEntry.key = key;
    newEntry.value = value;
    int index = compFunction(key.hashCode());
    buckets[index].insertFront(newEntry);
    n++;
    return newEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int index = compFunction(key.hashCode());
    if (buckets[index].isEmpty()) {
      return null;
    }
    else {
      try {
        ListNode currentNode = buckets[index].front();
        while(currentNode != null) {
          if (((Entry) currentNode.item()).key().equals(key)) {
            return (Entry) currentNode.item();
          }
          else {
            currentNode = currentNode.next();
          }
        }
      } catch(InvalidNodeException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    int index = compFunction(key.hashCode());
    if (buckets[index].isEmpty()) {
      return null;
    }
    else {
      try {
        ListNode currentNode = buckets[index].front();
        while(currentNode != null) {
          if (((Entry) currentNode.item()).key().equals(key)) {
            Entry entryToRemove = (Entry) currentNode.item();
            currentNode.remove();
            return entryToRemove;
          }
          else {
            currentNode = currentNode.next();
          }
        }
      } catch(InvalidNodeException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    buckets = new List[N];
    for (int i = 0; i < N; i++) {
      buckets[i] = new DList();
    }
  }
  
  private int findPrime(int near) {
    while(!isPrime(near)) {
      near++;
    }
    return near;
  }
  
  private boolean isPrime(int x) {
    for (int i = 2; i <= Math.sqrt(x); i++) {
      if (x % i == 0) return false;
    }
    return true;
  }
  
  public double expectedNumCollisions() {
    return n - N + N * Math.pow(1 - 1.0 / N, n);
  }
  
  public int numCollisions() {
    int sum = 0;
    for (int i = 0; i < N; i++) {
      if (buckets[i].length() > 1) {
        sum = sum + buckets[i].length() - 1;
      }
    }
    return sum;
  }
  
  public String colDistribution() {
    String str = "";
    for (int i = 0; i < N; i++) {
      str = str + "[" + buckets[i].length() + "]";
      if ((i + 1) % 20 == 0) {
        str = str + "\n";
      }
    }
    return str;
  }
  
  public static void main(String[] args) {
    HashTableChained htc = new HashTableChained(100);
    
    htc.insert("code monkey", "An expression of programmers");
    htc.insert("King", "Nickname of Lebron James");
    htc.insert("CP3", "Nickname of Chris Paul");
    htc.insert("code monkey", "A monkey who can code");
    
    System.out.println("The value of key 'King' should be 'Nickname of Lebron James': " + htc.find("King").value());
    System.out.println("The value of key 'CP3' should be 'Nickname of Chris Paul': " + htc.find("CP3").value());
    System.out.println("The value of key 'code monkey' should be 'A monkey who can code': " + htc.find("code monkey").value());
    
    System.out.println("Remove key 'King'");
    htc.remove("King");
    System.out.print("The entry with key 'King' should be null: ");
    if (htc.find("King") == null) System.out.println("is null");
  }

}
