package com.example.serverclientroom;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ClientGrid extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_grid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_grid, menu);
		return true;
	}
	
	private int positionR = 0;
	private int positionC = 0;
	
	/** Called when the user clicks the right button */
	public void right(View view) {
	    // Do something in response to button
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("Right");
		if(positionC < 4){
			positionC += 1;
			String s = "" + positionR + positionC;
			int num = Integer.parseInt(s);
			TextView tv = getTV(num);
			tv.setText("O");			
		}
		ClientManagement.write("Right");
	}
	
	/** Called when the user clicks the left button */
	public void left(View view) {
	    // Do something in response to button
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("Left");
		if(positionC > 0){
			positionC -= 1;
			String s = "" + positionR + positionC;
			int num = Integer.parseInt(s);
			TextView tv = getTV(num);
			tv.setText("O");			
		}
		ClientManagement.write("Left");
		
	}
	
	/** Called when the user clicks the up button */
	public void up(View view) {
	    // Do something in response to button
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("Up");
		if(positionR > 0){
			positionR -= 1;
			String s = "" + positionR + positionC;
			int num = Integer.parseInt(s);
			TextView tv = getTV(num);
			tv.setText("O");			
		}
		ClientManagement.write("Up");
		
	}
	
	/** Called when the user clicks the down button */
	public void down(View view) {
	    // Do something in response to button
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText("Down");
		if(positionR < 3){
			positionR += 1;
			String s = "" + positionR + positionC;
			int num = Integer.parseInt(s);
			TextView tv = getTV(num);
			tv.setText("O");			
		}
		ClientManagement.write("Down");
		
	}
	
	public TextView getTV(int s){
		TextView ans = null;
		switch (s) {
            case 0:  ans = (TextView) findViewById(R.id.textView00);
                     break;
            case 1:  ans = (TextView) findViewById(R.id.textView01);
                     break;
            case 2:  ans = (TextView) findViewById(R.id.textView02);
                     break;
            case 3:  ans = (TextView) findViewById(R.id.textView03);
                     break;
            case 4:  ans = (TextView) findViewById(R.id.textView04);
                     break;
            case 10: ans = (TextView) findViewById(R.id.textView10);
                     break;
            case 11: ans = (TextView) findViewById(R.id.textView11);
                     break;
            case 12: ans = (TextView) findViewById(R.id.textView12);
                     break;
            case 13: ans = (TextView) findViewById(R.id.textView13);
                     break;
            case 14: ans = (TextView) findViewById(R.id.textView14);
                     break;
            case 20: ans = (TextView) findViewById(R.id.textView20);
                     break;
            case 21: ans = (TextView) findViewById(R.id.textView21);
                     break;
            case 22: ans = (TextView) findViewById(R.id.textView22);
            		 break;
            case 23: ans = (TextView) findViewById(R.id.textView23);
            		 break;
            case 24: ans = (TextView) findViewById(R.id.textView24);
            		 break;
            case 30: ans = (TextView) findViewById(R.id.textView30);
            		 break;
            case 31: ans = (TextView) findViewById(R.id.textView31);
            		 break;
            case 32: ans = (TextView) findViewById(R.id.textView32);
            		 break;
            case 33: ans = (TextView) findViewById(R.id.textView33);
           			 break;
            case 34: ans = (TextView) findViewById(R.id.textView34);
            		 break;
		}
		
        return ans;
	}

}
