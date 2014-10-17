
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class Deserializer {

    private String filename;
    private LinkedList<NodeData> nodeQueue = new LinkedList<NodeData>();

    Deserializer(String filename) {
        this.filename = filename;

    }

    public LinkedList<NodeData> deserialzeAddress() {
        NodeData node;

        try {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            try {
                while ((node = (NodeData) ois.readObject()) != null) {
                    nodeQueue.add(node);


                }
            } catch (EOFException e) {
                ois.close();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return nodeQueue;
    }
}
