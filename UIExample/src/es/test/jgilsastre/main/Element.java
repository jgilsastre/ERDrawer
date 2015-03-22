package es.test.jgilsastre.main;

import es.test.jgilsastre.exception.ERException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class Element implements ERItem{

	public static final String ELEMENT_ID_NAME_SEPARATOR = "::";
	public static final String ELEMENT_SEPARATOR = "##";
	
	/**
	 * Identificador del elemento
	 */
	private String identifier;
	/**
	 * Posicion del elemento
	 */
	private Point centralPoint;
	/**
	 * Flag de permiso de edicion
	 */
	private boolean editable;
	/**
	 * Flag de resaltado del elemento
	 */
	private boolean highlighted;
	/**
	 * Flag de modo edicion
	 */
	private boolean inEdition;
	
	/**
	 * Atributos asociados al modo edición
	 */
	private Rect editionRect;
	private Point[] editPoints;
	private RectF[] editRects;
	private RectF[] touchListenerEditPoints;
	private Paint editPointsPaint;
	private Paint editPaint;
	
	private Point[] corners;
	/**
	 * Anchura del elemento
	 */
	private int width;
	/**
	 * Altura del elemento
	 */
	private int height;
	/**
	 * Texto asociado al elemento
	 */
	private Text name;
	
	/**
	 * Parámetros del dibujado 
	 */
	private Paint shadowPaint;
	private Paint intPaint;
		
	/**
	 * Nuevo elemento
	 * @param identifier		Identificador
	 * @param centralPoint		Posición
	 * @param width				Anchura
	 * @param height			Altura
	 * @param text				Texto del elemento
	 */
	public Element(String identifier, Point centralPoint, int width, int height, String text){
		super();
		this.identifier = identifier;
		this.centralPoint = centralPoint;
		this.editable = true;
		this.highlighted = false;
		this.inEdition = false;
		this.width = width;
		this.height = height;
		this.name = new Text(getTextPoint(centralPoint), text);
		
		editionRect = null;
		editPoints = null;
		editRects = null;
	}
	
	/**
	 * Crea un nuevo elemento sin texto, y con las proporciones por defecto en la posición recibida
	 * @param identifier	Identificador
	 * @param centralPoint 	Punto central
	 */
	public Element(String identifier, Point centralPoint) {
		this(identifier, centralPoint, ERItem.WIDTH_DEFAULT, ERItem.HEIGHT_DEFAULT, "");
	}
	
	/**
	 * Crea un nuevo elemento en la posición de inicio por defecto
	 * @param identifier	Identificador
	 * @param text			Texto del elemento
	 */
	public Element(String identifier, String text){
		this(identifier, new Point(ERItem.WIDTH_DEFAULT + 100, ERItem.HEIGHT_DEFAULT + 100), ERItem.WIDTH_DEFAULT, ERItem.HEIGHT_DEFAULT, text);
	}
	
	/**
	 * Devuelve la posición del elemento
	 * @return
	 */
	public Point getCentralPoint() {
		return centralPoint;
	}

	/**
	 * Modifica la posicion del elemento
	 * @param newPoint nueva posicion
	 */
	public void setPoint(Point newPoint) {
		this.centralPoint = newPoint;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Marca el elemento como no editable
	 */
	public void setReadOnly(){
		this.editable = false;
	}
	
	/**
	 * Marca el elemento como editable
	 */
	public void setEditable(){
		this.editable = true;
	}
	
	/**
	 * Devuelve si el elemento es editable
	 * @return cierto para editable, falso para bloqueado
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * Marca el elemento como resaltado o no
	 * @param highLight cierto para marcar como resaltado, falso para el caso contrario
	 */
	public void setHighLighted(boolean highLight){
		this.highlighted = highLight;
	}
	
	/**
	 * Devuelve si el elemento se encuentra resaltado
	 * @return cierto para resaltado, falso en caso contrario
	 */
	public boolean isHighLighted(){
		return highlighted;
	}
	
	/**
	 * Devuelve si el elemento se encuentra en modo edición
	 * @return cierto para modo edición, falso en caso contrario
	 */
	public boolean inEdition() {
		return inEdition;
	}

	@Override
	public int move(Point newPoint, Point initPosition) {
		if(isEditable()){
			setPoint(newPoint);
			name.move(getTextPoint(newPoint), initPosition);
			return Element.OK;
		}else{
			return Element.READ_ONLY;
		}
	}
	
	@Override
	public int move(int offsetX, int offsetY) {
		centralPoint.set(getCentralPoint().x + offsetX, getCentralPoint().y + offsetY);
		name.move(offsetX, offsetY);
		return Element.OK;
	}
	
	@Override
	public void scale(float newScale) {
		width = (int)((float)width * newScale);
		height = (int)((float)height * newScale);
	}

	@Override
	public void standOut() {
		setHighLighted(true);
		shadowPaint = getShadowPaint();
		shadowPaint.setColor(ERItem.COLOR_HIGHLIGHTED);
		shadowPaint.setStrokeWidth(STROKE_WIDTH_HIGHLIGHTED);
		name.standOut();
	}

	@Override
	public void standIn() {
		setHighLighted(false);
		shadowPaint = getShadowPaint();
		shadowPaint.setColor(ERItem.COLOR_DEFAULT);
		shadowPaint.setStrokeWidth(STROKE_WIDTH_DEF);
		name.standIn();
	}

	public abstract boolean touched(Point touchedPoint, Point initialPoint);

	protected abstract Point getTextPoint(Point p);
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Element)
			return ((Element)o).equals(identifier);
		else
			return false;
	}
	
	/**
	 * Inicia un elemento en modo edición
	 */
	public void startEdition(){
		this.inEdition = true;
	}
	
	/**
	 * Finaliza el modo edición del elemento
	 */
	public void finishEdition(){
		this.inEdition = false;
	}
	
	/**
	 * Comprobación de si un elemento está en modo edición
	 * @return cierto para modo edición, falso en caso contrario
	 */
	public boolean isInEdition(){
		return this.inEdition;
	}

	/**
	 * Devuelve la anchura del elemento
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Modifica la anchura de un elemento
	 * @param width nueva anchura del elemento
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Devuelve la altura del elemento
	 * @return altura del elemento
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Modifica la altura del elemento
	 * @param height nueva altura del elemento
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Devuelve el nombre del elemento
	 * @return Text asociado al elemento
	 */
	public Text getName() {
		return name;
	}
	
	/**
	 * Modifica el valor del nombre del elemento
	 * @param name nuevo nombre del elemento
	 */
	public void setName(String name){
		this.name.setText(name);
	}

	/**
	 * Modifica las proporciones del elemento
	 * @param proportions [0] se corresponde con la anchura y [1] se corresponde con la altura
	 */
	public void setProportion(int[] proportions) throws ERException{
		if(proportions != null && proportions.length == 2){
			this.width = proportions[0];
			this.height = proportions[1];
		}else{
			//TODO: Añadir código de excepción
			throw new ERException(1, "");
		}
	}
	
	public void edit(int pointEdition, int offset){
		if(pointEdition == POINT_UP || pointEdition == POINT_BOTTOM)
			height = height + offset;
		else
			width = width + offset;
	}
	
	public void edit(Point touched, Point previousTouched, Point initialPoint){
		if(touched(touched, initialPoint) && touchedEditionPoint(touched, initialPoint) == Element.NO_POINT){
			finishEdition();
		}else if(isInEdition()){
			int editionPointTouched = touchedEditionPoint(touched, initialPoint);
			switch (editionPointTouched){
				case Element.POINT_LEFT:
					width = width + previousTouched.x - touched.x;
					break;
				case Element.POINT_RIGHT:
					width = width + touched.x - previousTouched.x;
					break;
				case Element.POINT_UP:
					height = height + previousTouched.y - touched.y;
					break;
				case Element.POINT_BOTTOM:
					height = height + touched.y - previousTouched.y;
					break;
			}
		}
	}
	
	Point getRelativePoint(Point point, Point initialPoint){
		return new Point(point.x + initialPoint.x, point.y + initialPoint.y);
	}
	
	Paint getShadowPaint() {
		if(shadowPaint == null){
			shadowPaint = new Paint();
			shadowPaint.setColor(ERItem.COLOR_DEFAULT);
			shadowPaint.setStyle(Paint.Style.STROKE);
			shadowPaint.setStrokeWidth(STROKE_WIDTH_DEF);
			shadowPaint.setAntiAlias(true);
		}
		return shadowPaint;
	}

	Paint getIntPaint() {
		if(intPaint == null){
			intPaint = new Paint();
			intPaint.setColor(Color.WHITE);
			intPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			intPaint.setAntiAlias(true);
		}
		return intPaint;
	}
	
	private Rect getEditionRect(Point initialPoint) {
		Point relativePoint = getRelativePoint(getCentralPoint(), initialPoint);
		if(editionRect == null)
			editionRect = new Rect(relativePoint.x - (width / 2) - 2
					, relativePoint.y - (height / 2) - 2
					, relativePoint.x + (width / 2) - 2
					, relativePoint.y + (height / 2) - 2);
		else
			editionRect.set(relativePoint.x - (width / 2) - 2
					, relativePoint.y - (height / 2) - 2
					, relativePoint.x + (width / 2) - 2
					, relativePoint.y + (height / 2) - 2);		
		return editionRect;
	}
	
	public Point[] getCornersAfterMove(Point touchPoint, Point initialPoint){
		Point p = getRelativePoint(touchPoint, initialPoint);
		if(corners == null){
			corners = new Point[2];
			corners[CORNER_UP_LEFT] = new Point(p.x - (width /2), p.y - (height / 2));
			corners[CORNER_BOTTOM_RIGHT] = new Point(p.x + (width /2), p.y + (height / 2));
		}else{
			corners[CORNER_UP_LEFT].set(p.x - (width /2), p.y - (height / 2));
			corners[CORNER_BOTTOM_RIGHT].set(p.x + (width /2), p.y + (height / 2));
		}
		return corners;
		
	}
	
	private Point[] getEditPoints(Point initialPoint) {
		Point relativePoint = getRelativePoint(getCentralPoint(), initialPoint);
		if(editPoints == null){
			editPoints = new Point[4];
			editPoints[POINT_UP] = new Point(relativePoint.x, relativePoint.y - (height / 2));
			editPoints[POINT_LEFT] = new Point(relativePoint.x - (width / 2), relativePoint.y);
			editPoints[POINT_RIGHT] = new Point(relativePoint.x + (width / 2), relativePoint.y);
			editPoints[POINT_BOTTOM] = new Point(relativePoint.x, relativePoint.y + (height / 2));
		}else{
			editPoints[POINT_UP].set(relativePoint.x, relativePoint.y - (height / 2));
			editPoints[POINT_LEFT].set(relativePoint.x - (width / 2), relativePoint.y);
			editPoints[POINT_RIGHT].set(relativePoint.x + (width / 2), relativePoint.y);
			editPoints[POINT_BOTTOM].set(relativePoint.x, relativePoint.y + (height / 2));
		}
		return editPoints;
	}

	private RectF[] getEditRects(Point initialPoint) {
		Point p;
		if(editRects == null){
			editRects = new RectF[4];
			for(int i=POINT_UP;i<=POINT_BOTTOM;i++){
				p = getEditPoints(initialPoint)[i];
				editRects[i] = new RectF(p.x - EDITING_POINT_RADIUS, p.y - EDITING_POINT_RADIUS, p.x + EDITING_POINT_RADIUS, p.y + EDITING_POINT_RADIUS);
			}
		}else{
			for(int i=POINT_UP;i<=POINT_BOTTOM;i++){
				p = getEditPoints(initialPoint)[i];
				editRects[i].set(p.x - EDITING_POINT_RADIUS, p.y - EDITING_POINT_RADIUS, p.x + EDITING_POINT_RADIUS, p.y + EDITING_POINT_RADIUS);
			}
		}
		return editRects;
	}

	private RectF[] getTouchListenerEditPoints(Point initialPoint){
		Point p;
		if(touchListenerEditPoints == null){
			touchListenerEditPoints = new RectF[4];
			for(int i=POINT_UP;i<=POINT_BOTTOM;i++){
				p = getEditPoints(initialPoint)[i];
				touchListenerEditPoints[i] = new RectF(p.x - EDITING_POINT_TOUCH_LISTENER_RADIUS
						, p.y - EDITING_POINT_TOUCH_LISTENER_RADIUS
						, p.x + EDITING_POINT_TOUCH_LISTENER_RADIUS
						, p.y + EDITING_POINT_TOUCH_LISTENER_RADIUS);
			}
		}else{
			for(int i=POINT_UP;i<=POINT_BOTTOM;i++){
				p = getEditPoints(initialPoint)[i];
				touchListenerEditPoints[i].set(p.x - EDITING_POINT_TOUCH_LISTENER_RADIUS
						, p.y - EDITING_POINT_TOUCH_LISTENER_RADIUS
						, p.x + EDITING_POINT_TOUCH_LISTENER_RADIUS
						, p.y + EDITING_POINT_TOUCH_LISTENER_RADIUS);
			}
		}
		return touchListenerEditPoints;
	}
	
	public int touchedEditionPoint(Point p, Point initialPoint){
		RectF[] e = getTouchListenerEditPoints(initialPoint);
		for(int i=POINT_UP;i<=POINT_BOTTOM;i++){
			if(e[i].contains(p.x, p.y)){
				return i;
			}
		}
		return NO_POINT;
	}
	
	private Paint getEditPointsPaint() {
		if(editPointsPaint == null){
			editPointsPaint = new Paint();
			editPointsPaint.setColor(ERItem.COLOR_HIGHLIGHTED);
			editPointsPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			editPointsPaint.setAntiAlias(true);
		}
		return editPointsPaint;
	}

	private Paint getEditPaint() {
		if(editPaint == null){
			editPaint = new Paint();
			editPaint.setColor(ERItem.COLOR_HIGHLIGHTED);
			editPaint.setStyle(Paint.Style.STROKE);
			editPaint.setAntiAlias(true);
		}
		return editPaint;
	}
	
	/**
	 * Dibuja los cuatro puntos de edición de un elemento
	 * @param canvas
	 * @param initialPoint
	 */
	void drawEditionPoints(Canvas canvas, Point initialPoint){
		canvas.drawRect(getEditionRect(initialPoint), getEditPaint());
		for(int i = Element.POINT_UP; i<= Element.POINT_BOTTOM; i++){
			canvas.drawOval(getEditRects(initialPoint)[i], getEditPointsPaint());
		}
	}
	
	@Override
	public abstract void draw(Canvas canvas, Point initialPoint);
		
}
