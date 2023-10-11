package com.noak.maven.httpsDemo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {
	
	public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Insert the user's login name: ");
		
		try {
			HttpRequest request = HttpRequest.newBuilder(new URI("https://api.github.com/users/"+scan.nextLine())).GET().build();
			HttpResponse<String> res = client.send(request, BodyHandlers.ofString());
			
			Gson gson = new Gson();
			GithubUser githubUser = gson.fromJson(res.body(), GithubUser.class);
			
			URL url = new URL(githubUser.avatar_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream fis = conn.getInputStream();
			FileOutputStream fos =  new FileOutputStream("/Users/DELL/Pictures/githubPics/"+githubUser.login+".jpg");
			byte datas[] = new byte[30002];
			int data;
			while ((data=fis.read(datas)) !=-1) {
				fos.write(datas,0,data);
			}
			System.out.println("Done");
			
		} catch (URISyntaxException | IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	
		scan.close();
	}

}
