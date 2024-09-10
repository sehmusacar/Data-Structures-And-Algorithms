public class MergeSort extends SortAlgorithm {

    public MergeSort(int input_array[]) {
		super(input_array);
	}

    private void merge(int left, int middle, int right) {
        int leftArraySize = middle - left + 1; // Size of the left subarray
        int rightArraySize = right - middle; // Size of the right subarray
        int[] leftArray = new int[leftArraySize]; // Temporary array for left subarray
        int[] rightArray = new int[rightArraySize]; // Temporary array for right subarray
        int i = 0; // Index for copying into the left subarray
        int j = 0; // Index for copying into the right subarray

        // Copy elements from the main array to the left subarray
        while (i < leftArraySize) {
            leftArray[i] = arr[left + i];
            i++;
        }
        // Copy elements from the main array to the right subarray
        while (j < rightArraySize) {
            rightArray[j] = arr[middle + 1 + j];
            j++;
        }
        // Initial indexes for merging process
        int leftIndex = 0, rightIndex = 0, mergedIndex = left;

        // Main loop for merging two subarrays back into the original array
        while (leftIndex < leftArraySize && rightIndex < rightArraySize) {
            comparison_counter++; // Count each comparison
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                arr[mergedIndex] = leftArray[leftIndex++]; // Take element from left subarray
            } else {
                arr[mergedIndex] = rightArray[rightIndex++]; // Take element from right subarray
            }
            mergedIndex++; // Move to the next position in the main array
        }

        // Copy the remaining elements of leftArray, if any
        while (leftIndex < leftArraySize) {
            arr[mergedIndex] = leftArray[leftIndex++];
            mergedIndex++;
        }
        // Copy the remaining elements of rightArray, if any
        while (rightIndex < rightArraySize) {
            arr[mergedIndex] = rightArray[rightIndex++];
            mergedIndex++;
        }
    }

    // Recursive method to divide the array into halves and sort them
    private void sort(int left, int right) {
        if (left < right) { // Condition to continue the division
            int middle = (left + right) / 2; // Find the middle point
            sort(middle + 1, right); // Sort the right half recursively
            sort(left, middle); // Sort the left half recursively
            merge(left, middle, right); // Merge the sorted halves
        }
    }

    @Override
    public void sort() {
        sort(0, arr.length - 1); // Start the recursive sorting process
    }

    @Override
    public void print() {
        System.out.print("Merge Sort\t=>\t");
        super.print();
    }
}
