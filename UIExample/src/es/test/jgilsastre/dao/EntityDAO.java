package es.test.jgilsastre.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import es.test.jgilsastre.db.DBHandler;
import es.test.jgilsastre.main.Element;
import es.test.jgilsastre.main.Entity;
import es.test.jgilsastre.util.LoggerUtils;

public class EntityDAO implements DAOBasic<Entity> {
	
	public static final String TABLE_NAME = "entity";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String NAME = "name";
	public static final String WEAK = "weak";
	public static final String STRONG_ENTITY_ID = "strong_entity_id";
	
	private static EntityDAO instance;
	private DBHandler handler;
	private SQLiteDatabase db;
	private LoggerUtils log;
	private String[] columns;
	
	public static EntityDAO getInstance(Context context){
		if (instance == null){
			synchronized (EntityDAO.class) {
				if (instance == null){
					instance = new EntityDAO();
					instance.handler = new DBHandler(context);
				}
			}
		}
		return instance;
	}
	
	private EntityDAO(){
		db = null;
		log = new LoggerUtils(this.getClass().getName());
		columns = new String[]{IDENTIFIER
				, NAME
				, ElementDAO.POSITION_X
				, ElementDAO.POSITION_Y
				, ElementDAO.EDITABLE
				, WIDTH
				, HEIGHT
				, WEAK};
	}

	public static String createTable() {
		StringBuilder sb = ElementDAO.createTable(TABLE_NAME);
		sb.append(WIDTH + " INTEGER, ");
		sb.append(HEIGHT + " INTEGER, ");
		sb.append(NAME + " TEXT NOT NULL, ");
		sb.append(WEAK + " INTEGER, ");
		sb.append(STRONG_ENTITY_ID + "TEXT, ");
		return sb.toString();
	}
	
	@Override
	public void insert(Entity elem) throws DAOException {
		
	}
	
	public void insert(Entity elem, String idDiagram) throws DAOException {
		ContentValues values = new ContentValues();
		values.put(IDENTIFIER, elem.getIdentifier());
		values.put(ElementDAO.POSITION_X, elem.getCentralPoint().x);
		values.put(ElementDAO.POSITION_Y, elem.getCentralPoint().y);
		values.put(ElementDAO.EDITABLE, elem.isEditable() ? 1 : 0);
		values.put(ElementDAO.DIAGRAM_ID, idDiagram);
		values.put(WIDTH, elem.getWidth());
		values.put(HEIGHT, elem.getHeight());
		values.put(NAME, elem.getName().getText());
		values.put(WEAK, elem.isWeak() ? 1 : 0);
		
		db = handler.getWritableDatabase();
		if(db == null)
			throw new DAOException("insertEntity", "La base de datos es nula");
		
		long result = db.insert(TABLE_NAME, null, values);
		if(result==-1)
			throw new DAOException("insertEntity", "No se ha insertado ningún registro");
		log.debug("Insertada una entidad con _id: " + result);
		db.close();
	}

	@Override
	public void update(Entity elem) throws DAOException {
		ContentValues values = new ContentValues();
		values.put(ElementDAO.POSITION_X, elem.getCentralPoint().x);
		values.put(ElementDAO.POSITION_Y, elem.getCentralPoint().y);
		values.put(ElementDAO.EDITABLE, elem.isEditable() ? 1 : 0);
		values.put(WIDTH, elem.getWidth());
		values.put(HEIGHT, elem.getHeight());
		values.put(NAME, elem.getName().getText());
		values.put(WEAK, elem.isWeak() ? 1 : 0);
		
		db = handler.getWritableDatabase();
		if(db==null)
			throw new DAOException("updateEntity", "La base de datos es nula");
		
		long result = db.update(TABLE_NAME, values, IDENTIFIER + " LIKE ?", new String[]{elem.getIdentifier()});
		log.debug("Numero de registros actualizados para identificador " + elem.getIdentifier() + " -> " + String.valueOf(result));
		db.close();
	}

	@Override
	public Entity find(String identifier) throws DAOException {
		String where = IDENTIFIER + " LIKE ?";
		String[] args = new String[]{identifier};
		db = handler.getReadableDatabase();
		if(db == null)
			throw new DAOException("findDiagram", "La base de datos es nula");
		Entity entity = null;
		Cursor cursor = db.query(TABLE_NAME, columns, where, args, null, null, null);
//		if(cursor.moveToFirst()){
//			entity= new Entity(
//					cursor.getString(cursor.getColumnIndex(IDENTIFIER)), 
//					new Point(cursor.getInt(cursor.getColumnIndex(ElementDAO.POSITION_X)), cursor.getInt(cursor.getColumnIndex(ElementDAO.POSITION_Y))),
//					cursor.getString(cursor.getColumnIndex(NAME))
//					);
//			int editable = cursor.getInt(cursor.getColumnIndex(ElementDAO.EDITABLE));
//			if(editable == 0)
//				entity.setReadOnly();
//			else 
//				entity.setEditable();
//			entity.setWidth(cursor.getInt(cursor.getColumnIndex(WIDTH)));
//			entity.setHeight(cursor.getInt(cursor.getColumnIndex(HEIGHT)));
//			entity.setWeak((cursor.getInt(cursor.getColumnIndex(WEAK)) == 0) ? true : false);
//		}
		cursor.close();
		db.close();
		return entity;
	}

	@Override
	public List<Entity> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
