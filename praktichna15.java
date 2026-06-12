import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Diary {

    static final int MAX_ENTRIES = 50;
    static LocalDateTime[] dates = new LocalDateTime[MAX_ENTRIES];
    static String[] texts = new String[MAX_ENTRIES];
    static int count = 0;

    static final DateTimeFormatter storageFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    static DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    static String filePath = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1 - create new diary");
        System.out.println("2 - load existing diary");
        System.out.print("> ");
        String startChoice = sc.nextLine();

        if (startChoice.equals("2")) {
            System.out.print("file path: ");
            filePath = sc.nextLine();
            loadDiary();
        } else {
            System.out.print("new file path: ");
            filePath = sc.nextLine();
        }

        boolean run = true;

        while (run) {
            System.out.println("1 - add entry");
            System.out.println("2 - remove entry by date");
            System.out.println("3 - list all entries");
            System.out.println("4 - set display date format");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                addEntry(sc);
            } else if (c.equals("2")) {
                removeEntry(sc);
            } else if (c.equals("3")) {
                listEntries();
            } else if (c.equals("4")) {
                setDisplayFormat(sc);
            } else if (c.equals("0")) {
                run = false;
            } else {
                System.out.println("unknown command");
            }
        }

        System.out.print("save diary before exit? (y/n): ");
        String save = sc.nextLine();
        if (save.equals("y")) {
            System.out.print("file path to save (empty for same path): ");
            String path = sc.nextLine();
            if (!path.equals("")) {
                filePath = path;
            }
            saveDiary();
        }

        sc.close();
    }

    static void addEntry(Scanner sc) {
        if (count >= MAX_ENTRIES) {
            System.out.println("diary is full");
            return;
        }

        System.out.print("date (dd.MM.yyyy): ");
        String dateStr = sc.nextLine();

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(dateStr + " 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("invalid date format");
            return;
        }

        System.out.println("enter entry text, empty line to finish:");
        String text = "";
        String line = sc.nextLine();
        boolean first = true;
        while (!line.equals("")) {
            if (first) {
                text = line;
                first = false;
            } else {
                text = text + "\n" + line;
            }
            line = sc.nextLine();
        }

        if (text.equals("")) {
            System.out.println("entry text cannot be empty");
            return;
        }

        dates[count] = date;
        texts[count] = text;
        count++;
        System.out.println("entry added");
    }

    static void removeEntry(Scanner sc) {
        System.out.print("date to remove (dd.MM.yyyy): ");
        String dateStr = sc.nextLine();

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(dateStr + " 00:00", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("invalid date format");
            return;
        }

        int idx = -1;
        for (int i = 0; i < count; i++) {
            if (dates[i].equals(date)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("no entry for that date");
            return;
        }

        for (int i = idx; i < count - 1; i++) {
            dates[i] = dates[i + 1];
            texts[i] = texts[i + 1];
        }
        dates[count - 1] = null;
        texts[count - 1] = null;
        count--;
        System.out.println("entry removed");
    }

    static void listEntries() {
        if (count == 0) {
            System.out.println("no entries");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(dates[i].format(displayFormat));
            System.out.println(texts[i]);
        }
    }

    static void setDisplayFormat(Scanner sc) {
        System.out.println("1 - dd.MM.yyyy HH:mm");
        System.out.println("2 - yyyy-MM-dd HH:mm");
        System.out.println("3 - MMM d, yyyy HH:mm");
        System.out.println("4 - custom pattern");
        System.out.print("> ");
        String choice = sc.nextLine();

        String pattern;
        if (choice.equals("1")) {
            pattern = "dd.MM.yyyy HH:mm";
        } else if (choice.equals("2")) {
            pattern = "yyyy-MM-dd HH:mm";
        } else if (choice.equals("3")) {
            pattern = "MMM d, yyyy HH:mm";
        } else if (choice.equals("4")) {
            System.out.print("enter custom pattern: ");
            pattern = sc.nextLine();
        } else {
            System.out.println("unknown choice");
            return;
        }

        try {
            DateTimeFormatter test = DateTimeFormatter.ofPattern(pattern);
            if (count > 0) {
                dates[0].format(test);
            }
            displayFormat = test;
            System.out.println("format set");
        } catch (IllegalArgumentException e) {
            System.out.println("invalid pattern: " + e.getMessage());
        }
    }

    static void saveDiary() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (int i = 0; i < count; i++) {
                bw.write(dates[i].format(storageFormat));
                bw.newLine();
                bw.write(texts[i]);
                bw.newLine();
                bw.newLine();
            }
            System.out.println("diary saved to " + filePath);
        } catch (IOException e) {
            System.out.println("error saving diary: " + e.getMessage());
        }
    }

    static void loadDiary() {
        File f = new File(filePath);
        if (!f.exists()) {
            System.out.println("file does not exist, starting new diary");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                if (line.equals("")) {
                    line = br.readLine();
                    continue;
                }

                LocalDateTime date;
                try {
                    date = LocalDateTime.parse(line, storageFormat);
                } catch (DateTimeParseException e) {
                    System.out.println("error parsing date in file: " + e.getMessage());
                    return;
                }

                String text = "";
                boolean first = true;
                line = br.readLine();
                while (line != null && !line.equals("")) {
                    if (first) {
                        text = line;
                        first = false;
                    } else {
                        text = text + "\n" + line;
                    }
                    line = br.readLine();
                }

                if (count >= MAX_ENTRIES) {
                    System.out.println("too many entries in file, stopping at " + MAX_ENTRIES);
                    return;
                }

                dates[count] = date;
                texts[count] = text;
                count++;

                line = br.readLine();
            }
            System.out.println("diary loaded, entries: " + count);
        } catch (IOException e) {
            System.out.println("error loading diary: " + e.getMessage());
        }
    }
}