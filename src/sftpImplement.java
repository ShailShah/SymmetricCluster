
import com.jcraft.jsch.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class sftpImplement {

    private String filename;
    private String command;
    private NodeData node;

    public sftpImplement(NodeData node) {
        this.node = node;

    }

    public sftpImplement(String filename, String command) {
        this.filename = filename;
        this.command = command;
        transaction();
    }

    public LinkedList<String> checkExistence() {
        Session session = null;
        Channel channel = null;
        LinkedList<String> sendBack = new LinkedList<String>();
        try {

            JSch ssh = new JSch();
            ssh.setKnownHosts("~/.ssh/known_hosts");
            session = ssh.getSession(node.name, node.inet, 22);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(node.password);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            Vector ls = sftp.ls("/home/" + node.name + "/shared");
            for (int i = 0; i < ls.size(); i++) {
                String set = tokenize(String.valueOf(ls.get(i)));
                if (!(set.contains(".") || set.contains(".."))) {
                    sendBack.addLast(set);
                }

            }
            return sendBack;

        } catch (JSchException | SftpException e) {
//            e.printStackTrace();
        } finally {
            if (channel != null) {

                channel.disconnect();
            }
            if (session != null) {

                session.disconnect();
            }
        }
        return null;
    }

    public String tokenize(String input) {
        StringTokenizer st = new StringTokenizer(input, " ");
        String s = null;
        while (st.hasMoreTokens()) {
            s = st.nextToken();
        }

        return s;
    }

    public void transaction() {
        Session session = null;
        Channel channel = null;
        NodeData node = null;


        for (int i = 0; i < ControlModule.nData.size(); i++) {

            try {
                node = ControlModule.nData.get(i);

                if (!node.name.equals(System.getProperty("user.name"))) {
                    JSch ssh = new JSch();
                    ssh.setKnownHosts("~/.ssh/known_hosts");
                    session = ssh.getSession(node.name, node.inet, 22);
                    java.util.Properties config = new java.util.Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    session.setPassword(node.password);
                    session.connect();
                    channel = session.openChannel("sftp");
                    channel.connect();
                    ChannelSftp sftp = (ChannelSftp) channel;
                    if (command.contains("send")) {
//                        System.out.println("hi");
                        sftp.put("/home/" + System.getProperty("user.name") + "/shared/" + filename, "/home/" + node.name + "/shared");
                    } else if (command.contains("delete")) {
                        sftp.rm("/home/" + node.name + "/shared/" + filename);

                    }
                }
            } catch (JSchException | SftpException e) {
//                e.printStackTrace();
            } finally {
                if (channel != null) {

                    channel.disconnect();
                }
                if (session != null) {

                    session.disconnect();
                }
            }
        }
    }
}
