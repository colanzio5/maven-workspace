package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.Heap;
import edu.sdsu.cs.util.IValueGenerator;
import edu.sdsu.cs.util.ListTimer;
import edu.sdsu.cs.util.NameGenerator;

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

    private Assign4(InputStream in, OutputStream out) {
        System.out.print("\nAssign4 - Private Class Starting\n");
        try {
            int size = in.available();
            for(int i = 0; i < size; i++) {
                System.out.print((char)in.read() + "  ");
             }
             in.close();
        } catch (Exception e) {
            //TODO: handle exception
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
        File input_file = new File(input_path);
        File output_file = new File(output_path);
        System.out.println("\nIN PATH: " + input_path + "\nOUT PATH: " + output_path + "\n");

        try {
            InputStream in = new FileInputStream(input_file);
            OutputStream out = new FileOutputStream(output_file);
            try{
                new Assign4(in,out);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("FILE ERROR: " + e + "\n---end error---\n");
        }


        
    }
}
