package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class SendRequestClass {

	private static HttpURLConnection connection;

	public static void main(String[] args) {
		SendRequestClass sendRequestClass = new SendRequestClass();
		sendRequestClass.sendRequest();
		//sendRequestClass.sendRequestByJava11();
	}

	// Using Java 1.5
	void sendRequest() {
		URL url = null;
		BufferedReader br;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {

			url = new URL("https://jsonplaceholder.typicode.com/albums");
			connection = (HttpURLConnection) url.openConnection();

			// Request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			// Response Code check

			int status = connection.getResponseCode();

			if (status > 299) {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = br.readLine()) != null) {
					responseContent.append(line);
				}
				br.close();
			} else {

				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = br.readLine()) != null) {
					responseContent.append(line);
				}
				br.close();

			}
			//	System.out.println(responseContent.toString());
			parseJson(responseContent.toString());

		} catch (MalformedURLException e) {
			// TODO: handle exception

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			url = null;
			connection.disconnect();
		}
	}

	// Using Java11
	/*
	 * Uses : Async , Less Code and Functional Programming
	 * 
	 */
	void sendRequestByJava11() {

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums"))
				.build();

		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body)
				.thenApply(SendRequestClass::parseJson).join();//.thenAccept(System.out::println).join();
	}

	public static String parseJson(String response) {

		JSONArray albums = new JSONArray(response);

		for (int i = 0; i < albums.length(); i++) {
			JSONObject albumDetails = albums.getJSONObject(i);
			
			int userId = albumDetails.getInt("userId");
			int id = albumDetails.getInt("id");
			String title = albumDetails.getString("title");
			
			System.out.println(id+" "+ userId + " " + title);
		}
		return "";
	}

}
