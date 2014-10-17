
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class NetworkPing {

    public boolean pingSelf(String inet) {

        InetAddress localhost;
        try {
            NodeData node = null;
            localhost = InetAddress.getByName(inet);
            // this code assumes IPv4 is used
            byte[] ip = localhost.getAddress();

//            for (int i = 150; i <= 153; i++) {
//                ip[3] = (byte) i;
//                InetAddress address = InetAddress.getByAddress(ip);
            if (localhost.isReachable(1000)) {
                return true;
            }

        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return false;
    }

    public LinkedList<NodeData> ping() {
        LinkedList<NodeData> actNodes = new LinkedList<NodeData>();
        InetAddress localhost;
        try {
            NodeData node = null;
            localhost = InetAddress.getByName("10.3.1.151");
            // this code assumes IPv4 is used
            byte[] ip = localhost.getAddress();

            for (int i = 10; i <= 12; i++) {
                ip[3] = (byte) i;
                InetAddress address = InetAddress.getByAddress(ip);
                if (address.isReachable(1000)) {
                    for (int j = 0; j < ControlModule.nData.size(); j++) {
                        node = ControlModule.nData.get(j);
                        if (address.toString().contains(node.inet)) {
                            actNodes.add(node);
                        }
                    }

                }

            }

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return actNodes;


    }
}
