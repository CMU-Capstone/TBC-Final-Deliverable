package com.tbc.slackanalyser.controller.rest;


import com.tbc.slackanalyser.model.AddTokenModel;
import com.tbc.slackanalyser.controller.MongoConnectionManager;
import com.tbc.slackanalyser.model.SlackModel;
import com.tbc.slackanalyser.utils.TokenManager;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("slack_analyser/api")
public class SlackController {

    @Autowired
    MongoConnectionManager connectionManager;

    @CrossOrigin(origins = "*")
    @GetMapping("test")
    public String test(){
        return "Hello World";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("update")
    public ResponseEntity<HttpStatus> add() {
        SlackModel slackModel = new SlackModel();
        TokenManager tokenManager = new TokenManager();
        JSONArray tokenList = tokenManager.readFile();
        for(int i = 0; i < tokenList.length(); i++){
            JSONObject jsonObject = tokenList.getJSONObject(i);
            String hackathonName = jsonObject.get("hackathonName").toString();
            String OAuthToken = jsonObject.get("OAuthToken").toString();
            String botToken = jsonObject.get("botToken").toString();
            try{
                JSONArray userList = slackModel.getAllUserInfo(OAuthToken);
                Map<String, String> userMap = new HashMap<>();
                for(int j = 0; j < userList.length(); j++){
                    userMap.put(userList.getJSONObject(j).get("id").toString(), userList.getJSONObject(j).get("email").toString());
                }
                String newestTimeStamp = connectionManager.getNewestTimeStamp("Messages",hackathonName);
                List<String> channelList = slackModel.getChannelNames(botToken);
                for(String channelName : channelList){
                    JSONArray messageList = slackModel.filterRawMessage(channelName, newestTimeStamp, OAuthToken, botToken, hackathonName);
                    for(int j = 0; j < messageList.length(); j++){
                        messageList.getJSONObject(j).put("userEmail", userMap.getOrDefault(messageList.getJSONObject(j).get("userID").toString(),""));
                    }
                    if(messageList.length() > 0){
                        connectionManager.doWrite( messageList,"Messages");
                    }

                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("search")
    public List<Document> searchMessage(@RequestParam Map<String,String> allParam){
        return connectionManager.search(allParam);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("getAllTokens")
    public String getAllTokens(){
        SlackModel slackModel = new SlackModel();
        JSONArray tokenList = TokenManager.readFile();
        JSONArray result = new JSONArray();
        for(int i = 0; i < tokenList.length(); i++) {
            JSONObject jsonObject = tokenList.getJSONObject(i);
            String OAuthToken = jsonObject.get("OAuthToken").toString();
            String botToken = jsonObject.get("botToken").toString();
            try {
                JSONArray userList = slackModel.getAllUserInfo(OAuthToken);
                JSONArray channelList = slackModel.getChannelList(botToken);
                jsonObject.put("valid", "true");

            } catch (Exception e) {
                jsonObject.put("valid", "false");
            } finally {
                result.put(jsonObject);
            }
        }
        return result.toString();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("updateToken")
    public ResponseEntity<HttpStatus> updateToken (@RequestBody AddTokenModel addTokenBody) {
        TokenManager tokenManager = new TokenManager();
        String hackathonName = addTokenBody.getHackathonName();
        String OAuthToken = addTokenBody.getoAuthToken();
        String botToken = addTokenBody.getBotToken();
        JSONArray jsonArray = tokenManager.readFile();
        for(int i = 0; i < jsonArray.length(); i++){
            if(jsonArray.getJSONObject(i).get("hackathonName").toString().equalsIgnoreCase(hackathonName)){
                jsonArray.remove(i);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hackathonName", hackathonName);
        jsonObject.put("OAuthToken",OAuthToken);
        jsonObject.put("botToken",botToken);
        jsonArray.put(jsonObject);
        tokenManager.writeFile(jsonArray);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
