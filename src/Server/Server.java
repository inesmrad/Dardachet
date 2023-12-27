package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
   
    public static void main(String[] args)throws ConnectionFailedException {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(8889);
            while(true) {
                System.out.println("Waiting for clients' connection");
                socket = serverSocket.accept();
                System.out.println("Client connected !");
                ClientHandler clientThread = new ClientHandler(socket, clients);
                clients.add(clientThread);
                clientThread.start();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}