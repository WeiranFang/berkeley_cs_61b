import java.util.*;

public class Nuke2 {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String str = in.next();
    String str2 = str.substring(0, 1) + str.substring(2, str.length());
    System.out.println(str2);
  }
}