package com.citylive.server.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citylive.server.constants.Constants;
import com.citylive.server.pojo.Query;
import com.citylive.server.pojo.Response;
import com.citylive.server.pojo.ResponseType;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

@RestController
@RequestMapping("/message")
public class MessagingService {

	@PostConstruct
	private void initializeService() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.getApplicationDefault())
				.setDatabaseUrl("https://citylive-cb784.firebaseio.com/").build();

		FirebaseApp.initializeApp(options);
	}

	@RequestMapping("/notifyDevice/{param1}")
	public ResponseEntity sendNotificationToDevice(@PathVariable("param1") String param1)
			throws FirebaseMessagingException {

		// Send message to particular device id
		String registrationToken = param1;
        Notification notification = Notification.builder().setTitle("Notification Test").setBody("Stay alert!").build();
		Message message = Message.builder().setNotification(notification)
				.setToken(registrationToken).build();
		String response = FirebaseMessaging.getInstance().send(message);
		return ResponseEntity.ok("Successfully sent message: " + response);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/notifyTopic")
	public ResponseEntity sendNotificationToTopic(@RequestBody Response response) throws FirebaseMessagingException {

		String topic = response.getTopic();
		Message message = null;
		if (ResponseType.RESPONSE.equals(response.getType())) {
			Notification notification = Notification.builder().setTitle(Constants.RESPONSE_NOTIFICATION_TITLE).setBody(response.getResponseString()).build();
			message = Message.builder().setNotification(notification).setTopic(topic).build();
		} else {
			Notification notification = Notification.builder().setTitle("Unsubscribe").build();
			message = Message.builder().setNotification(notification).setTopic(topic).build();
		}
		// Send a message to the devices subscribed to the provided topic.
		String response_fb = FirebaseMessaging.getInstance().send(message);
		return ResponseEntity.ok("Successfully sent message: " + response_fb);

	}

	@RequestMapping(method = RequestMethod.POST, path = "/notifyDevices")
	public ResponseEntity sendNotificationToMultipleDevices(@RequestBody Query query)
			throws FirebaseMessagingException {
		// Upper limit on number of devices ids = 100
		List<String> registrationTokens = query.getDeviceIds();
		Notification notification = Notification.builder().setTitle(Constants.QUERY_NOTIFICATION_TITLE).setBody(query.getQuestion()).build();
		MulticastMessage message = MulticastMessage.builder().setNotification(notification)
                  .addAllTokens(registrationTokens).build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		// See the BatchResponse reference documentation
		// for the contents of response.
		return ResponseEntity.ok(response.getSuccessCount() + " messages were sent successfully");

	}
}
