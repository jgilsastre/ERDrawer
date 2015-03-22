package es.test.jgilsastre.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.test.jgilsastre.db.DBHandler;
import es.test.jgilsastre.main.Diagram;
import es.test.jgilsastre.util.LoggerUtils;

public class DiagramDAO implements DAOBasic<Diagram> {

	public static final String TABLE_NAME = "diagram";
	public static final String NAME = "name";
	public static final String EDITABLE = "editable";
	public static final String CREATED_DATE = "created_date";
	public static final String LAST_UPDATE = "last_update";
	public static final String PATH_DRIVE = "path_drive";
	
	private static DiagramDAO instance;
	private DBHandler handler;
	private SQLiteDatabase db;
	private LoggerUtils log;
	private String[] columns;
	
	public static DiagramDAO getInstance(Context context){
		if (instance == null){
			synchronized (DiagramDAO.class) {
				if (instance == null){
					instance = new DiagramDAO();
					instance.handler = new DBHandler(context);
				}
			}
		}
		return instance;
	}
	
	private DiagramDAO(){
		db = null;
		log = new LoggerUtils(this.getClass().getName());
		columns = new String[]{_ID, IDENTIFIER, NAME, EDITABLE, PATH_DRIVE};
	}
		
	public static String createTable() {
		StringBuilder sb = new StringBuilder("CREATE TABLE ");
		sb.append(TABLE_NAME + " (");
		sb.append(_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sb.append(IDENTIFIER + " TEXT NOT NULL, ");
		sb.append(NAME + " TEXT NOT NULL, ");
		sb.append(EDITABLE + " INTEGER, ");
		sb.append(CREATED_DATE + " INTEGER, ");
		sb.append(LAST_UPDATE + " INTEGER, ");
		sb.append(PATH_DRIVE + " TEXT)");
		return sb.toString();
	}
	
	@Override
	public void insert(Diagram elem) throws DAOException {
		
		ContentValues values = new ContentValues();
		values.put(IDENTIFIER, elem.getIdentifier());
		values.put(NAME, elem.getName());
		values.put(EDITABLE, 1);
		values.put(CREATED_DATE, new Date().getTime());
		values.put(PATH_DRIVE, elem.getPath());
		
		db = handler.getWritableDatabase();
		if(db==null)
			throw new DAOException("insertDiagram", "La base de datos es nula");
		
		long result = db.insert(TABLE_NAME, null, values);
		if(result==-1)
			throw new DAOException("insertDiagram", "No se ha insertado ningún registro");
		
		log.debug("Insertado un diagrama con _id: " + result);
		db.close();
		
	}

	@Override
	public void update(Diagram elem) throws DAOException {

		ContentValues values = new ContentValues();
		//values.put(IDENTIFIER, elem.getName());
		values.put(NAME, elem.getName());
		values.put(CREATED_DATE, new Date().getTime());
		values.put(EDITABLE, elem.isEditable() ? 1 : 0);
		values.put(LAST_UPDATE, new Date().getTime());
		values.put(PATH_DRIVE, elem.getPath());
		
		db = handler.getWritableDatabase();
		if(db==null)
			throw new DAOException("updateDiagram", "La base de datos es nula");
		
		long result = db.update(TABLE_NAME, values, IDENTIFIER + " LIKE ?", new String[]{elem.getIdentifier()});
		log.debug("Numero de registros actualizados para identificador " + elem.getIdentifier() + " -> " + String.valueOf(result));
		db.close();
		
	}

	@Override
	public Diagram find(String identifier) throws DAOException {
		
		String where = IDENTIFIER + " LIKE ?";
		String[] args = new String[]{identifier};
		db = handler.getReadableDatabase();
		if(db == null)
			throw new DAOException("findDiagram", "La base de datos es nula");
		Diagram diagram = null;
		Cursor cursor = db.query(TABLE_NAME, columns, where, args, null, null, null);
		if(cursor.moveToFirst()){
			diagram= new Diagram(cursor.getString(cursor.getColumnIndex(IDENTIFIER)), 
					cursor.getString(cursor.getColumnIndex(NAME)), 
					new Date(cursor.getInt(cursor.getColumnIndex(CREATED_DATE))),
					new Date(cursor.getInt(cursor.getColumnIndex(LAST_UPDATE))));
			if(cursor.getInt(cursor.getColumnIndex(EDITABLE)) == 0)
				diagram.setReadOnly();
			else 
				diagram.setEditable();
			diagram.setPath(cursor.getString(cursor.getColumnIndex(PATH_DRIVE)));
		}
		cursor.close();
		db.close();
		return diagram;
	}

	@Override
	public List<Diagram> findAll() throws DAOException {
		List<Diagram> diagrams = new ArrayList<Diagram>();
		Diagram diagram = null;
		db = handler.getReadableDatabase();
		if(db == null)
			throw new DAOException("findDiagram", "La base de datos es nula");
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				diagram= new Diagram(cursor.getString(cursor.getColumnIndex(IDENTIFIER)), 
						cursor.getString(cursor.getColumnIndex(NAME)), 
						new Date(cursor.getInt(cursor.getColumnIndex(CREATED_DATE))),
						new Date(cursor.getInt(cursor.getColumnIndex(LAST_UPDATE))));
				int editable = cursor.getInt(cursor.getColumnIndex(EDITABLE));
				if(editable == 0)
					diagram.setReadOnly();
				else 
					diagram.setEditable();
				diagram.setPath(cursor.getString(cursor.getColumnIndex(PATH_DRIVE)));
				diagrams.add(diagram);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return diagrams;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
}
