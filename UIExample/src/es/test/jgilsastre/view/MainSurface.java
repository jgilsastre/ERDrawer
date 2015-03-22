package es.test.jgilsastre.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import es.test.jgilsastre.activities.MainActivity;
import es.test.jgilsastre.exception.ERException;
import es.test.jgilsastre.main.Diagram;
import es.test.jgilsastre.main.ERItem;
import es.test.jgilsastre.main.Entity;
import es.test.jgilsastre.util.LoggerUtils;

public class MainSurface extends SurfaceView implements SurfaceHolder.Callback{

	private final int USER_ACTION_TOUCH = 1;
	private final int USER_ACTION_MOVE = 2;
//	private boolean editing = false;
	//Referencia a un thread usaremos para dibujar
	private SurfaceThread thread;
	//Atributos q indican las coord en donde se toco la pantalla
	private Point touchedPoint;
	private Point previousTouchedPoint;
	// variable q indica si se esta tocando la pantalla o no
	private boolean touched;
	private int userAction;
	private Diagram diagram;
//	private float scaleFactor;
//	private float scaleFocusX;
//	private float scaleFocusY;
//	private boolean scale;
//	private Element elementInEdition;
	private String selectedElementId;
//	private ScaleGestureDetector SGD;
	private GestureDetector gestureDetector;
	private MainActivityControl uiControl;
	private boolean firstTime;
	private Rect border;
	private Rect backgroundRect;
	private Paint borderPaint;
	private Paint backgroundPaint;
	private Paint gridPaint;
		
	public MainSurface(Context context, Diagram diagram) {
		super(context);
		this.firstTime = true;
		this.diagram = diagram;
		getHolder().addCallback(this);
//		SGD = new ScaleGestureDetector(context,new ScaleListener());
		gestureDetector = new GestureDetector(context, new ScrollListener());
//		scale = false;
//		elementInEdition = null;
		uiControl = new MainActivityControl((MainActivity)context);
		
		Entity ent = new Entity("entity", 0, new Point(150,150), "first and long word");
//      Attribute att = new Attribute("attribute", new Point(150, 400), "attribute","entity", "");
//      Union un = new Union("union", att.getCentralPoint(), att.getIdentifier(), ent.getCentralPoint(), ent.getIdentifier());
//      Relation rel = new Relation("relation", new Point(400, 150), "Relation");
		diagram.putElement(ent);
//      diagram.putElement(att);
//      diagram.putElement(un);
//      diagram.putElement(rel);
		borderPaint = new Paint();
		borderPaint.setColor(Color.BLACK);
		borderPaint.setStyle(Paint.Style.STROKE);
		borderPaint.setAntiAlias(true);
		borderPaint.setStrokeWidth(3);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.WHITE);
		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setAntiAlias(true);
		gridPaint = new Paint();
		gridPaint.setColor(Color.BLACK);
		gridPaint.setStyle(Paint.Style.STROKE);
		gridPaint.setAntiAlias(true);
		gridPaint.setStrokeWidth(2);
	}
	
//	public MainSurface(Context context){
//		this(context, new Diagram("", ""));
//	}

	@Override
	protected void onDraw(Canvas canvas) {

		// mitad de ancho
//		int totalWidth = canvas.getWidth();
//		int totalHeight = canvas.getHeight();
//		
//		Point maxPoint = new Point(canvas.getWidth(), canvas.getHeight());
		if(firstTime){
			diagram.setLimit(new Point(canvas.getWidth() * 2, canvas.getHeight() * 2));
			firstTime = false;
		}
		
		diagram.setDiagramCenter(canvas.getWidth() / 2, canvas.getHeight() / 2);
		// pinto fondo de blanco y el recuadro
		canvas.drawColor(Color.GRAY);
		
		
		if(border == null || backgroundRect == null){
			border = new Rect(0 , 0, canvas.getWidth(), canvas.getHeight());
			backgroundRect = new Rect(0, 0, diagram.getLimit().x, diagram.getLimit().y);
		}
		
		for(int i=100; i<diagram.getLimit().x;i+=100){
			canvas.drawLine(i, 0, i, diagram.getLimit().y, gridPaint);
		}
		for(int i=100; i<diagram.getLimit().y;i+=100){
			canvas.drawLine(0, i, diagram.getLimit().x, i, gridPaint);
		}
		canvas.drawRect(backgroundRect, backgroundPaint);
		canvas.drawRect(border, borderPaint);
		List<ERItem> elems = diagram.getERItems();
		
		for(ERItem e: elems)
			e.draw(canvas, diagram.getInitPosition());
		
		if(touched){
			switch (userAction){
			
			case USER_ACTION_TOUCH:
				selectedElementId = diagram.onTouch(touchedPoint);
				break;
			case USER_ACTION_MOVE:
				try {
					selectedElementId = diagram.onMove(touchedPoint, previousTouchedPoint);
				} catch (ERException e1) {
					
				}
				break;
			}
		}
//		if(editing)
//			selectedElementId = diagram.onLongPress(touchedPoint);
		
		previousTouchedPoint = touchedPoint;
		uiControl.setSelectedElement(selectedElementId);
		((MainActivity)getContext()).runOnUiThread(uiControl);
		
//			switch (userAction){
//			
//			case USER_ACTION_TOUCH:
//				for(Element e : diagram.getElements()){
//					
//				}
//				if(elementInEdition == null){
//					for(Element e: elementsList){
//						if(e.touched(touchedPoint, diagram.getInitPosition())){
//							e.standOut();
//							selectedElementId = e.getIdentifier();
////							((InitActivity)getContext()).runOnUiThread(new Runnable(){
////								public void run(){
////									((InitActivity)getContext()).manageRemoveButtonVisibility(true);
////								}
////							});
//						}else{
//							e.standIn();
//							selectedElementId = null;
//						}
//					}
//				}else{
//					if(!elementInEdition.touched(touchedPoint) && elementInEdition.touchedEditionPoint(touchedPoint, diagram.getInitPosition()) == Element.NO_POINT){
//						elementInEdition.finishEdition();
//						elementInEdition = null;
//					}
//				}
//				break;
//				
//			case USER_ACTION_MOVE:
//				if(elementInEdition == null){
//					boolean elementTouched = false;
					
//					for(Element e: elementsList){
//						if(e.touched(touchedPoint, diagram.getInitPosition())){
//							elementTouched = true;
//							e.standOut();
//							selectedElementId = e.getIdentifier();
//							e.move(touchedPoint, 0);
////							if(!(e instanceof Union)){
//							Union u = diagram.findUnion(e.getIdentifier());
//							if(u != null)
//								u.move(touchedPoint, e.getIdentifier());
////							}
//						}else{
//							e.standIn();
//							selectedElementId = null;
//						}
//					}
//					if(!elementTouched && previousTouchedPoint != null){
//						//Scrolling			
//						//Point newInitialPos = new Point(touchedPoint.x - previousTouchedPoint.x, touchedPoint.y - previousTouchedPoint.y);
//						Point newInitialPos = diagram.getInitPosition();
//						int offsetX = touchedPoint.x - previousTouchedPoint.x;
//						int offsetY = touchedPoint.y - previousTouchedPoint.y;
//						newInitialPos.x = newInitialPos.x + offsetX;
//						newInitialPos.y = newInitialPos.y + offsetY;
//						Log.e("Scroll", "Offsets = (" +  offsetX + ", " + offsetY + ")");
//						Log.e("Scroll", "Universo = (" +  diagram.getDiagramWidth() + ", " + diagram.getDiagramHeight() + ")");
//						Log.e("Scroll", "initialPos = (" + newInitialPos.x + ", " + newInitialPos.y + ")");
//						if(newInitialPos.x < 0){
//							newInitialPos.x = 0;
//						}
//						if(newInitialPos.x > diagram.getDiagramWidth()){
//							newInitialPos.x = diagram.getDiagramWidth();
//						}
//						if(newInitialPos.y < 0){
//							newInitialPos.y = 0;
//						}
//						if(newInitialPos.y > diagram.getDiagramHeight()){
//							newInitialPos.y = diagram.getDiagramHeight();
//						}
						
//						diagram.setInitPosition(newInitialPos);
						
//							for(ERItem el: elementsList){
//								el.move(touchedPoint.x - previousTouchedPoint.x, touchedPoint.y - previousTouchedPoint.y);
//							}
//					}
//					}else{
//						if(!elementInEdition.touched(touchedPoint) && elementInEdition.touchedEditionPoint(touchedPoint, diagram.getInitPosition()) == Element.NO_POINT){
//							elementInEdition.finishEdition();
//							elementInEdition = null;
//						}else{
//							int editionPointTouched = elementInEdition.touchedEditionPoint(touchedPoint, diagram.getInitPosition());
//							switch (editionPointTouched){
//							case Element.POINT_LEFT:
//								elementInEdition.edit(editionPointTouched, previousTouchedPoint.x - touchedPoint.x);
//								break;
//							case Element.POINT_RIGHT:
//								elementInEdition.edit(editionPointTouched, touchedPoint.x - previousTouchedPoint.x);
//								break;
//							case Element.POINT_UP:
//								elementInEdition.edit(editionPointTouched, previousTouchedPoint.y - touchedPoint.y);
//								break;
//							case Element.POINT_BOTTOM:
//								elementInEdition.edit(editionPointTouched, touchedPoint.y - previousTouchedPoint.y);
//								break;
//							}
//							if(editionPointTouched == Element.POINT_RIGHT || editionPointTouched == Element.POINT_LEFT){
//								elementInEdition.edit(editionPointTouched, previousTouchedPoint.x - touchedPoint.x);
//							}else{
//								elementInEdition.edit(editionPointTouched, previousTouchedPoint.y - touchedPoint.y);
//							}
//						}
						
//				}
//				break;
				
//			}
//		if(editing){
//			for(Element e: elementsList){
//				if(e.touched(touchedPoint)){
//					e.startEdition();
//					elementInEdition = e;
//				}
//			}
//			editing = false;
//		}
			
//		}
		
//		if(scale){
//			scale = false;
//			canvas.scale(scaleFactor, scaleFactor, scaleFocusX, scaleFocusY);
//		}
		
	}
	
//	class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//
//		@Override
//		public boolean onScale(ScaleGestureDetector detector) {
//			scaleFactor = detector.getScaleFactor();
//			scaleFocusX = detector.getFocusX();
//			scaleFocusY = detector.getFocusY();
//			return super.onScale(detector);
//		}
//
//		@Override
//		public boolean onScaleBegin(ScaleGestureDetector detector) {
//			scale = true;
//			return super.onScaleBegin(detector);
//		}
//
//		@Override
//		public void onScaleEnd(ScaleGestureDetector detector) {
//			scale = false;
//			super.onScaleEnd(detector);
//		}
//			
//	}
	
	class ScrollListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public void onLongPress(MotionEvent e) {
			Toast.makeText(getContext(), "onLongPress", 2).show();
			userAction = USER_ACTION_TOUCH;
			selectedElementId = diagram.onLongPress(touchedPoint);
			super.onLongPress(e);
		}
		
		

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		
		
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {

//		touched_x = (int) event.getX();
//		touched_y = (int) event.getY();
		touchedPoint = new Point((int) event.getX(), (int) event.getY());
		gestureDetector.onTouchEvent(event);
//		SGD.onTouchEvent(event);
		if(event.getPointerCount() > 1)
			Log.e("touched + 1", "Ha tocado mas de un dedo");

		Log.e("touched (X,Y)", "(" + touchedPoint.x + "," + touchedPoint.y + ")");

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:// Cuando se toca la pantalla
				Log.e("TouchEven ACTION_DOWN", "Se toco la pantalla ");
				touched = event.getPointerCount() == 1;
				userAction = USER_ACTION_TOUCH;
			break;
				
			case MotionEvent.ACTION_MOVE:// Cuando se desplaza el dedo por la pantalla
				touched = event.getPointerCount() == 1;
				Log.e("TouchEven ACTION_MOVE", "Nos desplazamos por la pantalla ");
				userAction = USER_ACTION_MOVE;
			break;
	
			case MotionEvent.ACTION_UP:// Cuando levantamos el dedo de la pantalla que estabamos tocando
				touched = false;
				Log.e("TouchEven ACTION_UP", "Ya no tocamos la pantalla");
			break;
	
			case MotionEvent.ACTION_CANCEL:
				touched = false;
				Log.e("TouchEven ACTION_CANCEL", " ");
			break;
	
			case MotionEvent.ACTION_OUTSIDE:
				Log.e("TouchEven ACTION_OUTSIDE", " ");
				touched = false;
			break;
	
			case MotionEvent.ACTION_SCROLL:
				Log.e("TouchEven ACTION_SCROLL", "Scrolling...");
			break;
				
			default:
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new SurfaceThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		Log.e("surfaceDestroyed ", "Hilo detenido ");

		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		
	}
	
	

}
