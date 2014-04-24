package com.loctalk.database;

import java.util.ArrayList;
import static com.loctalk.Constant.myAppID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;

public class AppDB extends DBConnect {
	
	public AppDB(Context context) {
		super(context);
	}

	/**
	 * To insert Message
	 * @param value
	 */
	public void insertMsg(JSONObject objStudent) {
		String sqlCards;
		
		try {
			sqlCards = String.format(ISql.INSERT_MSG, Integer.parseInt(objStudent.getString("ID")), 
					Integer.parseInt(objStudent.getString("AppID")), 
					objStudent.getString("Content"),
					objStudent.getString("Time"),
					objStudent.getString("Nick")
					);
					
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
 
	public void insertmyNick(String ni){
		String sqlCards;
		sqlCards = String.format(ISql.INSERT_MYNICK, ni);
		
		execNonQuery(sqlCards);
	}
	/**
	 * To insert Post
	 * @param value
	 */
	public void insertPost(JSONObject objStudent) {
		String sqlCards;
		
		try {
			sqlCards = String.format(ISql.INSERT_Post, Integer.parseInt(objStudent.getString("ID")), 
					Integer.parseInt(objStudent.getString("AppID")), 
					objStudent.getString("Content"),
					objStudent.getString("Time"),
					objStudent.getString("Category"),
					objStudent.getString("Nick")
					);
					
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void insertMSG(JSONObject objStudent) {
		String sqlCards;
		
		try {
			sqlCards = String.format(ISql.INSERT_MSG, Integer.parseInt(objStudent.getString("ID")), 
					Integer.parseInt(objStudent.getString("AppID")), 
					objStudent.getString("Content"),
					objStudent.getString("Time"),
					Integer.parseInt(objStudent.getString("Flag")));
					
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * To get all Post from a Category
	 * @return
	 */
	public ArrayList<JSONObject> getPost(String Category) {
		String sqlCards;
		sqlCards = String.format(ISql.GET_Post,Category );
		Cursor cursor = execQuery(sqlCards);

		ArrayList<JSONObject> listPost = new ArrayList<JSONObject>();
		JSONObject objStudent;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objStudent = new JSONObject();
					
					try {
						objStudent.put("ID", String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						objStudent.put("AppID", cursor.getString(cursor.getColumnIndex("AppID")));
						objStudent.put("Content", cursor.getString(cursor.getColumnIndex("Content")));
						objStudent.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
						objStudent.put("Category", cursor.getString(cursor.getColumnIndex("Category")));
						objStudent.put("Nick", cursor.getString(cursor.getColumnIndex("Nick")));
						listPost.add(objStudent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listPost;
	}
	
	public int countPost()
	{
		Cursor cursor = execQuery(ISql.COUNT_Post);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	
	public int countPremium()
	{
		Cursor cursor = execQuery(ISql.COUNT_PREMIUM);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	/**
	 * To insert peer
	 * @param value
	 */
	public void insertPeer(JSONObject objPeer) {
		String sqlCards;
		System.out.println("Insertpeer AppDB called via db.insertpeer()");
		try {
			sqlCards = String.format(ISql.INSERT_PEER, Integer.parseInt(objPeer.getString("AppID")), 
					objPeer.getString("Nick"),
					objPeer.getString("MAC"),
					objPeer.getString("IP"),
					Integer.parseInt(objPeer.getString("PC")),
					Integer.parseInt(objPeer.getString("Block")));
			
			System.out.println("Inserted into peer DB==="+objPeer.getString("Nick"));
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void insertChatReq(JSONObject objPeer) {
		String sqlCards;
		System.out.println("Insertpeer AppDB called via db.insertChatReq()");
		try {
			sqlCards = String.format(ISql.INSERT_CHATREQ, Integer.parseInt(objPeer.getString("AppID")), 
					objPeer.getString("Nick"),
					objPeer.getString("MAC"),
					objPeer.getString("IP"),
					Integer.parseInt(objPeer.getString("PC")),
					Integer.parseInt(objPeer.getString("Block")));
			
			System.out.println("Inserted into ChatReq DB==="+objPeer.getString("Nick"));
			
			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 
	 * 
	 * To remove Student
	 */
	public void removeMsg(int ID)
	{
		if(ID>0)
		{
			String sqlRemoveRegCard = String.format(ISql.REMOVE_MSG, ID);
			execNonQuery(sqlRemoveRegCard);
		}
	}
	public String[] getOnePeer(int ID)
	{	//JSONObject objPeer;
		System.out.println("getonepeer"+ID);
		String peerRet[] =  new String[5];
		if(ID>0)
		{
			String sqlRemoveRegCard = String.format(ISql.GET_ONEPEER, ID);
			Cursor cursor = execQuery(sqlRemoveRegCard);
			if(cursor!=null){
				//objPeer = new JSONObject();
				System.out.println("getonepeer cursor0"+cursor);
				
				if (cursor.moveToNext())
				{
					System.out.println("getonepeer cursor1"+cursor);
				try {
					peerRet[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID")));
					peerRet[1] = cursor.getString(cursor.getColumnIndex("Nick"));
					//cursor.getString(cursor.getColumnIndex("MAC"));
					peerRet[2] = cursor.getString(cursor.getColumnIndex("IP"));
					peerRet[3] = String.valueOf(cursor.getInt(cursor.getColumnIndex("PC")));
					peerRet[4] = String.valueOf(cursor.getInt(cursor.getColumnIndex("Block")));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("getonepeer err"+e);
				}
				}
				else{
					peerRet =  null;
				}
			}
		}
		return peerRet;
		
	}
	public ArrayList<String> getPersonalpeer()
	{	//JSONObject objPeer;
		ArrayList<String> peerRet=new ArrayList<String>();
			String sqlRemoveRegCard = String.format(ISql.GET_AppID_MSG);
			Cursor cursor = execQuery(sqlRemoveRegCard);
			if(cursor!=null){
				//objPeer = new JSONObject();
				System.out.println("getonepeer cursor0"+cursor);
				
				if (cursor.moveToNext())
				{
					System.out.println("getonepeer cursor1"+cursor);
				try {
					peerRet.add(String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID"))));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("getonepeer err"+e);
				}
				}
				else{
					peerRet =  null;
				}
			}
		return peerRet;
		
	}
	public ArrayList<JSONObject> getMSG(String AppID) {
		String sqlCards;
		sqlCards = String.format(ISql.GET_MSG,Integer.parseInt(AppID));
		Cursor cursor = execQuery(sqlCards);

		ArrayList<JSONObject> listMSG = new ArrayList<JSONObject>();
		JSONObject objStudent;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objStudent = new JSONObject();
					
					try {
						objStudent.put("ID", String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						objStudent.put("AppID", cursor.getString(cursor.getColumnIndex("AppID")));
						objStudent.put("Content", cursor.getString(cursor.getColumnIndex("Content")));
						objStudent.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
						objStudent.put("Flag", cursor.getString(cursor.getColumnIndex("Flag")));
						listMSG.add(objStudent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listMSG;
	}
	public ArrayList<JSONObject> getMSGlast(String AppID) {
		String sqlCards;
		sqlCards = String.format(ISql.GET_MSG_LAST,Integer.parseInt(AppID));
		Cursor cursor = execQuery(sqlCards);

		ArrayList<JSONObject> listMSG = new ArrayList<JSONObject>();
		JSONObject objStudent;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objStudent = new JSONObject();
					
					try {
						objStudent.put("ID", String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						objStudent.put("AppID", cursor.getString(cursor.getColumnIndex("AppID")));
						objStudent.put("Content", cursor.getString(cursor.getColumnIndex("Content")));
						objStudent.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
						objStudent.put("Flag", cursor.getString(cursor.getColumnIndex("Flag")));
						listMSG.add(objStudent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listMSG;
	}
	public String getMyNick()
	{	//JSONObject objPeer;
		String myNick = null ;
		int temp=0;
		//if(ID>0)
		System.out.println("In getmynick()");
		{
			
			Cursor cursor = execQuery(ISql.GET_MYNICK);
			
			if(cursor!=null){
				//objPeer = new JSONObject();
				while(cursor.moveToNext())
				{
				try {
					myNick = cursor.getString(cursor.getColumnIndex("nick"));
					temp++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("getmynick error"+e);
					e.printStackTrace();
				}
				}	
			}
		}
		System.out.println("Count mynicktbl = "+temp);
		return myNick;
		
	}
	
	/**
	 * To count no. of Students
	 * @return
	 */
	public int countMsg()
	{
		Cursor cursor = execQuery(ISql.COUNT_MSG);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	
	
	/**
	 * To count no. of Peers
	 * @return
	 */
	public int countPeer()
	{
		Cursor cursor = execQuery(ISql.COUNT_PEER);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	
	/**
	 * To count no. of Chat requests
	 * @return
	 */
	public void updPC(int status, String AppID){
		int test = updatePC(status, AppID);
		System.out.println("PC Updated with message: "+test+"<-----");
	}

	public void updpeer(String AppID,String Nick, String IP)
	{
		int test = updatepeertable(Nick, IP, AppID);
		System.out.println("Peers Updated with message: "+test+"<-----");
	}

	public void ultiUpdateNick(String newnick, String AppID){

		int t;
		updateNickChatreq(newnick, AppID);
		updateNickMessages(newnick, AppID);
		updateNickPeers(newnick, AppID);
		updateNickPosts(newnick, AppID);
		updateNickPremium(newnick, AppID);
		//System.out.println("PC Updated with message: "+test+"<-----");
		}

	

	public void updVote(String toAdd, String content){
		int prevVote;
		if(content.length()!=0)
		{
			String sqlRemoveRegCard = String.format(ISql.GET_CONTENT_PREMIUM, content);
			Cursor cursor = execQuery(sqlRemoveRegCard);
			if(cursor!=null){
				//objPeer = new JSONObject();
				System.out.println("getonepremium cursor0"+cursor);

				if (cursor.moveToNext())
				{
					System.out.println("getonepremium cursor1"+cursor);
				try {

					prevVote = cursor.getInt(cursor.getColumnIndex("Vote"));
					if(toAdd.equals("1"))
						prevVote = prevVote+1;
					else
						prevVote = prevVote-1;
					updateAdd2(Integer.toString(prevVote), content);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("getcontentpremium err"+e);
				}
				}	
			}
		}


	}
	public int countChatReq()
	{
		Cursor cursor = execQuery(ISql.COUNT_CHATREQ);
		int cntCards = 0;
		
		if(cursor!=null && cursor.getCount()>0) {
			cursor.moveToNext();
			cntCards = Integer.parseInt(cursor.getString(0));
		}
		
		if(cursor!= null) {
			cursor.close();
		}
			
		return cntCards;
	}
	/**
	 * To get all the Students
	 * @return
	 */
	public ArrayList<JSONObject> getMsgs() {
		Cursor cursor = execQuery(ISql.GET_MSG);

		ArrayList<JSONObject> listMsg = new ArrayList<JSONObject>();
		JSONObject objStudent;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objStudent = new JSONObject();
					
					try {
						objStudent.put("ID", String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						objStudent.put("AppID", cursor.getString(cursor.getColumnIndex("AppID")));
						objStudent.put("Content", cursor.getString(cursor.getColumnIndex("Content")));
						objStudent.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
						objStudent.put("Nick", cursor.getString(cursor.getColumnIndex("Nick")));
						listMsg.add(objStudent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listMsg;
	}
	
	public ArrayList<JSONObject> getPeers() {
		Cursor cursor = execQuery(ISql.GET_PEER);

		ArrayList<JSONObject> listPeer = new ArrayList<JSONObject>();
		JSONObject objPeer;
		
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					objPeer = new JSONObject();
					
					try {
						objPeer.put("AppID", String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID"))));
						objPeer.put("Nick", cursor.getString(cursor.getColumnIndex("Nick")));
						objPeer.put("MAC", cursor.getString(cursor.getColumnIndex("MAC")));
						objPeer.put("IP", cursor.getString(cursor.getColumnIndex("IP")));
						objPeer.put("PC", String.valueOf(cursor.getInt(cursor.getColumnIndex("PC"))));
						objPeer.put("Block", String.valueOf(cursor.getInt(cursor.getColumnIndex("Block"))));
						listPeer.add(objPeer);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listPeer;
	}
	
	
	public ArrayList<JSONObject> getPeers(String pc) {
		String sqlRemoveRegCard = String.format(ISql.GET_PEER_PC, Integer.parseInt(pc));
		Cursor cursor = execQuery(sqlRemoveRegCard);
		ArrayList<JSONObject> listPeer = new ArrayList<JSONObject>();
		JSONObject objPeer;
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {

					objPeer = new JSONObject();
					
					try {

						objPeer.put("AppID", String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID"))));
						objPeer.put("Nick", cursor.getString(cursor.getColumnIndex("Nick")));
						objPeer.put("MAC", cursor.getString(cursor.getColumnIndex("MAC")));
						objPeer.put("IP", cursor.getString(cursor.getColumnIndex("IP")));
						objPeer.put("PC", String.valueOf(cursor.getInt(cursor.getColumnIndex("PC"))));
						objPeer.put("Block", String.valueOf(cursor.getInt(cursor.getColumnIndex("Block"))));
						listPeer.add(objPeer);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Err in getpremium=="+e);
					}
					
				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listPeer;
	}
	
	public ArrayList<JSONObject> getPremium() {
		Cursor cursor = execQuery(ISql.GET_PREMIUM);
		/*
		 * testing added
		 */
		ArrayList<JSONObject> listPremium = new ArrayList<JSONObject>();
		JSONObject obj;
		System.out.println("Retrieving cursor"+cursor);
		if (cursor != null && cursor.getCount() > 0) {

			if (cursor.moveToNext()) {

				do {
					obj = new JSONObject();
					System.out.println("Using cursor");

					try {
						obj.put("ID", String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						System.out.println(String.valueOf(cursor.getInt(cursor.getColumnIndex("ID"))));
						obj.put("Nick", cursor.getString(cursor.getColumnIndex("Nick")));
						System.out.println(cursor.getString(cursor.getColumnIndex("Nick")));
						obj.put("AppID", String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID"))));
						System.out.println(String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID"))));
						obj.put("Content", cursor.getString(cursor.getColumnIndex("Content")));
						System.out.println(cursor.getString(cursor.getColumnIndex("Content")));
						obj.put("Time", cursor.getString(cursor.getColumnIndex("Time")));
						obj.put("Vote", cursor.getString(cursor.getColumnIndex("Vote")));
						obj.put("Liked", String.valueOf(cursor.getInt(cursor.getColumnIndex("Liked"))));
						listPremium.add(obj);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Err in getpremium=="+e);
					}

				} while (cursor.moveToNext());
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		return listPremium;
	}
	
	public String[] getOnePremium(int ID)
	{	//JSONObject objPeer;
		String peerRet[] =  new String[7];
		if(ID>0)
		{
			String sqlRemoveRegCard = String.format(ISql.GET_ONE_PREMIUM, ID);
			Cursor cursor = execQuery(sqlRemoveRegCard);
			if(cursor!=null){
				//objPeer = new JSONObject();
				System.out.println("getonepremium cursor0"+cursor);

				if (cursor.moveToNext())
				{
					System.out.println("getonepremium cursor1"+cursor);
				try {
					peerRet[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex("ID")));
					peerRet[1] = cursor.getString(cursor.getColumnIndex("Nick"));
					//cursor.getString(cursor.getColumnIndex("MAC"));
					peerRet[2] = String.valueOf(cursor.getInt(cursor.getColumnIndex("AppID")));
					peerRet[3] = cursor.getString(cursor.getColumnIndex("Content"));
					peerRet[4] = cursor.getString(cursor.getColumnIndex("Time"));
					peerRet[5] = String.valueOf(cursor.getInt(cursor.getColumnIndex("Vote")));
					peerRet[6] = String.valueOf(cursor.getInt(cursor.getColumnIndex("Liked")));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("getonepremium err"+e);
				}
				}	
			}
		}
		return peerRet;

	}
	
	public int getContentPremium(String content)
	{	//JSONObject objPeer;
		int ret=0;
		if(content.length()>0)
		{
			String sqlRemoveRegCard = String.format(ISql.GET_CONTENT_PREMIUM, content);
			Cursor cursor = execQuery(sqlRemoveRegCard);
			if(cursor!=null){
				//objPeer = new JSONObject();
				System.out.println("getonepremium cursor0"+cursor);

				if (cursor.moveToNext())
				{
					System.out.println("getonepremium cursor1"+cursor);
				try {

					ret = cursor.getInt(cursor.getColumnIndex("Vote"));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("getonepremium err"+e);
				}
				}	
			}
		}
		return ret;

	}
	
	public void upVote(String adID,String senderID){
		//adding to table Premium the vote count
		String sqlCards;
		System.out.println("Increment ad in AppDB called via db.incrementVote()");
		try {
			sqlCards = String.format(ISql.GET_P_COUNT, Integer.parseInt(adID), 
					Integer.parseInt(senderID));
			Cursor cursor = execQuery(sqlCards);

			int vote ;
			if (cursor != null && cursor.getCount() > 0) {

				if (cursor.moveToNext()) {
					vote = cursor.getInt(cursor.getColumnIndex("Vote"));
					int n = updateAdd(Integer.toString(vote), adID, senderID);

					System.out.println("Upvoted ad in premium DB==="+senderID);
				}
			}


		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 




	}

	public void insertPremium(JSONObject objPremium) {
		String sqlCards;
		System.out.println("Insertpremium AppDB called via db.insertpremium()");
		try {
			sqlCards = String.format(ISql.INSERT_PREMIUM, Integer.parseInt(objPremium.getString("ID")),
					objPremium.getString("Nick"),
					Integer.parseInt(objPremium.getString("AppID")),
					objPremium.getString("Content"),
					objPremium.getString("Time"),
					Integer.parseInt(objPremium.getString("Vote")),0); //liked or not
			System.out.println("Inserted into premium DB==="+objPremium.getString("AppID"));

			execNonQuery(sqlCards);			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	
public void removePremium(String adID){
		
		String sqlCards;
		System.out.println("Remove ad in AppDB called via db.removeAd()");
		try {
			sqlCards = String.format(ISql.GET_P_SENDER, Integer.parseInt(adID));
			Cursor cursor = execQuery(sqlCards);
			
			int sender ;
			if (cursor != null && cursor.getCount() > 0) {

				if (cursor.moveToNext()) {
					sender = cursor.getInt(cursor.getColumnIndex("AppID"));
					if (sender!=Integer.parseInt(myAppID)){ // here myappID is our ID, import it from whereever u know
						System.out.println("Permission denied.");
					}
					else{
						String str = String.format(ISql.REMOVE_PREMIUM, adID);
						execNonQuery(str);
					}
				}
			}
								
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		
	}
}
