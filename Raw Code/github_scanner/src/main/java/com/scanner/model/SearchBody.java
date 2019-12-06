package com.scanner.model;

import java.util.List;

public class SearchBody {
	List<String> hackthon_name;
	String project_name;
	String problem;
	String industry;
	String technology;
	String email;
	String header;
	String data;
	
	public List<String> getHackthon_name() {
		return hackthon_name;
	}
	public void setHackthon_name(List<String> hackthon_name) {
		this.hackthon_name = hackthon_name;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String user) {
		this.email = user;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	

}
