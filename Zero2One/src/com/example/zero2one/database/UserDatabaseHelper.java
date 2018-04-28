package com.example.zero2one.database;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper{
    
	//	The Android's default system path of your application database.
	private String DB_PATH; 

 
	private static String DB_NAME = UserDatabase.DATABASE_NAME+UserDatabase.DATABASE_NUMBER;
 
	private SQLiteDatabase myDataBase;
 
	private final Context myContext;
 

	public UserDatabaseHelper(Context context) {
 
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		String pkg = myContext.getPackageName();
		DB_PATH = "/data/data/";
		DB_PATH += pkg;
		DB_PATH += "/databases/";
		//PhonequakeLog.getInstance().log(DB_PATH);
	}	
 
	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 **/
	public void destroyCurrentVersion(){
		String outFileName = DB_PATH + DB_NAME;
		File f = new File(outFileName);
		f.delete();
		//PhonequakeLog.getInstance().log("Destroy Current Base");
	}
	public void createDataBase() throws IOException{
		this.createDataBase(true, false);
	}
	public void createDataBase(boolean deleteOldVersions, boolean forceRecreate) throws IOException{
 
		boolean dbExist = checkDataBase();
		
		if(dbExist && !forceRecreate){
		//	PhonequakeLog.getInstance().log("##DBEXISTS");
		}else{
 
            if(forceRecreate){
                try{
                   String outFileName = DB_PATH + DB_NAME;
                   File file = new File(outFileName);
                   file.delete();
                   file = null;
                }catch(Exception ers){
                 //   PhonequakeLog.getInstance().error(ers.getMessage());
                }
            }

			try{
     			// By calling this method and empty database will be created into the default system path
	   		//of your application so we are gonna be able to overwrite that database with our database.
		   	   SQLiteDatabase d =this.getReadableDatabase();
		   	   if(d!=null){
		   		   d.close();
		   	   }
		   	   
			}catch(Exception ers){}
 
			try {
 
				copyDataBase();
 
			} catch (IOException e) {
				e.printStackTrace();
				//throw new Error("Error copying database");

			}
			if(deleteOldVersions){
				try {
					//PhonequakeLog.getInstance().log("deleting old versions");
					deleteOlderVersionsDatabase();

				} catch (Exception e) {
					e.printStackTrace();
					//throw new Error("Error deleting database");

				}
			}

		}
	}
 
	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * 	@return true if it exists, false if it doesn't
	 */
	public boolean checkDataBase(){
 
		SQLiteDatabase checkDB = null;
 
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			
		}catch(SQLiteException e){
 
			//	database does't exist yet.
 
		}
 
		if(checkDB != null){
 
			checkDB.close();
 
		}
 
		return checkDB != null ? true : false;
	}
 
	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * 	system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException{

		String outFileName = DB_PATH + DB_NAME;
		
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;

		String[] path =  myContext.getAssets().list("");

		int size = 0;

		for(String y:path){
			if(y.toLowerCase().indexOf(UserDatabase.DATABASE_NAME)>=0){
				size = size+1;
			}
		}

		//PhonequakeLog.getInstance().log("Number of parts"+String.valueOf(size));

		for(int i=1;i<=size;i++){
			InputStream myInput = myContext.getAssets().open(DB_NAME+".db."+String.valueOf(i));
	
		//	PhonequakeLog.getInstance().log("Processing"+DB_NAME+".db."+String.valueOf(i));
	
			while ((length = myInput.read(buffer))>0){
				myOutput.write(buffer, 0, length);
			}
			myInput.close();
	
		}

		//	Close the streams
		myOutput.flush();
		myOutput.close();

	}
 
	public void deleteOlderVersionsDatabase()  throws Exception{
		Integer number = Integer.parseInt(UserDatabase.DATABASE_NUMBER);
		if(number>1){
			for(int i=1;i<number;i++){
				File f = new File(DB_PATH + UserDatabase.DATABASE_NAME+i);
				if(f.exists()){
					f.delete();
				}
				f = null;
			}
			
		}
	}
	public void openDataBase() throws SQLException{
 
		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
 
	@Override
	public synchronized void close() {
 
		if(myDataBase != null)
			myDataBase.close();
 
		super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}

	public void deleteDatabase() throws Exception {
		File f = new File(DB_PATH + DB_NAME);
		if(f.exists()){
		//	PhonequakeLog.getInstance().log("Deleting " + DB_NAME);
			f.delete();
		}
		f = null;
	}
 
	// Add your public helper methods to access and get content from the database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
	// to you to create adapters for your views.
 
}

