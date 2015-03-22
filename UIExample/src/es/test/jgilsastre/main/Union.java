package es.test.jgilsastre.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Union implements ERItem {

	public static final int INITIAL_POINT = 1;
	public static final int FINAL_POINT = 2;
	
	private String identifier;
	private Point initialPoint;
	private Point finalPoint;
	private Paint paint;
	private String initialElement;
	private String finalElement;
	
	public Union(String identifier, Point initialPoint, String initialElement, Point finalPoint, String finalElement) {
		super();
		this.identifier = identifier;
		this.initialPoint = initialPoint;
		this.finalPoint = finalPoint;
		this.initialElement = initialElement;
		this.finalElement = finalElement;
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setAntiAlias(true);
	}
	
	public String getInitialElement() {
		return initialElement;
	}

	public void setInitialElement(String initialElement) {
		this.initialElement = initialElement;
	}

	public String getFinalElement() {
		return finalElement;
	}

	public void setFinalElement(String finalElement) {
		this.finalElement = finalElement;
	}

	
	@Override
	public int move(Point newPoint, Point initPosition) {
//		switch (mode){
//		case INITIAL_POINT:
//			initialPoint = newPoint;
//			break;
//		case FINAL_POINT:
//			finalPoint = newPoint;
//			break;
//		}
		return Element.OK;
	}
	
	@Override
	public int move(int offsetX, int offsetY) {
//		initialPoint.set(initialPoint.x + offsetX, initialPoint.y + offsetY);
//		finalPoint.x = finalPoint.x + offsetX;
//		finalPoint.y = finalPoint.y + offsetY;
		return Element.OK;
	}
	
	
	public int move(Point newPoint, String elementId){
		if(initialElement.equals(elementId)){
			initialPoint = newPoint;
		}else if(finalElement.equals(elementId)){
			finalPoint = newPoint;
		}else{
			return Element.ERROR;
		}
		return Element.OK;
	}

	@Override
	public void standOut() {
		paint.setColor(ERItem.COLOR_HIGHLIGHTED);
	}

	@Override
	public void standIn() {
		paint.setColor(ERItem.COLOR_DEFAULT);
	}

	@Override
	public boolean touched(Point touchedPoint, Point initialPoint) {
		return false;
	}
	
	public Point getInitialPoint(){
		return initialPoint;
	}
	
	public Point getFinalPoint(){
		return finalPoint;
	}
	

	public Paint getPaint() {
		return paint;
	}

	@Override
	public void scale(float newScale) {
				
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void draw(Canvas canvas, Point initialPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
