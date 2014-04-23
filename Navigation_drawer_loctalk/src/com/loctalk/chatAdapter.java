package com.loctalk;

import java.util.ArrayList;
import java.util.Hashtable;





import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class chatAdapter extends BaseAdapter {
	
	private Context lcontext;
	private Hashtable<String, ArrayList<chatObject>> chatmsg;
	//private ArrayList<Hashtable<String, ArrayList<String>>> chat;
	private ArrayList<String> chat;
	Handler mHandler;
	public chatAdapter(Context context, ArrayList<String> list, Hashtable<String, ArrayList<chatObject>> listmsg,Handler mHandler){
		this.lcontext = context;
		this.chat = list;
		this.chatmsg = listmsg;
		this.mHandler=mHandler;
		System.out.println("Creating adapter");
		}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return chat.size(); 
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chat.get(position);

	}
	
	Context getContext(){
		return this.lcontext;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		vHolder holder = null ;

		System.out.println("In getView");
		
		if(vi==null){
			vi = LayoutInflater.from(lcontext).inflate(R.layout.peerrow_perchat, parent, false);
			holder = new vHolder();
			holder.nickView = (TextView) vi.findViewById(R.id.nick);
			holder.msgView = (TextView) vi.findViewById(R.id.last_chat);
			holder.timeView = (TextView) vi.findViewById(R.id.time);
			vi.setTag(holder);
		}
		else{
			holder = (vHolder)vi.getTag();
		}
		vi.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				System.out.println("chatadapter");
				mHandler.obtainMessage(3,position+"").sendToTarget();
			}
		});
		
		  String nick = (chat.get(position));
		  System.out.println("Nick at poistion "+position+" is "+nick);
		  ArrayList<chatObject> ar = chatmsg.get(nick);
		  System.out.println("First message is "+ar.get(0).getMessage());
		  String m = (ar.get(ar.size()-1)).getMessage();
		  if(m.length()>50){
			  m = m.substring(0, 80);
			  m = m + "...";
		  }
		  String t = (ar.get(ar.size()-1)).getTime();
		  System.out.println("Message :"+ m);
		  holder.nickView.setText(nick);
          holder.msgView.setText(m);
          holder.timeView.setText(t);
          System.out.println("Text Set for "+position);
            
          return vi;
	}
	
	
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}

class vHolder{
	TextView nickView;
	TextView msgView;
	TextView timeView;
}
