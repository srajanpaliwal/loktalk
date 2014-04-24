package com.loctalk;

import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import com.loctalk.R;
import com.loctalk.database.AppDB;
//import com.color.speechbubble.peerActivity.SendMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PeersFragment extends ListFragment implements dataTransferInterface{

	
	
	peerAdapter adapter;
	static String broadcastip;
	sender sen;
	ArrayList<peerData> peerName;
	ArrayList<String> id = new ArrayList<String>();
	Handler  mHandler;
	 public PeersFragment() {
		// TODO Auto-generated constructor stub
	}
	
	 
	 
	 
	 public void onAttach(Activity activity){
		  super.onAttach(activity);
		  Activity context = getActivity();
		  ((MainActivity)context).datatopeerfragment = this;
		  System.out.println("This is tempid====>>>>ololololol");
		  //((MainActivity)context).passdatatoActivity();
		  //System.out.println("from fragment"+((MainActivity)context).datatofragment.getClass());
		}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		broadcastip = getArguments().getString("broadip");
        View rootView = inflater.inflate(R.layout.peerlist, container, false);
        
        if (db == null)
			db = new AppDB(getActivity());
        
        
         
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		peerName = new ArrayList<peerData>();
		System.out.println("This is tempid====>>>>kokookokok");
		/*peerName.add(new peerData("chomu", 2));
		peerName.add(new peerData("totu", 1));
		peerName.add(new peerData("somu", 0));
		*/
		adapter = new peerAdapter(getActivity(), peerName);
		setListAdapter(adapter);
		System.out.println("peer fragment");
		String s;
		s = jsonFunctions1.createUltiJSON(myAppID,myNick,"Hi peers","peerReq");
		sen = new sender(s,broadcastip);
		sen.start();
		
	}
	
	public void onListItemClick(ListView l, View v, final int position, long id) {
		super.onListItemClick(l, v, position, id);
		final int index = position;
		final String tempid = this.id.get(index);
		System.out.println(tempid+"This is tempid====>>>>");
		int ti = Integer.parseInt(tempid);
		final String peerInfo[] = db.getOnePeer(ti);
		System.out.println("PeerInfo2"+peerInfo[2]);
		if(peerInfo[3].equals("0")){
		AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
		alert.setTitle("Send request to "+peerName.get(index).name);
		alert.setMessage("Need to send request For Chat first!!!");
		alert.setButton(alert.BUTTON_NEUTRAL, "Send Request", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
					String req = jsonFunctions1.createUltiJSON(myAppID,myNick,"Lets Talk", "chatReq");
					try{
					sen = new sender(req,peerInfo[2]);
					sen.start();
					}catch(Exception e){
						System.out.println("sender's problem====="+e);
					}
					System.out.println("==>><<=="+peerInfo[2]);
					System.out.println("Updating PC!!!!!!!!!!!!!");
					db.updPC(1, tempid);
						/*
						 * changing the colour to yellow
						 */
						
						peerName.get(position).status=1;
					
					adapter.notifyDataSetChanged();
			}
		});
		alert.setButton(alert.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alert.show();
		}
		else if(peerInfo[3].equals("1")){
			AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
			alert.setTitle("Your request has been sent to"+peerInfo[1]);
			alert.setMessage("Please wait fot the response");
			alert.setButton(alert.BUTTON_NEUTRAL, "Okay!", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
						
				}
			});
			
			alert.show();
		}
		else if(peerInfo[3].equals("2")){
			// Start PM Activity.
			mHandler.obtainMessage(2,""+peerInfo[0]).sendToTarget();
		}

	}
	
	public void passdatatofragment(String id,String msg) {
		
	}





	@Override
	public void passdatatopeerfragment(int PCstatus, String[] data,
			String peerip) {
		// TODO Auto-generated method stub
		System.out.println("pass to peer data fragment func called!!!!!!!!");
		
		for(int z=0; z<id.size();z++){
			System.out.println("====Arraylist ID==="+id.get(z));
		}
		int id1 = Integer.parseInt(data[0]);
		try{
		String[] pcdata = db.getOnePeer(id1);
		
		if(!(pcdata[0].equals(data[0])))
			dbFunctions.addtopeerdb(data[0], data[1], peerip, Integer.toString(PCstatus), "0");
		
			peerName.add(new peerData(data[1],PCstatus));
			id.add(data[0]);
		}catch(Exception e){
			System.out.println("passtodatapeerfragment...."+e);
			dbFunctions.addtopeerdb(data[0], data[1], peerip, Integer.toString(PCstatus), "0");
			peerName.add(new peerData(data[1],PCstatus));
			id.add(data[0]);
			
		}

		adapter.notifyDataSetChanged();
		//addtopeerlistview(peerName);
	}

	public void setHandler(Handler hand){
		mHandler=hand;
	}	
}

	
	
	

