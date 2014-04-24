package com.loctalk;

import java.util.ArrayList;
import static com.loctalk.MainActivity.brAddress;
import static com.loctalk.Constant.db;
import static com.loctalk.Constant.dbFunctions;
import static com.loctalk.Constant.jsonFunctions1;
import static com.loctalk.Constant.myAppID;
import static com.loctalk.Constant.myNick;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
public class Addfragment  extends ListFragment {



	ArrayList<single_row> llist;
	ArrayList<ids> adID ;
	AdAdapter adapter;
	ListView list;
	//ImageButton upvoteButton;
	String Str;
	sender adSen;
	int handCount=0; //remove later


	private final Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	    	 Str = msg.obj.toString();
	    	// once the position is received from the handler in adapter
	    	// send the broadcast containing the message in the format no.oflikesuuucontent
	    	// update the likes in our database as well.
	    	String[] handleStr = Str.split("popo");
	    	/*
	    	 * handleStr[0] = position
	    	 * handleStr[1] = vote count
	    	 * handleStr[2] = liked
	    	 */
	    	single_row sr = llist.get(Integer.parseInt(handleStr[0]));
	    	ids idid = adID.get(Integer.parseInt(handleStr[0]));
	    	db.updateAdd(handleStr[1], sr.description,handleStr[2]);
	    	System.out.println("content to be queried==="+sr.description);
	    	String toS = handleStr[2]+"uuu"+sr.description;
	    	String toSend = jsonFunctions1.createUltiJSON(myAppID, myNick, toS, "adUpvote");
	    	adSen = new sender(toSend, brAddress);
	    	adSen.start();
	    	handCount++;
	    	System.out.println("handler count=="+handCount);
	    	}
	    };


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_main_tiles, container, false);
		View rootview = inflater.inflate(R.layout.row_advt, container, false);

		//upvoteButton = (ImageButton) getView().findViewById(R.id.buttonUpvote);



		return rootView;



	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//adapter = new AdAdapter(getActivity(), llist);
		//list = (ListView) getActivity().findViewById(android.R.id.list);
		ArrayList<JSONObject> array = db.getPremium();
		adID = new ArrayList<ids>();
		int i = 0;
		JSONObject temp = null;
		llist = new ArrayList<single_row>();
		/*llist.add(new single_row("hello1", "aalo le lo","23"));
		llist.add(new single_row("hello2", "gobi le lo","24"));
		llist.add(new single_row("hello3", "pyaaz le lo","1"));
		llist.add(new single_row("hello4", "tamatar le lo","0"));
*/
		//list.setAdapter(new AdAdapter(getActivity(), llist));
		while(i<=array.size()-1){
			temp = array.get(i);
			try{
			System.out.println("====Ads from db===");
			System.out.println("Ad ID = " + temp.getString("ID"));
			System.out.println("Ad Nick = " + temp.getString("Nick"));
			System.out.println("App ID = " + temp.getString("AppID"));
			System.out.println("Ad Content = " + temp.getString("Content"));
			System.out.println("Ad Vote = " + temp.getString("Vote"));
			System.out.println("Ad Liked = " + temp.getInt("Liked"));
			llist.add(new single_row(temp.getString("Nick"),temp.getString("Content"),temp.getString("Vote"),temp.getInt("Liked")));
			adID.add(new ids(temp.getString("ID"), temp.getString("AppID")));
			}
			catch(Exception e ){
				System.out.println("JSON exception in ads"+e);
			}
			i++;
		}


		adapter = new AdAdapter(getActivity(), llist,mHandler);
		setListAdapter(adapter);
		}
	class ids{
		String adID;
		String apID;

		ids(String id1, String id2){
			adID = id1;
			apID = id2;
		}
	}




	}

