import java.util.Scanner;

class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String msg) {
        super(msg);
    }
}
class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String msg) {
        super(msg);
    }
}
class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
class WrongPasswordException extends Exception {
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
class MaxAttemptsExceededException extends Exception {
    public MaxAttemptsExceededException(String msg) {
        super(msg);
    }
}
class UserStorageFullException extends Exception {
    public UserStorageFullException(String msg) {
        super(msg);
    }
}
class InputReadException extends Exception {
    public InputReadException(String msg) {
        super(msg);
    }
}
public class loginapplicationpraktichna11 {

    static String[] usernames = new String[100];
    static String[] passwords = new String[100];
    static int count = 0;
    static int maxAttempts = 3;

    static void checkUsername(String name) throws InvalidUsernameException {
        if (name == null || name.length() == 0) {
            throw new InvalidUsernameException("Логiн порожнiй");
        }
        if (name.length() < 3) {
            throw new InvalidUsernameException("Логiн надто короткий, потрiбно мiнiмум 3 символи");
        }
        if (name.length() > 20) {
            throw new InvalidUsernameException("Логiн надто довгий, максимум 20 символiв");
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                throw new InvalidUsernameException("Неправильний символ: " + c);
            }
        }
    }
    static void checkPassword(String pass) throws InvalidPasswordException {
        if (pass == null || pass.length() == 0) {
            throw new InvalidPasswordException("Пароль порожнiй");
        }
        if (pass.length() < 6) {
            throw new InvalidPasswordException("Пароль надто короткий, потрiбно мiнiмум 6 символiв");
        }
        if (pass.length() > 50) {
            throw new InvalidPasswordException("Пароль надто довгий, максимум 50 символiв");
        }
        boolean letter = false;
        boolean digit = false;
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isLetter(pass.charAt(i))) letter = true;
            if (Character.isDigit(pass.charAt(i))) digit = true;
        }
        if (!letter) {
            throw new InvalidPasswordException("У паролi має бути хоча б одна буква");
        }
        if (!digit) {
            throw new InvalidPasswordException("У паролi має бути хоча б одна цифра");
        }
    }
    static int findUser(String name) throws UserNotFoundException {
        for (int i = 0; i < count; i++) {
            if (usernames[i].equals(name)) {
                return i;
            }
        }
        throw new UserNotFoundException("Користувача " + name + " не знайдено");
    }
    static void register(String name, String pass)
            throws InvalidUsernameException, InvalidPasswordException,
                   UserAlreadyExistsException, UserStorageFullException {

        if (count >= 100) {
            throw new UserStorageFullException("Мiсця немає, система заповнена");
        }
        checkUsername(name);
        checkPassword(pass);
        for (int i = 0; i < count; i++) {
            if (usernames[i].equals(name)) {
                throw new UserAlreadyExistsException("Такий користувач вже iснує");
            }
        }
        usernames[count] = name;
        passwords[count] = pass;
        count++;
        System.out.println("Реєстрацiя успiшна! Вiтаємо, " + name);
    }
    static void login(String name, String pass)
            throws InvalidUsernameException, InvalidPasswordException,
                   UserNotFoundException, WrongPasswordException {

        checkUsername(name);
        checkPassword(pass);
        int i = findUser(name);
        if (!passwords[i].equals(pass)) {
            throw new WrongPasswordException("Неправильний пароль");
        }
        System.out.println("Ви увiйшли! Привiт, " + name);
    }
    static String readInput(Scanner sc) throws InputReadException {
        try {
            return sc.nextLine().trim();
        } catch (java.util.NoSuchElementException e) {
            throw new InputReadException("Помилка читання вводу");
        } catch (IllegalStateException e) {
            throw new InputReadException("Scanner закрито");
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            register("admin", "Admin123");
            register("user1", "Pass456");
        } catch (InvalidUsernameException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (InvalidPasswordException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (UserAlreadyExistsException e) {
            System.out.println("Помилка: " + e.getMessage());
        } catch (UserStorageFullException e) {
            System.out.println("Помилка: " + e.getMessage());
        }

        System.out.println("Тестовi акаунти: admin/Admin123 | user1/Pass456");

        boolean run = true;
        while (run) {
            System.out.println("\n1 - Увiйти в акк.");
            System.out.println("2 - Зареєструватися");
            System.out.println("3 - Вийти");
            System.out.print("Вибiр: ");

            String choice;
            try {
                choice = readInput(sc);
            } catch (InputReadException e) {
                System.out.println("Помилка: " + e.getMessage());
                break;
            }

            if (choice.equals("1")) {
                System.out.print("Логiн: ");
                String name;
                try {
                    name = readInput(sc);
                } catch (InputReadException e) {
                    System.out.println("Помилка: " + e.getMessage());
                    continue;
                }
                System.out.print("Пароль: ");
                String pass;
                try {
                    pass = readInput(sc);
                } catch (InputReadException e) {
                    System.out.println("Помилка: " + e.getMessage());
                    continue;
                }

                int attempts = 0;
                boolean success = false;
                while (attempts < maxAttempts && !success) {
                    try {
                        login(name, pass);
                        success = true;
                    } catch (InvalidUsernameException e) {
                        System.out.println("Помилка: " + e.getMessage());
                        attempts++;
                    } catch (InvalidPasswordException e) {
                        System.out.println("Помилка: " + e.getMessage());
                        attempts++;
                    } catch (UserNotFoundException e) {
                        System.out.println("Помилка: " + e.getMessage());
                        attempts++;
                    } catch (WrongPasswordException e) {
                        System.out.println("Помилка: " + e.getMessage());
                        attempts++;
                    }
                    if (!success && attempts < maxAttempts) {
                        System.out.println("Залишилось спроб: " + (maxAttempts - attempts));
                        System.out.print("Логiн: ");
                        try {
                            name = readInput(sc);
                        } catch (InputReadException e) {
                            System.out.println("Помилка: " + e.getMessage());
                            break;
                        }
                        System.out.print("Пароль: ");
                        try {
                            pass = readInput(sc);
                        } catch (InputReadException e) {
                            System.out.println("Помилка: " + e.getMessage());
                            break;
                        }
                    }
                }
                if (!success) {
                    try {
                        throw new MaxAttemptsExceededException("Забагато невдалих спроб");
                    } catch (MaxAttemptsExceededException e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                }

            } else if (choice.equals("2")) {
                System.out.print("Логiн: ");
                String name;
                try {
                    name = readInput(sc);
                } catch (InputReadException e) {
                    System.out.println("Помилка: " + e.getMessage());
                    continue;
                }
                System.out.print("Пароль: ");
                String pass;
                try {
                    pass = readInput(sc);
                } catch (InputReadException e) {
                    System.out.println("Помилка: " + e.getMessage());
                    continue;
                }
                try {
                    register(name, pass);
                } catch (InvalidUsernameException e) {
                    System.out.println("Помилка логiну: " + e.getMessage());
                } catch (InvalidPasswordException e) {
                    System.out.println("Помилка паролю: " + e.getMessage());
                } catch (UserAlreadyExistsException e) {
                    System.out.println("Помилка: " + e.getMessage());
                } catch (UserStorageFullException e) {
                    System.out.println("Помилка: " + e.getMessage());
                }

            } else if (choice.equals("3")) {
                System.out.println("Пока");
                run = false;
            } else {
                System.out.println("Невiрний вибір");
            }
        }

        sc.close();
    }
}