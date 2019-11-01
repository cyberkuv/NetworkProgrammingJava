import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static final String POISON_PILL = "POISON_PILL";

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        System.out.println("Server : [ Started ]");
        serverSocketChannel.bind(new InetSocketAddress("localhost", 5000));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(256);

        System.out.println("Server : [ Awaiting Incoming Connections ]");
        while(true) {
            selector.select();
            Set <SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator <SelectionKey> iterator = selectedKeys.iterator();

            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if(key.isAcceptable()) {
                    register(selector, serverSocketChannel);
                }
                if(key.isReadable()) {
                    answer(buffer, key);
                }
                iterator.remove();
            }
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("Server : [ Connection Accepted ]");
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public static void answer(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel)key.channel();
        socketChannel.read(buffer);
        if(new String(buffer.array()).trim().equals(POISON_PILL)) {
            socketChannel.close();
            System.out.println("Server: [ No Longer Accepting Connections ]");
        }
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = Server.class.getCanonicalName();

        ProcessBuilder processBuilder = new ProcessBuilder(javaBin, "-cp", classpath, className);
        return processBuilder.start();
    }
}