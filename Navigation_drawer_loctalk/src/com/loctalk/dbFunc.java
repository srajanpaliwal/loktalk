package com.loctalk;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static  com.loctalk.Constant.myNick;
import static  com.loctalk.Constant.myAppID;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loctalk.database.AppDB;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

public class dbFunc {
	
	public dbFunc(AppDB con){
		if (db == null)
			db = con;
	}
	
	
	public String getAd(){
		JSONArray listPremium = new JSONArray();

		ArrayList<JSONObject> a = db.getPremium();
		 for(int i=0;i<a.size();i++){
			 listPremium.put(a.get(i));
		 }
		return listPremium.toString();
	}
	
	public void addonetoaddb(String id, String appID, String content, String time, String vote){
		
		System.out.println("function addtoaddb() called!!!");
		JSONObject objPremium = new JSONObject();
				
		try {
			objPremium.put("ID", id);
			objPremium.put("AppID", appID);
			objPremium.put("Content", content);
			objPremium.put("Time", time);
			objPremium.put("Vote", vote);
			System.out.println("Jason string for db==>>"+"\n"+objPremium.toString());
			db.insertPremium(objPremium);
			
			//setListAdapter(new ArrayAdapter<String>(peerActivity.this, android.R.layout.simple_list_item_1, ar));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PremiumDB error"+ e.getMessage().toString());
					
		}
	}
	
	
	public void incrementVote(String adID,String senderID){
		//adding to table Premium the vote count
		db.upVote(adID,senderID);
	}
	
	
	
	
public void addtoaddb(String dbString) throws JSONException{
		
		JSONArray ja = new JSONArray(dbString);
		int size = ja.length();
		
		JSONObject current = null;
		
		for(int i =0;i<size;i++){
				current = ja.getJSONObject(i);
				db.insertPremium(current);
				
			
		}
		
		
		System.out.println("function addtoaddb() called!!!");
		
			//setListAdapter(new ArrayAdapter<String>(peerActivity.this, android.R.layout.simple_list_item_1, ar));
			
					
		}
	
	public void incrementVote(String adID){
		//adding to table Premium the vote count
	}
	
	public void removeAd(String adID){
		
	}
	
	public void addtopeerdb(String appID, String nick, String ip, String pc, String block){
		/*
		 * method to add the peers in the table(contained in database-loctalk.sqlite)- "Peers".
		 */
		System.out.println("function addtopeerdb() called!!!");
		JSONObject objPeer = new JSONObject();
				ip=ip.replaceFirst("/", "");
		try {
			System.out.println("ipdb"+ ip);
			objPeer.put("AppID", appID);
			objPeer.put("Nick", nick);
			objPeer.put("MAC", "MAC-ADD");
			objPeer.put("IP", ip);
			objPeer.put("PC", pc);
			objPeer.put("Block", block);
			System.out.println("Jason string for db==>>"+"\n"+objPeer.toString());
			db.insertPeer(objPeer);
			//return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PeerDB error"+ e.getMessage().toString());
					
		}
	}
	
	public void addtoChatReq(String appID, String nick, String ip, String pc, String block){
		
		/*
		 * method to add the chat requests in the table(contained in database-loctalk.sqlite)- "ChatReq".
		 */
		System.out.println("function addtochatreq() called!!!");
		JSONObject objChatReq = new JSONObject();
				
		try {
			objChatReq.put("AppID", appID);
			objChatReq.put("Nick", nick);
			objChatReq.put("MAC", "MAC-ADD");
			objChatReq.put("IP", ip);
			
			objChatReq.put("PC", pc);
			objChatReq.put("Block", block);
			System.out.println("Jason string for db==>>"+"\n"+objChatReq.toString());
			db.insertChatReq(objChatReq);
			//return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PeerDB error"+ e.getMessage().toString());
	
	}
	}
public void addtopostdb(String ID, String AppID, String Content, String Time,String Category){
		
		/*
		 * Method to add post to the table-"Post".
		 */
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("ID", ID);
			obj.put("AppID", AppID);
			obj.put("Content", Content);
			obj.put("Time", Time);
			obj.put("Category", Category);
			System.out.println("Jason string for post DB==>>"+"\n"+obj.toString());
			db.insertPost(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error"+ e.getMessage().toString());
		}
	}
public ArrayList<Message1> getpostdb(String Category){
	
	/*
	 * Method to get post to the table-"Post".
	 */
	ArrayList<JSONObject> Post=new ArrayList<JSONObject>();
	ArrayList<Message1> Posttoreturn=new ArrayList<Message1>();
	Message1 pmessage=null;
	String AppID;
	int Arraylength;
	int j;
	int i;
	try {
		Post=db.getPost(Category);
		Arraylength=Post.size();
		for(i=0;i<=Arraylength-1;i++)
		{
			AppID=Post.get(i).get("AppID").toString();
			System.out.println("===========>"+Post.get(i).get("Content")+Post.get(i).get("Time"));
			if(AppID.equals(myAppID))
			{
					String mymes = Post.get(i).get("Content").toString();
					int length_of_content = mymes.length();
					String nickname = myNick;
					int length_of_nick = nickname.length();
					String mestime = Post.get(i).get("Time").toString();
					String finalmes = nickname+"\n"+"\n"+mymes+"\n"+"\n"+mestime;
					int timestartindex = length_of_nick + length_of_content + 2;
					
					SpannableString ss1 = new SpannableString(finalmes);
					ss1.setSpan(new RelativeSizeSpan(0.85f), 0,length_of_nick, 0); // set size
					ss1.setSpan(new RelativeSizeSpan(0.70f), timestartindex,finalmes.length(), 0); // set size
					ss1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, length_of_nick, 0);// set color
					ss1.setSpan(new ForegroundColorSpan(Color.MAGENTA),timestartindex,finalmes.length(), 0);// set color
					//String myspanstr = ss1.toString();
					System.out.println(ss1);
					System.out.println("hhhhhhhhhh");
					pmessage = new Message1(ss1,true);
					//pmessage=new Message1(myNick+Post.get(i).get("Content")+Post.get(i).get("Time"),true);//add time here
				
			}
			else
			{       
				String mymes = Post.get(i).get("Content").toString();
				int length_of_content = mymes.length();
				String nickname = myNick;
				int length_of_nick = nickname.length();
				String mestime = Post.get(i).get("Time").toString();
				String finalmes = nickname+"\n"+"\n"+mymes+"\n"+"\n"+mestime;
				int timestartindex = length_of_nick + length_of_content + 2;
				
				SpannableString ss1 = new SpannableString(finalmes);
				ss1.setSpan(new RelativeSizeSpan(0.85f), 0,length_of_nick, 0); // set size
				ss1.setSpan(new RelativeSizeSpan(0.70f), timestartindex,finalmes.length(), 0); // set size
				ss1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, length_of_nick, 0);// set color
				ss1.setSpan(new ForegroundColorSpan(Color.MAGENTA),timestartindex,finalmes.length(), 0);// set color
				//String myspanstr = ss1.toString();
				System.out.println(ss1);
				System.out.println("hhhhhhhhhh");
				pmessage = new Message1(ss1,true);
				//pmessage=new Message1(db.getOnePeer(Integer.parseInt(AppID))[1]+Post.get(i).get("Content")+Post.get(i).get("Time"),false);//add time here
			}
			
			
			Posttoreturn.add(Posttoreturn.size(),pmessage);
			for(j=0;j<=Posttoreturn.size()-1;j++)
			{
				System.out.println(";;;;;;;;;;;;;>"+Posttoreturn.get(i).getMessage());
			}
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("DB error"+ e.getMessage().toString());
	}
	
	return Posttoreturn;
}
	public void addtomsgdb(String ID, String AppID, String Content, String Time){
		
		/*
		 * Method to add messages to the table-"Messages".
		 */
		
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("ID", ID);
			obj.put("AppID", AppID);
			obj.put("Content", Content);
			obj.put("Time", Time);
			System.out.println("Jason string for Msg DB==>>"+"\n"+obj.toString());
			db.insertMsg(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error"+ e.getMessage().toString());
		}
	}
}
