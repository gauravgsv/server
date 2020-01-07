package com.citylive.server.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@RestController
@RequestMapping("/message")
public class MessagingService {
	
	@PostConstruct
	private void initializeService() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
    		    .setCredentials(GoogleCredentials.getApplicationDefault())
    		    .setDatabaseUrl("https://citylive-cb784.firebaseio.com/")
    		    .build();

    		FirebaseApp.initializeApp(options);
	}
	
	@RequestMapping("/notifyDevice/{param1}")
    public ResponseEntity sendNotificationToDevice(@PathVariable("param1") String param1) throws FirebaseMessagingException{
    	
    		// Send message to particular device id
    		String registrationToken = param1;
    		
    		Message message = Message.builder()
    		    .putData("score", "1000")
    		    .setToken(registrationToken)
    		    .build();  		
				String response = FirebaseMessaging.getInstance().send(message);
				return ResponseEntity.ok("Successfully sent message: " + response);
       
    }
	
	@RequestMapping("/notifyTopic/{param1}")
    public ResponseEntity sendNotificationToTopic(@PathVariable("param1") String param1) throws FirebaseMessagingException{
    	
		String topic = param1;

		Message message = Message.builder()
		    .putData("score", "500")
		    .setTopic(topic)
		    .build();

		// Send a message to the devices subscribed to the provided topic.
		String response = FirebaseMessaging.getInstance().send(message);
				return ResponseEntity.ok("Successfully sent message: " + response);
       
    }
}
