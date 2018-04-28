package com.example.zero2one.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ParamGeralDAO extends AbstractDAO{
	
	public static String TABLE_NAME = "param_geral";
	public ParamGeralDAO(Context context) {
		super(context);
	}

	public String getParameter(String key){
		String value = "";
		String where = "key = '"+key+"'";
		SQLiteDatabase db = getReadableUserDatabase();
		if(db==null){
			return "";
		}
		try{
			String[] fields = new String[]{"value"};
			Cursor cursor = db.query(TABLE_NAME, fields, 
					where, null, null, null, null);
			if(cursor.getCount()>0){
				cursor.moveToFirst();
				value = cursor.getString(0);
			}
			cursor.close();
		}catch(Exception srs){
			srs.printStackTrace();
		}finally{
			db.close();
		}
		return value;
	}
	
	public void setParameter(String key, String value){
		boolean exits = getParameterExists(key);
		if(exits){
			SQLiteDatabase db = getWritableUserDatabase();
			try{
				String sql = "update " + TABLE_NAME + " set value = '" + value +"' ";
				sql+=" where key = '"+key+"'";

				db.execSQL(sql);	
			}catch(Exception ers){
				ers.printStackTrace();
			}
			db.close();
		}else{
			ContentValues cv = new ContentValues();
			cv.put("key", key);
			cv.put("value", value);

			SQLiteDatabase db = getWritableUserDatabase();
			try{
				db.insert(TABLE_NAME, null, cv);
			}catch(Exception erzz){
				erzz.printStackTrace();
			}
			db.close();
		}
	}
	
	public boolean getParameterExists( String key){
		Integer ret = 0;
		String sql = "SELECT COUNT(*) AS QTD FROM "+TABLE_NAME + " WHERE key = '"+key+"'";
		

		SQLiteDatabase db = getReadableUserDatabase();
		if(db==null){
			return false;
		}
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.getCount()>0){
			cursor.moveToFirst();
			ret = cursor.getInt(0);
		}
		
		cursor.close();
		db.close();
		if(ret>0){
			return true;		
		}else{
			return false;
		}
	}

}
