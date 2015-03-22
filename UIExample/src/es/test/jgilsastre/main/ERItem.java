package es.test.jgilsastre.main;

import es.test.jgilsastre.exception.ERException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

/**
 * Define los métodos que necesita implementar una clase que representa elementos en el diagrama
 * @author Jorge Gil Sastre
 * @version 0.1
 */
public interface ERItem {

	public static final int WIDTH_DEFAULT = 200;
	public static final int HEIGHT_DEFAULT = 80;
	
	public static final int COLOR_DEFAULT = Color.BLACK;
	public static final int COLOR_HIGHLIGHTED = Color.BLUE;
	
	public static final int STROKE_WIDTH_DEF = 5;
	public static final int STROKE_WIDTH_HIGHLIGHTED = 7;
	
	public static final int POINT_UP = 0;
	public static final int POINT_LEFT = 1;
	public static final int POINT_RIGHT = 2;
	public static final int POINT_BOTTOM = 3;
	public static final int NO_POINT = -1;
	public static final int EDITING_POINT_RADIUS = 16;
	public static final int EDITING_POINT_TOUCH_LISTENER_RADIUS = 32;
	
	public static final int CORNER_UP_LEFT = 0;
	public static final int CORNER_BOTTOM_RIGHT = 1;
	
	public static final int OUT_OF_RANGE = -1;
	public static final int ERROR = -2;
	public static final int OK = 1;
	public static final int READ_ONLY = -3;
	
	/**
	 * Devuelve el Identificador del elemento
	 * @return
	 */
	public String getIdentifier();
	
	/**
	 * El elemento se mueve al punto indicado en el modo indicado
	 * @param newPoint: nueva posición del elemento
	 * @param mode: modo de movimiento
	 * @return el código de éxito o fracaso del movimiento
	 */
	public int move(Point newPoint, Point initPosition) throws ERException;
	
	/**
	 * El elemento se mueve con los offset indicados
	 * @param offsetX: offset del eje x
	 * @param offsetY: offset del eje y
	 * @return código de éxito o fracaso del movimiento
	 */
	public int move(int offsetX, int offsetY) throws ERException;
	
	/**
	 * Resaltar un elemento
	 */
	public void standOut();
	
	/**
	 * Quitar resaltado
	 */
	public void standIn();
	
	/**
	 * Recibe un punto y responde si el elemento ha sido tocado o no (contiene ese punto)
	 * @param touchedPoint: punto a comprobar
	 * @return cierto cuando ha tocado y falso en caso contrario
	 */
	public boolean touched(Point touchedPoint, Point initialPoint);
	
	/**
	 * Modifica el tamaño del elemento
	 * @param newScale: nueva escala
	 */
	public void scale(float newScale);
	
	/**
	 * Dibuja el elemento
	 * @param canvas: canvas en el que se debe dibujar
	 * @param initialPoint: punto relativo al universo de la vista local
	 */
	public void draw(Canvas canvas, Point initialPoint);
	
	public String toXML();
	
}
