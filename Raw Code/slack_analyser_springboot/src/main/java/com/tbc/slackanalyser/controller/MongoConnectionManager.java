package com.tbc.slackanalyser.controller;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.*;

@Controller
public class MongoConnectionManager {

    @Autowired
    private MongoClient mongoClient;

//    public static void main(String[] args){
//        MongoDBModel mongoDBModel = new MongoDBModel();
//        mongoDBModel.deleteAll("Messages");
//    }

//    public MongoConnectionManager(){
//        final LogManager lm = LogManager.getLogManager();
//        for(final Enumeration<String> i = lm.getLoggerNames(); i.hasMoreElements(); ) {
//            lm.getLogger( i.nextElement()).setLevel( Level.OFF );
//        }
//        mongoURL = MongoURIManager.readFile();
//    }

    public void doWrite(JSONArray jsonArrayToWrite, String collectionName){
        MongoDatabase database = mongoClient.getDatabase("SlackDatabase");
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> documents = new ArrayList<Document>();
        for(int i = 0; i < jsonArrayToWrite.length(); i++){
            JSONObject tmp = jsonArrayToWrite.getJSONObject(i);
            Document doc = new Document(tmp.toMap());
            documents.add(doc);
        }
        collection.insertMany(documents);
//        mongoClient.close();
    }


    public JSONArray getCollectionData(String collectionName){
        MongoDatabase database = mongoClient.getDatabase("SlackDatabase");
        MongoCollection<Document> collection = database.getCollection(collectionName);
//        System.out.println("Here is all documents currently stored in the database.");
        JSONArray res = new JSONArray();
        try (MongoCursor<Document> cursor = collection.find().iterator()){
            while (cursor.hasNext()) {
                // parse doc to json
                res.put(new JSONObject(cursor.next().toJson()));
            }
        }
//        collection.deleteMany(new Document());
//        System.out.println(collection.count());
//        mongoClient.close();
        return res;
    }

    public void deleteAll(String collectionName){
        MongoDatabase database = mongoClient.getDatabase("SlackDatabase");
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.deleteMany(new Document());
//        mongoClient.close();
    }

    public String getNewestTimeStamp(String collectionName, String hackathonName){
        MongoDatabase database = mongoClient.getDatabase("SlackDatabase");
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document doc = collection.find(eq("hackathonName", hackathonName)).sort(Sorts.descending("timeStamp")).first();
//        mongoClient.close();
        if(doc == null){
            return "0";
        }
        JSONObject jsonObject = new JSONObject(doc.toJson());
//        System.out.println(jsonObject.get("text").toString());
        if(jsonObject.keySet().contains("timeStamp")){
            return jsonObject.get("timeStamp").toString();
        }
        else{
            return "0";
        }
    }

//    public JSONArray search(Map<String, String> allParam){
//        MongoClientURI uri = new MongoClientURI(MongoURL);
//        MongoClient mongoClient = new MongoClient(uri);
//        MongoDatabase database = mongoClient.getDatabase("SlackDatabase");
//        MongoCollection<Document> collection = database.getCollection("Messages");
//        String email = allParam.getOrDefault("email",".*");
//        String keyword = allParam.getOrDefault("keyword","");
//        String keywordRegex = ".*" + keyword + ".*";
//        String start = allParam.getOrDefault("start","0");
//        String end = allParam.getOrDefault("end",getNewestTimeStamp("Messages"));
//        MongoCursor<Document> cursor = collection.find(and(gte("timeStamp",start),lte("timeStamp",end),
//                regex("userEmail", email), regex("text", keywordRegex))).iterator();
//        JSONArray result = new JSONArray();
//        try {
//            while (cursor.hasNext()) {
//                // parse doc to json
//                result.put(new JSONObject(cursor.next().toJson()));
//            }
//        } finally {
//            cursor.close();
//        }
//        mongoClient.close();
//        return result;
//    }

    public List<Document> search(Map<String, String> allParam){
        MongoDatabase database = mongoClient.getDatabase("SlackDatabase");
        MongoCollection<Document> collection = database.getCollection("Messages");
        String email = allParam.getOrDefault("email",".*");
        String keyword = allParam.getOrDefault("keyword","");
        String keywordRegex = ".*" + keyword + ".*";
        String start = allParam.getOrDefault("start","0");
        String end = allParam.getOrDefault("end",String.valueOf(System.currentTimeMillis()/1000));
        String channel = allParam.getOrDefault("channel",".*");
        String hackathonName = allParam.getOrDefault("hackathonName",".*");
        MongoCursor<Document> cursor = collection.find(and(gte("timeStamp",start),lte("timeStamp",end),
                regex("userEmail", email), regex("text", keywordRegex),
                regex("channelName", channel),regex("hackathonName", hackathonName))).iterator();
        List<Document> result = new ArrayList<Document>();
        try {
            while (cursor.hasNext()) {
                // parse doc to json
                result.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
//        mongoClient.close();
        return result;
    }

}
