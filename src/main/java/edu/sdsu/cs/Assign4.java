package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.HashTable;
import edu.sdsu.cs.datastructures.Heap;
import edu.sdsu.cs.util.IValueGenerator;
import edu.sdsu.cs.util.ListTimer;
import edu.sdsu.cs.util.NameGenerator;
import java.util.SortedSet;

import javax.print.DocFlavor.BYTE_ARRAY;

import java.util.*;
import java.lang.*;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.*;

/**
 * Assignment 2: Lists and inheritance
 * <p>The requirements defined on the class
 * <a href="https://edoras.sdsu.edu/~healey/cs310>website</a> define the
 * requirements for this project.</p>
 *
 * @author Colin Casazza, cssc0236
 */
public class Assign4 {

    private static final String TIMING_CAL_FILE = "timing_cal.txt";

    private Assign4(String in_path, FileOutputStream out, int n_size) {
        //System.out.print("\nNgram Search Starting - Gram Size:" + n_size + "\n");
        HashTable<String, Integer> ntable = new HashTable<>();
        String gram = "";

        /** BEGIN READING FILE */
        int c;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(in_path)));
            while ((c = in.read()) != -1) {
                char character = (char) c;
                if (n_size == 1)
                    gram = Character.toString(character);
                else {
                    gram = gram + character;
                    if (gram.length() > n_size)
                        gram = gram.substring(1);
                }
                processGram((String) gram, ntable, n_size);
            }
            in.close();
        } catch (Exception e) {
            System.out.println("read error" + e);
        }
        /** END READING FILE */

        /** BEGIN WRITE FILE */
        Iterator<String> keys = ntable.keys();
        Iterator<Integer> values = ntable.values();

        while (keys.hasNext() && values.hasNext()) {
            String k = keys.next();
            Integer v = values.next();
            String str = "K: " + k + " V: " + v + "\n";
            byte[] strToBytes = str.getBytes();
            try {
                out.write(strToBytes);
            } catch (Exception e) {
                System.out.println("write error");
            }
        }
        /** END READING FILE */
    }

    private void processGram(String gram, HashTable<String, Integer> ntable, int n_size) {
        if (gram.contains(" ") || gram.contains("\n") || gram.contains("\r") || (gram.length() != n_size))
            return;

        if(ntable.contains(gram)) {
            int new_value = ntable.getValue(gram);
            ntable.add(gram, new_value + 1);
        } else
           ntable.add(gram, 1);
    }

    /*
    * Application entry point which simply starts the security manager and
    * then creates an instance of the host class.
    *
    * @param args No runtime args
    */
    public static void main(String[] args) {

        String input_path = args[0];
        String output_path = args[1];
        int in_num = 5;

        File input_file = new File(input_path);
        File output_file = new File(output_path);

        try {
            FileOutputStream output_stream = new FileOutputStream(output_file);
            try {
                for (int i = in_num; i > 0; i--) {
                    new Assign4(input_path, output_stream, i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            output_stream.close();
        } catch (Exception e) {
            System.out.println("\nFILE ERROR: " + e + "\n---end error---\n");
        }

    }
}
