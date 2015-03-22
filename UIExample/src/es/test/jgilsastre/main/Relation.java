package es.test.jgilsastre.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.test.jgilsastre.parser.ParserConsts;
import es.test.jgilsastre.parser.ParserUtils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Relation extends Element {

	public static final int DEFAULT_WIDTH = 200;
	public static final int DEFAULT_HEIGHT = 150;
	public static final int DEFAULT_COLOR = Color.BLACK;
	public static final int STROKE_WIDTH_DEF = 2;
	public static final int STROKE_WIDTH_HIGHLIGHTED = 5;
	
	private int width;
	private int height;
	private Text name;
	private List<RelationShip> relationships;
	private Paint linesPaint;
	private Paint shadowPaint;
	private Paint rectPaint;
	private Rect rect;
	private Point[] points;
	private List<String> attributeIds;
	
	/**
	 * Construye una relación con los parámetros recibidos
	 * @param identifier: Identificador
	 * @param initialPoint: Punto central de dibujo
	 * @param name: Nombre
	 * @param width: Anchura
	 * @param height: Altura
	 * @param attributeIds: lista de identificadores de los atributos
	 */
	public Relation(String identifier, Point initialPoint, String name, int width, int height, ArrayList<String> attributeIds){
		super(identifier, initialPoint);
		this.name = new Text(getTextPoint(getCentralPoint()), name);
		this.width = width;
		this.height = height;
		this.attributeIds = attributeIds;  
		this.points = new Point[4];
		
		linesPaint = new Paint();
		linesPaint.setColor(Color.BLACK);
		linesPaint.setStyle(Paint.Style.STROKE);
		linesPaint.setStrokeWidth(STROKE_WIDTH_DEF);
		linesPaint.setAntiAlias(true);
		
		shadowPaint = new Paint();
		shadowPaint.setColor(Color.BLACK);
		shadowPaint.setStyle(Paint.Style.STROKE);
		shadowPaint.setStrokeWidth(STROKE_WIDTH_HIGHLIGHTED);
		shadowPaint.setAntiAlias(true);
		
		rectPaint = new Paint();
		rectPaint.setColor(Color.WHITE);
		rectPaint.setStyle(Paint.Style.FILL);
		rectPaint.setAntiAlias(true);
		rectPaint.setAlpha(255);
	}
	
	/**
	 * Construye una relación con los parámetros recibidos
	 * @param identifier: Identificador 
	 * @param centralPoint: Punto central de dibujo
	 * @param name: Nombre
	 */
	public Relation(String identifier, Point centralPoint, String name) {
		this(identifier, centralPoint, name, DEFAULT_WIDTH, DEFAULT_HEIGHT, new ArrayList<String>());
	}
	
	public Relation(String identifier){
		this(identifier, new Point(0,0), "");
	}

//	@Override
//	public int move(Point newPoint, Point initPosition) {
//		if(isEditable()){
//			setPoint(newPoint);
//			name.move(getTextPoint(newPoint), mode);
//			return Element.OK;
//		}else{
//			return Element.READ_ONLY;
//		}
//	}

	@Override
	public int move(int offsetX, int offsetY) {
		setPoint(new Point(getCentralPoint().x + offsetX, getCentralPoint().y + offsetY));
		name.move(offsetX, offsetY);
		return Element.OK;
	}
	
	@Override
	public void standOut() {
		setHighLighted(true);
		linesPaint.setColor(Color.BLUE);
		linesPaint.setStrokeWidth(STROKE_WIDTH_HIGHLIGHTED);
		shadowPaint.setColor(Color.BLUE);
		shadowPaint.setStrokeWidth(STROKE_WIDTH_HIGHLIGHTED + 2);
		name.standOut();
	}

	@Override
	public void standIn() {
		setHighLighted(false);
		linesPaint.setColor(Color.BLACK);
		linesPaint.setStrokeWidth(STROKE_WIDTH_DEF);
		shadowPaint.setColor(Color.BLACK);
		shadowPaint.setStrokeWidth(STROKE_WIDTH_HIGHLIGHTED);
		name.standIn();
	}

	@Override
	public boolean touched(Point touchedPoint, Point initialPoint) {
		if(rect != null)
			return rect.contains(touchedPoint.x, touchedPoint.y);
		else
			return false;
	}

	@Override
	public void scale(float newScale) {
		width = (int)((float)width * newScale);
		height = (int)((float)height * newScale);
	}
	
	public void setProportion(int[] proportions){
		this.width = proportions[0];
		this.height = proportions[1];
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Text getName() {
		return name;
	}

	public void setName(String name) {
		this.name.setText(name);
	}
	
	public void addAttribute(String attributeId){
		attributeIds.add(attributeId);
	}
	
	public void removeAttribute(String attributeId){
		attributeIds.remove(attributeId);
	}
	
	public List<String> getAttributeIds(){
		return attributeIds;
	}
	
	public List<RelationShip> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<RelationShip> relationships) {
		this.relationships = relationships;
	}

	public Paint getLinesPaint() {
		return linesPaint;
	}

	public Paint getShadowPaint() {
		return shadowPaint;
	}
	
	public Paint getRectPaint(){
		return rectPaint;
	}
	
	public void setName(Text name) {
		this.name = name;
	}

	public void setAttributeIds(List<String> attributeIds) {
		this.attributeIds = attributeIds;
	}	

	public Point getTextPoint(Point point){
		return new Point(point.x ,point.y + (height / 10));
	}
	
	public Point[] getPoints(){
		points[POINT_UP] = new Point(getCentralPoint().x, getCentralPoint().y - (height / 2));
		points[POINT_LEFT] = new Point(getCentralPoint().x - (width / 2), getCentralPoint().y);
		points[POINT_RIGHT] = new Point(getCentralPoint().x + (width / 2), getCentralPoint().y);
		points[POINT_BOTTOM] = new Point(getCentralPoint().x, getCentralPoint().y + (height / 2));
		return points;
	}
	
	public Rect getRect(){
		if(rect == null)
			rect = new Rect(getCentralPoint().x - (width / 2)
					, getCentralPoint().y - (height / 2)
					, getCentralPoint().x + (width / 2)
					, getCentralPoint().y + (height / 2));
		else
			rect.set(getCentralPoint().x - (width / 2)
					, getCentralPoint().y - (height / 2)
					, getCentralPoint().x + (width / 2)
					, getCentralPoint().y + (height / 2));
		return rect;
	}

	@Override
	public void draw(Canvas canvas, Point initialPoint) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		return getIdentifier() + ELEMENT_ID_NAME_SEPARATOR + getName().getText();
	}

	@Override
	public String toXML() {
		StringBuilder xmlRel = new StringBuilder();
		Map<String, String> tagAttributes = new HashMap<String, String>();
		tagAttributes.put(ParserConsts.ID, this.getIdentifier());
		xmlRel.append(ParserUtils.openTag(ParserConsts.RELATION, tagAttributes)); //Tag de inicio de la entidad
		tagAttributes.clear();
		tagAttributes.put(ParserConsts.POINT_X, String.valueOf(this.getCentralPoint().x));
		tagAttributes.put(ParserConsts.POINT_Y, String.valueOf(this.getCentralPoint().y));
		xmlRel.append(ParserUtils.simpleTag(ParserConsts.POINT, null, false, tagAttributes));
		xmlRel.append(this.getName().toXML()); //Tag del nombre
		tagAttributes.clear();
		tagAttributes.put(ParserConsts.WIDTH, String.valueOf(this.getWidth()));
		tagAttributes.put(ParserConsts.HEIGHT, String.valueOf(this.getHeight()));
		xmlRel.append(ParserUtils.simpleTag(ParserConsts.PROPORTION, null, false, tagAttributes));
		if(relationships.size() > 0){
			xmlRel.append(ParserUtils.openTag(ParserConsts.RELATED_ENTITIES, null));
			for(RelationShip r : relationships){
				tagAttributes.clear();
				tagAttributes.put(ParserConsts.CARDINALITY, r.getCardinality());
				xmlRel.append(ParserUtils.simpleTag(ParserConsts.ENTITY_ID, r.getEntity(), true, tagAttributes));
			}
			xmlRel.append(ParserUtils.closeTag(ParserConsts.RELATED_ENTITIES));
		}
		if(attributeIds.size() > 0){
			xmlRel.append(ParserUtils.openTag(ParserConsts.ATTRIBUTES, null));
			xmlRel.append(ParserConsts.ATTRIBUTES_FLAG);
			xmlRel.append(ParserUtils.closeTag(ParserConsts.ATTRIBUTES));
		}
		xmlRel.append(ParserUtils.closeTag(ParserConsts.RELATION));
		return xmlRel.toString();
	}
	
}
