import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class TextEditor {

    static final String FILE_NAME = "data.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("1 - write to file");
            System.out.println("2 - read file");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                writeToFile(sc);
            } else if (c.equals("2")) {
                readFile();
            } else if (c.equals("0")) {
                run = false;
            } else {
                System.out.println("unknown command");
            }
        }
        sc.close();
    }

    static void writeToFile(Scanner sc) {
        System.out.print("enter line: ");
        String line = sc.nextLine();

        FileWriter fw = null;
        try {
            fw = new FileWriter(FILE_NAME, true);
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                fw.write(chars[i]);
            }
            fw.write('\n');
            System.out.println("written");
        } catch (IOException e) {
            System.out.println("error writing to file: " + e.getMessage());
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    System.out.println("error closing file: " + e.getMessage());
                }
            }
        }
    }

    static void readFile() {
        FileReader fr = null;
        try {
            fr = new FileReader(FILE_NAME);
            int data = fr.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fr.read();
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + e.getMessage());
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println("error closing file: " + e.getMessage());
                }
            }
        }
    }
}