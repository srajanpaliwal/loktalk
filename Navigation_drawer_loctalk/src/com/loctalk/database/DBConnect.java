package com.loctalk.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBConnect extends SQLiteOpenHelper {
	// The Android's default system path of your application database.
	
	protected static String DB_PATH = "/data/data/com.loctalk/databases/";

	protected static String DB_NAME = "loctalk.sqlite";

	private SQLiteDatabase db;

	private final Context myContext;
	//Context tempcon = DBConnect.this;
	//String pa = myContext.getFilesDir().getPath();

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 * @param db_name
	 */
	public DBConnect(Context context) {
		super(context, DB_NAME, null, 2);
		
		if (db != null && db.isOpen())
			close();

		this.myContext = context;

		try {
			createDataBase();
			openDataBase();
		} catch (IOException e) {
			System.out.println("Exception in creation of database : "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			System.out.println("Database Exist");
		} else {
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		boolean checkdb = false;
        try{
        	 System.out.println("enter Database exist");
            String myPath = myContext.getFilesDir().getAbsolutePath().replace("files", "databases")+File.separator + DB_NAME;
            File dbfile = new File(myPath);                
            checkdb = dbfile.exists();
            if(checkdb)
            	System.out.println("Database exist");
            	else
            		System.out.println("Database doesn't exist");
        }
        catch(SQLiteException e){
            System.out.println("Database doesn't exist");
        }

        return checkdb;
	}

	public void copyCacheToMain(DBConnect objCache) throws IOException {
		
		// Open your local db as the input stream
		String inFileName = DB_PATH + DB_NAME;
		InputStream myInput = new FileInputStream(inFileName);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
		Log.d("CTM", "Cache To Main Database Copied !");
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 */

	private void copyDataBase() throws IOException {
		 System.out.println("In COpy ");
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
		 System.out.println(DB_NAME + "Database Copied !");
	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		if (isOpen())
			close();
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	public boolean isOpen() {
		if (db != null)
			return db.isOpen();

		return false;
	}

	@Override
	public synchronized void close() {
		if (db != null)
			db.close();
		// System.out.println("My db is:- " + db.isOpen());
		super.close();
	}

	public synchronized void execNonQuery(String sql) {
		try {
			
			db.execSQL(sql);
			// Log.d("SQL", sql);
		} catch (Exception e) {
			Log.e("Err", e.getMessage());
		} finally {
			// closeDb();
		}
	}

	public synchronized Cursor execQuery(String sql) {
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			// Log.d("SQL", sql);
		} catch (Exception e) {
			Log.e("Err", e.getMessage());
		}
		return cursor;
	}
	
	/*
	 * updated upvote func
	 */

	public synchronized int updateAdd(String values, String adContent, String Liked){
		ContentValues cv = new ContentValues();
		cv.put("Vote", values);
		cv.put("Liked", Integer.parseInt(Liked));
		String where = "Content=?";
		String whereArgs[] = new String[]{adContent};
		int n = db.update("Premium", cv, where, whereArgs);
		System.out.println("updating ad===="+adContent+"==Yo=="+n);
		return n;
		}
	
	public synchronized int updateAdd2(String values, String adContent){
		ContentValues cv = new ContentValues();
		cv.put("Vote", values);
		//cv.put("Liked", Integer.parseInt(Liked));
		String where = "Content=?";
		String whereArgs[] = new String[]{adContent};
		int n = db.update("Premium", cv, where, whereArgs);
		System.out.println("updating ad===="+adContent+"==Yo=="+n);
		return n;
		}
	
	public synchronized int updatePC(int values, String appID){
		ContentValues cv = new ContentValues();
		cv.put("PC", values);
		String where = "AppID="+appID;
		int n = db.update("Peers", cv, where, null);
		System.out.println("---->PC Updated with message: "+n);
		return n;
		}
	
	public synchronized int updatemyNick(String newnick){
		ContentValues cv = new ContentValues();
		cv.put("nick", newnick);
		return db.update("myNickTbl", cv, null, null);
	}
		
	
	
	public void backup() {
		try {
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				String currentDBPath = "\\data\\com.loctalk\\databases\\loctalk.sqlite";
				// String backupDBPath = "{database name}";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, DB_NAME);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB)
							.getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}