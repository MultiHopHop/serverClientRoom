package com.example.serverclientroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Client extends Activity {

	private ClientManagement cm;
	private static String SERVER_IP;
	
	private TextView tv;
	
	private Handler updateConversationHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		
		updateConversationHandler = new Handler();

	}
	
	public void establish(View view) {
		EditText ip = (EditText) findViewById(R.id.editTextIP);
		SERVER_IP = ip.getText().toString();
		
		tv = (TextView) findViewById(R.id.textView1);
		tv.setText(SERVER_IP);
		new Thread(new ClientThread()).start();
		
		
	}
	
	public void startgame(){
		Intent intent = new Intent(this, ClientGrid.class);
		startActivity(intent);
	}

	class ClientThread implements Runnable {

		@Override
		public void run() {

			cm = new ClientManagement(SERVER_IP);
			
			CommunicationThread commThread = new CommunicationThread();
			new Thread(commThread).start();

		}

	}
	
	class CommunicationThread implements Runnable {

		public void run() {

			while (!Thread.currentThread().isInterrupted()) {

				String read = ClientManagement.read();

				updateConversationHandler.post(new updateUIThread(read));
				if(read.contains("go")) break;
			}
			startgame();
		}
		
		/*
		String start = "no";
		
		while(!start.contains("go")){
			start = ClientManagement.read();
		}
		startgame();*/
	}
	
	class updateUIThread implements Runnable {
		private String msg;

		public updateUIThread(String str) {
			this.msg = str;
		}

		@Override
		public void run() {
			tv.setText(tv.getText().toString()+ "\n"+msg);
		}
	}
}
