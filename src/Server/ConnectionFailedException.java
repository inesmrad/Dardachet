package Server;

public class ConnectionFailedException extends Exception{
	public ConnectionFailedException() {
        super("Invalid operation: Connection Failed ");
    }
}
