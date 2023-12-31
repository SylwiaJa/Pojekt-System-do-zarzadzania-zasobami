import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String login = in.nextLine();
            String password = in.nextLine();
            System.out.println("Login: " + login);
            System.out.println("Hasło: " + password);
            if(login.equals("admin"))
                out.println("admin");
            else if (login.equals("manager")) {
                out.println("manager");
            } else if (login.equals("leader")) {
                out.println("leader");
            }else if(login.equals("employee")) {
                out.println("employee");
            }else
                out.println("error");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
