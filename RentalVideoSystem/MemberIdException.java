
public class MemberIdException extends Exception{
	public MemberIdException(String s) {
		super(s);
	}
	
	public MemberIdException() {
		super();
	}
	
	public MemberIdException(String s, Throwable c) {
		super(s, c);
	}
	
	public MemberIdException(Throwable c) {
		super(c);
	}
}
