package com.example.serverclientroom;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Grid extends Activity {
	// We don't do much here
	private TextView tv;
	private Handler updateConversationHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		
		tv = (TextView) findViewById(R.id.tv);
		updateConversationHandler = new Handler();
		
		new Thread(new CommunicationThread()).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}
	
	class CommunicationThread implements Runnable {

		public void run() {

			while (!Thread.currentThread().isInterrupted()) {

				String read = ClientManagement.read();

				updateConversationHandler.post(new updateUIThread(read));
				
			}
			
		}
		
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
