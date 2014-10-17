
import java.util.Hashtable;
import java.util.LinkedList;
import java.io.File;

public class FileMerger {

    private LinkedList<String> fqueue;

    public FileMerger(LinkedList<String> fqueue) {
        this.fqueue = fqueue;
        mergeFiles();
    }

    public void mergeFiles() {
        try {
            LinkedList<String> dqueue = new LinkedList<String>();
            String file1, file2;
            while (fqueue.size() != 1) {
                Thread[] threads = new Thread[fqueue.size() / 2];
                for (int i = 0; i < threads.length; i++) {
                    file1 = fqueue.removeFirst();
                    file2 = fqueue.removeFirst();
                    threads[i] = new Thread(new Merge(file1, file2));
                    threads[i].start();
                    fqueue.addLast(file1);
                    dqueue.addLast(file2);
                }
                if (fqueue.size() % 2 == 1) {
                    fqueue.addLast(fqueue.removeFirst());
                }
                for (int i = 0; i < threads.length; i++) {
                    threads[i].join();
                }
                int dsize = dqueue.size();
                for (int i = 0; i < dsize; i++) {
                    File f = new File("/home/" + System.getProperty("user.name") + "/shared/" + dqueue.removeFirst());
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
