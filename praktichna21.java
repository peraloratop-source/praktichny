import java.util.Scanner;
import java.util.Random;

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
        int[] mergeArr = copyArray(original);
        int[] countingArr = copyArray(original);

        long start = System.nanoTime();
        bubbleSort(bubbleArr, ascending);
        long end = System.nanoTime();
        System.out.println("bubble sort result:");
        printArray(bubbleArr);
        printTime(size, start, end);

        start = System.nanoTime();
        insertionSort(insertionArr, ascending);
        end = System.nanoTime();
        System.out.println("insertion sort result:");
        printArray(insertionArr);
        printTime(size, start, end);

        start = System.nanoTime();
        selectionSort(selectionArr, ascending);
        end = System.nanoTime();
        System.out.println("selection sort result:");
        printArray(selectionArr);
        printTime(size, start, end);

        start = System.nanoTime();
        mergeArr = mergeSort(mergeArr, ascending);
        end = System.nanoTime();
        System.out.println("merge sort result:");
        printArray(mergeArr);
        printTime(size, start, end);

        start = System.nanoTime();
        countingSort(countingArr, ascending, min, max);
        end = System.nanoTime();
        System.out.println("counting sort result:");
        printArray(countingArr);
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

    static int[] mergeSort(int[] arr, boolean ascending) {
        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;

        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }
        for (int i = mid; i < arr.length; i++) {
            right[i - mid] = arr[i];
        }

        left = mergeSort(left, ascending);
        right = mergeSort(right, ascending);

        return merge(left, right, ascending);
    }

    static int[] merge(int[] left, int[] right, boolean ascending) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {
            boolean takeLeft;
            if (ascending) {
                takeLeft = left[i] <= right[j];
            } else {
                takeLeft = left[i] >= right[j];
            }

            if (takeLeft) {
                result[k] = left[i];
                i++;
            } else {
                result[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            result[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            result[k] = right[j];
            j++;
            k++;
        }

        return result;
    }

    static void countingSort(int[] arr, boolean ascending, int min, int max) {
        int range = max - min + 1;
        int[] counts = new int[range];

        for (int i = 0; i < arr.length; i++) {
            counts[arr[i] - min]++;
        }

        int index = 0;
        if (ascending) {
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < counts[i]; j++) {
                    arr[index] = i + min;
                    index++;
                }
            }
        } else {
            for (int i = range - 1; i >= 0; i--) {
                for (int j = 0; j < counts[i]; j++) {
                    arr[index] = i + min;
                    index++;
                }
            }
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

    static void printTime(int size, long start, long end) {
        long nanos = end - start;
        long millis = nanos / 1000000;
        System.out.println("sorted " + size + " elements in " + millis + " ms (" + nanos + " ns)");
    }
}