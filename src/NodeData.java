
import java.io.Serializable;
import java.net.InetAddress;

public class NodeData implements Serializable {

    String name;
    String password;
    String inet;
    int nodeNumber;

    public NodeData(String name, String password, String inet, int nodeNumber) {
        this.name = name;
        this.password = password;
        this.inet = inet;
        this.nodeNumber = nodeNumber;

    }

    @Override
    public String toString() {
        return new StringBuffer(" Name : ")
                .append(this.name)
                .append(" Password : ")
                .append(this.password)
                .append(" Inet Address: ")
                .append(this.inet)
                .append(" Node Number: ")
                .append(this.nodeNumber).toString();
    }
}
