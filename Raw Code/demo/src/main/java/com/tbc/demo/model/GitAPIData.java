package com.tbc.demo.model;

import java.util.Arrays;

public class GitAPIData {
    private String id;
    private double timeStamp;
    private String hackathonName;
    private String channelName;
    private String userEmail;
    private String text;
    private String[] userIDs;

    public GitAPIData(String id, double timeStamp, String hackathonName, String channelName, String userEmail, String text, String[] userIDs) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.hackathonName = hackathonName;
        this.channelName = channelName;
        this.userEmail = userEmail;
        this.text = text;
        this.userIDs = userIDs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHackathonName() {
        return hackathonName;
    }

    public void setHackathonName(String hackathonName) {
        this.hackathonName = hackathonName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(String[] userIDs) {
        this.userIDs = userIDs;
    }

    @Override
    public String toString() {
        return "GitData{" +
                "id='" + id + '\'' +
                ", timeStamp=" + timeStamp +
                ", hackathonName='" + hackathonName + '\'' +
                ", channelName='" + channelName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", text='" + text + '\'' +
                ", userIDs='" + Arrays.toString(userIDs) + '\'' +
                '}';
    }
}
