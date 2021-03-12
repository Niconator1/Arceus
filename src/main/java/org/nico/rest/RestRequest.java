package org.nico.rest;

import org.nico.Arceus;
import org.nico.gateway.Gateway;
import org.nico.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestRequest {

    private final String suffix;
    private final String type;
    private final String payload;
    private String response;

    public RestRequest(String suffix, String type, String payload) {
        this.type = type;
        this.suffix = suffix;
        this.payload = payload;
    }

    public boolean send() {
        Gateway gateway = Arceus.getInstance().getGateway();
        if (gateway == null) {
            System.out.println("Gateway missing");
            return false;
        }
        String address = "http://" + gateway.getFullAddress() + "/api/" + Constants.API_KEY + "/" + suffix;
        System.out.println(type + " " + address);
        URL url;
        try {
            url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(type);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);
            if (payload != null) {
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    this.response = response.toString();
                    return true;
                }
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                this.response = response.toString();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getResponse() {
        return this.response;
    }


}
