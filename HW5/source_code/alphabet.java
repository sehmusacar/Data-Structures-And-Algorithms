import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;

public class alphabet {
	private Set<Character> english_alphabet = new LinkedHashSet<Character>();
	private Map<Character, Map<Character, Character>> map = new HashMap<Character,  Map<Character, Character>>();
	
	public alphabet() {
		// do not edit this method
		fill_english_alphabet();
		fill_map();
	}
	
	private void fill_english_alphabet() {
		// do not edit this method
		for(char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
		    english_alphabet.add(c);
		}
	}
	
	private void fill_map() {
		// Use an iterator to access elements from the set
		Iterator<Character> iterator = english_alphabet.iterator();
	
		// Calculate the size of the alphabet set
		int alphabetSize = english_alphabet.size();
	
		// Temporary storage for characters to reuse in inner loop
		Character[] alphabetArray = new Character[alphabetSize];
		int idx = 0;
		while (iterator.hasNext()) {
			alphabetArray[idx++] = iterator.next();
		}
	
		// Iterate over each character in the alphabet array
		int i = 0;
		while (i < alphabetSize) {
			char rowChar = alphabetArray[i]; // Character for the current row
			Map<Character, Character> innerMap = new HashMap<>(); // Create a new map for this row
	
			// Create the shifted character mapping for the current row
			int j = 0;
			while (j < alphabetSize) {
				int shiftedIndex = (i + j) % alphabetSize; // Calculate the shifted index
				char colChar = alphabetArray[j]; // Character for the current column
				char shiftedChar = alphabetArray[shiftedIndex]; // Shifted character based on the index
				innerMap.put(colChar, shiftedChar); // Put the column character and shifted character into the map
				j++;
			}
	
			// Put the current row character and its map into the main map
			map.put(rowChar, innerMap);
			i++;
		}
	}	
	
	public void print_map() {
		// do not edit this method
		System.out.println("*** Viegenere Cipher ***\n\n");
		System.out.println("    " + english_alphabet);
		System.out.print("    ------------------------------------------------------------------------------");
		for(Character k: map.keySet()) {
			System.out.print("\n" + k + " | ");
			System.out.print(map.get(k).values());
		}
		System.out.println("\n");
		
	}

	public Map get_map() {
		return map;
	}
}
