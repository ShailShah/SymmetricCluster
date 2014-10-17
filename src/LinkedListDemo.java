
import java.util.*;

public class LinkedListDemo {

    /**
     * @param args
     */
    public static void main(String args[]) {
        // create a linked list
        LinkedList<String> ll = new LinkedList<String>();
        // add elements to the linked list
        ll.add("out04.txt");
        ll.add("out13.txt");
        ll.add("out21.txt");
        ll.add("out34.txt");
        ll.add("out3.txt");
        ll.add("out20.txt");
        ll.add("out1.txt");
        /*
         * ll.add("out0.txt");
         ll.add("out1.txt");
         ll.add("out2.txt");
         ll.add("out10.txt");
         ll.add("out11.txt");
         * */

        //ll.addLast("");
        //ll.addFirst("A");
        //ll.add(1, "A2");
        //System.out.println("Original contents of ll: " + ll);

        // remove elements from the linked list
        //ll.remove("F");
        //ll.remove(2);
        //System.out.println("Contents of ll after deletion: "+ ll);

        // remove first and last elements
        //ll.removeFirst();
        //ll.removeLast();
        //System.out.println("ll after deleting first and last: "+ ll);

        // get and set a value
        int a = ll.lastIndexOf(ll.getLast());
        int num[];

        for (int i = 0; i <= a; i++) {
            Object val = ll.get(i);
            String s = (String) val;
            StringTokenizer st2 = new StringTokenizer(s, ".");
            String file = (String) st2.nextElement();
            for (int j = 0; j <= a; j++) {
                if (i != j) {
                    Object value = ll.get(j);
                    String st = (String) value;
                    //StringTokenizer stkr = new StringTokenizer(st, ".");
                    //String files= (String) stkr.nextElement();
                    if (st.indexOf(file) != -1) {
                        a--;
                        ll.remove(i);
                        i--;
                        break;
                    }
                }

            }

        }
        System.out.println("length " + a);
        //Object val = ll.get(2);
        //ll.set(2, (String) val + " Changed");
        System.out.println("ll after change: " + ll);
    }
}
