package com.upgrad.assignment.controller;

import com.upgrad.assignment.model.CovidList;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CovidController {
    @GetMapping("/data")
    public void getCovidData() throws IOException {
        // Create a neat value object to hold the URL
        URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");

        // Open a connection(?) on the URL(?) and cast the response(??)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");

        // This line makes the request
        InputStream responseStream = connection.getInputStream();

        // Manually converting the response body InputStream to APOD using Jackson
        ObjectMapper mapper = new ObjectMapper();
        CovidList apod = mapper.readValue(responseStream, CovidList.class);

        // Finally we have the response
        System.out.println(apod);
    }
    @GetMapping("/hello/{country}")
    public String getData(@PathVariable("country") String country, @RequestParam String from, @RequestParam String to) throws IOException, JSONException {
        try {
            System.out.println("https://api.covid19api.com/country/"+country+"/status/confirmed?from="+from+"&to="+to);
//            URL url = new URL("https://api.covid19api.com/country/south-africa/status/confirmed?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z");
                String uri = "https://api.covid19api.com/country/"+country+"/status/confirmed?from="+from+"&to="+to;
            URL url = new URL(uri);
            // Open a connection(?) on the URL(?) and cast the response(??)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Now it's "open", we can set the request method, headers etc.
            connection.setRequestProperty("accept", "application/json");

            // This line makes the request
            InputStream responseStream = connection.getInputStream();


//            // Manually converting the response body InputStream to APOD using Jackson
//            ObjectMapper mapper = new ObjectMapper();
//
//            Map map = mapper.readValue(responseStream, Map.class);

            // Finally we have the response
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
//            new JSONObject(responseStrBuilder.toString());
            return responseStrBuilder.toString();
        }catch (Exception e){
            throw e;
        }
//        return "hello";
    }
}
