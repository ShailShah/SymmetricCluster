
import java.io.*;
import java.util.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shail
 */
public class ControlModule {

    public static LinkedList<NodeData> nData;
    public static int numClients = 0;
    public static LinkedList<NodeData> actData;

    public static void main(String args[]) {
        int count = 0;

        getClients();

        for (int i = 0; i < nData.size(); i++) {
            System.out.println(nData.get(i));
        }

        //Enter the data file
        System.out.println("sending data.txt");
        transferFilesClient("data.txt");
        sftpImplement sftp = new sftpImplement("data.txt", "send");
        System.out.println("data.txt sent");
        try {
            copyFile("program.jar", "/home/" + System.getProperty("user.name") + "/shared/program.jar");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("sent jar file to shared folder");
        sftpImplement sftp1 = new sftpImplement("program.jar", "send");
        while (checkFilesPresent() == false) {
        }
        System.out.println("both jar and input files present");
        System.out.println("Splitting Files:");
        LinkedList<NodeData> actNodes;
        //splitting the file
        NetworkPing ping = new NetworkPing();
        actNodes = ping.ping();
        if (actNodes.size() != 0) {
            FileSplitter split = new FileSplitter("/home/" + System.getProperty("user.name") + "/shared/data.txt", actNodes.size(), actNodes);
        }
        System.out.println("Splitting Files over");

        while (true) {
            System.out.println("Executing");
            LinkedList<String> beforeList = getFileList();

            //Execute the file
            executeFile();
            System.out.println("Execution over");
            //to check if this computer is present in the network
            System.out.println("starts checking for presence in computer");
            while (!checkPresence()) {
                count = 1;
            }
            System.out.println(" pc connected to network");

            if (count == 1) {
                LinkedList<NodeData> actNode;
                NetworkPing ping2 = new NetworkPing();
                actNode = ping2.ping();

                for (int i = 0; i < actNode.size(); i++) {
                    sftpImplement sftp3 = new sftpImplement(actNode.get(i));
                    LinkedList<String> v = sftp3.checkExistence();
                    LinkedList<String> list = getFileList();
                    for (int j = 0; j < list.size(); j++) {
                        if (!v.contains(list.get(j)) && list.get(j).contains("out")) {
                            sftpImplement sftp4 = new sftpImplement(list.get(j), "send");
                        }
                    }
                    for (int j = 0; j < v.size(); j++) {
                        if ((!list.contains(v.get(j))) && v.get(j).contains("file")) {
                            File f = new File(v.get(j));
                            if (f.exists()) {
                                f.delete();
                            }
                        }
                    }
                }
            } else if (count == 0) {
                LinkedList<String> afterList = getFileList();
                for (int i = 0; i < afterList.size(); i++) {
                    if (!beforeList.contains(afterList.get(i))) {
                        sftpImplement sftp2 = new sftpImplement(afterList.get(i), "send");
                    }
                }
                for (int i = 0; i < beforeList.size(); i++) {
                    if (!afterList.contains(beforeList.get(i)) && beforeList.get(i).contains("file")) {
                        sftpImplement sftp2 = new sftpImplement(beforeList.get(i), "delete");
                    }
                }
            }
            System.out.println("Starting timer");
            long startTimer = System.currentTimeMillis();
            long currentTimer = 0;
            int found = 0;
            while (currentTimer - startTimer < 120000) {
                found = 0;
                LinkedList<String> forMerge = getFileList();
                for (int i = 0; i < forMerge.size(); i++) {
                    if (forMerge.get(i).contains("file")) {
                        found = 0;
                        break;
                    }
                    found = 1;
                }
                if (found == 1) {
                    break;
                }
                currentTimer = System.currentTimeMillis();
            }
            System.out.println("Timer Ended");
            System.out.println("found:" + found);
            LinkedList<String> toBeSort = getFileList();
            if (found == 1) {
                System.out.println("Final Merging Starts");
                LinkedList<String> toBeMerged = new LinkedList<String>();
                for (int i = 0; i < toBeSort.size(); i++) {
                    if (!toBeSort.get(i).contains("out")) {
                        toBeSort.remove(i);
                    }
                }
                toBeMerged = sortAllOutput(toBeSort);
                mergeEverything(toBeMerged);
                System.out.println("Final Merging Stops");
                break;
            }

            System.out.println("Split files again");
            LinkedList<String> againList = getFileList();
            NetworkPing ping3 = new NetworkPing();
            LinkedList<NodeData> splNode = ping3.ping();
            for (int i = 0; i < againList.size(); i++) {
                if (againList.get(i).contains("file")) {
//                    System.out.println(againList.get(i));
                    FileSplitter fsplit = new FileSplitter("/home/" + System.getProperty("user.name") + "/shared/" + againList.get(i), splNode.size(), splNode);
                    break;
                }
            }
            System.out.println("Split files again over");
        }
    }

    public static LinkedList<String> sortAllOutput(LinkedList<String> inqueue) {
        int a = inqueue.lastIndexOf(inqueue.getLast());
        int num[];

        for (int i = 0; i <= a; i++) {
            Object val = inqueue.get(i);
            String s = (String) val;
            StringTokenizer st2 = new StringTokenizer(s, ".");
            String file = (String) st2.nextElement();
            for (int j = 0; j <= a; j++) {
                if (i != j) {
                    Object value = inqueue.get(j);
                    String st = (String) value;
                    //StringTokenizer stkr = new StringTokenizer(st, ".");
                    //String files= (String) stkr.nextElement();
                    if (st.indexOf(file) != -1) {
                        a--;
                        File f = new File("/home/" + System.getProperty("user.name") + "/shared/" + inqueue.get(i));
                        if (f.exists()) {
                            f.delete();
                        }
                        inqueue.remove(i);
                        i--;
                        break;
                    }
                }

            }

        }
//        System.out.println(inqueue.toString());
        return inqueue;
    }

    public static void mergeEverything(LinkedList<String> fqueue) {
        FileMerger merge = new FileMerger(fqueue);
    }

    public static LinkedList<String> getFileList() {
        // Directory path here
        LinkedList<String> fileList = new LinkedList<String>();
        String path = "/home/" + System.getProperty("user.name") + "/shared";
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        //adding all the files to sharedFiles2
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                fileList.add(files);

            }
        }
        return fileList;
    }

    public static boolean checkPresence() {
        boolean active = false;
        NodeData node;
        //check if my computer is in the network
        for (int i = 0; i < nData.size(); i++) {
            node = nData.get(i);
            if (node.name.equals(System.getProperty("user.name"))) {
                NetworkPing pingSelf = new NetworkPing();
                active = pingSelf.pingSelf(node.inet);
            }
        }
        return active;
    }

    public static boolean checkFilesPresent() {
        LinkedList<String> sharedFiles1 = new LinkedList<String>();
        String path = "/home/" + System.getProperty("user.name") + "/shared/";

        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        //adding all the files to sharedFiles2
        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                sharedFiles1.add(files);

            }
        }
        for (int i = 0; i < sharedFiles1.size(); i++) {
            if (sharedFiles1.contains("data.txt") && sharedFiles1.contains("program.jar")) {
                return true;
            }
        }
        return false;
    }

    public static void executeFile() {
        NodeData node = null;
        LinkedList<String> list = getFileList();
        for (int i = 0; i < nData.size(); i++) {
            node = nData.get(i);
            if (node.name.equals(System.getProperty("user.name"))) {

                try {
                    for (int j = 0; j < list.size(); j++) {
                        String s = list.get(j);

                        if (s.contains("file") && (int) s.charAt(s.length() - 5) - 48 == node.nodeNumber) {
                            Process p = Runtime.getRuntime().exec("java -jar " + "/home/" + System.getProperty("user.name") + "/shared/program.jar " + "/home/" + System.getProperty("user.name") + "/shared/" + s);
                            p.waitFor();
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void transferFilesClient(String file1) {
        try {
            int i;
            Scanner file = new Scanner(new BufferedReader(new FileReader(new File(file1))));
            ArrayList<Integer> arr1 = new ArrayList();

            while (file.hasNextInt()) {
                arr1.add(file.nextInt());
            }
            file.close();


            BufferedWriter out = new BufferedWriter(new FileWriter(new File("/home/" + System.getProperty("user.name") + "/shared/" + file1)));
            for (i = 0; i < arr1.size(); i++) {
                out.write(String.valueOf(arr1.get(i)) + " ");
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(String sou, String destin) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        File source = new File(sou);
        File dest = new File(destin);

        input = new FileInputStream(source);
        output = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }

        input.close();
        output.close();

    }

    public static void getClients() {
        Serializer serial = new Serializer();
        nData = serial.returnAddress();
        Deserializer deserial = new Deserializer("clients.ser");
        nData = deserial.deserialzeAddress();
    }
}
