package com.citylive.server.pojo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Query {
	
	private String question;
	
	private String topic;
	
	private ArrayList<String> deviceIds;

}
