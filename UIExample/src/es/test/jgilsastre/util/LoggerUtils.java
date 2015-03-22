package es.test.jgilsastre.util;

import android.util.Log;

public class LoggerUtils {

	public static final String PATTERN = "#c: #m";
	public static final String TAG = "ER_DROID";
	public static final String TAG_ERROR = "ER_DROID_ERROR";
	private final String className;
	
	public LoggerUtils (String className){
		this.className = className;
	}

	public void debug (String msg){
		Log.d(TAG, format(msg));
	}
	
	public void warn (String msg) {
		Log.w(TAG, format(msg));
	}
	
	public void error (String msg) {
		Log.e(TAG_ERROR, format(msg));
	}
	
	public void verbose (String msg) {
		Log.v(TAG, format(msg));
	}
	
	public void info (String msg) {
		Log.i(TAG, format(msg));
	}
	
	public void error (String msg, Throwable th){
		Log.e(TAG_ERROR, format(msg), th);
	}
	
	private String format (String msg) {
		if (msg==null){
			msg="";
		}
		String s = PATTERN;
		s = s.replace("#c", className);
		s = s.replace("#m", msg);
		return s;
	}
	
}
