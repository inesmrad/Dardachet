package Client;

public class InvalidColorException extends Exception {
	public InvalidColorException() {
        super("Invalid operation: This color can't be applied ");
    }
}
