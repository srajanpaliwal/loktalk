package com.loctalk;

import android.text.SpannableString;

/**
 * Message is a Custom Object to encapsulate message information/fields
 * 
 * @author Adil Soomro
 *
 */
public class Message1 {
	/**
	 * The content of the message
	 */
	String message;
	SpannableString mymes;
	/**
	 * boolean to determine, who is sender of this message
	 */
	boolean isMine;
	/**
	 * boolean to determine, whether the message is a status message or not.
	 * it reflects the changes/updates about the sender is writing, have entered text etc
	 */
	boolean isStatusMessage;
	
	/**
	 * Constructor to make a Message object
	 */
/*	public Message1(String message, boolean isMine) {
		super();
		this.message = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
	}*/
	
	public Message1(SpannableString message, boolean isMine) {
		super();
		this.mymes = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
	}
	 
	public void add(SpannableString message, boolean isMine) {
		
		this.mymes = message;
		this.isMine = isMine;
		this.isStatusMessage = false;
		return;
	}
	public SpannableString getMessage() {
		return mymes;
	}
	
	public void setMessage(SpannableString message) {
		this.mymes = message;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	public boolean isStatusMessage() {
		return isStatusMessage;
	}
	public void setStatusMessage(boolean isStatusMessage) {
		this.isStatusMessage = isStatusMessage;
	}
	
	
}
