package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            // Create and set up a cookie manager
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            // Create the URL for the login page
            URL loginPageUrl = new URL("https://flow.polar.com/login");

            // Send a GET request to the login page
            HttpURLConnection connection = (HttpURLConnection) loginPageUrl.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the login page
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Extract the CSRF token
            String csrfToken = extractCsrfToken(response.toString());

            // Create the URL for the login
            URL loginUrl = new URL("https://flow.polar.com/login");
            HttpURLConnection postConnection = (HttpURLConnection) loginUrl.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            postConnection.setDoOutput(true);

            // Create the POST parameters
            // Enter your actual username and password here
            String postParams = "email=your-email@example.com&password=your-password&csrfToken=" + csrfToken;

            // Send the POST request
            OutputStream os = postConnection.getOutputStream();
            os.write(postParams.getBytes());
            os.flush();
            os.close();

            // Check the response code of the POST request
            int responseCode = postConnection.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create the URL for the follow-up page
                URL followUpUrl = new URL("https://flow.polar.com/diary");
                HttpURLConnection followUpConnection = (HttpURLConnection) followUpUrl.openConnection();
                followUpConnection.setRequestMethod("GET");

                // Read the response from the follow-up page
                BufferedReader inFollowUp = new BufferedReader(new InputStreamReader(followUpConnection.getInputStream()));
                StringBuilder followUpResponse = new StringBuilder();
                while ((inputLine = inFollowUp.readLine()) != null) {
                    followUpResponse.append(inputLine);
                }
                inFollowUp.close();

                // Extract the title and username from the follow-up page
                String followUpTitle = extractTitle(followUpResponse.toString());
                String followUpUserName = extractUserName(followUpResponse.toString());

                System.out.println("Follow Up Page Title: " + followUpTitle);
                System.out.println("Follow Up Page Username: " + followUpUserName);
            } else {
                System.out.println("Login not successful");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Extract the CSRF token from the HTML content
    public static String extractCsrfToken(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Element csrfTokenElement = doc.select("input[name=csrfToken]").first();

        if (csrfTokenElement != null) {
            return csrfTokenElement.val();
        }

        return "";
    }

    // Extract the title from the HTML content
    public static String extractTitle(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        return doc.title();
    }

    // Extract the username from the HTML content
    public static String extractUserName(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Elements elements = doc.getElementsByClass("main-nav__username");

        if (!elements.isEmpty()) {
            return elements.first().text();
        }
        return "";
    }
}
