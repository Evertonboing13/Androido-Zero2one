package com.example.zero2one;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.zero2one.database.UserDatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class AppInstagram extends Activity {
	
	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		List<Timeline> list = getDemoData();
		setContentView(R.layout.instagram);
		listview = (ListView) findViewById(R.id.lstcontatos);
		TimelineListadapter adapter = new TimelineListadapter(this, 
				R.layout.timeline, list);
		listview.setAdapter(adapter);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					getServerData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}}).start();
		
		loadDatabase();
		
	}
	
	public void loadDatabase() {
		UserDatabaseHelper helper = new UserDatabaseHelper(this);
		try{
			if(!helper.checkDataBase()){
				helper.destroyCurrentVersion();
				helper.createDataBase(false,true);
				System.out.println("DATABASE CREATED");
			}else{
				System.out.println("DATABASE Reuse");
			}
		}catch(Exception ers){
			ers.printStackTrace();
		}
	}

	public void getServerData() throws Exception {
		String url = "http://ip.jsontest.com/?mime=5";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		final StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(AppInstagram.this, response, Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	public List<Timeline> getDemoData(){
		List<Timeline> l = new ArrayList<Timeline>();
		
		Timeline t1 = new Timeline();
		t1.setPostId(UUID.randomUUID().toString());
		t1.setTitle("pessoa 1");
		t1.setImageUrl("https://c1.staticflickr.com/8/7019/6605050151_5f98fbe45d_m.jpg");
		t1.setComment("summer time #summer");
		t1.setBitmap(null);
		l.add(t1);
		
		Timeline t2 = new Timeline();
		t2.setPostId(UUID.randomUUID().toString());
		t2.setTitle("pessoa 2");
		t2.setImageUrl("https://c1.staticflickr.com/8/7559/16046847987_1b0782797a_h.jpg");
		t2.setComment("Roadtrip #road #funday");
		t2.setBitmap(null);
		l.add(t2);
		
		Timeline t3 = new Timeline();
		t3.setPostId(UUID.randomUUID().toString());
		t3.setTitle("pessoa 3");
		t3.setImageUrl("https://c1.staticflickr.com/4/3737/13148302505_57bf0b1aea_h.jpg");
		t3.setComment("Baby time #baby #mom");
		t3.setBitmap(null);
		l.add(t3);
		
		Timeline t4 = new Timeline();
		t4.setPostId(UUID.randomUUID().toString());
		t4.setTitle("pessoa 4");
		t4.setImageUrl("https://c1.staticflickr.com/9/8195/8149375513_c2f37308d1_b.jpg");
		t4.setComment("Success time #goAndroid");
		t4.setBitmap(null);
		l.add(t4);
		
		//https://c1.staticflickr.com/8/7019/6605050151_5f98fbe45d_m.jpg
		//https://c1.staticflickr.com/8/7559/16046847987_1b0782797a_h.jpg
		//https://c1.staticflickr.com/4/3737/13148302505_57bf0b1aea_h.jpg
		//https://c1.staticflickr.com/9/8195/8149375513_c2f37308d1_b.jpg
		return l;
	}
}
