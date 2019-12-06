package com.tbc.demo.controller;

import com.google.gson.*;
import com.tbc.demo.util.ResponseBodyConverter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ServiceController {
    @Value("${connection.git.url}")
    private String gitLink;
    @Value("${connection.slack.url}")
    private String slackLink;

    public String[] getAllHackathons(){
        try {
            // add the parameters to search url
            String url = gitLink + "/all";

            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Origin", "*");

            if (HttpURLConnection.HTTP_OK == con.getResponseCode()){
                String respondsBody = ResponseBodyConverter.convertResponseBody(new BufferedReader(
                        new InputStreamReader(con.getInputStream())));
                return new Gson().fromJson(respondsBody, String[].class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[] {"An error has occurred when trying to connect to service, try again later"};
    }

    /**
     * This method will first hit the githubscanner to get all projects of a given problem
     * It will then get all users from that pool of projects to get all the messages they
     * sent
     * @param problem The problem that you want to target
     * @return The questions and the projects that are present
     * @throws UnsupportedEncodingException This only happens if UTF-8 is not support (in all cases this should not happen)
     */
    public Document getQuestionsByProblemAndIndustry(String problem, String industry) throws UnsupportedEncodingException {
        if (problem == null || problem.equalsIgnoreCase("null")) return new Document("error", "Please Select a Problem Type");
        if (industry == null || industry.equalsIgnoreCase("null")) return new Document("error", "Please Select a Industry");
        Document results = new Document();
        results.append("problem", problem);
        List<Document> objects = searchGitAPI(null, null, problem, industry,
                null, null, null, null);

        for (Document doc : objects){
            results.append("Questions", new Document());
            List<String> users = doc.getList("email", String.class);
            for (String user : users){
                List<Document> docList = new ArrayList<>();
                Document[] documents = searchSlackAPI(null, user, -1, -1, null, null);
                for (Document message : documents){
                    if (message.getString("hackathonName").equalsIgnoreCase(doc.getString("hackthon_name"))) docList.add(message);
                }

                results.get("Questions", Document.class).append(user, docList);
            }
        }
        return results;
    }

    /**
     * This method is will search the the github api and consolidate the technologies count
     * @param problem The problem type in question
     * @return The tech counts in document format
     * @throws UnsupportedEncodingException This only happens if UTF-8 is not support (in all cases this should not happen)
     */
    public Document getTechByProblemAndIndustry(String problem, String industry) throws UnsupportedEncodingException {
        if (problem == null || problem.equalsIgnoreCase("null")) return new Document("error", "Please Select a Problem Type");
        if (industry == null || industry.equalsIgnoreCase("null")) return new Document("error", "Please Select a Industry");
        Document results = new Document();
        results.append("problem", problem);
        List<Document> objects = searchGitAPI(null, null, problem, industry,
                null, null, null, null);

        for (Document d: objects){
            ArrayList technology = d.get("technology", ArrayList.class);
            System.out.println(technology);
            for (Object tech : technology){
                String techString = (String)tech;
                int count = results.getInteger(techString, 0);
                results.append(techString, ++count);
            }
        }

        return results;
    }

    /**
     * This is the base GitScanner search method
     * @param hackathonName The hackathon name to search for
     * @param project_name The project name to search for
     * @param problem The problem type to search for
     * @param industry The industry to search for
     * @param technology The technology to seach for
     * @param user The user to search for
     * @param header The head your want to search (data will be the target value you want to find)
     * @param data The target value in header you want to find
     * @return List of Documents representing this query results
     */
    public List<Document> searchGitAPI(String hackathonName, String project_name, String problem, String industry,
                                       String technology, String user, String header, String data){
        JsonObject body = new JsonObject();

        if(hackathonName == null || hackathonName.equalsIgnoreCase("null")) body.add("hackthon_name", JsonNull.INSTANCE);
        else body.addProperty("hackthon_name", hackathonName);

        if(project_name == null || project_name.equalsIgnoreCase("null")) body.add("project_name", JsonNull.INSTANCE);
        else body.addProperty("project_name", project_name);

        if (problem == null || problem.equalsIgnoreCase("null")) body.add("problem", JsonNull.INSTANCE);
        else body.addProperty("problem", problem);

        if(industry == null || industry.equalsIgnoreCase("null")) body.add("industry", JsonNull.INSTANCE);
        else body.addProperty("industry", industry);

        if(technology == null || technology.equalsIgnoreCase("null")) body.add("technology", JsonNull.INSTANCE);
        else body.addProperty("technology", technology);

        if(user == null || user.equalsIgnoreCase("null")) body.add("user", JsonNull.INSTANCE);
        else body.addProperty("user", user);

        if(header == null || header.equalsIgnoreCase("null")) body.add("header", JsonNull.INSTANCE);
        else body.addProperty("header", header);

        if(data == null || data.equalsIgnoreCase("null")) body.add("data", JsonNull.INSTANCE);
        else body.addProperty("data", data);

        try{
            HttpURLConnection con = (HttpURLConnection) new URL(gitLink + "/search").openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Origin", "*");
            try(OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8)){
                String value = body.toString();
                osw.write(value);
                osw.flush();
            }
            if (HttpURLConnection.HTTP_OK == con.getResponseCode()){
                String respondsBody = ResponseBodyConverter.convertResponseBody(new BufferedReader(
                        new InputStreamReader(con.getInputStream())));
                Document[] objects = new Gson().fromJson(respondsBody, Document[].class);

                return Arrays.asList(objects);
            } else return null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is the base Slack request controller
     * @param keyword Keyword to be searched for
     * @param email Email to be searched for
     * @param start Start time to search from
     * @param end End time to search from
     * @param channel Channel to search for
     * @param hackathonName Hackthon to serach for
     * @return A Document[] that represents the response
     * @throws UnsupportedEncodingException This only happens if UTF-8 is not support (in all cases this should not happen)
     */
    public Document[] searchSlackAPI(String keyword, String email, long start, long end, String channel, String hackathonName) throws UnsupportedEncodingException {
        // Setting up the parameters for the query
        // Ensure encoded and add them to parameters
        String parameters = "?";
        if (keyword != null) {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            parameters += "keyword=" + keyword;
        }
        if (email != null) {
            email = URLEncoder.encode(email, "UTF-8");
            if (parameters.charAt(parameters.length()-1) != '?') parameters += "&";
            parameters += "email=" + email;
        }
        if (channel != null){
            channel = URLEncoder.encode(channel, "UTF-8");
            if (parameters.charAt(parameters.length()-1) != '?') parameters += "&";
            parameters += "channel=" + channel;
        }
        if (hackathonName != null){
            hackathonName = URLEncoder.encode(hackathonName, "UTF-8");
            if (parameters.charAt(parameters.length()-1) != '?') parameters += "&";
            parameters += "channel=" + hackathonName;
        }

        // Handle the simple logical check for start before end
        if (start > end) return new Document[]{new Document("Error","Start and end date invalid")};
        // longs are expected to be above 0
        // if less than 0 we assume as null
        if (start >= 0) {
            if (parameters.charAt(parameters.length()-1) != '?') parameters += "&";
            parameters += "start=" + start;
        }
        if (end >= 0) {
            if (parameters.charAt(parameters.length()-1) != '?') parameters += "&";
            parameters += "end=" + end;
        }

        try {
            // add the parameters to search url
            String url = slackLink + "/search";
            if (!parameters.equals("?")) url += parameters;

            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Origin", "*");

            if (HttpURLConnection.HTTP_OK == con.getResponseCode()){
                String respondsBody = ResponseBodyConverter.convertResponseBody(new BufferedReader(
                        new InputStreamReader(con.getInputStream())));
                return new Gson().fromJson(respondsBody, Document[].class);
            } else return new Document[]{new Document("Error",con.getResponseCode()+"")};

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Document[]{new Document("Error","Service has failed to respond")};
    }
}
