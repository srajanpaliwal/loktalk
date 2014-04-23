package com.loctalk;

public class chatObject {
	private String msg;
	private String time;
	
	public chatObject(String m, String t){
		msg = m;
		time = t;
	}
	
	public String getMessage(){
		return this.msg;
	}
	
	public String getTime(){
		return this.time;
	}
}
