import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCP {
    private static final int MAX_CLIENTS = 100;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
        try(ServerSocket serverSocket = new ServerSocket(12345);){
            System.out.println("Serwer nasłuchuje na porcie 12345");
    while (true){
        Socket clientSocket = serverSocket.accept();
        System.out.println("Połączono z "+clientSocket.getInetAddress().getHostAddress());
        ClientHandler clientHandler = new ClientHandler(clientSocket);
        executorService.execute(clientHandler);
    }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}
