public class BubbleSort extends SortAlgorithm {

	public BubbleSort(int input_array[]) {
		super(input_array);
	}

    @Override
    public void sort() {
        int n = arr.length; // Length of the array
        int swap_counter = 0; // Counter to keep track of swaps
        do {
            swap_counter = 0; // Reset swap counter for each pass through the array
            for (int currentIndex = 1; currentIndex < n; currentIndex++) {
                comparison_counter++; // Increment comparison counter
                // If previous element is greater than current, swap them
                if (arr[currentIndex - 1] > arr[currentIndex]) {
                    swap(currentIndex - 1, currentIndex);
                    swap_counter += 1; // Increment swap counter
                }
            }
            n--; // Decrease n since the last element is already in place
        } while (swap_counter > 0); // Continue if there was at least one swap
    }

    @Override
    public void print() {
    	System.out.print("Bubble Sort\t=>\t");
    	super.print();
    }
}
