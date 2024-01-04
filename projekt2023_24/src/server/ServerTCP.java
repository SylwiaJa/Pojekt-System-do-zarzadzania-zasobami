package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ServerTCP {
    private static final int MAX_CLIENTS = 100;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
        try (ServerSocket serverSocket = new ServerSocket(12345);) {
            System.out.println("Serwer nasłuchuje na porcie 12345");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Połączono z " + clientSocket.getInetAddress().getHostAddress() + " " + clientSocket.getInetAddress().getHostName());
                Callable<Employee> clientHandler = new ClientHandler(clientSocket);
                FutureTask<Employee> task = new FutureTask<>(clientHandler);
                executorService.execute(task);

                try {
                    Employee result = task.get();
                    Login login = new Login();
                    login.endLogin(result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}