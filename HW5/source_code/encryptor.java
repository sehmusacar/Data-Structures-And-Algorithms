import java.util.Map;

public class encryptor {
	private Map<Character, Map<Character, Character>> map;
	private String key;
	private String keystream = "";
	private String plain_text;
	private String cipher_text = "";
	
	public encryptor(Map<Character, Map<Character, Character>> _map, String _key, String text) {
		this.map = _map;
		this.key = _key;
		this.plain_text = text;
	}
	
	public void encrypt() {
		// do not edit this method
		generate_keystream();
		generate_cipher_text();
	}
	
	private void generate_keystream() {
		// Verify if the key needs to be extended to match the plain text length
		if (plain_text.length() > key.length()) {
			// Extend the keystream by repeating the key until it covers the entire plain text
			while (keystream.length() < plain_text.length()) {
				keystream = keystream.concat(key);  // Concatenate key to build the keystream
			}
			// Truncate the keystream to fit the plain text size exactly
			keystream = keystream.substring(0, plain_text.length());
		} else {
			// Use only the needed segment of the key when it's already sufficient
			keystream = key.substring(0, plain_text.length());
		}
	}	
	
	private void generate_cipher_text() {
		// Loop over every character in the input text
		int i = 0;
		while (i < plain_text.length()) {
			char currentChar = plain_text.charAt(i);  // Retrieve the current character from input text
			char cipherKeyChar = keystream.charAt(i);  // Fetch the corresponding cipher key character
			// Perform encryption by utilizing a double-layered map lookup
			cipher_text = cipher_text.concat(String.valueOf(map.get(cipherKeyChar).get(currentChar)));
			i++;
		}
	}
			

	public String get_keystream() {
		return keystream;
	}
	
	public String get_cipher_text() {
		return cipher_text;
	}
}
