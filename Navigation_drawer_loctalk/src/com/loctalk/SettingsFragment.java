package com.loctalk;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

public class SettingsFragment  extends ListFragment {

	
	
	ArrayList<row> llist;
	SettingsAdapter adapter;
	ListView list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.settings, container, false);
		//View list = (ListView) getActivity().findViewById(android.R.id.list);
		
		return rootView;
		
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		llist = new ArrayList<row>();
		llist.add(new row("About"));
		llist.add(new row("Notification Tone"));
		llist.add(new row("Notification Volume"));
		adapter = new SettingsAdapter(getActivity(), llist);
		setListAdapter(adapter);
	}	
	
	     
		
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

