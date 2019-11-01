import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Broker {
    private static SocketChannel socketChannel;
    private static ByteBuffer buffer;
    private static Broker broker;

    public static Broker start() {
        if(broker == null)
            broker = new Broker();
        return broker;
    }

    public static void close() throws IOException {
        socketChannel.close();
        buffer = null;
    }

    private Broker() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 5000));
            buffer = ByteBuffer.allocate(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String res = null;
        try {
            socketChannel.write(buffer);
            buffer.clear();
            socketChannel.read(buffer);
            res = new String(buffer.array()).trim();
            System.out.println("Broker : [ " + res + " ]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        Broker broker = new Broker();
    }
}