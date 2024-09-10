import java.util.Map;
import java.util.Iterator;

public class decryptor {
	private Map<Character, Map<Character, Character>> map;
	private String key;
	private String keystream = "";
	private String plain_text = "";
	private String cipher_text;
	
	public decryptor(Map<Character, Map<Character, Character>> _map, String _key, String text) {
		this.map = _map;
		this.key = _key;
		this.cipher_text = text;
	}

	public void decrypt() {
		// do not edit this method
		generate_keystream();
		generate_plain_text();
	}
	
	private void generate_keystream() {
		// Check if the cipher text is longer than the key
		if (cipher_text.length() > key.length()) {
			// Repeat the key until it matches or exceeds the length of the cipher text
			while (keystream.length() < cipher_text.length()) {
				keystream += key; // Append the key to the keystream
			}
			// Trim the keystream to match the length of the cipher text
			keystream = keystream.substring(0, cipher_text.length());
		} else {
			// If the key is already as long as or longer than the cipher text, simply trim the key
			keystream = key.substring(0, cipher_text.length());
		}
	}	
	
	private void generate_plain_text() {
		// Iterate through each character in the cipher_text
		int i = 0;
		while (i < cipher_text.length()) {
			char cipher_char = cipher_text.charAt(i); // Get the character from the cipher text at index i
			char key_char = keystream.charAt(i); // Get the corresponding character from the keystream
	
			// Use an iterator to access keys from the inner map obtained via map.get(key_char)
			Iterator<Character> keySetIterator = map.get(key_char).keySet().iterator();
			while (keySetIterator.hasNext()) {
				char row = keySetIterator.next(); // Get the next key from the iterator
	
				// Check if the mapped character matches the cipher character
				if (map.get(key_char).get(row) == cipher_char) {
					plain_text += row; // Append the decoded character to plain_text
					break; // Exit the loop once the correct character is found
				}
			}
			i++; // Increment the index to move to the next character
		}
	}	

	public String get_keystream() {
		return keystream;
	}
	
	public String get_plain_text() {
		return plain_text;
	}
}
