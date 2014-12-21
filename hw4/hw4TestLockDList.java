import list.*;
import tests.Test;

public class hw4TestLockDList {
    public static void main(String[] args) {
        Test t;
        LockDList l;
        DListNode n;

        // 1.
        // LockDListNodes are always used in LockDLists (instead of DListNodes)
        // Note: this test is not comprehensive!
        t = new Test("LockDListNodes are always used in LockDLists");
        l = new LockDList();
        l.insertFront(1);
        n = l.front();
        t.expect(true);
        t.run(n instanceof LockDListNode);

        // 2.
        // Locked nodes cannot be removed from a list.
        t = new Test("Locked Nodes");
        l = new LockDList();
        l.insertBack(1);
        l.insertBack(2);
        l.insertBack(3);
        l.insertBack(4);
        l.lockNode(l.next(l.front()));
        l.remove(l.front());
        l.remove(l.front());
        l.remove(l.front());
        t.expect("[  2  3  4  ]");
        t.run(l.toString());
    }
}