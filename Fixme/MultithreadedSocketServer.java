import java.net.*;
import java.io.*;

public class MultithreadedSocketServer {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocket server = new ServerSocket(5000);
            int counter = 0;
            System.out.println("Server Started ....");
            while (true) {
                counter++;
                Socket serverClient = server.accept(); // server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                ServerClientThread sct = new ServerClientThread(serverClient, counter); // send the request to a separate thread
                sct.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}