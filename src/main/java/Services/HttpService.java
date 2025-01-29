package Services;

import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpService {

    public String sendPostRequest(String url, String jsonInputString) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during the POST request.");
            return null;
        }
    }
}

