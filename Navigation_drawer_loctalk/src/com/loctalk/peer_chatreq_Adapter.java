package com.loctalk;

import java.util.ArrayList;






import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class peer_chatreq_Adapter extends BaseAdapter {
	
	private Context lcontext;
	private ArrayList<String> llist;
	
	
	
	public peer_chatreq_Adapter(Context context, ArrayList<String> list){
		this.lcontext = context;
		this.llist = list;
		System.out.println("Creating adapter");
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
	
	Context getContext(){
		return this.lcontext;
	}


	//ViewFlipper vf;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//View vi = convertView;
		final Req_ViewHolder holder ;
		//RelativeLayout lay = null;
		System.out.println("In getView");
		if(convertView==null){
			convertView = LayoutInflater.from(lcontext).inflate(R.layout.peerrow_chatreq, null, false);
			holder = new Req_ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.nick);
			holder.flip=(ViewFlipper)convertView.findViewById(R.id.flipper);
			holder.flip.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
			System.out.println(holder.flip.getDisplayedChild());
			convertView.setTag(holder);
		}
		else{
			holder = (Req_ViewHolder)convertView.getTag();
		}
			Button acceptBt = (Button) convertView.findViewById(R.id.Accept);
			Button rejectBt = (Button) convertView.findViewById(R.id.Decline);
			acceptBt.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					holder.flip.setDisplayedChild(1);
					
					//System.out.println(holder.flip.getDisplayedChild());
					
				}
			
			});
		
				
			rejectBt.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					holder.flip.setDisplayedChild(2);
					//System.out.println(holder.flip.getDisplayedChild());
						
				}
				
			});
			
          //holder.flip=vf;
          String list = llist.get(position);
          holder.tv.setText(list);
          System.out.println("Text Set for "+position);
            
          return convertView;
	}
	
	
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}

class Req_ViewHolder{
	TextView tv;
	ViewFlipper flip;
	RelativeLayout rl;
}
	

		
		
	
	
