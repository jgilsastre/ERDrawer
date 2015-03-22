package es.test.jgilsastre.exception;

public class ERException extends Exception {

	private int code;
	private String messageLog;
	private String literalId;
	
	public static final int ERROR_CODE_MISSING_STRONG_RELATION_ID = 200;
	
	/**
	 * @param code
	 * @param messageLog
	 */
	public ERException(int code, String messageLog) {
		super();
		this.code = code;
		this.messageLog = messageLog;
	}

	public String getMessageLog() {
		return messageLog;
	}

	public void setMessageLog(String messageLog) {
		this.messageLog = messageLog;
	}

	public String getLiteralId() {
		return literalId;
	}

	public void setLiteralId(String literalId) {
		this.literalId = literalId;
	}

	public int getCode() {
		return code;
	}
	
}
