package com.loctalk;

import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import com.loctalk.database.AppDB;

import navigation.NavDrawerItem;
import navigation.NavDrawerListAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements dataTransfertoActivityInterface{
	dataTransferInterface datatofragment;
	dataTransferInterface datatopeerfragment;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	//Fragment fragment = null;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private ArrayList<String> adRepliers = new ArrayList<String>();
	private NavDrawerListAdapter adapter;
	receiver receiverthread;
	sender senMain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//super.onCreate(savedInstanceState);
		
		/*
		 * request people for premium ads table
		 */
		String adrequest = jsonFunctions1.createUltiJSON(myAppID, myNick, "need ads", "adReq");
		senMain = new sender(adrequest, getBroadcastAddress());
		senMain.start();
		
		setContentView(R.layout.activity_main);
		System.out.println("Layout ke baad wala");
		//Initialization of database constants
		if (db == null){
			db = new AppDB(this);
			dbFunctions=new dbFunc(db);
		}
		
		 TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		 System.out.println("IMEI===="+mngr.getDeviceId());
		 myAppID = mngr.getDeviceId();
		 
		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(0, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(0, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(0, -1), true, "22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(0, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(0, -1), true, "50+"));
		
		
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			try{
				String extractedNick  = db.getMyNick();
			
			if(extractedNick.equals("defaultnick")){
				final Dialog dialog = new Dialog(this);
				 dialog.setContentView(R.layout.change_nick);
				 dialog.setTitle("Change Nick");	
				 Button dialogButtonA = (Button) dialog.findViewById(R.id.dialogButtonOK);
				 Button dialogButtonC = (Button) dialog.findViewById(R.id.dialogButtonCancel);

				 //cancel button clicked
				 dialogButtonC.setOnClickListener(new OnClickListener() {

					 @Override
					 public void onClick(View v) {
						 dialog.cancel();
					 }
				 });

				 //Go button clicked
				 dialogButtonA.setOnClickListener(new OnClickListener() {
					 @Override
					 public void onClick(View v) {
						 EditText location = (EditText)dialog.findViewById(R.id.location);
						 
						 
						 String loc = location.getText().toString();
						 if(!(loc.isEmpty())){
							 db.insertmyNick(loc);
							 myNick = loc;
				
						 }
						 else{
				 			 Toast mtoast = Toast.makeText(MainActivity.this, "Please enter a valid Nick.", Toast.LENGTH_LONG);
				 		 	 mtoast.show();
						 }
					 dialog.dismiss();


					 }

				 });

				 dialog.show();
			}

			else
				displayView(0);
		}
		catch(Exception e){
			System.out.println("Error in getting nick"+e);
		}
		}
		receiverthread=new receiver(mHandler);
		receiverthread.start();
		
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	String frag;
	ListFragment listfragment = null;
	ListFragment listfragment2 = null;
	ListFragment listfragment3 = null;
	ListFragment listfragment4 = null;
	ListFragment listfragment5 = null;

	int selected;
	FragmentTransaction ft;
	static Fragment fragment = null;
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		
		//ListFragment listfragment = null;
		switch (position) {
		case 0:
			if(listfragment == null){
				listfragment = new groupFragment();
				selected=0;
			}
			else
			{
				selected=0;
			}
			
			break;
		case 1:
			if(listfragment2 == null){
				listfragment2 = new Addfragment();
				selected=1;
			}
			else
			{
				selected=1;
			}
			break;
		case 2:
			if(listfragment3 == null){
				listfragment3 = new PeersFragment();
				selected=2;
			}
			else
			{
				selected=2;
			}
			break;
		case 3:
			if(listfragment4 == null){
				listfragment4 = new groupFragment();
				selected=3;
			}
			else
			{
				selected=3;
			}
			break;
		case 4:
			//pop up a dialog box
			 final Dialog dialog = new Dialog(this);
			 dialog.setContentView(R.layout.change_nick);
			 dialog.setTitle("Change Nick");	
			 Button dialogButtonA = (Button) dialog.findViewById(R.id.dialogButtonOK);
			 Button dialogButtonC = (Button) dialog.findViewById(R.id.dialogButtonCancel);

			 //cancel button clicked
			 dialogButtonC.setOnClickListener(new OnClickListener() {

				 @Override
				 public void onClick(View v) {
					 dialog.cancel();
				 }
			 });

			 //Go button clicked
			 dialogButtonA.setOnClickListener(new OnClickListener() {
				 @Override
				 public void onClick(View v) {
					 EditText location = (EditText)dialog.findViewById(R.id.location);
					 
					 
					 String loc = location.getText().toString();
					 if(!(loc.isEmpty())){
						 db.updatemyNick(loc);
						 myNick = loc;
						 System.out.println("updated nick===="+myNick);
			
					 }
					 else{
			 			 Toast mtoast = Toast.makeText(MainActivity.this, "Please enter a valid Nick.", Toast.LENGTH_LONG);
			 		 	 mtoast.show();
					 }
				 dialog.dismiss();


				 }

			 });

			 dialog.show();
			break;
		/*case 5:
			fragment = new WhatsHotFragment();
			break;*/

		default:
			break;
		}
		Bundle bundl;
		
		switch(selected){
		case 2:
		try{
			System.out.println("fragment--peer is getting created");
			bundl = new Bundle();
			bundl.putString("flag", "Group1");
			bundl.putString("broadip", getBroadcastAddress());
			listfragment3.setArguments(bundl);
			ft =getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame_container, listfragment3);
			ft.commit();
			}catch(Exception e){
				System.out.println("yaha aaya error!!!!!"+e);
			}	
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			break;
		case 0:
			try{
			System.out.println("fragment--0 is getting created");
			bundl = new Bundle();
			bundl.putString("flag", "postGen");
			listfragment.setArguments(bundl);
			ft =getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame_container, listfragment,"postGen");
			ft.commit();
			}
			catch (Exception e) {
				System.out.println("Error in frag,ent switch=====>"+e);
			}
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			break;
		case 1:
			System.out.println("fragment is getting created");
			FragmentTransaction ft2 =getSupportFragmentManager().beginTransaction();
			ft2.replace(R.id.frame_container, listfragment2);
			//ft.addToBackStack(frag);
			ft2.commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 3:
			try{
			System.out.println("fragment--0 is getting created");
			bundl = new Bundle();
			bundl.putString("flag", "postEvent");
			listfragment4.setArguments(bundl);
			ft =getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame_container, listfragment4,"postEvent");
			ft.commit();
			}
			catch (Exception e) {
				System.out.println("Error in frag,ent switch=====>"+e);
			}
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			
			break;	
		default:
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}
	
	

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(title);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	

	private final Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	/*
	    	 * receiving message from receiver thread via handler
	    	 * 
	    	 * obj conatins a string(recStr) of the form message-splitstr-ip
	    	 * 
	    	 * recAr[0] contains message
	    	 * recAr[1] contains IP of the message from where it was sent.
	    	 */
	    	try{
	    	String recStr = msg.obj.toString();
	    	String[] recAr = recStr.split("splitstr");
	    	System.out.println("message=="+ recAr[0]+"=== IP :"+recAr[1]);
	    	
	    		/*
	    		 * Parsing the received message and carrying out the required tasks.
	    		 */

				String[] parsedStr = jsonFunctions1.parseUltiJSON(recAr[0]);
				if(!(parsedStr[0].equals(myAppID))){
				if(parsedStr[3].equals("adReq")){
					String adRep = jsonFunctions1.createUltiJSON(myAppID, myNick, "reply for adReq", "adReply");
					senMain = new sender(adRep,recAr[1]);
					senMain.start();
				}

				else if(parsedStr[3].equals("adReply")){
					if(adRepliers.size()==0){
						String adseeker = jsonFunctions1.createUltiJSON(myAppID, myNick, "send ads", "adSeek");
						senMain = new sender(adseeker, recAr[1]);
						senMain.start();
					}
					
					adRepliers.add(recAr[1]);
				}

				else if(parsedStr[3].equals("adSeek")){
					String ads = jsonFunctions1.createUltiJSON(myAppID, myNick, dbFunctions.getAd(),"adSent");
					senMain = new sender(ads, recAr[1]);
					senMain.start();

				}

				else if(parsedStr[3].equals("adSent")){
					dbFunctions.addtoaddb(parsedStr[2]);

				}

				else if(parsedStr[3].equals("adUpvote")){

				}

				else if(parsedStr[3].equals("adDlt")){

				}

				else if(parsedStr[3].equals("postAd")){
					String ID=(db.countPremium()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addonetoaddb(ID, parsedStr[0],parsedStr[2], time, parsedStr[3]);
					Fragment frag=getSupportFragmentManager().findFragmentByTag("postAd");
					if(frag.isVisible())
					datatofragment.passdatatofragment("message",parsedStr[2]);

				}

				else if(parsedStr[3].equals("postGen")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0],msg.toString(), time, parsedStr[3]);
					Fragment frag=getSupportFragmentManager().findFragmentByTag("postGen");
					if(frag.isVisible())
					datatofragment.passdatatofragment("message",msg.toString());
				}

				else if(parsedStr[3].equals("postEvent")){
					String ID=(db.countPost()+1)+"";
					Calendar c = Calendar.getInstance(); 
					String time=c.getTime().toString();
					dbFunctions.addtopostdb(ID, parsedStr[0], msg.toString(), time, parsedStr[3]);
					Fragment frag=getSupportFragmentManager().findFragmentByTag("postEvent");
					if(frag.isVisible())
					datatofragment.passdatatofragment("message",msg.toString());

				}

				else if(parsedStr[3].equals("postHelp")){

				}

				else if(parsedStr[3].equals("postBusi")){

				}

				else if(parsedStr[3].equals("postFood")){

				}

				else if(parsedStr[3].equals("chatMsg")){
				//	datatofragment.passdatatofragment("message",msg.toString());
				}

				else if(parsedStr[3].equals("chatReq")){

				}

				else if(parsedStr[3].equals("chatReply")){
					
				}

				else if(parsedStr[3].equals("peerReq")){
					String toSend = jsonFunctions1.createUltiJSON(myAppID, myNick, "Hi peer", "peerReply");
					senMain = new sender(toSend,recAr[1]);
					senMain.start();

				}

				else if(parsedStr[3].equals("peerReply")){
					datatopeerfragment.passdatatopeerfragment(0, parsedStr,recAr[1]);
				}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("handle message"+e);
			}

	    }
	};

	String getBroadcastAddress() {
		String s;
		try {
	    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	    DhcpInfo dhcp = wifi.getDhcpInfo();
	    // handle null somehow

	    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
	    byte[] quads = new byte[4];
	    for (int k = 0; k < 4; k++)
	      quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
	    
	    
			s = InetAddress.getByAddress(quads).toString();
			System.out.println("Address====>"+s);
			
		} catch (Exception e) {
			System.out.println("Error in getBroadcastAddress====>"+e);
			s="NOIP";
		}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
		//please insert a check if wifi is not on or it is not able to get the host string
		return s;
	}
	
	public void addtodb(String id, String nick, String msg, String flag){
		JSONObject objStudent = new JSONObject();
		String strID = id;
		String strMsg = msg;
		
			// getNick(), getTime(), getFlag(), getID()
		String strNick = nick ;
		//Date d = new Date();
		//CharSequence s  = DateFormat.format("hh:mm:ss", d.getTime());
		//String strTime = s.toString();//SystemClock.currentThreadTimeMillis() ;
		String strTime = "10:10 AM";
		String strFlag= flag ;
		
		try {
			objStudent.put("ID", strID);
			objStudent.put("nick", strNick);
			
			objStudent.put("time", strTime);
			objStudent.put("msg", strMsg);
			objStudent.put("flag", strFlag);
			System.out.println("Jason string for db==>>"+"\n"+objStudent.toString());
			db.insertMsg(objStudent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error"+ e.getMessage().toString());
		}
	}

	@Override
	public void passdatatoActivity() {
		datatofragment.passdatatofragment("ip",getBroadcastAddress());
	}
	}
	


