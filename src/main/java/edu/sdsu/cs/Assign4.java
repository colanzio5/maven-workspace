package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.HashTable;
import edu.sdsu.cs.datastructures.Heap;
import edu.sdsu.cs.util.IValueGenerator;
import edu.sdsu.cs.util.ListTimer;
import edu.sdsu.cs.util.NameGenerator;
import java.util.SortedSet;

import javax.print.DocFlavor.BYTE_ARRAY;

import java.util.*;

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
 * @author STUDENT NAME HERE, csscXXXX
 */
public class Assign4 {

    private static final String TIMING_CAL_FILE = "timing_cal.txt";

    private Assign4(InputStream in, OutputStream out, int gram_size) {
        System.out.print("\nAssign4 - Private Class Starting\n");
        HashTable<String, Integer> ntable = new HashTable<>();
        int n_size = gram_size;
        String gram = "";

        try {
            int size = in.available();
            System.out.print("SIZE: " + size + "\n");
            for (int i = n_size - 1; i < size; i++) {
                char c = (char) in.read();
                if (gram.length() >= gram_size) {
                    gram = gram.substring(1) + c;
                } else {
                    gram = gram + c;
                }
                //System.out.println("GRAM: " + gram + "\n");
                if( gram.matches("^.*[^a-zA-Z0-9].*$") || gram.contains("\n") || gram.contains("\r")) {
                    //System.out.println("Invalid: " + gram);
                } else {
                    //System.out.println("Valid: " + gram);
                    if (!ntable.contains(gram)) {
                        //add key, v = 1
                        ntable.add(gram, 1);
                    } else {
                        ntable.add(gram, ntable.getValue(gram) + 1);
                    }
                }

            }
            in.close();
            System.out.println("Starting file Write");
            Iterator it = ntable.keys();
            while(it.hasNext()){
                String key = (String) it.next();
                byte[] output = ("\nK: [ " + key + " ] V: " + ntable.getValue(key).toString() + " ").getBytes();
                out.write(output);
            }
            //ntable.show();

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.print("\nAssign4 - Private Class Ended\n");
    }

    private static void writeFile(List<String> data, String filename) {
        try {
            Files.write(Paths.get(filename), data, Charset.defaultCharset());
        } catch (SecurityException se) {
            System.err.println("Security Exception : " + se.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Application entry point which simply starts the security manager and
     * then creates an instance of the host class.
     *
     * @param args No runtime args
     */
    public static void main(String[] args) {

        String input_path = args[0];
        String output_path = args[1];
        int in_num = Integer.parseInt(args[2]);
        File input_file = new File(input_path);
        File output_file = new File(output_path);
        System.out.println("\nIN PATH: " + input_path + "\nOUT PATH: " + output_path + "\n");

        try {
            InputStream in = new FileInputStream(input_file);
            OutputStream out = new FileOutputStream(output_file);
            try {
                new Assign4(in, out, in_num);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("FILE ERROR: " + e + "\n---end error---\n");
        }

    }
}
