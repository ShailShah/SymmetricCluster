
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shail
 */
public class test {

    public static void main(String args[]) {
        String fname = "/home/shail/shared/file20.txt";
        String[] seperated = fname.split("\\/");
        String[] seperat = seperated[4].split("\\.");
//        long start = System.currentTimeMillis();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println(seperat[0]);

    }
}
