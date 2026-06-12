import java.util.Scanner;

public class UserAuthSystem {

    static final int MAX_USERS = 15;
    static String[] names = new String[MAX_USERS];
    static String[] pass = new String[MAX_USERS];
    static int count = 0;

    static String[] banned = {"admin", "pass", "password", "qwerty", "ytrewq"};

    static class TooManyUsersException extends Exception {
        public TooManyUsersException(String m) { super(m); }
    }
    static class InvalidUsernameException extends Exception {
        public InvalidUsernameException(String m) { super(m); }
    }
    static class InvalidPasswordException extends Exception {
        public InvalidPasswordException(String m) { super(m); }
    }
    static class UserNotFoundException extends Exception {
        public UserNotFoundException(String m) { super(m); }
    }
    static class AuthenticationException extends Exception {
        public AuthenticationException(String m) { super(m); }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("1 - add user");
            System.out.println("2 - remove user");
            System.out.println("3 - login");
            System.out.println("4 - list users");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                try {
                    addUser(sc);
                } catch (TooManyUsersException | InvalidUsernameException | InvalidPasswordException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("2")) {
                try {
                    removeUser(sc);
                } catch (UserNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("3")) {
                try {
                    login(sc);
                } catch (AuthenticationException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("4")) {
                printUsers();
            } else if (c.equals("0")) {
                run = false;
            } else {
                System.out.println("unknown command");
            }
        }
        sc.close();
    }

    static void addUser(Scanner sc) throws TooManyUsersException, InvalidUsernameException, InvalidPasswordException {
        if (count >= MAX_USERS) {
            throw new TooManyUsersException("cannot add more users");
        }

        System.out.print("username: ");
        String name = sc.nextLine();
        checkName(name);

        System.out.print("password: ");
        String p = sc.nextLine();
        checkPass(p);

        int idx = -1;
        for (int i = 0; i < MAX_USERS; i++) {
            if (names[i] == null) {
                idx = i;
                break;
            }
        }

        names[idx] = name;
        pass[idx] = p;
        count++;
        System.out.println("user added");
    }

    static void checkName(String name) throws InvalidUsernameException {
        if (name.length() < 5) {
            throw new InvalidUsernameException("username must be at least 5 characters");
        }
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                throw new InvalidUsernameException("username must not contain spaces");
            }
        }
        for (int i = 0; i < MAX_USERS; i++) {
            if (names[i] != null && names[i].equals(name)) {
                throw new InvalidUsernameException("user already exists");
            }
        }
    }

    static void checkPass(String p) throws InvalidPasswordException {
        if (p.length() < 10) {
            throw new InvalidPasswordException("password must be at least 10 characters");
        }

        int digits = 0;
        boolean special = false;

        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (c == ' ') {
                throw new InvalidPasswordException("password must not contain spaces");
            }

            boolean low = c >= 'a' && c <= 'z';
            boolean up = c >= 'A' && c <= 'Z';
            boolean dig = c >= '0' && c <= '9';
            boolean spec = !low && !up && !dig;

            if (dig) digits++;
            if (spec) special = true;
        }

        if (digits < 3) {
            throw new InvalidPasswordException("password must contain at least 3 digits");
        }
        if (!special) {
            throw new InvalidPasswordException("password must contain at least 1 special character");
        }

        String low = lower(p);
        for (int i = 0; i < banned.length; i++) {
            if (has(low, banned[i])) {
                throw new InvalidPasswordException("password contains a banned word: " + banned[i]);
            }
        }
    }

    static String lower(String s) {
        char[] a = s.toCharArray();
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= 'A' && a[i] <= 'Z') a[i] += 32;
        }
        return new String(a);
    }

    static boolean has(String text, String sub) {
        for (int i = 0; i <= text.length() - sub.length(); i++) {
            boolean ok = true;
            for (int j = 0; j < sub.length(); j++) {
                if (text.charAt(i + j) != sub.charAt(j)) {
                    ok = false;
                    break;
                }
            }
            if (ok) return true;
        }
        return false;
    }

    static void removeUser(Scanner sc) throws UserNotFoundException {
        System.out.print("username to remove: ");
        String name = sc.nextLine();

        for (int i = 0; i < MAX_USERS; i++) {
            if (names[i] != null && names[i].equals(name)) {
                names[i] = null;
                pass[i] = null;
                count--;
                System.out.println("removed");
                return;
            }
        }
        throw new UserNotFoundException("no such user");
    }

    static void login(Scanner sc) throws AuthenticationException {
        System.out.print("username: ");
        String name = sc.nextLine();
        System.out.print("password: ");
        String p = sc.nextLine();

        for (int i = 0; i < MAX_USERS; i++) {
            if (names[i] != null && names[i].equals(name)) {
                if (pass[i].equals(p)) {
                    System.out.println("authenticated");
                    return;
                }
                throw new AuthenticationException("wrong password");
            }
        }
        throw new AuthenticationException("no such user");
    }

    static void printUsers() {
        if (count == 0) {
            System.out.println("no users");
            return;
        }
        for (int i = 0; i < MAX_USERS; i++) {
            if (names[i] != null) System.out.println(names[i]);
        }
    }
}