package Server;



import java.io.*;


import java.net.*;
import java.util.*;
public class ClientHandler extends Thread {

    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private static FileWriter fileWriter;

	private static String path = System.getProperty("user.home") + "\\Desktop\\";
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        try {
        	if (fileWriter == null) {
				fileWriter = new FileWriter(path + "chat4.txt", true);
			}
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
            	// writing to the file
            	fileWriter.write(msg + "\n");
				fileWriter.flush();
                for (ClientHandler client : clients) {
                    client.writer.println(msg);
					
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                writer.close();
                socket.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
}