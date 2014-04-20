package com.example.socketiotest;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.widget.AdapterView.OnItemClickListener;

public class UsersListView extends ActionBarActivity {
	ListView msgList;
    ArrayList<MessageDetails> details;
    AdapterView.AdapterContextMenuInfo info;
    
	public SocketIO socket;
	
	private final static String URL = "http://192.168.1.3:3000";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_users_list_view);

		msgList = (ListView) findViewById(R.id.MessageList);
        
        details = new ArrayList<MessageDetails>();
            
        SocketIOComm();
        
        
        
		
	}
	
	public class CustomAdapter extends BaseAdapter {
		 
		    private ArrayList<MessageDetails> _data;
		    Context _c;
		    
		    CustomAdapter (ArrayList<MessageDetails> data, Context c){
		        _data = data;
		        _c = c;
		    }
		   
		    public int getCount() {
		        // TODO Auto-generated method stub
		        return _data.size();
		    }
		    
		    public Object getItem(int position) {
		        // TODO Auto-generated method stub
		        return _data.get(position);
		    }
	 
		    public long getItemId(int position) {
		        // TODO Auto-generated method stub
		        return position;
		    }
	   
		    public View getView(int position, View convertView, ViewGroup parent) {
		        // TODO Auto-generated method stub
		         View v = convertView;
		         if (v == null)
		         {
		            LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		            v = vi.inflate(R.layout.list_item_message, null);
		         }
		 
		           ImageView image = (ImageView) v.findViewById(R.id.icon);
		           TextView fromView = (TextView)v.findViewById(R.id.From);
		 
		           MessageDetails msg = _data.get(position);
		           image.setImageResource(msg.icon);
		           fromView.setText(msg.from);	                                     
		                        
		        return v;
		    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.users_list_view, menu);
		return true;
	}
	
	public void SocketIOComm(){
		try{
	    	
	    	socket = new SocketIO(URL);	    	
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
	                //System.out.println("Server said: " + data);
	            }

				@Override
				public void onDisconnect() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onConnect() {
					// TODO Auto-generated method stub
					Toast.makeText(UsersListView.this, "Connected", Toast.LENGTH_LONG).show();
				}

				@Override
				public void on(String event, IOAcknowledge ack,
						Object... args) {
					// TODO Auto-generated method stub
					
						if (event.equals("usernames")) {											
							
							if(args.length!=0){
								for(int i=0;i<args.length;i++){
																		
							}
						
						}
					
					}
				}
				@Override
				public void onError(SocketIOException arg0) {
					// TODO Auto-generated method stub
										
					Toast.makeText(UsersListView.this,  "error: " + arg0.getMessage(), Toast.LENGTH_LONG).show();
	                arg0.printStackTrace();
	                if (arg0.getMessage().endsWith("+0")) {
	                    socket.disconnect();
	                    try {
	                        socket = new SocketIO(URL, this);
	                    } catch (MalformedURLException e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                }
					
				}
			});	    		
	    	
	    	} catch (Exception e){
	    		Toast.makeText(UsersListView.this, "Error connecting the ip", Toast.LENGTH_LONG).show();
	    	  }
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
			View rootView = inflater.inflate(R.layout.fragment_users_list_view,
					container, false);
			return rootView;
		}
	}

}
