package com.loctalk;

import java.util.ArrayList;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdAdapter extends BaseAdapter {

	private Context lcontext;
	private ArrayList<single_row> llist;
	Handler mHandler;



	public AdAdapter(Context context, ArrayList<single_row> list,Handler mHandler){
		this.lcontext = context;
		this.llist = list;
		this.mHandler=mHandler;
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

            vi = LayoutInflater.from(lcontext).inflate(R.layout.row_advt, parent, false);

          TextView title = (TextView) vi.findViewById(R.id.title);
          TextView description = (TextView) vi.findViewById(R.id.description);
          final TextView uvCounter = (TextView) vi.findViewById(R.id.uvCounter);
          
          final ImageButton imageButton = (ImageButton) vi.findViewById(R.id.buttonUpvote);
         
          final single_row list = llist.get(position);
          
          if(list.liked==1){
        	  imageButton.setImageResource(R.drawable.up11);
          }
          else if(list.liked==0){
        	  imageButton.setImageResource(R.drawable.upvote);
          }
          title.setText(list.title);
          description.setText(list.description);
          uvCounter.setText(list.uvCounter);
          
         
          
          
          imageButton.setOnClickListener(new OnClickListener() {
        	  boolean isOn=false;
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isOn=!isOn;
				if(list.liked==1){
					imageButton.setImageResource(R.drawable.upvote);
					
				
					int tbaba;
					tbaba=db.getContentPremium(list.description);
					tbaba = tbaba-1;
					System.out.println("tbaba==="+tbaba);
					uvCounter.setText(Integer.toString(tbaba));
					String sendtoadfrag = ""+position+"popo"+tbaba+"popo"+0;
					list.uvCounter=""+tbaba;
					list.liked = 0;
					
					// pass the position via handler
					mHandler.obtainMessage(1,sendtoadfrag).sendToTarget();
				}
				else{
					imageButton.setImageResource(R.drawable.up11);
					int tbaba;
					tbaba=db.getContentPremium(list.description);
					tbaba = tbaba+1;
					System.out.println("tbaba==="+tbaba);
					uvCounter.setText(Integer.toString(tbaba));
					String sendtoadfrag = ""+position+"popo"+tbaba+"popo"+1;
					list.uvCounter=""+tbaba;
					list.liked = 0;
					
					// pass the position via handler
					mHandler.obtainMessage(1,sendtoadfrag).sendToTarget();
					
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


	class single_row{
		String title;
		String description;
		int liked;
		String uvCounter;



public single_row(String title , String description, String uvCounter, int liked ) {
	// TODO Auto-generated constructor stub
	super();
	this.title = title;
	this.description = description;
	this.uvCounter = uvCounter;
	this.liked = liked;

}

public void add(String title , String description,String uvCounter, int liked){
	this.title = title;
	this.description = description;
	this.uvCounter = uvCounter;
	this.liked = liked;
	return;
}










	}

