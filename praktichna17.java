import java.util.Scanner;

class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }
}

class MyList {

    private Node head;
    private int size;

    public MyList() {
        head = null;
        size = 0;
    }

    public void add(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }

        Node node = new Node(value);

        if (index == 0) {
            node.next = head;
            head = node;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            node.next = prev.next;
            prev.next = node;
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }

        if (index == 0) {
            head = head.next;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            prev.next = prev.next.next;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    public int size() {
        return size;
    }

    public int bufferSize() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyList list = new MyList();
        boolean run = true;

        while (run) {
            System.out.println("1 - add to end");
            System.out.println("2 - add at index");
            System.out.println("3 - remove at index");
            System.out.println("4 - get element at index");
            System.out.println("5 - size");
            System.out.println("6 - buffer size");
            System.out.println("7 - print all elements");
            System.out.println("0 - exit");
            System.out.print("> ");
            String c = sc.nextLine();

            if (c.equals("1")) {
                System.out.print("value: ");
                int value = Integer.parseInt(sc.nextLine());
                list.add(value);
                System.out.println("added");
            } else if (c.equals("2")) {
                System.out.print("index: ");
                int index = Integer.parseInt(sc.nextLine());
                System.out.print("value: ");
                int value = Integer.parseInt(sc.nextLine());
                try {
                    list.add(index, value);
                    System.out.println("added");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("3")) {
                System.out.print("index: ");
                int index = Integer.parseInt(sc.nextLine());
                try {
                    list.remove(index);
                    System.out.println("removed");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("4")) {
                System.out.print("index: ");
                int index = Integer.parseInt(sc.nextLine());
                try {
                    System.out.println("value: " + list.get(index));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (c.equals("5")) {
                System.out.println("size: " + list.size());
            } else if (c.equals("6")) {
                System.out.println("buffer size: " + list.bufferSize());
            } else if (c.equals("7")) {
                if (list.size() == 0) {
                    System.out.println("empty");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(i + ": " + list.get(i));
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