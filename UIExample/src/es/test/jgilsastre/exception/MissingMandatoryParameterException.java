package es.test.jgilsastre.exception;

public class MissingMandatoryParameterException extends ERException {

	public MissingMandatoryParameterException(int code, String messageLog) {
		super(code, messageLog);
	}

}
