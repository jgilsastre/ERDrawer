package es.test.jgilsastre.view;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.SurfaceHolder;


public class SurfaceThread extends Thread {

	private SurfaceHolder sh;
	private MainSurface view;
	private boolean run;
	
	/**
	 * Constructor
	 * @param SurfaceHolder
	 * @param El Surface propio
	 */
	public SurfaceThread(SurfaceHolder sh, MainSurface view) {
		this.sh = sh;
		this.view = view;
		run = false;
	}

	class Gesture extends GestureDetector.SimpleOnGestureListener{
		
	}
	/**
	 * Marca el hilo corriendo o no
	 * @param run
	 */
	public void setRunning(boolean run) {
		this.run = run;
	}

	/**
	 * Metodo run
	 */
	@SuppressLint("WrongCall")
	public void run() {
		//Instancia a canvas
		Canvas canvas;
		//Mientras la variable run sea true va a pintar contínuamente.
		while(run) {
			canvas = null;
			try {
				//definimos nulo el area en donde pintar
				canvas = sh.lockCanvas(null);
				//usamos synchronized para asegurarnos que no halla ningun otro thread usando ese objeto 
				synchronized(sh) {
					//Le decimos al surface view que ejecute el metodo onDraw y el cavas para dibujar
					view.onDraw(canvas);
				}
			} finally {
				/*En caso de que halla algun error liberamos el canvas 
				 * para no dejar el surfaceview en un estado inconsistente
				 */				
				if(canvas != null)
					//liberamos el canvas
					sh.unlockCanvasAndPost(canvas);
			}
		}
	}
		
}
