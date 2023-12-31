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

            // Pytanie o login
            out.println("Podaj login:");
            String login = in.nextLine();

            // Pytanie o hasło
            out.println("Podaj hasło:");
            String password = in.nextLine();

            System.out.println("Login: " + login);
            System.out.println("Hasło: " + password);

            // Tutaj możesz dodać logikę weryfikacji loginu i hasła

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
