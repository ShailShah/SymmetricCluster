
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class FileSplitter {

    private File file;
    private double[] chunkSizes;
    private int numClients;
    private long fileSize;
    private LinkedList<NodeData> actNodes;
    private String fname;

    public FileSplitter(String fname, int numClients, LinkedList<NodeData> actNodes) {

        this.file = new File(fname);
        this.fileSize = getNumInts(fname);
        this.fname = fname;

        this.numClients = numClients;
        this.actNodes = actNodes;
        chunkSizes = new double[this.numClients];
        for (int i = 0; i < numClients; i++) {
            chunkSizes[i] = 1 / ((double) numClients);
        }
        split();
    }

    public long getNumInts(String file) {
        try {
            File f = new File(file);
            long count = 0;
            Scanner in = new Scanner(new BufferedReader(new FileReader(f)));
            while (in.hasNextInt()) {
                in.nextInt();
                count++;
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void split() {
        try {
            long startindex = 0;
            long lastindex = -1;
            String[] seperated = fname.split("\\/");
            String[] seperat = seperated[4].split("\\.");
            if (seperat[0].contains("data")) {
                seperat[0] = "file";
            }
            Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
            for (int fileCount = 0; fileCount < numClients; fileCount++) {
                File newFile = new File("/home/" + System.getProperty("user.name") + "/shared/" + seperat[0] + actNodes.get(fileCount).nodeNumber + ".txt");
                BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
                int size = (int) (chunkSizes[fileCount] * fileSize);
                startindex = lastindex + 1;
                if (fileCount == numClients - 1) {
                    lastindex = fileSize - 1;
                } else {
                    lastindex = startindex + size - 1;
                }
                for (int i = 0; i <= lastindex - startindex; i++) {
                    out.write(String.valueOf(in.nextInt()) + " ");
                }
                out.close();
            }
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
