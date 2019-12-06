package com.tbc.demo.model;

public class SlackAPIData {
    private String _id;
    private String timeStamp;
    private String hackathonName;
    private String channelName;
    private String userEmail;
    private String text;
    private String userID;

    public SlackAPIData(String id, String timeStamp, String hackathonName, String channelName, String userEmail, String text, String userID) {
        this._id = id;
        this.timeStamp = timeStamp;
        this.hackathonName = hackathonName;
        this.channelName = channelName;
        this.userEmail = userEmail;
        this.text = text;
        this.userID = userID;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "SlackData{" +
                "id='" + _id + '\'' +
                ", timeStamp=" + timeStamp +
                ", hackathonName='" + hackathonName + '\'' +
                ", channelName='" + channelName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", text='" + text + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
