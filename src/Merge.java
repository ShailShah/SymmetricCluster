
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
public class Merge implements Runnable {

    private String filename1;
    private String filename2;

    public Merge(String filename1, String filename2) {
        this.filename1 = "/home/" + System.getProperty("user.name") + "/shared/" + filename1;
        this.filename2 = "/home/" + System.getProperty("user.name") + "/shared/" + filename2;

    }

    public void run() {
        int i, j, t = 0;
        int dig;

        try {
            ArrayList<Integer> fin = new ArrayList<Integer>();
            Scanner file1 = new Scanner(new BufferedReader(new FileReader(new File(filename1))));
            ArrayList<Integer> arr1 = new ArrayList();

            while (file1.hasNextInt()) {
                arr1.add(file1.nextInt());
            }
            file1.close();

            Scanner file2 = new Scanner(new BufferedReader(new FileReader(new File(filename2))));
            ArrayList<Integer> arr2 = new ArrayList();

            while (file2.hasNextInt()) {
                arr2.add(file2.nextInt());
            }
            file2.close();

            fin = merge(arr1, arr2);

            BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename1)));
            for (i = 0; i < fin.size(); i++) {
                out.write(String.valueOf(fin.get(i)) + " ");
            }

            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b) {

        ArrayList<Integer> answer = new ArrayList<Integer>();
        int i = 0, j = 0, k = 0;

        while (i < a.size() && j < b.size()) {
            if (a.get(i) < b.get(j)) {
                answer.add(a.get(i++));
            } else {
                answer.add(b.get(j++));
            }
        }

        while (i < a.size()) {
            answer.add(a.get(i++));
        }


        while (j < b.size()) {
            answer.add(b.get(j++));
        }

        return answer;
    }
}
