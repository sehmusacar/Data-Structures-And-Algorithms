public class SelectionSort extends SortAlgorithm {

    public SelectionSort(int[] input_array) {
        super(input_array);
    }

    @Override
    public void sort() {
        int n = arr.length; // Store the length of the array

        // Loop through each element of the array except the last one
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i; // Assume the current position is the minimum
            
            // Find the smallest element's index in the unsorted part
            for (int currentIndex = i + 1; currentIndex < n; currentIndex++) {
                comparison_counter++; // Increment comparison count for each comparison made
                if (arr[currentIndex] < arr[minIndex]) {
                    minIndex = currentIndex; // Update the minimum index if a smaller element is found
                }
            }
            // Swap the found minimum element with the current element
            swap(minIndex, i);
        }
    }

    @Override
    public void print() {
        System.out.print("Selection Sort\t=>\t");
        super.print();
    }
}
