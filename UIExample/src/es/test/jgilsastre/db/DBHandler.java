package es.test.jgilsastre.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import es.test.jgilsastre.dao.DiagramDAO;
import es.test.jgilsastre.util.LoggerUtils;

public class DBHandler extends SQLiteOpenHelper {

	public static final String DB_NAME = "erdroid.db";
	public static final int DB_VERSION = 1;
	private LoggerUtils log;
	
	public DBHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

		log = new LoggerUtils(this.getClass().getName());
		
	}
	public DBHandler(Context context){
		this(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(db.isReadOnly()){
			db = getWritableDatabase();
		}
		log.debug("Lanzando los creates de las tablas");
		db.execSQL(DiagramDAO.createTable());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
