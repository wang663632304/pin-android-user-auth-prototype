package com.mindpin.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MindpinDBHelper extends SQLiteOpenHelper{
	
	private static final String create_table_users = "create table " +
			Constants.TABLE_USERS + "(" + 
			Constants.KEY_ID + " integer primary key autoincrement, "+
			Constants.TABLE_USERS__USER_ID + " integer not null, "+
			Constants.TABLE_USERS__NAME + " text not null, "+
			Constants.TABLE_USERS__COOKIES + " text not null, "+
			Constants.TABLE_USERS__INFO + " text not null);";
	
	public MindpinDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(create_table_users);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+Constants.TABLE_USERS);
		onCreate(db);
	}
}
