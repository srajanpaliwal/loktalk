package com.loctalk;

import java.util.ArrayList;



import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class chat_reqFrag extends ListFragment {

	
	ArrayList<String> peerName;
	ArrayList<String> messages;
	peer_chatreq_Adapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_main_chatreq, container, false);
		//View list = (ListView) getActivity().findViewById(android.R.id.list);
		
		return rootView;
	}
	
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		peerName = new ArrayList<String>();
		peerName.add("ABC DEF");
		peerName.add("GHI JKL");
		peerName.add("MNO PQR");
		adapter = new peer_chatreq_Adapter(this.getActivity(), peerName);
		setListAdapter(adapter);
		
	}
	
}
