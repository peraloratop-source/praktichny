import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditorAdvanced {

    static final String FILE_NAME = "data.txt";
    static final int MAX_LINES = 1000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("1 - write lines to file");
            System.out.println("2 - read file");
            System.out.println("3 - read range of lines");
            System.out.println("4 - insert line at position");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                writeLines(sc);
            } else if (c.equals("2")) {
                readFile();
            } else if (c.equals("3")) {
                readRange(sc);
            } else if (c.equals("4")) {
                insertLine(sc);
            } else if (c.equals("0")) {
                run = false;
            } else {
                System.out.println("unknown command");
            }
        }
        sc.close();
    }

    static void writeLines(Scanner sc) {
        System.out.println("enter lines, empty line to stop:");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            int lineNumber = countLines() + 1;
            String line = sc.nextLine();
            while (!line.equals("")) {
                bw.write(line);
                bw.newLine();
                System.out.println(lineNumber + " | " + line);
                lineNumber++;
                line = sc.nextLine();
            }
            System.out.println("written");
        } catch (IOException e) {
            System.out.println("error writing to file: " + e.getMessage());
        }
    }

    static void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            int lineNumber = 1;
            String line = br.readLine();
            while (line != null) {
                System.out.println(lineNumber + " | " + line);
                lineNumber++;
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + e.getMessage());
        }
    }

    static void readRange(Scanner sc) {
        System.out.print("start line: ");
        int start = Integer.parseInt(sc.nextLine());
        System.out.print("end line: ");
        int end = Integer.parseInt(sc.nextLine());

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            int lineNumber = 1;
            String line = br.readLine();
            while (line != null) {
                if (lineNumber >= start && lineNumber <= end) {
                    System.out.println(lineNumber + " | " + line);
                }
                lineNumber++;
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + e.getMessage());
        }
    }

    static void insertLine(Scanner sc) {
        String[] lines = new String[MAX_LINES];
        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            while (line != null && total < MAX_LINES) {
                lines[total] = line;
                total++;
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + e.getMessage());
            return;
        }

        System.out.print("insert at line number: ");
        int pos = Integer.parseInt(sc.nextLine());

        if (pos < 1 || pos > total + 1) {
            System.out.println("invalid line number");
            return;
        }

        System.out.print("text to insert: ");
        String newLine = sc.nextLine();

        if (total >= MAX_LINES) {
            System.out.println("file too large to insert");
            return;
        }

        for (int i = total; i >= pos; i--) {
            lines[i] = lines[i - 1];
        }
        lines[pos - 1] = newLine;
        total++;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (int i = 0; i < total; i++) {
                bw.write(lines[i]);
                bw.newLine();
                System.out.println((i + 1) + " | " + lines[i]);
            }
            System.out.println("inserted");
        } catch (IOException e) {
            System.out.println("error writing to file: " + e.getMessage());
        }
    }

    static int countLines() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = br.readLine();
            while (line != null) {
                count++;
                line = br.readLine();
            }
        } catch (IOException e) {
            return 0;
        }
        return count;
    }
}