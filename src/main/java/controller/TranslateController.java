package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateController {

    private String translateURL = "https://script.google.com/macros/s/AKfycbwnyupGSg4_GuVoD0-uFRhfvPUPLTvNWZ6iXaAzC-Oe6uEBtx-qgC5cTrYiuZ0Dertp/exec";

    public String translateText(String langFrom, String langTo, String text) throws Exception {
        String urlStr = translateURL +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
