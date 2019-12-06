package com.scanner.controller.rest;

import java.util.*;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scanner.model.AddRepoModel;
import com.scanner.model.ResetHackthonoModel;
import com.scanner.model.SearchBody;
import com.scanner.controller.MongoConnectionManager;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 */
@RestController
@RequestMapping("github_scanner/api")
public class DataRestAPI {

	@Autowired
	private MongoConnectionManager connectionManager;
	
	@CrossOrigin(origins = "*")
	@PostMapping("add")
	public ResponseEntity<HttpStatus> add(@RequestBody AddRepoModel addBody) {
		String hackthonName = addBody.getHackthon_name();
		List<String> repoLinks = addBody.getGitRepos();
		connectionManager.mongoSaveRepo(hackthonName, repoLinks);
		
		System.out.println("add all repos information for hackthon " + hackthonName);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("all")
	public List<String> pollAccess() {
		return connectionManager.getAllHackthonNames();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("reset")
	public ResponseEntity<HttpStatus> reset(@RequestBody ResetHackthonoModel resetBody) {
		String hackthonName = resetBody.getHackthon_name();
		connectionManager.resetHackthon(hackthonName);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("search")
	public List<Document> search(@RequestBody SearchBody searchBody) {
		List<String> hackthonName = searchBody.getHackthon_name();
		String project_name = searchBody.getProject_name();
		String problem = searchBody.getProblem();
		String industry = searchBody.getIndustry();
		String technology = searchBody.getTechnology();
		String email = searchBody.getEmail();
		String header = searchBody.getHeader();
		String data = searchBody.getData();
		return connectionManager.search(hackthonName, project_name, problem, industry, technology, email, header, data);
	}
}










