package com.example.socketiotest;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;





public class MainActivity extends ActionBarActivity {
	
	public SocketIO socket;
	public String message1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		EditText et = (EditText)findViewById(R.id.editText1);
		message1 = et.getText().toString();
		
		Button button= (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	try{
		    	socket = new SocketIO("http://192.168.1.3:3000");
		    	
		    	socket.connect(new IOCallback() {
		            @Override
		            public void onMessage(JSONObject json, IOAcknowledge ack) {
		                try {
		                    System.out.println("Server said:" + json.toString(2));
		                } catch (JSONException e) {
		                    e.printStackTrace();
		                }
		            }

		            @Override
		            public void onMessage(String data, IOAcknowledge ack) {
		                System.out.println("Server said: " + data);
		            }

					@Override
					public void onDisconnect() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onConnect() {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();
					}

					@Override
					public void on(String event, IOAcknowledge ack,
							Object... args) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onError(SocketIOException socketIOException) {
						// TODO Auto-generated method stub
						
					}

		           
		           
		        });

		    	try {
		            JSONObject json = new JSONObject();
		            json.putOpt("nickname", message1);
		            socket.emit("new user", json);
		            
		        } catch (JSONException ex) {
		            ex.printStackTrace();
		        }
		    	
		    
		    	
		    	
		    	} catch (Exception e){
		    		Toast.makeText(MainActivity.this, "Error connecting the ip", Toast.LENGTH_LONG).show();
		    	  }
		    }
		});
		
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
