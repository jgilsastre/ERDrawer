package es.test.jgilsastre.main;

import java.util.HashMap;
import java.util.Map;

import es.test.jgilsastre.parser.ParserConsts;
import es.test.jgilsastre.parser.ParserUtils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;

public class Text implements ERItem {

	/**
	 * Tamaño del texto por defecto
	 */
	public static final int DEFAULT_TEXT_SIZE = 20;
	
	/**
	 * Punto central desde donde se dibuja el texto
	 */
	private Point centralPoint;
	
	/**
	 * Valor del texto
	 */
	private String text;
	
	/**
	 * Objeto necesario para definir los parámetros de dibujo del texto
	 */
	private Paint paint;
	
	/**
	 * Tamaño del texto
	 */
	private int textSize;
	
	/**
	 * Constructor de un texto
	 * @param centralPoint: punto central desde el que se escribe el texto
	 * @param text : valor del texto
	 * @param textSize : tamaño del texto
	 */
	public Text(Point centralPoint, String text, int textSize) {
		super();
		this.text = text;
		this.textSize = textSize;
		this.centralPoint = centralPoint;
		
		paint = new Paint();
		paint.setColor(ERItem.COLOR_DEFAULT);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		paint.setTextAlign(Align.CENTER);
	}
	
	/**
	 * Constructor de un texto con el tamaño por defecto
	 * @param centralPoint: punto central desde el que se escribe el texto
	 * @param text : valor del texto
	 */
	public Text(Point centralPoint, String text) {
		this(centralPoint, text, DEFAULT_TEXT_SIZE);
	}

	@Override
	public int move(Point newPoint, Point initPosition) {
		centralPoint.set(newPoint.x + initPosition.x, newPoint.y + initPosition.y);
		return Element.OK;
	}
	
	@Override
	public int move(int offsetX, int offsetY) {
		centralPoint.set(centralPoint.x + offsetX, centralPoint.y + offsetY);
		return Element.OK;
	}
	
	/**
	 * Devuelve el valor del texto
	 * @return
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Devuelve la posicion del texto
	 * @return
	 */
	public Point getCentralPoint(){
		return centralPoint;
	}

	/**
	 * Modifica el valor del texto
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void standOut() {
		paint.setColor(ERItem.COLOR_HIGHLIGHTED);
		paint.setFakeBoldText(true);
	}

	@Override
	public void standIn() {
		paint.setColor(ERItem.COLOR_DEFAULT);
		paint.setFakeBoldText(false);
	}

	@Override
	public boolean touched(Point touchedPoint, Point initialPoint) {
		return false;
	}

	@Override
	public void scale(float newScale) {
		textSize = (int)((float)textSize * newScale);
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	public void draw(Canvas canvas, Point initialPoint) {
		Point relativePoint = new Point(getCentralPoint().x + initialPoint.x, getCentralPoint().y + initialPoint.y);
		canvas.drawText(text, relativePoint.x, relativePoint.y, paint);
	}

	@Override
	public String toXML() {
		StringBuilder xmlText = new StringBuilder();
		Map<String, String> tagAttributes = new HashMap<String, String>();
		tagAttributes.put(ParserConsts.TEXTSIZE, String.valueOf(this.textSize));
		xmlText.append(ParserUtils.simpleTag(ParserConsts.NAME, this.getText(), true, tagAttributes));
		return xmlText.toString();
	}
}
