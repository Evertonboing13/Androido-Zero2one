package com.example.zero2one.dao;

import com.example.zero2one.database.UserDatabase;
import com.example.zero2one.database.UserDatabaseUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AbstractDAO {
	
		public Context context;
		public UserDatabase userDatabase;
		public static String dbLockUser   = "DBUser";
		
		public AbstractDAO(Context context) {
			this.context = context;
			this.userDatabase = new UserDatabase(context);
		}	
		
		public Context getContext() {
			return context;
		}



		public UserDatabase getUserDatabase() {
			return userDatabase;
		}

		public void setUserDatabase(UserDatabase userDatabase) {
			this.userDatabase = userDatabase;
		}

		public void close(){
			this.userDatabase.close();
	
		}
		
		public SQLiteDatabase getWritableUserDatabase(){
			synchronized(dbLockUser) {
			   return UserDatabaseUtil.getNoLockWritableDatabase(userDatabase);
			}
		}
		
		public SQLiteDatabase getReadableUserDatabase(){
			synchronized(dbLockUser) {
			   return UserDatabaseUtil.getNoLockReadableDatabase(userDatabase);
			}
		}	
		
}
