package Client;

public class FileNotFoundException extends Exception {
	public FileNotFoundException() {
        super("Invalid operation: This file is not found in the precised directory ");
    }
}
