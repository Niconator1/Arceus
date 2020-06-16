package org.nico.rest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class RestRequest {
    private String url;
    private RequestType type;
    private String payload;
    private boolean alreadySend = false;
    private String result = "";

    public RestRequest(String url, RequestType type) {
        this.url = url;
        this.type = type;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean send() throws IOException {
        if (!alreadySend) {
            if (Objects.nonNull(url)) {
                URL url = new URL(this.url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod(type.toString());
                con.setDoInput(true);
                con.setDoOutput(true);
                if (Objects.nonNull(payload)) {
                    con.connect();
                    OutputStream os = con.getOutputStream();
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
                    pw.write(payload);
                    pw.close();
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                result = content.toString();
                alreadySend = true;
                return true;
            }
        }
        return false;
    }

    public String getResult() {
        if (!alreadySend) {
            try {
                send();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
