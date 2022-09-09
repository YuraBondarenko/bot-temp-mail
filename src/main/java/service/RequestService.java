package service;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

public class RequestService {

    public static String sendRequest(String request) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.tempmail.lol" + request).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "TempMailJavaAPI/1.0");
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder sb = new StringBuilder();
            while ((bytesRead = connection.getInputStream().read(buffer)) != -1) {
                sb.append(new String(buffer, 0, bytesRead));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
