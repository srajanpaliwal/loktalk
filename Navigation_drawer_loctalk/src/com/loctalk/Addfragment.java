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

public class Addfragment  extends ListFragment {

	
	
	ArrayList<single_row> llist;
	AdAdapter adapter;
	ListView list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_main_tiles, container, false);
		//View list = (ListView) getActivity().findViewById(android.R.id.list);
		
		return rootView;
		
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//adapter = new AdAdapter(getActivity(), llist);
		//list = (ListView) getActivity().findViewById(android.R.id.list);
		llist = new ArrayList<single_row>();
//		llist.add(new single_row("hello1", "aalo le lo hajfhahfahfafhahffaf kjdfklhafa hdafakhfasf ufajfkaf lkjfajf "+"\n"+"aalo le lo hajfhahfahfafhahffaf kjdfklhafa hdafakhfasf ufajfkaf lkjfajf "));
//		llist.add(new single_row("hello2", "gobi le lo"));
//		llist.add(new single_row("hello3", "pyaaz le lo"));
//		llist.add(new single_row("hello4", "tamatar le lo"));
		ArrayList<JSONObject> array = db.getPremium();
		int i = 0;
		JSONObject temp = null;
		while(i<=array.size()-1){
			temp = array.get(i);
			try{
			llist.add(new single_row(temp.getString("Nick"),temp.getString("Content")));
			}
			catch(Exception e ){
				System.out.println("JSON exception in ads"+e);
			}
			i++;
		}
		adapter = new AdAdapter(getActivity(), llist);
		setListAdapter(adapter);
	}	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

