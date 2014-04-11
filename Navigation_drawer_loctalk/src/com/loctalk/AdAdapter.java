package com.loctalk;

import java.util.ArrayList;








import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdAdapter extends BaseAdapter {
	
	private Context lcontext;
	private ArrayList<single_row> llist;
	
	
	
	
	public AdAdapter(Context context, ArrayList<single_row> list){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		
            vi = LayoutInflater.from(lcontext).inflate(R.layout.row_advt, parent, false);
		
          TextView title = (TextView) vi.findViewById(R.id.title);
          TextView description = (TextView) vi.findViewById(R.id.description);
          final ImageButton imageButton = (ImageButton) vi.findViewById(R.id.buttonUpvote);
         
          single_row list = llist.get(position);
          
          title.setText(list.title);
          description.setText(list.description);
          
          imageButton.setOnClickListener(new OnClickListener() {
        	  boolean isOn=false;
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isOn=!isOn;
				if(!isOn)
					imageButton.setImageResource(R.drawable.upvote);
				else
				imageButton.setImageResource(R.drawable.up11);
				
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
	
	
	class single_row{
		String title;
		String description;
		boolean buttonUpvote;
		
		
		
public single_row(String title , String description) {
	// TODO Auto-generated constructor stub
	super();
	this.title = title;
	this.description = description;
	
}

public void add(String title , String description){
	this.title = title;
	this.description = description;
	return;
}
		
		
		
		
		
		
		
		
		
		
	}
	
	
