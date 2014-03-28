package com.example.serverclientroom;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Server extends Activity {
	
	ServerManagement sm;

	Handler updateConversationHandler;
	Handler playerConnectedHandler;

	Thread serverThread = null;

	private TextView text;
	private TextView connected;
	//private int numOfPlayers = 0;
	
	//ArrayList<Socket> Socketlist;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);

		text = (TextView) findViewById(R.id.text2);
		connected = (TextView) findViewById(R.id.textViewconnected);
		//Socketlist = new ArrayList<Socket>();
		
		playerConnectedHandler = new Handler();
		updateConversationHandler = new Handler();

		this.serverThread = new Thread(new ServerThread());
		this.serverThread.start();

	}

	@Override
	protected void onStop() {
		super.onStop();
		sm.stop();
	}

	class ServerThread implements Runnable {

		public void run() {
			
			sm = new ServerManagement();
			
			boolean accepted = sm.accept();
			if(accepted) playerConnectedHandler.post(new updatePlayerConnected());
			
			CommunicationThread commThread = new CommunicationThread();
			new Thread(commThread).start();
			
			ServerManagement.write("Connected to server.");
    	    
			
			
			/*while (!Thread.currentThread().isInterrupted()) {
				if(numOfPlayers >2) break;

				try {

					Socket newSocket = serverSocket.accept();
					Socketlist.add(newSocket);

					writer = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(newSocket.getOutputStream())),
							true);
		    	    
					writer.println("Connected to server.");
					
					connected.setText(connected.getText().toString() + "\n" + numOfPlayers + " Player connected");

					CommunicationThread commThread = new CommunicationThread(newSocket);
					new Thread(commThread).start();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
		}
	}

	class CommunicationThread implements Runnable {

		public void run() {

			while (!Thread.currentThread().isInterrupted()) {

				String read = ServerManagement.read();

				updateConversationHandler.post(new updateUIThread(read));

			}
		}

	}

	class updatePlayerConnected implements Runnable {

		@Override
		public void run() {
			connected.setText(connected.getText().toString() + "\n" + " 1 Player connected");
		}
	}
	
	class updateUIThread implements Runnable {
		private String msg;

		public updateUIThread(String str) {
			this.msg = str;
		}

		@Override
		public void run() {
			text.setText(text.getText().toString()+ "\n"+"Player Says: "+ msg );
		}
	}
	
	
	public String getLocalIpAddress() {
	    try {
	    	for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                String ipv4;
					if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = inetAddress.getHostAddress())) {
	                	
	                	return ipv4;
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        //Log.e(LOG_TAG, ex.toString());
	    }
	    return null;
	}
	
	/** Called when the user clicks the getIP button */
	public void getIP(View view) {
	    // Do something in response to button
		TextView text = (TextView) findViewById(R.id.textView1);
		String ip = getLocalIpAddress();
		text.setText(ip);		
	}
	
	/** Called when the user clicks the Play button */
	public void play(View view) {
	    // Do something in response to button
		EditText et = (EditText) findViewById(R.id.editText1);
		String text = et.getText().toString();
		ServerManagement.write(text);
		
		/*Intent intent = new Intent(this, Grid.class);
		startActivity(intent);*/			
	}
	
}