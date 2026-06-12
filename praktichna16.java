import java.util.Scanner;

class Vector {

    private int[] data;
    private int size;

    public Vector() {
        data = new int[4];
        size = 0;
    }

    public Vector(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        data = new int[capacity];
        size = 0;
    }

    public void add(int value) {
        if (size == data.length) {
            resize();
        }
        data[size] = value;
        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }

        if (size == data.length) {
            resize();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }
        return data[index];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    private void resize() {
        int newCapacity = data.length * 2;
        int[] newData = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vector v = new Vector();
        boolean run = true;

        while (run) {
            System.out.println("1 - add to end");
            System.out.println("2 - add at index");
            System.out.println("3 - remove at index");
            System.out.println("4 - get element at index");
            System.out.println("5 - size");
            System.out.println("6 - capacity");
            System.out.println("7 - print all elements");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                System.out.print("value: ");
                int value = Integer.parseInt(sc.nextLine());
                v.add(value);
                System.out.println("added");
            } else if (c.equals("2")) {
                System.out.print("index: ");
                int index = Integer.parseInt(sc.nextLine());
                System.out.print("value: ");
                int value = Integer.parseInt(sc.nextLine());
                try {
                    v.add(index, value);
                    System.out.println("added");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("3")) {
                System.out.print("index: ");
                int index = Integer.parseInt(sc.nextLine());
                try {
                    v.remove(index);
                    System.out.println("removed");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("4")) {
                System.out.print("index: ");
                int index = Integer.parseInt(sc.nextLine());
                try {
                    System.out.println("value: " + v.get(index));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("5")) {
                System.out.println("size: " + v.size());
            } else if (c.equals("6")) {
                System.out.println("capacity: " + v.capacity());
            } else if (c.equals("7")) {
                if (v.size() == 0) {
                    System.out.println("empty");
                } else {
                    for (int i = 0; i < v.size(); i++) {
                        System.out.println(i + ": " + v.get(i));
                    }
                }
            } else if (c.equals("0")) {
                run = false;
            } else {
                System.out.println("unknown command");
            }
        }

        sc.close();
    }
}