import java.net.*;
import java.io.*;

public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("[Server Started]");
            System.out.println("[Awaiting Broker Or Market Connections]");

            socket = serverSocket.accept();
            System.out.println("[Connected]");
    
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
    
            String str = bufferedReader.readLine();
            System.out.println("[---" + str + "---]");

            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            pr.println("---Yebo---");
            pr.flush();
            System.out.println("[Closing Connection]");
            socket.close();
            serverSocket.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws IOException {
        Server broker = new Server(5000);
        System.out.println("[Broker: " + broker + "]");
        Server market = new Server(5001);
        System.out.println("[Market: " + market + "]");
    }
}