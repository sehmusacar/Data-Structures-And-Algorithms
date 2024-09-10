public class preprocessor {
	private String initial_string;
	private String preprocessed_string;
		
	public preprocessor(String str) {
		this.initial_string = str;
		this.preprocessed_string = "";
	}

	public void preprocess() {
		// do not edit this method
		capitalize();
		clean();
	}
	
	private void capitalize() {
		// Convert the entire initial string to upper case for standardization
		preprocessed_string = initial_string.toUpperCase();
	}
	
	private void clean() {
		// Remove all characters from the preprocessed string that aren't uppercase letters
		preprocessed_string = preprocessed_string.replaceAll("[^A-Z]", "");
	}
	
	public String get_preprocessed_string() {
		return preprocessed_string;
	}
}
