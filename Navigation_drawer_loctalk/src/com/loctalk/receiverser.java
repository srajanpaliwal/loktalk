package com.loctalk;

import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class receiverser extends Service {

	DatagramSocket socket=null;
	public static boolean sactive=false;
	byte[] data = new byte[2048];
	JSONObject jo = new JSONObject();
	DatagramPacket packet =new DatagramPacket(data, data.length);
	//TextView textv;
	public static Handler mHandler;
	public boolean state=false;
	String pass;
	sender senMain; 
	MainActivity ma = new MainActivity();
	
	public receiverser(){
		
	}
	
	public receiverser(Handler hand){
		mHandler = hand;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	


	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return START_STICKY;
	}
	

	public void onCreate() {
		
		super.onCreate();
		sactive=true;
		try {
			socket = new DatagramSocket(4444);
			socket.setReceiveBufferSize(data.length);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("socket creation error ########"+ e);
		}
		
		run();		
	}
	
	public void run()
	{
			
			System.out.println("-----> receive started");
			new Receivedata().execute(socket);
//			try
//			{
//				//System.out.println("receiving");
//				socket.receive(packet);
//				System.out.println("received");
//				String message =new String(packet.getData(), 0, packet.getLength());
//				System.out.println(""+message);
//				pass=message;
//				String ip = packet.getAddress().toString();
//				String temp = pass+"splitstr"+ip;
//				state=true;
//				System.out.println("received"+temp);
//
//				if(MainActivity.active){
//					//mHandler.obtainMessage(1,temp).sendToTarget();
//					ma.dothejob(temp);
//				}
//				else{
//					addtodb(temp);
//					
//				}
//				//ma.getstring(message);
//				//textv.setText(message);
//				//textv.setText("fuck"+textv.getText().toString()+message);
//				
//			
//			}
//			catch(Exception e)
//			{
//				System.out.println("809080980"+e);
//			}
			
		
	}
	
	 public void dothejob(String msg) {
	    	/*
	    	 * receiving message from receiver thread via handler
	    	 * 
	    	 * obj conatins a string(recStr) of the form message-splitstr-ip
	    	 * 
	    	 * recAr[0] contains message
	    	 * recAr[1] contains IP of the message from where it was sent.
	    	 */
		 
		 new Receivedata().execute(socket);
		 
		 System.out.println("========= Reached dothejob function ========");
		 
	    	try{
	    	//String recStr = msg.obj.toString();
	    	String[] recAr = msg.split("splitstr");
	    	System.out.println("message=="+ recAr[0]+"=== IP :"+recAr[1]);
	    	
	    		/*
	    		 * Parsing the received message and carrying out the required tasks.
	    		 */

				String[] parsedStr = jsonFunctions1.parseUltiJSON(recAr[0]);
				if(!(parsedStr[0].equals(myAppID))){
				if(parsedStr[3].equals("adReq")){
					String adRep = jsonFunctions1.createUltiJSON(myAppID, myNick, "reply for adReq", "adReply");
					senMain = new sender(adRep,recAr[1]);
					senMain.start();
				}

				else if(parsedStr[3].equals("adReply")){
//					if(adRepliers.size()==0){
//						String adseeker = jsonFunctions1.createUltiJSON(myAppID, myNick, "send ads", "adSeek");
//						senMain = new sender(adseeker, recAr[1]);
//						senMain.start();
//					}
//					
//					adRepliers.add(recAr[1]);
				}

				else if(parsedStr[3].equals("adSeek")){
					String ads = jsonFunctions1.createUltiJSON(myAppID, myNick, dbFunctions.getAd(),"adSent");
					senMain = new sender(ads, recAr[1]);
					senMain.start();

				}

				else if(parsedStr[3].equals("adSent")){
					dbFunctions.addtoaddb(parsedStr[2]);

				}

				else if(parsedStr[3].equals("adUpvote")){

				}

				else if(parsedStr[3].equals("adDlt")){

				}

				else if(parsedStr[3].equals("postAd")){
					String ID=(db.countPremium()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addonetoaddb(ID, parsedStr[0],parsedStr[2], time, parsedStr[3],parsedStr[1]);
					

				}

				else if(parsedStr[3].equals("postGen")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0],msg.toString(), time, parsedStr[3],parsedStr[1]);
					
				}

				else if(parsedStr[3].equals("postEvent")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0], msg.toString(), time, parsedStr[3],parsedStr[1]);
					

				}

				else if(parsedStr[3].equals("postHelp")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0], msg.toString(), time, parsedStr[3],parsedStr[1]);
					
				}

				else if(parsedStr[3].equals("postBusi")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0], msg.toString(), time, parsedStr[3],parsedStr[1]);
					
				}

				else if(parsedStr[3].equals("postFood")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0], msg.toString(), time, parsedStr[3],parsedStr[1]);
				
				}

				else if(parsedStr[3].equals("chatMsg")){
				//	datatofragment.passdatatofragment("message",msg.toString());
				}

				else if(parsedStr[3].equals("chatReq")){
					
				}

				else if(parsedStr[3].equals("chatReply")){
					
				}

				else if(parsedStr[3].equals("peerReq")){
					String toSend = jsonFunctions1.createUltiJSON(myAppID, myNick, "Hi peer", "peerReply");
					senMain = new sender(toSend,recAr[1]);
					senMain.start();

				}

				else if(parsedStr[3].equals("peerReply")){
					int id = Integer.parseInt(parsedStr[0]);
					try{
					String[] pcdata = db.getOnePeer(id);
					if(pcdata[0].equals(Integer.toString(id))){
						int id1 = Integer.parseInt(parsedStr[0]);
						
							dbFunctions.addtopeerdb(parsedStr[0], parsedStr[1], recAr[1], Integer.toString(1), "0");
						
						//datatopeerfragment.passdatatopeerfragment(1, parsedStr,recAr[1]);
					}
					else{
						//datatopeerfragment.passdatatopeerfragment(0, parsedStr,recAr[1]);
						dbFunctions.addtopeerdb(parsedStr[0], parsedStr[1], recAr[1], Integer.toString(0), "0");
					}
					}catch(Exception e){
						System.out.println("Get One peer array"+e);
						//datatopeerfragment.passdatatopeerfragment(0, parsedStr,recAr[1]);
						dbFunctions.addtopeerdb(parsedStr[0], parsedStr[1], recAr[1], Integer.toString(0), "0");
					}
					//System.out.println("#######> Got the PC as: "+Integer.parseInt(pcdata[3]));
					
				}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("handle message"+e);
			}

	    }
	
	private class Receivedata extends AsyncTask<DatagramSocket,Void, String> {
		
		    @Override
		    protected void onPreExecute() {
		        super.onPreExecute();

		    }
		    
	     protected String doInBackground(DatagramSocket... socket) {
	    	
	    	 try
				{
					//System.out.println("receiving");
					socket[0].receive(packet);
					System.out.println("received");
					String message =new String(packet.getData(), 0, packet.getLength());
					System.out.println(""+message);
					pass=message;
					String ip = packet.getAddress().toString();
					String temp = pass+"splitstr"+ip;
					state=true;
					System.out.println("received"+temp);
					
					return temp;
				}
					catch(Exception e)
					{
						System.out.println("809080980"+e);
						return null;
					}
	 	       
	     }

	     protected void onPostExecute(String result) {
	         System.out.println("This is the message received.."+result);
	         if(result!=null){
	        	 if(MainActivity.active){
						//mHandler.obtainMessage(1,temp).sendToTarget();
						//ma.dothejob(result);
	        		 sendmessage(result);
	        		 //mHandler.obtainMessage(1,result).sendToTarget();
					}
					else{
						dothejob(result);
						
					}
	         }
	    	 
	     }
     
	    	 
	     }
	
	public void sendmessage(String temp){
		//mHandler.obtainMessage(1,temp).sendToTarget();
		new Receivedata().execute(socket);
		try{
		mHandler.obtainMessage(1,temp).sendToTarget();
		}catch(Exception e){
			System.out.println("SendMessage error #######"+e);
		}
		//ma.dothejob(temp);
	}
	
	public void onDestroy() {
		sactive = false;
		//stopSelf();
	}

}
