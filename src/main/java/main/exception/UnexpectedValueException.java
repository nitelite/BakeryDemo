package main.exception;

public class UnexpectedValueException extends RuntimeException{
	public UnexpectedValueException(String error) {
		super(error);
	}
}
