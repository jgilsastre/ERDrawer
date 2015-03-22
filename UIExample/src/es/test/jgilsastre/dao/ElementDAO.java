package es.test.jgilsastre.dao;

public class ElementDAO {

	public static final String POSITION_X = "position_x";
	public static final String POSITION_Y = "position_y";
	public static final String EDITABLE = "editable";
	public static final String DIAGRAM_ID = "diagram_id";
		
	public static StringBuilder createTable(String tableName) {
		StringBuilder sb = new StringBuilder("CREATE TABLE ");
		sb.append(tableName + " (");
		sb.append(DAOBasic._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sb.append(DAOBasic.IDENTIFIER + " TEXT NOT NULL, ");
		sb.append(POSITION_X + " INTEGER NOT NULL, ");
		sb.append(POSITION_Y + " INTEGER NOT NULL, ");
		sb.append(EDITABLE + " INTEGER, ");
		sb.append(DIAGRAM_ID + " TEXT NOT NULL, ");
		return sb;
	}
	
}
