package com.loctalk;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;
public class chatreqFragment extends ListFragment implements dataTransferInterface {
	ArrayList<String> peerName;
	ArrayList<String> AppIDlist=new ArrayList<String>();
	chatreqAdapter adapter;
	sender sen;
	private final Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	String Str = msg.obj.toString();
	    	String [] peer=db.getOnePeer(Integer.parseInt(AppIDlist.get(Integer.parseInt(Str))));
	    	db.updatePC(2,peer[0]);
	    	String s = jsonFunctions1.createUltiJSON(myAppID,myNick,"Hi peers","chatReply");
	    	System.out.println("lkasdjlaksdjla++++++++++>"+peer[2]);
			sen = new sender(s,peer[2]);
			sen.start();
	    	}
	    };
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_main_tiles, container, false);
		return rootView;
		
	}
	 public void onAttach(Activity activity){
		  super.onAttach(activity);
		  Activity context = getActivity();
		  ((MainActivity)context).datatopeerfragment = this;
		}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		peerName = new ArrayList<String>();
		int i;
		ArrayList<JSONObject> peerspc;
		if(db.countPeer()>0){
			peerspc=db.getPeers("4");
			for(i=0;i<peerspc.size();i++){
			try {
				AppIDlist.add(peerspc.get(i).get("AppID").toString());
				peerName.add(peerspc.get(i).get("Nick").toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		adapter = new chatreqAdapter(getActivity(), peerName,mHandler);
		setListAdapter(adapter);
	}
	@Override
	public void passdatatofragment(String id, String msg) {
		if(id.equals("removepeer"))
		{
			AppIDlist.remove(msg);
			String [] peer=db.getOnePeer(Integer.parseInt(msg));
			peerName.remove(peer[1]);
		}
		else if(id.equals("requestadd"))
		{
			String[] parsed;
			try {
				parsed = jsonFunctions1.parseUltiJSON(msg.toString());
				AppIDlist.add(parsed[0]);
				peerName.add(parsed[1]);
			} catch (JSONException e) {
				System.out.println("Chatreq json errro"+e);
			}
			adapter.notifyDataSetChanged();
		}
	}
	@Override
	public void passdatatopeerfragment(int PCstatus, String[] data,
			String peerip) {
		// TODO Auto-generated method stub
		
	}	
}
