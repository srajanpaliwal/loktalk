package com.loctalk;

import java.util.ArrayList;
import java.util.Hashtable;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class personalchat extends ListFragment {

	chatAdapter adapter;
	ArrayList<String> nick ;
	Hashtable<String,ArrayList<chatObject>> msg;
	ViewFlipper vf;
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
		adapter = new chatAdapter(this.getActivity(), nick, msg);
		setListAdapter(adapter);
		System.out.println("Exiting onCreate()");
		
		
	}
	
	
	public void generate_data(){
		nick.add("A");
		nick.add("B");
		nick.add("C");
		chatObject a = new chatObject("Message1","5:40");
		chatObject b = new chatObject("Message2","5:40");
		chatObject c = new chatObject("Message3","5:40");
		chatObject d = new chatObject("Message4","5:40");
		chatObject e = new chatObject("Message5","5:40");
		chatObject f = new chatObject("Message6","5:40");
		chatObject g = new chatObject("Message7","5:40");
		chatObject h = new chatObject("Message8","5:40");
		chatObject i = new chatObject("Message9","5:40");
		ArrayList<chatObject> temp = new ArrayList();
		temp.add(a);
		temp.add(b);
		temp.add(c);
		msg.put("A",temp);
		ArrayList<chatObject> temp1 = new ArrayList();
		temp1.add(d);
		temp1.add(e);
		temp1.add(f);
msg.put("B", temp1);
		ArrayList<chatObject> temp2 = new ArrayList();
		temp2.add(g);
		temp2.add(h);
		temp2.add(i);
		msg.put("C", temp2);
	}
	
	
}
