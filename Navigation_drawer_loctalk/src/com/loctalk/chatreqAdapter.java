package com.loctalk;

import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;

import java.util.ArrayList;






import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

public class chatreqAdapter extends BaseAdapter {
	
	private Context lcontext;
	private ArrayList<String> llist;
	Handler mHandler;
	sender sen;
	
	
	public chatreqAdapter(Context context, ArrayList<String> list,Handler handler){
		this.mHandler=handler;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//View vi = convertView;
		final ViewHolder holder ;
		//RelativeLayout lay = null;
		System.out.println("In getView");
		if(convertView==null){
			convertView = LayoutInflater.from(lcontext).inflate(R.layout.chatreq, null, false);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.nick);
			holder.flip=(ViewFlipper)convertView.findViewById(R.id.flipper);
			holder.flip.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
			//vf.setDisplayedChild(0);
			//ViewFlipper temp =(ViewFlipper)vi.findViewById(R.id.flipper);
			//holder.rl=(RelativeLayout)temp.findViewById(0);
			System.out.println(holder.flip.getDisplayedChild());
			convertView.setTag(holder);
		}
		else{
			//System.out.println(holder.flip.getDisplayedChild());
			holder = (ViewHolder)convertView.getTag();
		}
			Button acceptBt = (Button) convertView.findViewById(R.id.Accept);
			Button rejectBt = (Button) convertView.findViewById(R.id.Decline);
			acceptBt.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					holder.flip.showNext();
					mHandler.obtainMessage(1,position+"").sendToTarget();
					System.out.println(holder.flip.getDisplayedChild());
				}
			});
		
				
			rejectBt.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					(Toast.makeText(getContext(), "Rejected", 1)).show();
					//vf.setDisplayedChild(R.id.second);
					
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

class ViewHolder{
	TextView tv;
	ViewFlipper flip;
	RelativeLayout rl;
	// flip;
}
	

		
		

	
	
