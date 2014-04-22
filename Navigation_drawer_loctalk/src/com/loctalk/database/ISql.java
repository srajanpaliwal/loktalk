package com.loctalk.database;

public interface ISql {
	
	String INSERT_MSG = "INSERT OR REPLACE INTO Messages(ID, AppID, Content, Time) values (%d, %d, '%s', '%s')";
	String GET_MSG = "SELECT ID,AppID,Content,Time FROM Messages";
	String INSERT_Post = "INSERT OR REPLACE INTO Posts(ID, AppID, Content, Time,Category) values (%d, %d, '%s', '%s','%s')";
	String GET_Post = "SELECT ID,AppID,Content,Time,Category FROM Posts where Category='%s'";
	String COUNT_Post = "SELECT count(ID) from Posts";
	String REMOVE_MSG = "DELETE FROM Messages WHERE ID = %d";
	String COUNT_MSG = "SELECT count(ID) from Messages";
	
	//String GET_STUDENTS = "Select * from tblStudent" ;
	
	String INSERT_PEER = "INSERT OR REPLACE INTO Peers(AppID, Nick, MAC, IP, PC, Block) values (%d, '%s', '%s', '%s', %d, %d)";
	String GET_PEER = "SELECT AppID,Nick,MAC,IP,PC,Block FROM Peers";
	String REMOVE_PEER = "DELETE FROM Peers WHERE AppID = %d";
	String COUNT_PEER = "SELECT count(AppID) from Peers";
	String GET_ONEPEER = "SELECT AppID,Nick,IP,PC,Block FROM Peers WHERE AppID = %d";
	String INSERT_CHATREQ = "INSERT OR REPLACE INTO ChatReq(AppID, Nick, MAC, IP, PC, Block) values (%d, '%s', '%s', '%s', %d, %d)";
	String COUNT_CHATREQ = "SELECT count(AppID) from ChatReq";
	
	String INSERT_MYNICK = "INSERT OR REPLACE INTO myNickTbl(nick) values ('%s')";
	String GET_MYNICK = "SELECT nick FROM myNickTbl";
	
	String INSERT_PREMIUM = "INSERT OR REPLACE INTO Premium(ID, Nick, AppID, Content, Time, Vote) values (%d, '%s', %d,'%s', '%s', %d)";
	String GET_PREMIUM = "SELECT ID,Nick,AppID,Content,Time,Vote FROM Premium";
	String REMOVE_PREMIUM = "DELETE FROM Premium WHERE ID = %d";
	String GET_P_COUNT = "SELECT Vote FROM Premium WHERE ID = %d AND AppID = %d";
	String GET_P_SENDER = "SELECT AppID FROM Premium WHERE ID = %d";
	String COUNT_PREMIUM = "SELECT count(ID) from Premium";
}
