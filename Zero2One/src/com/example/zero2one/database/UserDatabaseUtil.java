package com.example.zero2one.database;

import android.database.sqlite.SQLiteDatabase;

public class UserDatabaseUtil {

	public static synchronized SQLiteDatabase getNoLockWritableDatabase(UserDatabase database){
		SQLiteDatabase sqLiteDatabase = null;
		boolean ok= false;
		try{
		   sqLiteDatabase = database.getWritableDatabase();
		   ok= true;
		}catch(Exception erz){
			ok = false;
		}
		
		if(!ok ||sqLiteDatabase.isDbLockedByOtherThreads()){
			sqLiteDatabase.close();
			boolean dispacth = false;
			
			for(int z=0;z<5;z++){
			   try{
 				   Thread.sleep(2000);
			   }catch(Exception ers){}
			   
			   try{  
				   sqLiteDatabase = database.getWritableDatabase();
			   }catch(Exception erz){
				   dispacth = false;
				   sqLiteDatabase = null;
			   }
			   
			   if(sqLiteDatabase!=null && sqLiteDatabase.isDbLockedByOtherThreads()){
				   dispacth = false;
				   sqLiteDatabase.close();
				   sqLiteDatabase = null;
				   continue;
			   }else{
				   dispacth = true;
				   break;
			   }
			}
		}
		return sqLiteDatabase;
	}
	
	public static synchronized SQLiteDatabase getNoLockReadableDatabase(UserDatabase database){
		SQLiteDatabase sqLiteDatabase = null;
		boolean ok= false;
		try{
		   sqLiteDatabase = database.getReadableDatabase();
		   ok= true;
		}catch(Exception erz){
			erz.printStackTrace();
			ok = false;
		}
		
		if(!ok ||sqLiteDatabase.isDbLockedByOtherThreads()){
			if(sqLiteDatabase!=null){
			   sqLiteDatabase.close();
			}
			boolean dispacth = false;
			
			for(int z=0;z<5;z++){
			   try{
 				   Thread.sleep(2000);
			   }catch(Exception ers){}
			   
			   try{  
				   sqLiteDatabase = database.getReadableDatabase();
			   }catch(Exception erz){
				   dispacth = false;
				   sqLiteDatabase = null;
			   }
			   
			   if(sqLiteDatabase!=null && sqLiteDatabase.isDbLockedByOtherThreads()){
				   dispacth = false;
				   sqLiteDatabase.close();
				   sqLiteDatabase = null;
				   continue;
			   }
			}
		}
		return sqLiteDatabase;
	}	
}
