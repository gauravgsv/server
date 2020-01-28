package com.citylive.server.pojo;

import java.sql.Time;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
	
	private String topic;
	
	private MessageType type;
	
	private String sender;
	
	private Time messageTimestamp;
	
	private String responseString;

}
