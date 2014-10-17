
import java.io.*;
import java.util.LinkedList;

public class Serializer {

    private LinkedList<NodeData> nData;

    public Serializer() {

        serializeAddress();
    }

    public void serializeAddress() {

        try {
            NodeData node0 = new NodeData("shail", "shail", "10.3.1.12", 0);
//            NodeData node1 = new NodeData("alekhya", "siriusblack", "10.3.1.255", 1);
            NodeData node2 = new NodeData("arnab", "idk", "10.3.1.10", 2);
//            NodeData node3 = new NodeData("pooja", "pooja", "10.3.1.151", 3);
            FileOutputStream fout = new FileOutputStream(new File("clients.ser"));
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(node0);
//            oos.writeObject(node1);
            oos.writeObject(node2);
//            oos.writeObject(node3);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<NodeData> returnAddress() {

        return this.nData;

    }
}
