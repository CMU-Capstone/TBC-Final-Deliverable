package com.tbc.demo.controller;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * The MongoDB Controller for all Mongo logic
 * */
@Controller
public class MongoController {
	@Autowired
	private MongoClient mongoClient;

	/**
	 * Gets all users from both databases
	 * @return Set of user emails
	 */
	public Set<String> getAllUsers(){
		MongoDatabase slackDB = mongoClient.getDatabase("SlackDatabase");
		MongoCollection<Document> slackCollection = slackDB.getCollection("Messages");
		MongoCursor<Document> cursor = slackCollection.find().cursor();
		Set<String> uniqueEmails = new TreeSet<>();

		while(cursor.hasNext()){
			uniqueEmails.add(cursor.next().getString("userEmail"));
		}
		cursor.close();

		MongoDatabase gitDB = mongoClient.getDatabase("hackthons");
		MongoIterable<String> collections = gitDB.listCollectionNames();
		Bson unwind = Aggregates.unwind("$email");
		List<Bson> pipeline = Collections.singletonList(unwind);
		for (String collection : collections){
			cursor = gitDB.getCollection(collection).aggregate(pipeline).cursor();
			while(cursor.hasNext()){
				uniqueEmails.add(cursor.next().getString("email"));
			}
			cursor.close();
		}
		return uniqueEmails;
	}

	/**
	 * This Method will hit the mongoDB server to find all the unique technology being used
	 * @return Set of all the technology
	 */
	public Set<String> getUniqueTech(){
		MongoDatabase gitDB = mongoClient.getDatabase("hackthons");
		MongoIterable<String> collections = gitDB.listCollectionNames();
		Bson unwind = Aggregates.unwind("$technology");
		List<Bson> pipeline = Collections.singletonList(unwind);

		Set<String> tech = new TreeSet<>();
		for (String collection : collections){
			MongoCursor<Document> solutions = gitDB.getCollection(collection).aggregate(pipeline).cursor();
			while(solutions.hasNext()){
				tech.add(solutions.next().getString("technology"));
			}
		}
		return tech;
	}

	/**
	 * This method will get the top 5 technologies from all hackathons straight from mongodb
	 * @return A LinkedHashMap of the top 5 results if they exist
	 */
	public LinkedHashMap<String, Integer> getTop5Problems(){
		MongoDatabase gitDB = mongoClient.getDatabase("hackthons");
		MongoIterable<String> collections = gitDB.listCollectionNames();
		Bson unwind = Aggregates.unwind("$problem");
		Bson sortByCount = Aggregates.sortByCount("$problem");
		List<Bson> pipeline = Arrays.asList(unwind, sortByCount);

		HashMap<String, Integer> counts = new HashMap<>();

		for (String collection : collections){
			MongoCursor<Document> solutions = gitDB.getCollection(collection).aggregate(pipeline).cursor();
			while(solutions.hasNext()){
				Document current = solutions.next();
				Integer i = counts.get(current.getString("_id"));
				if (i == null) i = 0;
				counts.put(current.getString("_id"), ++i);
			}
		}

		return getTop5FromMap(counts, "Problems");
	}

	public LinkedHashMap<String, Integer> getTop5Technology(){
		MongoDatabase gitDB = mongoClient.getDatabase("hackthons");
		MongoIterable<String> collections = gitDB.listCollectionNames();
		Bson unwind = Aggregates.unwind("$technology");
		Bson sortByCount = Aggregates.sortByCount("$technology");
		List<Bson> pipeline = Arrays.asList(unwind, sortByCount);

		HashMap<String, Integer> counts = new HashMap<>();

		for (String collection : collections){
			MongoCursor<Document> solutions = gitDB.getCollection(collection).aggregate(pipeline).cursor();
			while(solutions.hasNext()){
				Document current = solutions.next();
				Integer i = counts.get(current.getString("_id"));
				if (i == null) i = 0;
				counts.put(current.getString("_id"), ++i);
			}
		}

		return getTop5FromMap(counts, "Technology");
	}

	public LinkedHashMap<String, Integer> getTop5Industry(){
		MongoDatabase gitDB = mongoClient.getDatabase("hackthons");
		MongoIterable<String> collections = gitDB.listCollectionNames();
		Bson unwind = Aggregates.unwind("$industry");
		Bson sortByCount = Aggregates.sortByCount("$industry");
		List<Bson> pipeline = Arrays.asList(unwind, sortByCount);

		HashMap<String, Integer> counts = new HashMap<>();

		for (String collection : collections){
			MongoCursor<Document> solutions = gitDB.getCollection(collection).aggregate(pipeline).cursor();
			while(solutions.hasNext()){
				Document current = solutions.next();
				Integer i = counts.get(current.getString("_id"));
				if (i == null) i = 0;
				counts.put(current.getString("_id"), ++i);
			}
		}

		return getTop5FromMap(counts, "Industries");
	}

	private LinkedHashMap<String, Integer> getTop5FromMap(Map<String, Integer> map, String searchTerm){
		LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
		Set<Map.Entry<String, Integer>> entries = map.entrySet();

		// Handles an empty input set and returns message to display on front end
		if (entries.size() == 0){
			linkedHashMap.put("No " + searchTerm + " found.", 0);
			return linkedHashMap;
		}
		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {

			@Override
			public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		};

		ArrayList<Map.Entry<String, Integer>> entireList = new ArrayList<>(entries);

		entireList.sort(valueComparator);

		int i = 0;
		while (i < 5 &&  i < entireList.size()){
			linkedHashMap.put(entireList.get(i).getKey(), entireList.get(i).getValue());
			i++;
		}
		return linkedHashMap;
	}


}

