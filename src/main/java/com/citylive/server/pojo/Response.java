package com.citylive.server.pojo;

import lombok.Data;

@Data
public class Response {
	
	private String topic;
	
	private ResponseType type;
	
	private String responseString;

}
