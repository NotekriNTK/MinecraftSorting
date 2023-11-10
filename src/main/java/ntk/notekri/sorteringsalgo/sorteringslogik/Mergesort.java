package ntk.notekri.sorteringsalgo.sorteringslogik;

import java.util.List;

public class Mergesort {
    public static void mergeSort(int[] arr, List<int[]> order) {
        if (arr == null) {
            return;
        }

        int n = arr.length;
        if (n <= 1) {
            return; // Array with 0 or 1 element is already sorted
        }

        int[] left = new int[n / 2];
        int[] right = new int[n - n / 2];

        // Divide the array into two halves
        System.arraycopy(arr, 0, left, 0, n / 2);
        System.arraycopy(arr, n / 2, right, 0, n - n / 2);

        // Recursively sort the two halves
        mergeSort(left, order);
        mergeSort(right, order);

        // Merge the sorted halves
        merge(arr, left, right,order);
    }

    private static void merge(int[] arr, int[] left, int[] right, List<int[]> order) {
        int leftLength = left.length;
        int rightLength = right.length;
        int i = 0, j = 0, k = 0;
        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            int[] set = {k,arr[k]};
            order.add(set);
            k++;
        }

        while (i < leftLength) {
            arr[k] = left[i];
            int[] set = {k,arr[k]};
            order.add(set);
            i++;
            k++;
        }

        while (j < rightLength) {
            arr[k] = right[j];
            int[] set = {k,arr[k]};
            order.add(set);
            j++;
            k++;
        }
    }
}
