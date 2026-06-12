import java.util.Scanner;
import java.util.Random;
import java.time.LocalTime;
import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("array size: ");
        int size = Integer.parseInt(sc.nextLine());

        System.out.print("min value: ");
        int min = Integer.parseInt(sc.nextLine());

        System.out.print("max value: ");
        int max = Integer.parseInt(sc.nextLine());

        System.out.println("1 - ascending");
        System.out.println("2 - descending");
        System.out.print("> ");
        String order = sc.nextLine();

        boolean ascending = order.equals("1");

        int[] original = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            original[i] = min + rand.nextInt(max - min + 1);
        }

        System.out.println("original array:");
        printArray(original);

        int[] bubbleArr = copyArray(original);
        int[] insertionArr = copyArray(original);
        int[] selectionArr = copyArray(original);

        LocalTime start = LocalTime.now();
        bubbleSort(bubbleArr, ascending);
        LocalTime end = LocalTime.now();
        System.out.println("bubble sort result:");
        printArray(bubbleArr);
        printTime(size, start, end);

        start = LocalTime.now();
        insertionSort(insertionArr, ascending);
        end = LocalTime.now();
        System.out.println("insertion sort result:");
        printArray(insertionArr);
        printTime(size, start, end);

        start = LocalTime.now();
        selectionSort(selectionArr, ascending);
        end = LocalTime.now();
        System.out.println("selection sort result:");
        printArray(selectionArr);
        printTime(size, start, end);

        sc.close();
    }

    static void bubbleSort(int[] arr, boolean ascending) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                boolean swap;
                if (ascending) {
                    swap = arr[j] > arr[j + 1];
                } else {
                    swap = arr[j] < arr[j + 1];
                }

                if (swap) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void insertionSort(int[] arr, boolean ascending) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0) {
                boolean move;
                if (ascending) {
                    move = arr[j] > key;
                } else {
                    move = arr[j] < key;
                }

                if (!move) {
                    break;
                }

                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void selectionSort(int[] arr, boolean ascending) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int targetIndex = i;

            for (int j = i + 1; j < n; j++) {
                boolean better;
                if (ascending) {
                    better = arr[j] < arr[targetIndex];
                } else {
                    better = arr[j] > arr[targetIndex];
                }

                if (better) {
                    targetIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[targetIndex];
            arr[targetIndex] = temp;
        }
    }

    static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void printTime(int size, LocalTime start, LocalTime end) {
        Duration duration = Duration.between(start, end);
        long nanos = duration.toNanos();
        long millis = nanos / 1000000;
        System.out.println("sorted " + size + " elements in " + millis + " ms (" + nanos + " ns)");
    }
}