import java.net.*;
import java.io.*;

public class Market {
    public Market(String address, int port) {
        try {
            Socket s = new Socket(address, port);

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("---Market: Am I Connected?---");
            pr.flush();

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();
            System.out.println("[Server: " + str + "]");
            s.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws  IOException {
        Market market = new Market("localhost", 5001);
    }
}