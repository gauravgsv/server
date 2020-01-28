package com.citylive.server.pojo;

import java.sql.Time;

import lombok.Data;

@Data
public class Response {
	
	private String topic;
	
	private MessageType type;
	
	private String sender;
	
	private Time messageTimestamp;
	
	private String responseString;

}
