package com.tbc.slackanalyser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlackanalyserApplication {

    public static void main(String[] args) {
//        if (args.length != 0) MongoURIManager.writeFile(args[0]);
//        else System.out.println("Default");
        SpringApplication.run(SlackanalyserApplication.class, args);
    }

}
