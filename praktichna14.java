import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Diary {

    static final int MAX_ENTRIES = 50;
    static LocalDate[] dates = new LocalDate[MAX_ENTRIES];
    static String[] texts = new String[MAX_ENTRIES];
    static int count = 0;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("1 - add entry");
            System.out.println("2 - remove entry by date");
            System.out.println("3 - list all entries");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                addEntry(sc);
            } else if (c.equals("2")) {
                removeEntry(sc);
            } else if (c.equals("3")) {
                listEntries();
            } else if (c.equals("0")) {
                run = false;
            } else {
                System.out.println("unknown command");
            }
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
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
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
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
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
            System.out.println(dates[i].format(formatter) + " 00:00");
            System.out.println(texts[i]);
        }
    }
}