
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shail
 */
public class FileControl {

    private LinkedList<String> list;
    private LinkedList<String> files;

    public FileControl(LinkedList<String> list) {
        this.list = list;
        this.files = files;
    }

    public void check() {
        int i;
        String temp;
        String sub = "FileProcessed";
        if (list.equals("Data")) {
            FileSplitter split;
//            split = new FileSplitter("Data");
        }
        for (i = 0; i < list.size(); i++) {
            temp = list.get(i);
            if (temp.contains(sub)) {
                files.add(temp);
            }
        }
    }
}
