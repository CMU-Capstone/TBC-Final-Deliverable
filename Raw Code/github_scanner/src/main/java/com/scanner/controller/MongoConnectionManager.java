package com.scanner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.*;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * The utility for the project
 * */
@Controller
public class MongoConnectionManager {
	@Autowired
	private MongoClient mongoClient;
	
//	public void closeConnection() {
//		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
//        mongoLogger.setLevel(Level.OFF);
//
//        if (mongoClient != null) {
//        	mongoClient.close();
//        }
//	}
	
	public String convert(String path) {
		String prefix = "https://raw.githubusercontent.com/";
		String suffix = "/master/README.md";
		String content = path.substring(19);
		return prefix + content + suffix;
	}


    private void getExtensionCount(URL url, Map<String, Integer> map) throws IOException {
        System.out.println("!!!!" + url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "", inputLine;
        while ((inputLine = reader.readLine()) != null) {
            line += inputLine + "\n";
        }

        JSONArray array = new JSONArray(line);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            String type = object.getString("type");
            if (type.equals("file")) {
                String name = object.getString("name");
                String extension;
                if (name.contains(".")) {
                    extension = name.split("\\.")[1];
                    if (map.containsKey(extension)) {
                        map.put(extension, map.get(extension)+1);
                    } else {
                        map.put(extension, 1);
                    }
                }
            } else if (type.equals("dir")){
                String pathName = object.getString("name");
                getExtensionCount(new URL(url.toString()+"/"+pathName), map);
            }
        }
    }
	
	public void mongoSaveRepo(String hackthonName, List<String> repoLinks) {
		MongoDatabase database = mongoClient.getDatabase("hackthons");
		MongoCollection<Document> collection = database.getCollection(hackthonName);
		for (String link : repoLinks) {
			String path = convert(link);
			mongoSaveRepoHelper(path, hackthonName, collection);
		}
		System.out.println("Scan all git repos for " + hackthonName + ", and save to mongoDB successfully");
//		closeConnection();
	}
	
	public void mongoSaveRepoHelper(String path, String hacthonName, MongoCollection<Document> collection) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(path).openStream()))){
			String inputLine;
			Document doc = new Document("hackthon_name", hacthonName);
			while ((inputLine = in.readLine()) != null) {
				System.out.println("everyline: " + inputLine);
				if (inputLine.length() == 0) {
					continue;
				} else if (inputLine.startsWith("# Project name")) {
					String projectName = in.readLine();
					doc.append("project_name", projectName);
				} else if (inputLine.startsWith("# Project problem type")) {
					String problem = in.readLine();
					String[] problems = problem.split(",");
					List<String> problemsList = new ArrayList<>();
					for (String everyProblem : problems) {
						problemsList.add(everyProblem.trim());
					}
					doc.append("problem", problemsList);
				} else if (inputLine.startsWith("# Project industry")) {
					String industry = in.readLine();
					String[] industries = industry.split(",");
					List<String> industriesList = new ArrayList<>();
					for (String everyIndustry : industries) {
						industriesList.add(everyIndustry.trim());
					}
					doc.append("industry", industriesList);
				} else if (inputLine.startsWith("# Technologies used")) {
					String technogloy = in.readLine();
					String[] technologies = technogloy.split(",");
					List<String> technologiesList = new ArrayList<>();
					for (String everyTechnology : technologies) {
						technologiesList.add(everyTechnology.trim());
					}
					doc.append("technology", technologiesList);
				} else if (inputLine.startsWith("# Participants")) {
					List<String> usersList = new ArrayList<>();
					String user = in.readLine();
					System.out.println("user : " + user);
					while (user != null && user.length() != 0) {
						usersList.add(user);
						user = in.readLine();
					}
					List<String> usersListClean = new ArrayList<>();
					for (int i = 0; i < usersList.size(); i++) {
						String email = usersList.get(i).split(":")[1].trim();
						if (i != usersList.size() - 1) {
							email = email.substring(0, email.length() - 5);
						}
						usersListClean.add(email);
					}
					doc.append("email", usersListClean);
				} else if (inputLine.contains("#")){
					System.out.println("!!!!!!!!!!!" + inputLine);
					String otherHeader = inputLine.substring(2, inputLine.length());
					System.out.println("next headers: " + otherHeader);
					StringBuilder sb = new StringBuilder();
					String content = in.readLine();
					while(content != null && content.length() != 0) {
						System.out.println("content: " + content);
						sb.append(content);
						content = in.readLine();
					}
					doc.append(otherHeader, sb.toString());
				} 
			}
			
			String content = path.substring(8);
			String[] parts = content.split("/");
			
	        String userName = parts[1];
	        String projectName = parts[2];
	        
	        Map<String, Integer> map = new HashMap<>();
	        URL url2 = new URL("https://api.github.com/repos/"+userName+"/"+projectName+"/contents");
	        try {
				getExtensionCount(url2, map);
//				JSONObject output = new JSONObject(map);
				Document fileExtensions = new Document();
				for (String key: map.keySet()) {
					fileExtensions.append(key, map.get(key));
				}
				doc.append("file_extension", fileExtensions);
			} catch	(IOException io){
				System.out.println("reached connection limited for github api");
			}
			collection.insertOne(doc);
			System.out.println("Add one ducoment: " + doc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getAllHackthonNames() {
		List<String> allHackthonNames = new ArrayList<>();
		MongoDatabase database = mongoClient.getDatabase("hackthons");
		for (String everyHackthon: database.listCollectionNames()) {
			allHackthonNames.add(everyHackthon);
		}
//		closeConnection();
		Collections.sort(allHackthonNames);
		return allHackthonNames;
	}
	
	public void resetHackthon(String hackthonName) {
		try {
			MongoDatabase database = mongoClient.getDatabase("hackthons");
			MongoCollection<Document> collection = database.getCollection(hackthonName);
			collection.drop();
//			closeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Document> search(List<String> hackthon_name, String project_name, String problem, String industry, String technology, String user, String header, String data) {
		List<Document> result = new ArrayList<>();
		List<String> searchRange = new ArrayList<>();
		try {
			MongoDatabase database = mongoClient.getDatabase("hackthons");
			if (hackthon_name == null || hackthon_name.size() == 0) {
				for (String hackthonName : database.listCollectionNames()) {
					searchRange.add(hackthonName);
				}
			} else {
				searchRange = hackthon_name;
			}
			
			MongoCollection<Document> collection;
			for (String hackthon: searchRange) {
				collection = database.getCollection(hackthon);
				
				BasicDBObject andQuery = new BasicDBObject();
				List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				if (project_name != null &&  project_name.length() != 0) {
					BasicDBObject project_nameOBJ = new BasicDBObject("project_name", project_name);
					obj.add(project_nameOBJ);
				}
				
				if (problem != null && problem.length() != 0) {
					BasicDBObject problemOBJ = new BasicDBObject();
					problemOBJ.put("problem", new BasicDBObject("$regex", problem)
						.append("$options", "si"));
				    obj.add(problemOBJ);;
				}
				
				if (industry != null && industry.length() != 0) {
					BasicDBObject industryOBJ = new BasicDBObject();
					industryOBJ.put("industry", new BasicDBObject("$regex", industry)
						.append("$options", "si"));
				    obj.add(industryOBJ);
				}
				
				if (technology != null && technology.length() != 0) {
					BasicDBObject technologyOBJ = new BasicDBObject();
					technologyOBJ.put("technology", new BasicDBObject("$regex", technology)
						.append("$options", "si"));
				    obj.add(technologyOBJ);
				}
				
				if (user != null && user.length() != 0) {
					BasicDBObject userOBJ = new BasicDBObject("email", user);
					obj.add(userOBJ);
				}
				
				if (header != null && header.length() != 0 && data != null && data.length() != 0) {
					BasicDBObject regexQuery = new BasicDBObject();
					regexQuery.put(header, new BasicDBObject("$regex", data)
						.append("$options", "si"));
				    obj.add(regexQuery);
				}
				
				if (obj != null && obj.size() != 0) {
					andQuery.put("$and", obj);
//					System.out.println(andQuery.toString());
				}

				try(MongoCursor<Document> cursor = collection.find(andQuery).iterator()){
					while (cursor!= null && cursor.hasNext()) {
						result.add(cursor.next());
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
//		closeConnection();
		return result;
	}
}

