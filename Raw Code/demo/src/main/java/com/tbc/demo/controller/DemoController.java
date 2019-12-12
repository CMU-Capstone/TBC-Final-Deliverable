package com.tbc.demo.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("demo_controller/api")
public class DemoController {

    @Autowired
    ServiceController service;
    @Autowired
    MongoController mongoController;

    @CrossOrigin(origins = "*")
    @GetMapping("/getTop5Industry")
    public LinkedHashMap getTop5Industry(){
        return mongoController.getTop5Industry();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getTop5Technology")
    public LinkedHashMap getTop5Technology(){
        return mongoController.getTop5Technology();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/topFiveProblems")
    public LinkedHashMap getTop5Problems(){
        return mongoController.getTop5Problems();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getTotalNumberOfProjects")
    public int getTotalNumberOfProjects(){
        // Just need to return the size of the search from gitApi search
        return service.searchGitAPI(null, null, null, null,
                null, null, null ,null).size();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getQuestionsByProblems")
    public Document searchQuestionsByProblems(@RequestParam String problem, @RequestParam String industry){
        try {
            return service.getQuestionsByProblemAndIndustry(problem, industry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Handle the error - this error should never occur but if it does, the server should be in some serious trouble
        return new Document("Error", "An Error was found when fetching your request. " +
                "This error is an internal one and has to do with server encoding. Please " +
                "contact your administrator for assistance.");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getTechByProblems")
    public Document searchTechByProblems(@RequestParam String problem, @RequestParam String industry){
        try {
            return service.getTechByProblemAndIndustry(problem, industry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Handle the error - this error should never occur but if it does, the server should be in some serious trouble
        return new Document("Error", "An Error was found when fetching your request. " +
                "This error is an internal one and has to do with server encoding. Please " +
                "contact your administrator for assistance.");
    }

    /**
     * This method will be used to generate the dashboard
     * @return String array of all hackathons
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getAllHackathons")
    public String[] getAllHackathons(){
        return service.getAllHackathons();
    }

    /**
     * This method will be used to get number of unique users
     * @return String array of all hackathons
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getUniqueUserCount")
    public int getUniqueUserCount(){
        return mongoController.getAllUsers().size();
    }

    /**
     * This method will be used to get number of unique users
     * @return String array of all hackathons
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/getUniqueTechCount")
    public int getUniqueTechCount(){
        return mongoController.getUniqueTech().size();
    }
}
