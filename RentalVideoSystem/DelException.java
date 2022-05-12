
public class DelException extends Exception{
	public DelException(String s) {
		super(s);
	}
	
	public DelException() {
		super();
	}
	
	public DelException(String s, Throwable c) {
		super(s, c);
	}
	
	public DelException(Throwable c) {
		super(c);
	}
}
