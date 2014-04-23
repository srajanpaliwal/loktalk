package com.loctalk;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingsAdapter extends BaseAdapter {
	
	private Context lcontext;
	private ArrayList<row> llist;
	
	
	
	public SettingsAdapter(Context context, ArrayList<row> list){
		this.lcontext = context;
		this.llist = list;
		}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return llist.size(); 
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return llist.get(position);

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		
            vi = LayoutInflater.from(lcontext).inflate(R.layout.settings_row, parent, false);
		
          TextView title = (TextView) vi.findViewById(R.id.setting);
          
          row list = llist.get(position);
          
          title.setText(list.function);
          final View x=vi;
          x.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(position==0){
					Intent myIntent = new Intent(lcontext,about.class);
					myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					x.getContext().startActivity(myIntent);
				}
				
			}
		});
          
            
          return vi;
          
	}
	
	
	
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
	
	
	class row{
		String function;
				
public row(String title) {
	// TODO Auto-generated constructor stub
	super();
	this.function = title;
	
}

public void add(String title){
	this.function = title;
	return;
}
		
		
		
		
		
		
		
		
		
		
	}
	
	
