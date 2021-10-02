package com.carcity.CarCity.Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;





@Service
public class PushNotificationUtil {
	Logger logger = LoggerFactory.getLogger(PushNotificationUtil.class);




	public void sendPushNotification(List<String> notificationTokens,String title,
			String message) {
		if(notificationTokens!=null) {
			for(String notificationToken:notificationTokens) {
				sendPushNotification(notificationToken,title,message);
			}
		}


	}
	public void sendPushNotification(String notificationToken,String title,
			String message) {
		try {
			logger.info("PushNotificationUtil "+ title + " "+ message+ " ");

			if(notificationToken!=null && !notificationToken.isEmpty()) {
				logger.info("PushNotificationUtil sending");
				title="Car City";
				logger.info("PushNotificationUtil title "+title);
				sendNotificationToAndroid(notificationToken,
						title,message);

			} 



		} catch (Exception ex) {
			logger.error("PushNotificationUtil",  ex);
		}

	}



	@SuppressWarnings("unchecked")
	public String sendNotificationToAndroid(String fcmToken,String title,
			String messagetosend) throws IOException {

		String apiKey=null;

		apiKey="AAAAhH2H33Y:APA91bEiZJoZ-qZoGc8YdhQ7D5mrrRHQcwMOMRBjrbSaBUSPMmibQ_8LsfW8xnjnZufgTqdu49XJcrMz_M96yHbo-gN3HPKYaN4CLTgFBuVGixtQp-T1hdND-6cNhxmLxn4FJ-gSO16z";


		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + apiKey);
		conn.setDoOutput(true);
		JSONObject message = new JSONObject();


		message.put("to", fcmToken);
		message.put("priority", "high");

		JSONObject data = new JSONObject();			
		data.put("body", messagetosend);
		data.put("title", title);
		



		message.put("data", data);


		JSONObject notification = new JSONObject();			
		notification.put("body", messagetosend);
		notification.put("title", title);
		


		message.put("notification", notification);



		logger.info("Message to send : " + message);

		OutputStream os = conn.getOutputStream();
		os.write(message.toString().getBytes());
		os.flush();
		os.close();

		int responseCode = conn.getResponseCode();
		logger.info("\nSending 'POST' request to URL : " + url);
		logger.info("Post parameters : " + message.toString());
		logger.info("Response Code : " + responseCode);
		logger.info("Response Code : " + conn.getResponseMessage());

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		logger.info(response.toString());

		return response.toString();


	}






}
