package client;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    public static String encryptPassword(String password) {
        try {
            // Tworzymy instancję obiektu MessageDigest z algorytmem SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Przekazujemy hasło do obiektu MessageDigest z użyciem UTF-8
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Konwertujemy wynikowy bajtowy hash do postaci szesnastkowej (hex)
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Zwracamy zaszyfrowane hasło
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Tutaj możesz obsłużyć wyjątek odpowiednio do twojej aplikacji
            return null;
        }
    }
}
