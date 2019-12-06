package com.scanner.model;

import java.util.List;

public class AddRepoModel {
	String hackthon_name;
	List<String> gitRepos;
	
	public String getHackthon_name() {
		return hackthon_name;
	}
	public void setHackthon_name(String hackthon_name) {
		this.hackthon_name = hackthon_name;
	}
	public List<String> getGitRepos() {
		return gitRepos;
	}
	public void setGitRepos(List<String> gitRepos) {
		this.gitRepos = gitRepos;
	}
	
}
