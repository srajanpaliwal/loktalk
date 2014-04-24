package com.loctalk;

import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

public class personalchat extends ListFragment {

	chatAdapter adapter;
	ArrayList<String> nick ;
	ArrayList<String> AppID=new ArrayList<String>() ;
	Hashtable<String,ArrayList<chatObject>> msg;
	ViewFlipper vf;
	Handler Hand;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_main_perchat, container, false);
		//View list = (ListView) getActivity().findViewById(android.R.id.list);
		
		return rootView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		nick = new ArrayList();
		
		msg = new Hashtable();
		generate_data();
		adapter = new chatAdapter(this.getActivity(), nick, msg,mHandler);
		setListAdapter(adapter);
		System.out.println("Exiting onCreate()");
		
		
	}
	Handler mHandler =new Handler(){
		    @Override
		    public void handleMessage(Message msg){
		    	if(msg.what==3){
		    		System.out.println("personal chat");
		    		Hand.obtainMessage(2,""+AppID.get(Integer.parseInt(msg.obj.toString()))).sendToTarget();
		    	}
		    }
	    };
	
	public void generate_data(){
		ArrayList<String> PeersID=db.getPersonalpeer();
		int i;
		String[] peer;
		ArrayList<JSONObject> message;
		chatObject chat;
		ArrayList<chatObject> temp = new ArrayList();
		if(db.countMsg()>0)
		{
			for(i=0;i<PeersID.size();i++)
			{
				peer=db.getOnePeer(Integer.parseInt(PeersID.get(i)));
				nick.add(peer[1]);
				AppID.add(peer[0]);
				message=db.getMSGlast(PeersID.get(i));
				try {
					chat = new chatObject(message.get(0).get("Content").toString(),message.get(0).get("Time").toString());
					temp.add(chat);
					msg.put(peer[1],temp);
				} catch (JSONException e) {
					System.out.println("generate_data personal chat"+e);
				}
			}
		}
	}
	public void setHandler(Handler hand){
		Hand=hand;
	}
}
