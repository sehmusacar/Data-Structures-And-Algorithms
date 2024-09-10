public class QuickSort extends SortAlgorithm {

    public QuickSort(int[] input_array) {
        super(input_array);
    }

    private int partition(int left, int right) {
        int middle  = arr[right]; // Choose the rightmost element as middle 
        int leftIndex = left - 1; // Index for the smaller element

        // Traverse through all elements, compare each with middle 
        for (int currentElementIndex = left; currentElementIndex < right; currentElementIndex++) {
            comparison_counter++; // Increase comparison count
            // If current element is smaller than or equal to middle , swap it with the element at leftIndex
            if (arr[currentElementIndex] <= middle ) {
                leftIndex++; // Move left index to the right
                swap(leftIndex, currentElementIndex); // Swap current element with the element at leftIndex
            }
        }
        swap(leftIndex + 1, right); // Swap the middle  element with the element at leftIndex + 1
        return leftIndex + 1; // Return the partitioning index
    }

    // Recursive method to apply quicksort
    private void sort(int left, int right) {
        if (left < right) { // Base condition to end the recursion
            int partitionIndex = partition(left, right); // Partition the array and get the partition index
            sort(partitionIndex + 1, right); // Recursively sort the right part
            sort(left, partitionIndex - 1); // Recursively sort the left part
        }
    }

    @Override
    public void sort() {
        sort(0, arr.length - 1); // Start the recursive sorting process
    }

    @Override
    public void print() {
        System.out.print("Quick Sort\t=>\t");
        super.print();
    }
}
